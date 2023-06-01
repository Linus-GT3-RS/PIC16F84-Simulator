package pic16f84_simulator.backend.memory;
import pic16f84_simulator.MC;
import pic16f84_simulator.backend.tools.Utils;

public class StackBuffer {
    
    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
    public StackBuffer() {
        allow = Utils.allow(allow, this);
        this.stack = new int[8];
    }
    
    private int tos = 0; // = Stackpointer --> points to TopOfStack
    private int[] stack;
    
    
    /*
     * pushes (pc+1 + offset) onto stack
     */
    public void push(int offset) {
        this.stack[tos] = MC.control.pc + 1 + offset;
        tos = (tos + 1) % 8; // tos++
    }
    
    public void push() {
        push(0);
    }
    
    /*
     * pops newest entry from stack into the pc
     */
    public void pop() {
       tos = (tos + 7) % 8; // tos--
       MC.control.pc = this.stack[tos];
    }

}
