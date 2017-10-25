package io.springboot.example.controller;

/**
 * Created by sakther on 10/07/17.
 */
public class Hola {

    public static void main(String ... args) {

        Exception e = new RuntimeException("custom exception");

        System.out.println(e.getClass().getSimpleName());
    }
}
