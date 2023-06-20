package pic16f84_simulator.frontend.controller;

import java.util.Arrays;

import javax.swing.table.DefaultTableModel;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.GUI;
import pic16f84_simulator.frontend.collections.PinSelector;
import pic16f84_simulator.frontend.pm.TestprogrammViewer;
import pic16f84_simulator.frontend.ram.Ram_gui;

public class ButtonInteraction {
    
    static public Thread t;
    static public boolean nothread = true;
    static private boolean runnable = true;
    private static boolean isStopped = true;
    
    static public int timer;
    
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
        // TODO Laufzeitzähler reset
        // TODO Breakpoints reset? --> breakpoints würd ich lassen, frage is nur, ob man da irwas refreshen muss oder so.. warsch den zähler oder
        
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
                        try { Thread.sleep(400); } catch (InterruptedException e) { e.printStackTrace(); }
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
        if(TestprogrammViewer.BreakPoints.size() > counter) {
            breakpoint = TestprogrammViewer.BreakPoints.get(counter);
            counter++;
        }        
        if(MC.control.pc() == breakpoint) {
            runnable = false;
        }
        else {
            runnable =  true;
        }
        
    }

}
