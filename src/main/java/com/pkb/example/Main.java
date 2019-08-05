package com.pkb.example;

import java.util.InputMismatchException;
import java.util.Scanner;

import com.pkb.unit.Bus;
import com.pkb.unit.LocalBus;
import com.pkb.unit.tracker.Tracker;

import io.reactivex.observers.TestObserver;

public class Main {
    public static void main(String[] args) throws InterruptedException {
        Bus bus = new LocalBus();
        MutableConfig mutableConfig = new MutableConfig(bus);
        MyHttpServer myHttpServer = new MyHttpServer(bus, mutableConfig);
        myHttpServer.enable();

        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.println("Enter new port number");
            try {
                // Watch for restart of web server
                TestObserver<Boolean> testObserver = Tracker.unitRestarted(bus, MyHttpServer.UNIT_ID).test();

                // Change the config
                mutableConfig.setPort(scan.nextInt());

                // Enforce that restart happened before moving on
                testObserver.awaitCount(1);
                testObserver.assertResult(true);
            } catch (InputMismatchException e) {
                System.out.println("Input has to be a number. ");
            }
        }
    }
}
