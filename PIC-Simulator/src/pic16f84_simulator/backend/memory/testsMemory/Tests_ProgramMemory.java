package pic16f84_simulator.backend.memory.testsMemory;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import pic16f84_simulator.backend.memory.ProgramMemory;

class Tests_ProgramMemory {

    @Test
    void testReadTestPrograme() {
        ProgramMemory pm = new ProgramMemory();
        List<String> data = pm.readTestProgram( // how input relative path?
                "C:\\Users\\eduar\\Documents\\Informatik\\Rechnerarchitekturen\\pic-simulator\\Files\\TPicSim1.LST"
                );
        assertArrayEquals(pm.getMemory(0),new int[] {0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
     }

    @Test
    void testStore() {
        ProgramMemory pm = new ProgramMemory();
        pm.Store("00003011");
        assertArrayEquals(pm.getMemory(0),new int[] {0,0,1,1,0,0,0,0,0,0,0,1,0,0,0,1} );
    }
}


