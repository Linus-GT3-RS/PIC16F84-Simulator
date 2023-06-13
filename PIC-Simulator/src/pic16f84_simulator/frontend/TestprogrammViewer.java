package pic16f84_simulator.frontend;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

import javax.swing.DefaultListSelectionModel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.SwingUtilities;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import pic16f84_simulator.MC;

public class TestprogrammViewer {
    
    public static ArrayList<Integer> BreakPoints = new ArrayList<>() ; // include BreakPoints -> PC
    public static int PCLine; // beinhaltet die Zeile des aktuellen Programmzähler
    public static ArrayList<Integer> pcLines = new ArrayList<>(); // beinhaltet alle Zeilennummern mit Programmcode PCLine -> PC (index von PCLines): z.B. Index 0 (PC = 0) beihaltet Zeile 18 im Testprogramm
    public static boolean loaded = false;
    
    public static  JScrollPane testprogramm_view;
    public static JTable testprogramm_table;
    
    
    public static void highlightPCLine() {
        if(MC.control.pc() != 0 && MC.control.pc() < pcLines.size()) {
            PCLine = pcLines.get(MC.control.pc());   
        }
        testprogramm_table.setDefaultRenderer(Object.class, new DefaultTableCellRenderer()
        {
            @Override
            public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column)
            {
                final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
                c.setBackground(row == PCLine ? Color.YELLOW : Color.WHITE);
                return c;
            }
        });
        testprogramm_table.repaint();
    }
 
    static JScrollPane Table(String[] head,String[][] rows) {
        DefaultTableModel model = new DefaultTableModel(rows,head);
        testprogramm_table = new JTable(model) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are now editable
            }
        };
        testprogramm_table.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Mauslinksklick
                    int selectedRow = testprogramm_table.getSelectedRow();
                    if(pcLines.contains(selectedRow)) {
                        toggleBreakpoint(selectedRow);
                        System.out.println("Breakpoints are at: " + BreakPoints);
                    }
                }
            }
            
            private void toggleBreakpoint(int selectedRow) { // store Breakpoints
                if(testprogramm_table.getValueAt(selectedRow, 0) == "B") {
                    testprogramm_table.setValueAt("", selectedRow, 0);
                    for(int i = 0 ; i < pcLines.size() ; i++) {
                       if(selectedRow == pcLines.get(i)) { 
                           for(int j = 0; j < BreakPoints.size(); j++) {
                               if(i == BreakPoints.get(j)) {
                                   BreakPoints.remove(j);
                               }
                           }
                            
                       } 
                    }
                } else {
                    testprogramm_table.setValueAt("B", selectedRow, 0);
                    for(int i = 0; i < pcLines.size();i++) {
                        if(pcLines.get(i) == selectedRow) {
                            BreakPoints.add(i);
                        }
                    }
                }
                
            }
        });
        JScrollPane scroll = new JScrollPane(testprogramm_table);
        
        testprogramm_table.setGridColor(Color.LIGHT_GRAY);
        testprogramm_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        TableColumn column = testprogramm_table.getColumnModel().getColumn(0);
        column.setPreferredWidth(10);
        column = testprogramm_table.getColumnModel().getColumn(1);
        column.setPreferredWidth(475);
        testprogramm_table.setFont(new Font("Arial", Font.PLAIN, 12));
        testprogramm_table.setAutoCreateRowSorter(true);
        
        return scroll;
    }
    
    // When you like to use another programm
    static void overrideProgramm(String path) {
        MC.ram.powerOnReset();
        // stack Reset + TOS -> Stackpointer
        String[][] data = MC.pm.loadTestProgram(path);
        DefaultTableModel model = new DefaultTableModel(data,new String[] {"", ""});
        testprogramm_table.setModel(model);
        
        TableColumn column = testprogramm_table.getColumnModel().getColumn(0);
        column.setPreferredWidth(10);
        column = testprogramm_table.getColumnModel().getColumn(1);
        column.setPreferredWidth(475);
        
        for(int i=0 ; i<data.length; i++) {
            if(i< model.getRowCount()) {
                model.setValueAt(data[i][0], i, 0);
                model.setValueAt(data[i][1], i, 1);
            }else {
                model.addRow(new String[] {"  ",data[i][1]});
            }
        }
        if(data.length < model.getRowCount()) {
            for(int i = data.length; i < model.getRowCount(); i++) {
                model.setValueAt("  ", i, 0);
                model.setValueAt("                                              ", i, 1);
            }
        }
        
        // Inform the TableModel about the changes
        model.fireTableDataChanged();

        // Update the JScrollPane view
        testprogramm_view.setViewportView(testprogramm_table);
        testprogramm_view.revalidate();
        testprogramm_view.repaint();
        MC.control.pc(0);
        TestprogrammViewer.PCLine = TestprogrammViewer.pcLines.get(MC.control.pc());
        TestprogrammViewer.highlightPCLine();
    }

    // remove spaces by number of letter
    public static String removeSpaces(String zeile) {
        String elem = zeile.substring(0, 9);
        int count = 0;
        for(int i = 0; i < 9; i++) {
            if(checkDigit(elem.charAt(i))) {
                count++;
            }
        }
        String subA = elem;
        String subB = zeile.substring(9+((int)(count*0.72)),zeile.length());
        return subA+subB;
    }

    // check digit if is a letter
    static boolean checkDigit(char charAt) {
        switch (charAt) {
        case  'A': 
        case  'B':
        case  'C':
        case  'D':
        case  'F': return true;
        default: return false;
          
        }
    }

    // Set string in between an other string
    public static String StringSetter(String c, int i, String zeile) {
        String result = "";
        for(int j = 0; j < zeile.length(); j++) {
            if(j == i ) {
                result += c;
            }else {
                result += zeile.charAt(j);
            }
        }
        return result;
    }
}
