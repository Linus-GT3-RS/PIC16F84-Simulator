package testCases.other;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;

class Test_Memory_StackBuffer {

    @Test
    void testPush() {
        // test without use off additional offset
        MC.control.pc = 3;
        MC.stack.push();
        MC.stack.pop();
        assertEquals(MC.control.pc, 4);
        
        // test Ringbuffer behavior: overflow
        for(int i = 0; i < 9; i++) {
            MC.control.pc = i;
            MC.stack.push();
        }   
        MC.stack.pop();
        assertEquals(MC.control.pc, 9);
        
        // test with use of additional offset
        MC.stack.push(4);
        MC.stack.pop();
        assertEquals(MC.control.pc, 14);        
    }

    @Test
    void testPop() {
        MC.control.pc = 2;
        MC.stack.push();
        MC.stack.pop();
        assertEquals(3, MC.control.pc);
        
        // test Ringbuffer behavior: underflow
        MC.control.pc = 0;
        for(int i = 0; i < 8; i++) {
            MC.stack.push(i);
        }
        MC.stack.pop();
        assertEquals(8, MC.control.pc);
    }
    
}
