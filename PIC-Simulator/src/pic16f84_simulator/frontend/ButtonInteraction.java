package pic16f84_simulator.frontend;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;

public class ButtonInteraction {
    static public Thread t;
    static private boolean runnable = true;
    static public boolean nothread = true;
    
    static public int timer;
    
    /*
     * for Help Methods
     */
    static int counter = 0; // counter for Breakpoints
    static int breakpoint = -1; // next Breakpoint
    
    static void button_next() {
        MC.control.exe();
        PinSelector.loadPins();
        
        // update W-Reg
        GUI.table_w.setModel(new DefaultTableModel(new Object[][] {{"W-Reg",Utils.binaryToHex(MC.alu.wReg.read())}},new String[] {"",""}));
        GUI.table_prs.setModel(new DefaultTableModel(new Object[][] {{"VT (T)",MC.prescaler.getPRS(0)},{"VT (W)",MC.prescaler.getPRS(1)}},new String[] {"",""}));

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
        if(nothread) {
            nothread = false;
            runnable = true;
            t = new Thread() {
                @Override
                public void run() {
                    boolean greater = MC.control.pc() > -1;
                    boolean smaller = MC.control.pc() < TestprogrammViewer.pcLines.size()-1;
                    
                    while(runnable && greater && smaller) {
                        button_next();
                        try {
                            Thread.sleep(200);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        runnable();
                        greater = MC.control.pc() > -1;
                        smaller = MC.control.pc() < TestprogrammViewer.pcLines.get(TestprogrammViewer.pcLines.size()-1);
                    }
                    nothread =  true;
                }
            };
            t.start();
        }
    }
    
    private static void runnable() {
        
        
        if(TestprogrammViewer.BreakPoints.size()>counter) {
            breakpoint = TestprogrammViewer.BreakPoints.get(counter);
            counter++;
        }
        if(MC.control.pc() == breakpoint) {
            runnable = false;
        }else {
            runnable =  true;
        }
        
    }

}
