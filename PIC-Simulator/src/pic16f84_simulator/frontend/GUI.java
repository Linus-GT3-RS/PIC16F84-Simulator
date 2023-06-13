package pic16f84_simulator.frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.table.TableColumnModel;
import javax.swing.table.TableModel;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.TP;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import java.awt.Image;

import javax.swing.BorderFactory;
import javax.swing.DefaultListModel;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.ImageIcon;
import javax.swing.LayoutStyle.ComponentPlacement;
import javax.swing.ListModel;
import javax.swing.SpringLayout;
import java.awt.GridBagLayout;
import java.awt.GridBagConstraints;
import java.awt.Insets;
import javax.swing.SwingConstants;
import java.awt.Font;
import javax.swing.JToolBar;
import javax.swing.JDesktopPane;
import javax.swing.JFileChooser;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;

import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;

import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JComponent;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import java.awt.List;
import javax.swing.JMenu;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Graphics;

import javax.swing.border.BevelBorder;

public class GUI extends JFrame {
    
    public static boolean modus = false; // for testing is default false, otherwise issues
    
    public static JMenuBar menuBar = new JMenuBar();
    public static JPanel contentPane = new JPanel();
    
    /*
     * >>>>> pannel_collection
     */
    public static JPanel panel_collection = new JPanel();
    public static JTable stack_table;
    /*
     * >>>>> pannel_pm
     */
    public static JPanel testprogrammPanel = new JPanel(new BorderLayout());
    private JTable table;
   

    /**
     * Launch the application.
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
        
        JPanel panel_stack = new JPanel();
        panel_stack.setBackground(new Color(166, 222, 247));
        panel_stack.setBounds(0, 558, 481, 222);
        panel_collection.add(panel_stack);

        JPanel center = new JPanel(new FlowLayout(FlowLayout.CENTER));
        JLabel Stacklabel = new JLabel("Stack");
        center.setBackground(new Color(166, 222, 247));
        center.add(Stacklabel);
        
        panel_stack.setLayout(new BorderLayout()); // Set BorderLayout for panel_stack
        panel_stack.add(center, BorderLayout.NORTH); // Add center panel to panel_stack's NORTH
        
        panel_stack.setBorder(BorderFactory.createEmptyBorder(0, 100, 65, 100)); // Padding
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
        panel_stack.add(stack_table, BorderLayout.CENTER); // Add stack_table to panel_stack's CENTER
        stack_table.setShowGrid(false);
        stack_table.setBackground(new Color(166, 222, 247));
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
        JPanel panel_3 = new JPanel();
        panel_3.setBackground(new Color(17, 135, 185));
        panel_3.setBounds(0, 0, 481, 248);
        panel_collection.add(panel_3);

        JPanel panel_controller = new JPanel();
        panel_controller.setPreferredSize(new Dimension(10, 125));
        panel_controller.setBackground(new Color(218, 112, 214));
        contentPane.add(panel_controller, BorderLayout.SOUTH);
        panel_controller.setLayout(null);

        JButton btnNewButton_4 = new JButton("Restart");
        btnNewButton_4.setBounds(564, 71, 75, 27);
        btnNewButton_4.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_controller.add(btnNewButton_4);

        JButton btnNewButton_3_1 = new JButton("RUN");
        btnNewButton_3_1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if(TestprogrammViewer.loaded) {
                    MC.control.exe();
                    PinSelector.loadPins();
                }
            }
        });
        btnNewButton_3_1.setBounds(684, 36, 67, 27);
        btnNewButton_3_1.setFont(new Font("Arial", Font.BOLD, 16));
        panel_controller.add(btnNewButton_3_1);

        JButton btnNewButton_1_1 = new JButton("Stop");
        btnNewButton_1_1.setBounds(787, 38, 37, 23);
        btnNewButton_1_1.setBorder(new BevelBorder(BevelBorder.LOWERED, null, null, null, null));
        btnNewButton_1_1.setBackground(new Color(95, 158, 160));
        btnNewButton_1_1.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_controller.add(btnNewButton_1_1);

        JButton btnNewButton_2_1 = new JButton("Next");
        btnNewButton_2_1.setBounds(876, 71, 63, 27);
        btnNewButton_2_1.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_controller.add(btnNewButton_2_1);

        
        /*
         * >>>>>>>>>>>>>> panel_ram
         */
        JPanel panel_ram = new JPanel();
        panel_ram.setBorder(new EmptyBorder(8, 0, 13, 0));
        panel_ram.setPreferredSize(new Dimension(520, 10));
        panel_ram.setBackground(new Color(244, 164, 96));
        contentPane.add(panel_ram, BorderLayout.WEST);
        panel_ram.setLayout(new FlowLayout(FlowLayout.CENTER, 15, 7));
        
        JPanel panel_1 = new JPanel();
        panel_1.setPreferredSize(new Dimension(470, 400));
        panel_ram.add(panel_1);
        
        JLabel lblNewLabel = new JLabel("SFR");
        panel_1.add(lblNewLabel);
        
        JPanel panel = new JPanel();
        panel.setPreferredSize(new Dimension(470, 240));
        panel_ram.add(panel);
        panel.setLayout(new FlowLayout(FlowLayout.CENTER, 5, 5));
        
        JLabel lblNewLabel_2 = new JLabel("GPR");
        panel.add(lblNewLabel_2);

        
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
        
        JMenuItem tp101 = new JMenuItem("Testprogramm 101");
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
