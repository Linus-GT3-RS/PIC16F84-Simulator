package testCases;

import static org.junit.Assert.assertThrows;
import static org.junit.jupiter.api.Assertions.*;
import pic16f84_simulator.backend.control.*;
import org.junit.jupiter.api.Test;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.memory.SFR;

class Test_WatchDog {

    @Test
    void testWatchDog() {
        MC.wdog.startWD();// -> WatchDog TimeOut occured -> read Console throw Exception
        assertEquals(0,MC.ram.readSpecificBit(SFR.STATUS.asIndex(), 3));
    }

}
