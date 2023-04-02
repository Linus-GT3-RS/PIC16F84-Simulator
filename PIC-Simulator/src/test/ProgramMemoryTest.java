package test;

import static org.junit.jupiter.api.Assertions.*;

import java.nio.file.FileSystems;
import java.nio.file.Path;

import org.junit.jupiter.api.Test;
import pic16f84_simulator.backend.memory.ProgramMemory;

class ProgramMemoryTest {

    @Test
    void testReadTestPrograme() {
        ProgramMemory pm = new ProgramMemory();
        pm.readTestProgram( 
                "Files\\TPicSim1.LST"
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


