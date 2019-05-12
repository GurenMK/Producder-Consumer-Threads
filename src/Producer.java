import java.util.ArrayList;
import java.util.Random;

public class Producer extends Thread{
    ArrayList<Character> buffer;
    ArrayList<Character> products = new ArrayList<>(); //created products

    public Producer(ArrayList<Character> b){
        this.buffer = b;
        this.setName("Producer Thread");
    } //end constructor

    public void run(){
        while(true){
            //produce item
            Random r = new Random();
            char c = (char)(r.nextInt(26) + 'a');
            products.add(c);

            try {
                ProdconSync.prod.acquire();
                ProdconSync.mutex.acquire();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            //deposit item into empty buffer slot
            char a = products.get(0);
            buffer.add(a); //add item from products array
            products.remove(0); //remove newly added item from products
            System.out.println(Thread.currentThread().getName() + " deposited item: " + a + " *** " + buffer);

            ProdconSync.mutex.release();
            ProdconSync.con.release(); //signal the consumer to try to access the buffer
        } //end while
    } //end run
} //end class

