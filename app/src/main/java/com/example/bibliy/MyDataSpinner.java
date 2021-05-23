package com.example.bibliy;

public class MyDataSpinner {
    private final String name;
    private final int id;

    public MyDataSpinner(String name, int id) {
        this.name = name;
        this.id = id;
    }

    public MyDataSpinner(int id, String name) {
        this.name = name;
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return id;
    }
}

