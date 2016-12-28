/**
 * Created by jmaharjan on 12/28/16.
 */
public class ThreadConsolePrint {

    public class MyRunnable implements Runnable {
        public void run() {
            //Code
        }
    }

    public static void main(String[] args) {
        int count = 0;
        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.print(".");
            }
        });

        try {
            System.out.print("RUNNING");
            while (count<6) {
                java.lang.Thread.sleep(1000L);
                thread.run();
                count++;
                if(count == 6){
                    System.out.print("\nRUNNING");
                    count =0;
                }

            }

        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

}
