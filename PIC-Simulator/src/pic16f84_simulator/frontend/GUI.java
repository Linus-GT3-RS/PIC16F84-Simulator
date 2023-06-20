package pic16f84_simulator.frontend;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.Dimension;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ScrollPaneConstants;
import javax.swing.SwingConstants;
import javax.swing.border.BevelBorder;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.TP;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.collections.PinSelector;
import pic16f84_simulator.frontend.controller.ButtonInteraction;
import pic16f84_simulator.frontend.pm.TestprogrammViewer;
import pic16f84_simulator.frontend.ram.Ram_gui;

public class GUI extends JFrame {

    public static boolean modus = false; // for testing is default false, otherwise issues

    public static JMenuBar menuBar = new JMenuBar();
    public static JPanel contentPane = new JPanel();

    /*
     * >>>>> pannel_collection
     */
    public static JPanel panel_collection = new JPanel();
    public static JTable stack_table;
    public static JTable table_w;
    public static JTable table_prs;
    public static JLabel programmtime;

    /*
     * >>>>> pannel_pm
     */
    public static JPanel testprogrammPanel = new JPanel(new BorderLayout());
    public static JTable table;
    public static JTable table_grp;
    public static JTable table_fsr;

    /**
     * >>>>> panel_controller
     */
    public static JButton btn_run;
    public static JButton btn_stop;
    public static JButton btn_restart;
    public static JButton btn_next;
    public static JButton btn_ignore;



    /**
     * Launch the application
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
                    frame.modus = true;
                    frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * Create the frame.
     */
    public GUI() {
        setVisible(true);
        setTitle("PIC16F84 Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(0, 0, 1920, 1080);
        setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH); // Important DO_NOT_DELETE !!!
        // forBackUp: setExtendedState(getExtendedState() | JFrame.MAXIMIZED_BOTH); // Important DO_NOT_DELETE !!!



        // --------------------------------------- Menubar ------------------------------------------------------------------------------------

        setJMenuBar(menuBar);
        setUpMenuBar();

        // +++++++++++++++++++++++++++++++++++++++++++++++++++++++++ Content Pane +++++++++++++++++++++++++++++++++++++++++++++++++++++++++
        contentPane = new JPanel();
        contentPane.setForeground(new Color(192, 192, 192));
        contentPane.setBackground(new Color(54, 54, 54));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(4, 4));

        /*
         *  >>>>>>>>>>>>> panel_collection
         */

        panel_collection.setPreferredSize(new Dimension(100, 10));
        contentPane.add(panel_collection, BorderLayout.CENTER);
        panel_collection.setLayout(null);

        JPanel panel_register = new JPanel();
        panel_register.setBackground(new Color(17, 135, 185));
        panel_register.setBounds(0, 0, 481, 248);
        panel_collection.add(panel_register);


