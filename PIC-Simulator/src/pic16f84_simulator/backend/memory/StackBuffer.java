package pic16f84_simulator.backend.memory;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.GUI;
import pic16f84_simulator.frontend.collections.StackViewer;

public class StackBuffer {
    
    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
    public StackBuffer() {
        allow = Utils.allow(allow, this);
        this.stack = new int[8];
    }
    
    private int tos = 0; // = Stackpointer --> points to TopOfStack == next cell to be written (== 1 above last written cell)
    private int[] stack;
    
    
    /*
     * pushes (pc+1 + offset) onto stack
     */
    public void push(int offset) {
        this.stack[tos] = MC.control.pc() + 1 + offset;
        tos = (tos + 1) % 8; // tos++
        if(GUI.modus) {
            StackViewer.updateStack();
        }
    }
    
    public void push() {
        push(0);
    }
    
    /*
     * pops newest entry from stack into the pc 
     */
    public void pop() {
       tos = (tos + 7) % 8; // tos--
       MC.control.pc(this.stack[tos]);
       if(GUI.modus) {
           StackViewer.updateStack();
       }  
    }

    /*
     * for GUI
     */
    public String[][] loadStack() {
        String[][] data = new String [8][3];
        for(int i = 0; i < data.length; i++) {
            data[stack.length-i-1][1] = Integer.toString(this.stack[i]);
            if(((tos + 7) % 8) == i) {
                data[stack.length-i-1][0] = "-->";
                data[stack.length-i-1][2] = "<--";
            }else {
                data[stack.length-i-1][0] = "   ";
                data[stack.length-i-1][2] = "   ";
            }
        }
        return data;
    }
    
    /*
     * only for testing
     */
    public int getTOSValue() {
        if(((tos + 7) % 8) >= 0) {
            return this.stack[(tos + 7) % 8];
        }else {
            throw new IndexOutOfBoundsException("Stack is empty");
        }
        
    }
    
    public int[] getStack() {
        return this.stack;
    }
    
    public int getTOS() {
        return this.tos;
    }
    
    
    /**
     * --------------------------------------- danger resets ---------------------------------------------------
     */
    
    public void resetStack() {
        this.stack = new int[8];
    }
    
    public void resetTOS() {
        this.tos = 0;
    }
    
    public void full_resetStack() {
        resetStack();
        resetTOS();
    }
    
}
