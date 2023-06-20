package pic16f84_simulator.frontend.collections;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;

import pic16f84_simulator.MC;
import pic16f84_simulator.frontend.GUI;

public class StackViewer {
    
    public static void updateStack() {
        DefaultTableModel model = new DefaultTableModel(MC.stack.loadStack(),new String[] {"        ", "        ", "        "}) ;
        GUI.stack_table.setModel(model);
        GUI.stack_table.setEnabled(false);
    }
}
