package core;

public class MySingleton {

    private static volatile MySingleton instance = null;

    private MySingleton() {
    }

    public static MySingleton getInstance() {
        if (instance == null) {
            synchronized (MySingleton.class) {
                if (instance == null) {
                    instance = new MySingleton();
                }
            }
        }
        return instance;
    }

    public static void main(String[] args) {
        System.out.println("Started");
        MySingleton created = getInstance();
        System.out.println("Completed");
    }
}
