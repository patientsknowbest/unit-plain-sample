package com.pkb.example;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.pkb.unit.Bus;
import com.pkb.unit.LocalBus;

public class Main {
    public static void main(String[] args) {
        Bus bus = new LocalBus();
        MutableConfig mutableConfig = new MutableConfig(bus);
        MyHttpServer myHttpServer = new MyHttpServer(bus, mutableConfig);
        myHttpServer.enable();

        Scanner scan = new Scanner(System.in);

        while (true){
            System.out.println("Enter new port number");
            try{
                mutableConfig.setPort(scan.nextInt());
            }catch(InputMismatchException e){
                System.out.println("Input has to be a number. ");
            }
        }
    }
}
