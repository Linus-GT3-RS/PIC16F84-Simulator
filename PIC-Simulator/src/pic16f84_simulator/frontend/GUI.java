package pic16f84_simulator.frontend;

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

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
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.JMenuBar;
import javax.swing.JPopupMenu;
import java.awt.Component;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import javax.swing.JMenuItem;
import javax.swing.JCheckBoxMenuItem;
import javax.swing.JSeparator;
import javax.swing.JTextArea;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JTable;
import java.awt.List;
import javax.swing.JMenu;

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
        setTitle("PIC16F84 Simulator");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setBounds(100, 100, 1024, 768);
        
        JMenuBar menuBar = new JMenuBar();
        setJMenuBar(menuBar);
        
        JMenu mnNewMenu = new JMenu("Programm laden...");
        mnNewMenu.setFont(new Font("Arial", Font.PLAIN, 12));
        menuBar.add(mnNewMenu);
        
        JMenuItem mnNewMenu_1 = new JMenuItem("TP 1");
        mnNewMenu_1.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(mnNewMenu_1);
        
        JMenuItem mnNewMenu_1_2 = new JMenuItem("TP 2");
        mnNewMenu_1_2.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(mnNewMenu_1_2);
        
        JMenuItem mnNewMenu_1_2_1 = new JMenuItem("TP 3");
        mnNewMenu_1_2_1.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(mnNewMenu_1_2_1);
        
        JMenuItem mnNewMenu_1_2_2 = new JMenuItem("TP 4");
        mnNewMenu_1_2_2.setFont(new Font("Arial", Font.PLAIN, 12));
        mnNewMenu.add(mnNewMenu_1_2_2);
        
        
        
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
        
        JButton btnNewButton = new JButton("Reset");
        btnNewButton.setFont(new Font("Arial", Font.PLAIN, 16));
        btnNewButton.setBounds(259, 672, 100, 50);
        btnNewButton.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        contentPane.setLayout(null);
        contentPane.add(btnNewButton);
        
        JButton btnNewButton_2 = new JButton("Next");
        btnNewButton_2.setFont(new Font("Arial", Font.PLAIN, 16));
        btnNewButton_2.setBounds(619, 672, 100, 50);
        btnNewButton_2.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        contentPane.add(btnNewButton_2);
        
        JButton btnNewButton_1 = new JButton("Stop");
        btnNewButton_1.setFont(new Font("Arial", Font.PLAIN, 16));
        btnNewButton_1.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton_1.setBounds(497, 633, 100, 50);
        contentPane.add(btnNewButton_1);
        
        JButton btnNewButton_3 = new JButton("RUN");
        btnNewButton_3.setFont(new Font("Arial", Font.BOLD, 16));
        btnNewButton_3.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
            }
        });
        btnNewButton_3.setBounds(380, 633, 100, 50);
        contentPane.add(btnNewButton_3);
        
        JCheckBoxMenuItem chckbxmntmNewCheckItem = new JCheckBoxMenuItem("WatchDog");
        chckbxmntmNewCheckItem.setBounds(380, 282, 137, 26);
        contentPane.add(chckbxmntmNewCheckItem);
        
        JLabel lblNewLabel = new JLabel("Pins");
        lblNewLabel.setForeground(new Color(255, 255, 255));
        lblNewLabel.setBounds(377, 34, 59, 23);
        contentPane.add(lblNewLabel);
        
        JLabel lblTimer = new JLabel("Timer");
        lblTimer.setForeground(Color.WHITE);
        lblTimer.setBounds(538, 248, 59, 23);
        contentPane.add(lblTimer);
        
        JLabel lblWatchdog = new JLabel("WatchDog");
        lblWatchdog.setForeground(Color.WHITE);
        lblWatchdog.setBounds(380, 248, 82, 23);
        contentPane.add(lblWatchdog);
        
        
    }
    private static void addPopup(Component component, final JPopupMenu popup) {
    }
}
