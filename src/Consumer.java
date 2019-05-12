import java.util.ArrayList;

public class Consumer extends Thread{
    ArrayList<Character> buffer;

    public Consumer(ArrayList<Character> b){
        this.buffer = b;
        this.setName("Consumer Thread");
    } //end constructor

    public void run(){
        while(true){

            try {
                ProdconSync.con.acquire();
                ProdconSync.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //remove items from the full slot of buffer
            char c = buffer.get(0);
            buffer.remove(0); //consumer item
            System.out.println(Thread.currentThread().getName() + " consumed item: " + c + " *** " + buffer);

            ProdconSync.mutex.release();
            ProdconSync.prod.release(); //signal the producer to try to access the buffer
        } //end while
    } //end run
} //end class

