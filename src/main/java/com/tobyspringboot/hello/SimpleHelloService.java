package com.tobyspringboot.hello;

import org.springframework.stereotype.Service;

@Service
public class SimpleHelloService implements HelloService {

    public String sayHello(String name) {
        return "hello " + name;
    }

}
