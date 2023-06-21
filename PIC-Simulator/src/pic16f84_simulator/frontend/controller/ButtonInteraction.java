package pic16f84_simulator.frontend.controller;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import junit.framework.Test;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.GUI;
import pic16f84_simulator.frontend.collections.PinSelector;
import pic16f84_simulator.frontend.pm.TestprogrammViewer;
import pic16f84_simulator.frontend.ram.Ram_gui;

public class ButtonInteraction {
    
    public static Thread t;
    public static boolean nothread = true;
    private static boolean runnable = true;
    private static boolean isStopped = true;
    
    public static int timer;
    
    /**
     * for Help Methods
     */
    static int counter = 0; // counter for Breakpoints
    static int breakpoint = -1; // next Breakpoint
    
    
    public static void button_stop() {
        isStopped = true;
    }
    
    public static void button_ignore() {
        MC.control.pcpp();
    }
    
    public static void button_restart() {
        MC.ram.powerOnReset();
        MC.control.pc(0);
        MC.stack.full_resetStack();
        MC.alu.resetW_Reg();
        ButtonInteraction.timer = 0;
        
        // has to be last !!!
        GUI.updateGUI();
    }
    
    public static void button_next() {
        MC.control.exe();
        GUI.updateGUI(); 
    }
    
    public static void button_run() throws InterruptedException {
        if(nothread) {
            GUI.btn_restart.setEnabled(false);
            GUI.btn_next.setEnabled(false);
            GUI.btn_ignore.setEnabled(false);
            
            isStopped = false;
            nothread = false;
            runnable = true;
            t = new Thread() {                
                @Override
                public void run() {
                    boolean greater = MC.control.pc() > -1;
                    boolean smaller = MC.control.pc() < TestprogrammViewer.pcLines.size() -1;
                    
                    while(runnable && greater && smaller && isStopped == false) {
                        button_next();
                        try { Thread.sleep(10); } catch (InterruptedException e) { e.printStackTrace(); }
                        runnable();
                        greater = MC.control.pc() > - 1;
                        smaller = MC.control.pc() < TestprogrammViewer.pcLines.get(TestprogrammViewer.pcLines.size() -1);
                    }
                    GUI.btn_restart.setEnabled(true);
                    GUI.btn_next.setEnabled(true);
                    GUI.btn_ignore.setEnabled(true);
                    nothread = true;
                }
            };
            t.start();
        }
    }
    
    private static void runnable() {
        runnable = true;
        for(int i = 0; i < TestprogrammViewer.BreakPoints.size(); i++) {
            if(TestprogrammViewer.BreakPoints.get(i) == MC.control.pc()) {
                runnable = false;
                break;
            }
        }
    }

}
