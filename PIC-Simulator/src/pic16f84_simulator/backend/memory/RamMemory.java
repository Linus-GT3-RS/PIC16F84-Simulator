// Linus
package pic16f84_simulator.backend.memory;

public class RamMemory {

    
    
    
    

    public class Spielewiese {


        // Klassenmethode Ram
        public static void writeRAM(int adressIsIndex, int[] data) {
            // steht nur hier, dass man weis, dass es aufgerufen wird
            mirrorBank(adressIsIndex);


        }
        
        
        public static int mirrorBank(int index) {
            switch(index) {
            // Bsp. PCL
            case 130 -> {return 2;}
            // ... so weiter für die doppelten
            default -> {return index;}            
            }
        }
        

        public static void main(String[] args) {

            System.out.println(SFRRegisters.TMR0.indexInSFR());


            writeRAM(SFRRegisters.PCL.indexInSFR(), new int[2]);



            int[][] test = new int[][] {{0,1,1},{0,0,0,},{}};

            



        }

    }







}
