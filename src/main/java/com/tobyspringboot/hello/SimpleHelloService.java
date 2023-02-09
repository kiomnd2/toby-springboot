package com.tobyspringboot.hello;

public class SimpleHelloService implements HelloService {

    public String sayHello(String name) {
        return "hello " + name;
    }

}
