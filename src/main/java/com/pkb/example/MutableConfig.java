package com.pkb.example;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import com.pkb.unit.Bus;
import com.pkb.unit.Unit;

public class MutableConfig extends Unit {
    public static final String UNIT_ID = "mutable-config";

    private HashMap<String, Integer> config;

    public MutableConfig(Bus bus) {
        super(UNIT_ID, bus, 1, TimeUnit.SECONDS);
        config = new HashMap<>();
    }

    @Override
    protected HandleOutcome handleStart() {
        return HandleOutcome.SUCCESS;
    }

    @Override
    protected HandleOutcome handleStop() {
        return HandleOutcome.SUCCESS;
    }

    public int getPort() {
        return config.getOrDefault("port", 8080);
    }

    public void setPort(int x) {
        config.put("port", x);
        failed();
    }
}
