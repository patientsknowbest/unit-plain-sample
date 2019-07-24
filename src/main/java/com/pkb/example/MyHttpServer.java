package com.pkb.example;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.TimeUnit;

import com.pkb.unit.Bus;
import com.pkb.unit.Unit;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpServer;

public class MyHttpServer extends Unit {
    public static final String UNIT_ID = "http-server";
    private final MutableConfig mutableConfig;

    private HttpServer httpServer;

    public MyHttpServer(Bus bus, MutableConfig mutableConfig) {
        super(UNIT_ID, bus, 1, TimeUnit.SECONDS);
        this.mutableConfig = mutableConfig;
        addDependency(MutableConfig.UNIT_ID);
    }

    protected HandleOutcome handleStart() {
        try {
            httpServer = HttpServer.create(new InetSocketAddress(mutableConfig.getPort()), mutableConfig.getPort());
            httpServer.createContext("/", this::index);
            httpServer.start();
            return HandleOutcome.SUCCESS;
        } catch (IOException e) {
            return HandleOutcome.FAILURE;
        }
    }

    private void index(HttpExchange ex) {
        try {
            ex.sendResponseHeaders(200, 0);
            ex.getResponseBody().write("yo".getBytes());
            ex.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected HandleOutcome handleStop() {
        httpServer.stop(1);
        return HandleOutcome.SUCCESS;
    }
}
