package pic16f84_simulator.backend.control.twv;
import java.util.TimerTask;
import java.util.concurrent.TimeUnit;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.SwingConstants;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.GUI;
import pic16f84_simulator.frontend.controller.ButtonInteraction;

public class WatchDog {

    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class    
    public WatchDog(){
        allow = Utils.allow(allow, this);
    }

    private Thread t;
    //private java.util.Timer timer;

    private int std = 18000; // standardVal in µs
    private boolean noThread = true; // ensures that only one WDog is running 
    private boolean runnable = false;
    private boolean notimeout = true;
    

    public void start() { // this.on = true;
        if(noThread) {
            noThread = false;
            runnable = true;
            notimeout = true;
            startWDThread();
        }
        
    }

    /*
     * creates a new Timer in background
     *  - after set time(= getTimer()) the run() is executed --> entering means WDog overflowed
     */
    private void startWDThread() {
        
        t = new Thread() {
            @Override
            public void run() {
                while(runnable && notimeout) {
                    System.out.println("running timer:" + ButtonInteraction.timer + " wDog " + getWDogTimerVal());
                    if(ButtonInteraction.timer >= getWDogTimerVal()) {
                        notimeout = false;
                    }
                }
                if(notimeout == false) {
                    ButtonInteraction.button_stop();
                    watchDogTimeOut();
                    GUI.updateGUI();
                    JFrame wdogFrame = new JFrame();
                    wdogFrame.setVisible(true);
                    wdogFrame.setTitle("WatchDog");
                    wdogFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                    wdogFrame.setBounds(600, 350, 300, 200);
                    JLabel text = new JLabel("WatchDog Timeout!");
                    text.setHorizontalAlignment(SwingConstants.CENTER);
                    wdogFrame.add(text);
                    System.out.println("WatchDog Timeout!");
                }
            }
        };
        t.start();
    }



    public void stop() { // this.on = false;
        if(noThread == false) {
            runnable = false;
            noThread = true;
        }
    }
    
    public static void watchDogTimeOut(){
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 3, 0);
        MC.ram.writeSpecificBit(SFR.STATUS.asIndex(), 4, 1);
        MC.control.pc(0);
        MC.ram.otherReset();
        // System.out.println("Watchdog timer has overflowed"); 
    }
    
    
    
    /*
     * ------------------------------ Getter & Setter -------------------------------------
     */
    public int getWDogTimerVal() {
        return this.std * MC.prescaler.getPRS(1);
    }
    
    // When SLEEP or CLRWDT
    public void clearPRS() {
        if(MC.ram.readSpecificBit(SFR.OPTION.asIndex(), 4) == 1) { // case 0 -> TMR
            MC.prescaler.clearPRS();
        }
         
    }

    

}