        DefaultTableModel registermodel = new DefaultTableModel(loadData(),new String[] {"","","","","","","","",""});
        JTable table_register = new JTable(registermodel) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are now editable
            }
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                return renderer;
            } 
        };
        table_register.setFont(new Font("Arial", Font.PLAIN, 8));
        for(int i = 0; i < table_register.getColumnCount();i++) {
            TableColumn column = table_register.getColumnModel().getColumn(i);
            if(i == 0) {
                column.setPreferredWidth(25);
            }else {
                column.setPreferredWidth(12);
            }
        }
        table_register.setEnabled(false);
        DefaultTableModel wReg_model = new DefaultTableModel(new Object[][] {{"W-Reg",Utils.binaryToHex(MC.alu.wReg.read())}},new String[] {"",""});
        table_w = new JTable(wReg_model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are now editable
            }
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                return renderer;
            } 
        };
        table_w.setBounds(350, 180, 100, 15);
        table_w.setBackground(new Color(17, 135, 185));
        table_w.setShowGrid(false);
        DefaultTableModel prs_model = new DefaultTableModel(new Object[][] {{"VT (T)",MC.prescaler.getPRS(0)},{"VT (W)",MC.prescaler.getPRS(1)}},new String[] {"",""}  );
        table_prs = new JTable(prs_model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are now editable
            }
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                return renderer;
            }
        };


        table_prs.getColumnModel().getColumn(0).setPreferredWidth(0);
        table_prs.getColumnModel().getColumn(1).setPreferredWidth(5);
        table_prs.setBounds(350,200,100,37);
        table_prs.setBackground(new Color(17, 135, 185));
        table_prs.setShowGrid(false);
        table_prs.setRowSelectionAllowed(false);
        panel_register.add(table_w);
        panel_register.add(table_prs);
        table_register.setRowSelectionAllowed(false);
        table_register.setBounds(10,100, 332, 48);;
        panel_register.add(table_register);

        JPanel labelPanel = new JPanel(new BorderLayout());
        labelPanel.setBounds(350, 30, 100, 150);

        JLabel Stacklabel = new JLabel("Stack");
        labelPanel.setBackground(new Color(17, 135, 185));
        Stacklabel.setHorizontalAlignment(SwingConstants.CENTER);
        labelPanel.add(Stacklabel,BorderLayout.NORTH); 

        panel_register.setBorder(BorderFactory.createEmptyBorder(0, 100, 65, 100)); // Padding
        DefaultTableModel model = new DefaultTableModel(MC.stack.loadStack(), new String[] {"        ", "        ", "        "});
        stack_table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are now editable
            }
            @Override
            public TableCellRenderer getCellRenderer(int row, int column) {
                DefaultTableCellRenderer renderer = new DefaultTableCellRenderer();
                renderer.setHorizontalAlignment(SwingConstants.CENTER);
                return renderer;
            }        
        };
        stack_table.setBounds(100, 0, 281, 183);
        labelPanel.add(stack_table,BorderLayout.CENTER);
        panel_register.add(labelPanel);
        stack_table.setShowGrid(false);
        stack_table.setBackground(new Color(17, 135, 185));
        stack_table.setBorder(BorderFactory.createLineBorder(Color.black, 2));

        JPanel panel_pins = new JPanel();
        panel_pins.setBounds(0, 296, 481, 215);
        panel_pins.setBackground(Color.WHITE);
        panel_collection.add(panel_pins);

        ImageIcon icon = new ImageIcon("Pic.png");
        Image image = icon.getImage().getScaledInstance(470, 215, Image.SCALE_SMOOTH);

        JLabel Pic_Image = new JLabel(new ImageIcon(image));

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.gridx = 0;
        gbc.gridy = 0;
        gbc.anchor = GridBagConstraints.CENTER;
        panel_pins.add(Pic_Image);
        PinSelector.Pin_Table();

        JPanel panel_programmtimer = new JPanel();
        panel_programmtimer.setBackground(new Color(166, 222, 247));
        panel_programmtimer.setBounds(0, 558, 481, 222);
        panel_programmtimer.setLayout(null);
        programmtime = new JLabel("0 µs");
        programmtime.setBounds(201, 5, 80, 19);
        programmtime.setFont(new Font("Arial",Font.PLAIN,16));
        programmtime.setHorizontalAlignment(SwingConstants.CENTER);
        JButton timer_button = new JButton();
        timer_button.setText("Laufzeit zurücksetzen");
        timer_button.setBounds(155, 30, 170, 24);
        timer_button.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                ButtonInteraction.timer = 0;
                programmtime.setText("0 µs");
            }
        });
       
        panel_programmtimer.add(timer_button);
        
        
        panel_programmtimer.add(programmtime);
        panel_collection.add(panel_programmtimer);

        JButton btn_resetRuntimeCounter = new JButton("Reset Runtime Counter");
        btn_resetRuntimeCounter.setBounds(62, 6, 139, 21);
        panel_programmtimer.add(btn_resetRuntimeCounter);

        JButton btn_toggleWDog = new JButton("Toggle WDog");
        btn_toggleWDog.setBounds(160, 66, 160, 21);
        panel_programmtimer.add(btn_toggleWDog);
        panel_register.setLayout(null);



        /*
         * >>>>>>>>>>>> Panel_Controller
         */

        JPanel panel_controller = new JPanel();
        panel_controller.setPreferredSize(new Dimension(10, 125));
        panel_controller.setBackground(new Color(218, 112, 214));
        contentPane.add(panel_controller, BorderLayout.SOUTH);
        panel_controller.setLayout(null);

        btn_restart = new JButton("Restart");
        btn_restart.setBorderPainted(false);
        btn_restart.setBackground(new Color(0, 255, 0));
        btn_restart.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(TestprogrammViewer.loaded) {
                    ButtonInteraction.button_restart();
                }
            }
        });
        btn_restart.setBounds(386, 71, 148, 27);
        btn_restart.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_controller.add(btn_restart);

        btn_next = new JButton("Next");
        btn_next.setBorderPainted(false);
        btn_next.setBorder(null);
        btn_next.setBackground(new Color(0, 255, 0));
        btn_next.setEnabled(true);
        btn_next.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(TestprogrammViewer.loaded) {
                    ButtonInteraction.button_next();
                }
            }
        });
        btn_next.setBounds(911, 52, 114, 27);
        btn_next.setFont(new Font("Arial", Font.BOLD, 16));
        panel_controller.add(btn_next);

        btn_stop = new JButton("Stop");
        btn_stop.setBorderPainted(false);
        btn_stop.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(TestprogrammViewer.loaded) {
                    ButtonInteraction.button_stop();
                }
            }
        });
        btn_stop.setBounds(558, 54, 108, 23);
        btn_stop.setBackground(new Color(0, 255, 0));
        btn_stop.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_controller.add(btn_stop);

        btn_run = new JButton("RUN");
        btn_run.setBorder(null);
        btn_run.setBackground(new Color(0, 255, 0));
        btn_run.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(TestprogrammViewer.loaded) {
                    try {
                        ButtonInteraction.button_run();
                    } catch (InterruptedException e1) {
                        e1.printStackTrace();
                    }
                }
            }
        });
        btn_run.setBounds(702, 36, 156, 27);
        btn_run.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_controller.add(btn_run);

        btn_ignore = new JButton("Ignore");
        btn_ignore.setBorderPainted(false);
        btn_ignore.setBackground(new Color(0, 255, 0));
        btn_ignore.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(TestprogrammViewer.loaded) {
                    ButtonInteraction.button_ignore();
                }
            }
        });
        btn_ignore.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btn_ignore.setBounds(1050, 76, 85, 21);
        panel_controller.add(btn_ignore);



        /*
         * >>>>>>>>>>>>>> panel_ram
         */
        JPanel panel_ram = new JPanel();
        panel_ram.setBorder(new EmptyBorder(8, 0, 13, 0));
        panel_ram.setPreferredSize(new Dimension(520, 10));
        panel_ram.setBackground(new Color(244, 164, 96));
        contentPane.add(panel_ram, BorderLayout.WEST);
        panel_ram.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 7));

        JPanel panel_fsr = new JPanel();
        panel_fsr.setPreferredSize(new Dimension(470, 300));
        panel_ram.add(panel_fsr);
        panel_fsr.setLayout(null);

        JScrollPane scrollPane_fsr = new JScrollPane();
        scrollPane_fsr.setEnabled(false);
        scrollPane_fsr.setBounds(0, 20, 469, 280);
        scrollPane_fsr.setVerticalScrollBarPolicy(ScrollPaneConstants.VERTICAL_SCROLLBAR_ALWAYS);
        scrollPane_fsr.setViewportBorder(null);
        scrollPane_fsr.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane_fsr.setForeground(Color.WHITE);
        panel_fsr.add(scrollPane_fsr);

        table_fsr = new JTable();
        table_fsr.setEnabled(false);
        table_fsr.setFont(new Font("Tahoma", Font.PLAIN, 9));        
        table_fsr.setModel(new DefaultTableModel(
                new Object[][] {
                    {"0x00 INDF", null, null, null, null, null, null, null, null},
                    {"0x01 TMR0", null, null, null, null, null, null, null, null},
                    {"0x02 PCL", null, null, null, null, null, null, null, null},
                    {"0x03 STATUS", null, null, null, null, null, null, null, null},
                    {"0x04 FSR", null, null, null, null, null, null, null, null},
                    {"0x05 PORTA", null, null, null, null, null, null, null, null},
                    {"0x06 PORTB", null, null, null, null, null, null, null, null},
                    {"0x08 EEDATA", null, null, null, null, null, null, null, null},
                    {"0x09 EEADR", null, null, null, null, null, null, null, null},
                    {"0x0A PCLATCH", null, null, null, null, null, null, null, null},
                    {"0x0B INTCON", null, null, null, null, null, null, null, null},

                    {"0x81 OPTION", null, null, null, null, null, null, null, null},
                    {"0x85 TRISA", null, null, null, null, null, null, null, null},
                    {"0x86 TRISB", null, null, null, null, null, null, null, null},
                    {"0x88 EECON1", null, null, null, null, null, null, null, null},
                    {"0x89 EECON2", null, null, null, null, null, null, null, null},
                },
                new String[] {
                        "Register", "Bit 7", "Bit 6", "Bit 5", "Bit 4", "Bit 3", "Bit 2", "Bit 1", "Bit 0"
                }
                ));
        table_fsr.getColumnModel().getColumn(0).setPreferredWidth(70);
        table_fsr.getColumnModel().getColumn(1).setPreferredWidth(50);
        table_fsr.getColumnModel().getColumn(2).setPreferredWidth(50);
        table_fsr.getColumnModel().getColumn(3).setPreferredWidth(50);
        table_fsr.getColumnModel().getColumn(4).setPreferredWidth(50);
        table_fsr.getColumnModel().getColumn(5).setPreferredWidth(50);
        table_fsr.getColumnModel().getColumn(6).setPreferredWidth(50);
        table_fsr.getColumnModel().getColumn(7).setPreferredWidth(50);
        table_fsr.getColumnModel().getColumn(8).setPreferredWidth(50);
        scrollPane_fsr.setViewportView(table_fsr);

        JPanel panel_gpr = new JPanel();
        panel_gpr.setPreferredSize(new Dimension(470, 340));
        panel_ram.add(panel_gpr);
        panel_gpr.setLayout(null);

        JLabel lblNewLabel = new JLabel("GPR");
        lblNewLabel.setBounds(226, 5, 18, 13);
        panel_gpr.add(lblNewLabel);

        JScrollPane scrollPane = new JScrollPane();
        scrollPane.setBounds(1, 23, 469, 307);
        scrollPane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        scrollPane.setForeground(new Color(255, 255, 255));
        scrollPane.setViewportBorder(null);
        panel_gpr.add(scrollPane);

        table_grp = new JTable();
        table_grp.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        table_grp.setName("");
        table_grp.setEnabled(false);
        table_grp.setRowSelectionAllowed(false);
        table_grp.setBorder(null);
        scrollPane.setViewportView(table_grp);
        table_grp.setModel(new DefaultTableModel(
                new Object[68][9],
                new String[] {
                        "Address", "Bit 7", "Bit 6", "Bit 5", "Bit 4", "Bit 3", "Bit 2", "Bit 1", "Bit 0"
                } )
                );
        table_grp.getColumnModel().getColumn(0).setPreferredWidth(70);
        table_grp.getColumnModel().getColumn(1).setPreferredWidth(50);
        table_grp.getColumnModel().getColumn(2).setPreferredWidth(50);
        table_grp.getColumnModel().getColumn(3).setPreferredWidth(50);
        table_grp.getColumnModel().getColumn(4).setPreferredWidth(50);
        table_grp.getColumnModel().getColumn(5).setPreferredWidth(50);
        table_grp.getColumnModel().getColumn(6).setPreferredWidth(50);
        table_grp.getColumnModel().getColumn(7).setPreferredWidth(50);
        table_grp.getColumnModel().getColumn(8).setPreferredWidth(50);

        JLabel lblNewLabel_1 = new JLabel("Bank0 == Bank1");
        panel_ram.add(lblNewLabel_1);


        /*
         * >>>>>>>>>>>>>>>> panel_pm
         */
        JPanel panel_pm = new JPanel(new BorderLayout());
        panel_pm.setPreferredSize(new Dimension(520, 0));

        JPanel centerPanel = new JPanel(new FlowLayout(FlowLayout.CENTER));
        centerPanel.setBackground(new Color(255, 215, 0));
        JLabel label = new JLabel("Testprogramm");
        centerPanel.add(label);

        panel_pm.add(centerPanel, BorderLayout.NORTH);



        testprogrammPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10)); // Padding von 10 Pixeln
        testprogrammPanel.setBackground(new Color(255, 215, 0));

        //Dummy File for first Initializiation
        String[] head = new String[] {"", ""};
        String[][] line = new String[42][2];
        line[0][0] = "    ";
        line[0][1] = "                                                                                                                                                            ";
        TestprogrammViewer.testprogramm_view = TestprogrammViewer.Table(head,line);
        testprogrammPanel.add(TestprogrammViewer.testprogramm_view, BorderLayout.CENTER);
        panel_pm.add(testprogrammPanel, BorderLayout.CENTER);

        contentPane.add(panel_pm, BorderLayout.EAST);



    }

    /**
     * visually updates following gui components:
     * - ram
     * - pins
     * - w_reg
     */
    public static void updateGUI() {        
        // stack_gui gets updated autom. after any method call on it FIXME does not work when using btn_restart()
        
        // pc_gui gets updated autom. after any method call on it
        
        // prescaler_gui gets updated autom. after any method call on it
        
        // runtimeCounter_gui gets updated autom. after any method call on it
        
        Ram_gui.update();

        // update pins
        PinSelector.loadPins();
        
        // update W-Reg
        GUI.table_w.setModel(new DefaultTableModel(
                new Object[][] {{"W-Reg",Utils.binaryToHex(MC.alu.wReg.read())}},new String[] {"",""}));
        GUI.table_prs.setModel(
                new DefaultTableModel(new Object[][] {{"VT (T)", 
                    MC.prescaler.getPRS(0)},{"VT (W)", MC.prescaler.getPRS(1)}}, new String[] {"",""}));
    }

    private Object[][] loadData() {
        String[] data = new String[] {"STATUS","IRP","RP1","RP0", "TO","PD","Z","DC","C","OPTION","RBPU","INTEDG","TOCS","TOSE","PSA","PS2","PS1","PS0","INTCON","GIE","EEIE","TOIE","INTE","RBIE","TOIF","INTF","RBIF"};
        int pointer = 0;
        Object[][] result = new Object[3][9];
        for(int i = 0; i < result.length;i++) {
            for(int j = 0; j < result[i].length;j++) {
                result[i][j] = data[pointer];
                pointer++;
            }
        }
        return result;
    }

    private void setUpMenuBar () {

        /*
         * ++++++++++++++++ Programm laden ++++++++++++++++++++
         */
        JMenu mnNewMenu = new JMenu("Programm laden...");
        mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mnNewMenu);

        JMenuItem tp1 = new JMenuItem("Testprogramm 1");
        tp1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s1);
            }
        });
        tp1.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp1);

        JMenuItem tp2 = new JMenuItem("Testprogramm 2");
        tp2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s2);
            }
        });
        tp2.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp2);

        JMenuItem tp21 = new JMenuItem("Testprogramm 2.1");
        tp21.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s21);
            }
        });
        tp21.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp21);

        JMenuItem tp3 = new JMenuItem("Testprogramm 3");
        tp3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s3);
            }
        });
        tp3.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp3);

        JMenuItem tp4 = new JMenuItem("Testprogramm 4");
        tp4.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s4);
            }
        });
        tp4.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp4);

        JMenuItem tp5 = new JMenuItem("Testprogramm 5");
        tp5.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s5);
            }
        });
        tp5.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp5);

        JMenuItem tp6 = new JMenuItem("Testprogramm 6");
        tp6.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s6);
            }
        });
        tp6.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp6);

        JMenuItem tp7 = new JMenuItem("Testprogramm 7");
        tp7.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s7);
            }
        });
        tp7.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp7);

        JMenuItem tp8 = new JMenuItem("Testprogramm 8");
        tp8.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s8);
            }
        });
        tp8.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp8);

        JMenuItem tp9 = new JMenuItem("Testprogramm 9");
        tp9.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s9);
            }
        });
        tp9.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp9);

        JMenuItem tp10 = new JMenuItem("Testprogramm 10");
        tp10.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s10);
            }
        });
        tp10.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp10);

        JMenuItem tp101 = new JMenuItem("Testprogramm 10.1");
        tp101.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s101);
            }
        });
        tp101.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp101);

        JMenuItem tp11 = new JMenuItem("Testprogramm 11");
        tp11.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s11);
            }
        });
        tp11.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp11);

        JMenuItem tp12 = new JMenuItem("Testprogramm 12");
        tp12.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s12);
            }
        });
        tp12.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp12);

        JMenuItem tp13 = new JMenuItem("Testprogramm 13");
        tp13.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s13);
            }
        });
        tp13.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp13);

        JMenuItem tp14 = new JMenuItem("Testprogramm 14");
        tp14.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s14);
            }
        });
        tp14.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp14);

        JMenuItem tp15 = new JMenuItem("Testprogramm 15");
        tp15.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                TestprogrammViewer.overrideProgramm(TP.s15);
            }
        });
        tp15.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp15);

        JMenuItem tpx = new JMenuItem("weitere laden ...");
        tpx.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(e.getSource() == tpx) {
                    JFileChooser fileChooser = new JFileChooser();
                    int response = fileChooser.showOpenDialog(null); //select file to open -> return 0 for open, else 1 (close)
                    if(response== fileChooser.APPROVE_OPTION) {
                        TestprogrammViewer.overrideProgramm(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                }
            }
        });
        tpx.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tpx);


        /*
         *  +++++++++++ Help +++++++++++
         */
        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mnHelp);

        JMenuItem mnNewMenu_1_1 = new JMenuItem("Bei uns gibt es keine Hilfe");
        mnNewMenu_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
        mnHelp.add(mnNewMenu_1_1);

    }
}
