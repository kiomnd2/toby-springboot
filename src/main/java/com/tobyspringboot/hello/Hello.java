package com.tobyspringboot.hello;

public class Hello {

    private String name;

    private int count;

    public Hello(String name, int count) {
        this.name = name;
        this.count = count;
    }

    public String getName() {
        return name;
    }

    public Hello setName(String name) {
        this.name = name;
        return this;
    }

    public int getCount() {
        return count;
    }

    public Hello setCount(int count) {
        this.count = count;
        return this;
    }
}
