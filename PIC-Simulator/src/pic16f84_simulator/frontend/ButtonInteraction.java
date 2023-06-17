package pic16f84_simulator.frontend;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;

public class ButtonInteraction {
    
    static void button_next() {
        MC.control.exe();
        PinSelector.loadPins();
        System.out.println(Arrays.toString(MC.alu.wReg.read()) + " in Hex: " + Utils.binaryToHex(MC.alu.wReg.read()));
        
        // update W-Reg
        GUI.table_w.setModel(new DefaultTableModel(new Object[][] {{"W-Reg",Utils.binaryToHex(MC.alu.wReg.read())}},new String[] {"",""}));

        // update FSR-table
        GUI.table_fsr.setModel(new DefaultTableModel(MC.ram.getAllSingleSFRReg_gui(), new String[] {
                "Register", "Bit 7", "Bit 6", "Bit 5", "Bit 4", "Bit 3", "Bit 2", "Bit 1", "Bit 0" }));
        GUI.table_fsr.getColumnModel().getColumn(0).setPreferredWidth(70);
        GUI.table_fsr.getColumnModel().getColumn(1).setPreferredWidth(50);
        GUI.table_fsr.getColumnModel().getColumn(2).setPreferredWidth(50);
        GUI.table_fsr.getColumnModel().getColumn(3).setPreferredWidth(50);
        GUI.table_fsr.getColumnModel().getColumn(4).setPreferredWidth(50);
        GUI.table_fsr.getColumnModel().getColumn(5).setPreferredWidth(50);
        GUI.table_fsr.getColumnModel().getColumn(6).setPreferredWidth(50);
        GUI.table_fsr.getColumnModel().getColumn(7).setPreferredWidth(50);
        GUI.table_fsr.getColumnModel().getColumn(8).setPreferredWidth(50);

     // update GPR-table
        GUI.table_grp.setModel(new DefaultTableModel(MC.ram.getGPR_bank0_gui(), new String[] {
                "Address", "Bit 7", "Bit 6", "Bit 5", "Bit 4", "Bit 3", "Bit 2", "Bit 1", "Bit 0"}) );
        
    }
    
    static void button_run() throws InterruptedException {
        Thread t = new Thread() {
            @Override
            public void run() {

                while(true) {
                    button_next();
                    try {
                        Thread.sleep(750);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                    
            }
        };
    }

}
