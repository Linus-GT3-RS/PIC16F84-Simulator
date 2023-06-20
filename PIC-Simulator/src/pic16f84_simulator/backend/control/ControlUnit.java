package pic16f84_simulator.backend.control;
import java.util.Arrays;

import pic16f84_simulator.MC;
import pic16f84_simulator.backend.control.instruction.BitOps;
import pic16f84_simulator.backend.control.instruction.ByteOps;
import pic16f84_simulator.backend.control.instruction.Instruction;
import pic16f84_simulator.backend.control.instruction.InstructionDecoder;
import pic16f84_simulator.backend.control.instruction.LitConOps;
import pic16f84_simulator.backend.control.twv.Prescaler;
import pic16f84_simulator.backend.memory.Register;
import pic16f84_simulator.backend.memory.SFR;
import pic16f84_simulator.backend.tools.Utils;
import pic16f84_simulator.frontend.GUI;
import pic16f84_simulator.frontend.controller.ButtonInteraction;
import pic16f84_simulator.frontend.pm.TestprogrammViewer;

public class ControlUnit {

    private static boolean allow = true; // secures the creation of ONLY ONE instance of this class
    public ControlUnit() {
        allow = Utils.allow(allow, this);      
    }

    private int pc = 0; // 13bits long

    public Register instrReg = new Register(14);
    public InstructionDecoder instrDecoder = new InstructionDecoder();


    public void exe() {        
        instrReg.write(MC.pm.readDataCell(pc)); // load instrReg
        Instruction instruct = instrDecoder.extractOpC(instrReg.read()); // load OpCode

        if(instruct instanceof ByteOps instr) {            
            int dBit = instrReg.readBit(instr.indexDbit)  ;
            int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg.read(), instr.fileStart, instrReg.read().length)); 
            if(SFR.getRPOflag() == 1) {
                indexFile = indexFile + 128;
            }
            if(indexFile == 0 || indexFile == 128)
            {
                indexFile = Utils.binaryToDec(MC.ram.readDataCell(SFR.FSR.asIndex()));
            }
            instr.exe(dBit, indexFile);
            pcpp();
        }
        if(instruct instanceof BitOps instr) {            
            int indexBit = Utils.binaryToDec(Arrays.copyOfRange(instrReg.read(), instr.dBitStart, instr.dBitEnd+1));
            indexBit = 7 - indexBit;
            int indexFile = Utils.binaryToDec(Arrays.copyOfRange(instrReg.read(), instr.fileStart, instrReg.read().length));
            if(SFR.getRPOflag() == 1) {
                indexFile = indexFile + 128;
            }
            if(indexFile == 0 || indexFile == 128)
            {
                indexFile = Utils.binaryToDec(MC.ram.readDataCell(SFR.FSR.asIndex()));
            }
            instr.exe(indexBit, indexFile); 
            pcpp();
        }
        if(instruct instanceof LitConOps instr){
            int[] k = new int[14 - instr.kStart()];
            System.arraycopy(instrReg.read(),instr.kStart(),  k, 0, (14-instr.kStart()));
            instr.exe(k);
            if(instr != LitConOps.RETURN && instr != LitConOps.GOTO) {
                pcpp();
            }
        }
        MC.timer.tryIncrInternalTimer(); // has to be called after pcpp to insure correct pc is pushed onto stack in case of tmr0Interrupt
        
        ButtonInteraction.timer++;
        if(GUI.modus) {
            GUI.programmtime.setText(Integer.toString(ButtonInteraction.timer) + " µs");
        }
    }

/*
 * ----------------------------------------- ProgrammCounter ----------------------------------------------
 */
    /*
     * increments pc while handeling pc-overflow
     * updates pcl with new val from pc
     */
    public void pcpp() {
        pc = (pc + 1) % 1024;
        int[] pc_bin = Utils.decToBinary(pc, 13);
        int[] newPCL = Arrays.copyOfRange(pc_bin, 5, pc_bin.length);
        MC.ram.writeDataCell(SFR.PCL.asIndex(), newPCL, false);
        if(GUI.modus) {
            TestprogrammViewer.highlightPCLine();
        }
    }

    public int pc() {
        return this.pc;
    }
    
    /*
     * sets pc 
     * updates pcl afterwards
     */
    public void pc(int val) {
        if(val >= 1024 || val < 0) {
            throw new IllegalArgumentException("newVal for pc cannot be: " + val);
        }
        this.pc = val;
        int[] pc_bin = Utils.decToBinary(pc, 13);
        int[] newPCL = Arrays.copyOfRange(pc_bin, 5, pc_bin.length);
        MC.ram.writeDataCell(SFR.PCL.asIndex(), newPCL, false);
        if(GUI.modus) {
            TestprogrammViewer.highlightPCLine();
        }
    }
    
    /*
     * is to be called AFTER writing to pcl
     * 
     * builds & sets new pc using pclatch and pcl
     *   - ignores pclatch 4-3, as all PICF8x do !!
     */
    public void updatePC() {
        int[] pclatch = MC.ram.readDataCell(SFR.PCLATH.asIndex());
        int[] pcl = MC.ram.readDataCell(SFR.PCL.asIndex());
        
        int[] newPC = new int[13]; // 00_000 0000_0000
        System.arraycopy(pclatch, 5, newPC, 2, 3); // sets 00_xxx 0000_0000
        System.arraycopy(pcl, 0, newPC, 5, pcl.length); // sets 00_000 xxxx_xxxx
        this.pc = Utils.binaryToDec(newPC);
        
        if(GUI.modus) {
            TestprogrammViewer.highlightPCLine();
        }
    }
    
    
    public void danger_setPC(int val) {
        this.pc = val;
    }



}
