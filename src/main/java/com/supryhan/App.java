package com.supryhan;

public abstract class App {

    protected abstract void run();

    protected static void launch(App app) {
        app.run();
    }
}