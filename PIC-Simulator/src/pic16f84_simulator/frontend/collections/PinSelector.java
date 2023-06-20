package pic16f84_simulator.frontend.collections;

import java.awt.event.MouseEvent;

import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.SwingUtilities;
import javax.swing.event.MouseInputAdapter;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.frontend.GUI;
import pic16f84_simulator.backend.*;


public class PinSelector {
    
    static public JTable table_pin_upper;
    static public JTable table_pin_under;
    
    static public void Pin_Table() {
        
        JPanel panel_table_upper = new JPanel();
        panel_table_upper.setBounds(-85,255,600,70);
        DefaultTableModel pin_upper_mode = new DefaultTableModel(new String[][] {{"I/O","","","", "", "", "", "", "", "","","","","","","",""}, {"PIN","","", "", "", "", "", "", "","","","","","","",""}},new String[] {"","","","","","","","","",""});
        table_pin_upper = new JTable(pin_upper_mode) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are now editable
            }
        };
        TableColumn column_pin_upper;
        for(int i = 0; i< table_pin_upper.getColumnCount();i++) {
            column_pin_upper = table_pin_upper.getColumnModel().getColumn(i);
            column_pin_upper.setPreferredWidth(29);
        }
        table_pin_upper.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Mauslinksklick
                    int selectedRow = table_pin_upper.getSelectedRow();
                    int selectedColumn = table_pin_upper.getSelectedColumn();
                    if((selectedColumn == 1) || (selectedColumn == 2) || (selectedColumn > 5 && selectedColumn < 10)) {
                        getPort(selectedColumn).toggle(selectedRow);    
                    }
                    loadPins();
                }
            }

            private Ports getPort(int selectedColumn) {
                switch (selectedColumn) {
                case 1 -> { return Ports.RA1;}
                case 2 -> { return Ports.RA0;}
                case 6 -> { return Ports.RB7;}
                case 7 -> { return Ports.RB6;}
                case 8 -> { return Ports.RB5;}
                case 9 -> { return Ports.RB4;}
                default -> {return null;}
                
                }
                
            }
        }
        );
    
        panel_table_upper.add(table_pin_upper);
        
        GUI.panel_collection.add(panel_table_upper);
       
        
        
        JPanel panel_table_under = new JPanel();
        panel_table_under.setBounds(-86, 510, 600, 50);
        
        DefaultTableModel pin_under_mode = new DefaultTableModel(new String[][] {{"I/O","","","", "", "", "", "", "", "","","","","","","",""}, {"PIN","","", "", "", "", "", "", "","","","","","","",""}},new String[] {"","","","","","","","","",""});
        table_pin_under = new JTable(pin_under_mode) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false; // cells are now editable
            }
        };
        TableColumn column_pin_under;
        for(int i = 0; i < table_pin_under.getColumnCount();i++) {
            column_pin_under = table_pin_under.getColumnModel().getColumn(i);
            column_pin_under.setPreferredWidth(29);
        }
        table_pin_under.addMouseListener(new MouseInputAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    // Mauslinksklick
                    int selectedRow = table_pin_under.getSelectedRow();
                    int selectedColumn = table_pin_under.getSelectedColumn();
                    if((selectedColumn == 1) || (selectedColumn == 2) || (selectedColumn == 3) || (selectedColumn > 5 && selectedColumn < 10)) {
                        getPort(selectedColumn).toggle(selectedRow);    
                    }
                    loadPins();
                }
            }

            private Ports getPort(int selectedColumn) {
                switch (selectedColumn) {
                case 1 -> { return Ports.RA2;}
                case 2 -> { return Ports.RA3;}
                case 3 -> { return Ports.RA4;}
                case 6 -> { return Ports.RB0;}
                case 7 -> { return Ports.RB1;}
                case 8 -> { return Ports.RB2;}
                case 9 -> { return Ports.RB3;}
                default -> {return null;}
                
                }
                
            }
        }
        );
        panel_table_under.add(table_pin_under);
        GUI.panel_collection.add(panel_table_under);
        DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        table_pin_upper.setDefaultRenderer(Object.class, centerRenderer);
        table_pin_under.setDefaultRenderer(Object.class, centerRenderer);
        loadPins();
    }
    
    static public void loadPins() {
        for(int i = 1; i < table_pin_upper.getColumnCount();i++) {
            int value_tris_upper = 0;
            int value_pin_upper = 0;
            int value_tris_under = 0;
            int value_pin_under = 0;
            switch (i) {
            case 1 -> {
                        value_tris_upper = Ports.RA1.direction();
                        value_pin_upper = Ports.RA1.asValue();
                        value_tris_under = Ports.RA2.direction();
                        value_pin_under = Ports.RA2.asValue();
                      }
            case 2 -> {
                        value_tris_upper = Ports.RA0.direction();
                        value_pin_upper = Ports.RA0.asValue();
                        value_tris_under = Ports.RA3.direction();
                        value_pin_under = Ports.RA3.asValue();
                      }
            case 3 -> {
                        value_tris_under = Ports.RA4.direction();
                        value_pin_under = Ports.RA4.asValue();
                      }
            case 6 -> {
                        value_tris_upper = Ports.RB7.direction();
                        value_pin_upper = Ports.RB7.asValue();
                        value_tris_under = Ports.RB0.direction();
                        value_pin_under = Ports.RB0.asValue();
                      }
            case 7 -> {
                        value_tris_upper = Ports.RB6.direction();
                        value_pin_upper = Ports.RB6.asValue();
                        value_tris_under = Ports.RB1.direction();
                        value_pin_under = Ports.RB1.asValue();
                      }
            case 8 -> {
                        value_tris_upper = Ports.RB5.direction();
                        value_pin_upper = Ports.RB5.asValue();
                        value_tris_under = Ports.RB2.direction();
                        value_pin_under = Ports.RB2.asValue();
                      }
            case 9 -> {
                        value_tris_upper = Ports.RB4.direction();
                        value_pin_upper = Ports.RB4.asValue();
                        value_tris_under = Ports.RB3.direction();
                        value_pin_under = Ports.RB3.asValue();
                      }
            default -> {}
            }
            table_pin_upper.setValueAt(value_tris_upper, 0, i);
            table_pin_upper.setValueAt(value_pin_upper, 1, i);
            table_pin_under.setValueAt(value_tris_under, 0, i);
            table_pin_under.setValueAt(value_pin_under, 1, i);
        }
        
        
    }
    
}
