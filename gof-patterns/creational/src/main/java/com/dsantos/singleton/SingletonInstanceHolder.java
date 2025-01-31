package com.dsantos.singleton;

public class SingletonInstanceHolder {

    private SingletonInstanceHolder() {
    }

    private static final class InstanceHolder {
        private static final SingletonInstanceHolder instance = new SingletonInstanceHolder();
    }

    public static SingletonInstanceHolder getInstance() {
        return InstanceHolder.instance;
    }

    public void showMessage() {
        System.out.println("Singleton instance");
    }
}