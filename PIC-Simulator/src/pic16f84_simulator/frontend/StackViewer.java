package pic16f84_simulator.frontend;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import pic16f84_simulator.MC;

public class StackViewer {
    
    public static void updateStack() {
        DefaultTableModel model = new DefaultTableModel(MC.stack.loadStack(),new String[] {"        ", "        ", "        "}) ;
        GUI.stack_table.setModel(model);
    }
}
