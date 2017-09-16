package com.eyck.rxjavademo.test_demo;

/**
 * Created by Eyck on 2017/8/31.
 */

public class User {
    String name;
    int age;

    public User(String name, int age) {
        this.name = name;
        this.age = age;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }
}
