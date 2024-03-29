package multithreading;

class Waiter implements Runnable {
    private boolean shouldFinish;
    void finish() { shouldFinish = true; }
    public void run() {
        long iteration = 0;
        while (!shouldFinish) {
            iteration++;
        }
        System.out.println("Finished after: " + iteration);
    }
}

class DataRace {
    public static void main(String[] args) throws InterruptedException {
        Waiter waiter = new Waiter();
        Thread waiterThread = new Thread(waiter);
        waiterThread.start();
        waiter.finish();
        waiterThread.join();
    }
}

