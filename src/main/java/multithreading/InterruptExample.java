package multithreading;

public class InterruptExample extends Thread {
    public void run() {
        try {
            while (!isInterrupted()) {
                // Potentially infinity task
                System.out.println("Thread is running");
                Thread.sleep(1000);
            }
        } catch (InterruptedException e) {
            System.out.println("Interrupted via exception");
        } finally {
            System.out.println("Thread is stopping");
        }
    }

    public static void main(String[] args) {
        InterruptExample thread = new InterruptExample();
        thread.start();
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        thread.interrupt();
    }
}
