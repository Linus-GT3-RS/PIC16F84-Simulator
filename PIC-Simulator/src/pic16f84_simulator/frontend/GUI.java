package pic16f84_simulator.frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.TP;

import java.awt.Color;
import java.awt.FlowLayout;
import java.awt.BorderLayout;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.GridLayout;
import javax.swing.GroupLayout;
import javax.swing.GroupLayout.Alignment;
import javax.swing.LayoutStyle.ComponentPlacement;
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
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import java.awt.List;
import javax.swing.JMenu;
import java.awt.Dimension;
import java.awt.Frame;
import javax.swing.border.BevelBorder;

public class GUI extends JFrame {

    private JPanel contentPane;

    /**
     * Launch the application.
     */
    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    GUI frame = new GUI();
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

        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);

        JMenu mnNewMenu = new JMenu("Programm laden...");
        mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mnNewMenu);

        JMenuItem tp1 = new JMenuItem("Testprogramm 1");
        tp1.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s1);
            }
        });
        tp1.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp1);

        JMenuItem tp2 = new JMenuItem("Testprogramm 2");
        tp2.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s2);
            }
        });
        tp2.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp2);

        JMenuItem tp3 = new JMenuItem("Testprogramm 3");
        tp3.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s3);
            }
        });
        tp3.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp3);

        JMenuItem tp4 = new JMenuItem("Testprogramm 4");
        tp4.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s4);
            }
        });
        tp4.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp4);
        
        JMenuItem tp5 = new JMenuItem("Testprogramm 5");
        tp5.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s5);
            }
        });
        tp5.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp5);
        
        JMenuItem tp6 = new JMenuItem("Testprogramm 6");
        tp6.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s6);
            }
        });
        tp6.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp6);
        
        JMenuItem tp7 = new JMenuItem("Testprogramm 7");
        tp7.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s7);
            }
        });
        tp7.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp7);
        
        JMenuItem tp8 = new JMenuItem("Testprogramm 8");
        tp8.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s8);
            }
        });
        tp8.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp8);
        
        JMenuItem tp9 = new JMenuItem("Testprogramm 9");
        tp9.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s9);
            }
        });
        tp9.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp9);
        
        JMenuItem tp10 = new JMenuItem("Testprogramm 10");
        tp10.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s10);
            }
        });
        tp10.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp10);
        
        JMenuItem tp101 = new JMenuItem("Testprogramm 101");
        tp101.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s101);
            }
        });
        tp101.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tp101);
        
        JMenuItem tp11 = new JMenuItem("Testprogramm 11");
        tp11.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                MC.pm.loadTestProgram(TP.s11);
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
                        MC.pm.loadTestProgram(fileChooser.getSelectedFile().getAbsolutePath());
                        System.out.println(fileChooser.getSelectedFile().getAbsolutePath());
                    }
                }
            }
        });
        tpx.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(tpx);



        JMenu mnHelp = new JMenu("Help");
        mnHelp.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mnHelp);

        JMenuItem mnNewMenu_1_1 = new JMenuItem("Bei uns gibt es keine Hilfe");
        mnNewMenu_1_1.setFont(new Font("Arial", Font.PLAIN, 12));
        mnHelp.add(mnNewMenu_1_1);
        contentPane = new JPanel();
        contentPane.setForeground(new Color(192, 192, 192));
        contentPane.setBackground(new Color(54, 54, 54));
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));

        setContentPane(contentPane);
        contentPane.setLayout(new BorderLayout(4, 4));

        JPanel panel_collection = new JPanel();
        panel_collection.setPreferredSize(new Dimension(100, 10));
        panel_collection.setBackground(new Color(100, 149, 237));
        contentPane.add(panel_collection, BorderLayout.CENTER);
        panel_collection.setLayout(null);

        JLabel lblWatchdog_1 = new JLabel("WatchDog");
        lblWatchdog_1.setBounds(0, 0, 118, 635);
        lblWatchdog_1.setForeground(Color.WHITE);
        panel_collection.add(lblWatchdog_1);

        JCheckBoxMenuItem chckbxmntmNewCheckItem_1 = new JCheckBoxMenuItem("WatchDog");
        chckbxmntmNewCheckItem_1.setBounds(102, 154, 118, 174);
        panel_collection.add(chckbxmntmNewCheckItem_1);

        JLabel lblTimer_1 = new JLabel("Timer");
        lblTimer_1.setBounds(236, 0, 118, 635);
        lblTimer_1.setForeground(Color.WHITE);
        panel_collection.add(lblTimer_1);

        JLabel lblNewLabel_1 = new JLabel("Pins");
        lblNewLabel_1.setBounds(354, 0, 118, 635);
        lblNewLabel_1.setForeground(Color.WHITE);
        panel_collection.add(lblNewLabel_1);

        JPanel panel_controller = new JPanel();
        panel_controller.setPreferredSize(new Dimension(10, 125));
        panel_controller.setBackground(new Color(218, 112, 214));
        contentPane.add(panel_controller, BorderLayout.SOUTH);
        panel_controller.setLayout(null);

        JButton btnNewButton_4 = new JButton("Reset");
        btnNewButton_4.setBounds(564, 71, 75, 27);
        btnNewButton_4.setFont(new Font("Arial", Font.PLAIN, 16));
        panel_controller.add(btnNewButton_4);

        JButton btnNewButton_3_1 = new JButton("RUN");
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

        JPanel panel_pm = new JPanel();
        panel_pm.setPreferredSize(new Dimension(530, 10));
        panel_pm.setBackground(new Color(255, 215, 0));
        contentPane.add(panel_pm, BorderLayout.EAST);


    }
    private static void addPopup(Component component, final JPopupMenu popup) {
    }
}
