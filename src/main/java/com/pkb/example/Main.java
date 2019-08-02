package com.pkb.example;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.pkb.unit.Bus;
import com.pkb.unit.LocalBus;
import com.pkb.unit.tracker.Tracker;

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
                Tracker.unitRestarted(bus, MyHttpServer.UNIT_ID).blockingSubscribe(restarted -> {
                    System.out.println("MyHttpServer restarted after config change");
                });
            }catch(InputMismatchException e){
                System.out.println("Input has to be a number. ");
            }
        }
    }
}
