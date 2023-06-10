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
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import pic16f84_simulator.MC;

public class ProgrammViewer {
    
    public static ArrayList<Integer> BreakPoints = new ArrayList<>() ;
    public static int PCLine; // beinhaltet die Zeile des aktuellen Programmzähler
    public static ArrayList<Integer> pcLines = new ArrayList<>(); // beinhaltet alle Zeilen mit Programmcode
    public static boolean loaded = false;
    
    public static  JScrollPane testprogramm_view;
    public static JTable testprogramm_table;
    
    
    public static void highlightPCLine() {
        if(MC.control.pc!=0 && MC.control.pc < pcLines.size()) {
            PCLine = pcLines.get(MC.control.pc);   
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
        JScrollPane scroll = new JScrollPane(testprogramm_table);
        
        testprogramm_table.setGridColor(Color.LIGHT_GRAY);
        testprogramm_table.setAutoResizeMode(JTable.AUTO_RESIZE_OFF);
        
        // Calculate width of columns based on content
        for (int columnIndex = 0; columnIndex < testprogramm_table.getColumnCount(); columnIndex++) {
            TableColumn column = testprogramm_table.getColumnModel().getColumn(columnIndex);
            int preferredWidth = 0;
            
            for (int rowIndex = 0; rowIndex < testprogramm_table.getRowCount(); rowIndex++) {
                TableCellRenderer cellRenderer = testprogramm_table.getCellRenderer(rowIndex, columnIndex);
                Component cellComponent = testprogramm_table.prepareRenderer(cellRenderer, rowIndex, columnIndex);
                
                int cellWidth = cellComponent.getPreferredSize().width;
                preferredWidth = Math.max(preferredWidth, cellWidth);
            }
            
            column.setPreferredWidth(preferredWidth + testprogramm_table.getIntercellSpacing().width);
        }
        testprogramm_table.setFont(new Font("Arial", Font.PLAIN, 12));
        testprogramm_table.setAutoCreateRowSorter(true);
        return scroll;
    }
    
    // When you like to use another programm
    static void overrideProgramm(String path) {
        String[][] data = MC.pm.loadTestProgram(path);
        DefaultTableModel model = (DefaultTableModel) testprogramm_table.getModel();
        
        testprogramm_table.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                if (e.getButton() == MouseEvent.BUTTON1) {
                    // Mauslinksklick
                    int selectedRow = testprogramm_table.getSelectedRow();
                    toggleBreakpoint(selectedRow);
                }
            }
            
            private void toggleBreakpoint(int selectedRow) { // store Breakpoints
                if(testprogramm_table.getValueAt(selectedRow, 0) == "B") {
                    testprogramm_table.setValueAt("", selectedRow, 0);
                    for(int i = 0 ; i < BreakPoints.size() ; i++) {
                        if(BreakPoints.get(i) == selectedRow) {
                            BreakPoints.remove(i);
                        }
                    }
                } else {
                    testprogramm_table.setValueAt("B", selectedRow, 0);
                    BreakPoints.add(selectedRow);
                }
                
            }
        });

            
       
        
        for(int i=0 ; i<data.length; i++) {
            if(i< model.getRowCount()) {
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
        MC.control.pc = 0;
        ProgrammViewer.PCLine = ProgrammViewer.pcLines.get(MC.control.pc);
        ProgrammViewer.highlightPCLine();
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
