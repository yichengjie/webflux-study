package com.yicj.func.event.impl;

import com.yicj.func.event.MyEventListener;
import com.yicj.func.event.MyEventProcessor;

import java.util.Arrays;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

public class DefaultEventProcessor implements MyEventProcessor {

    private MyEventListener<String> eventListener ;

    private ScheduledExecutorService executor = Executors.newSingleThreadScheduledExecutor() ;


    @Override
    public void register(MyEventListener eventListener) {
        this.eventListener = eventListener ;
    }

    @Override
    public void dataChunk(String... values) {
        executor.schedule(() -> eventListener.onDataChunk(Arrays.asList(values)),
                500,
                TimeUnit.MICROSECONDS) ;
    }

    @Override
    public void processComplete() {
        executor.schedule(()-> eventListener.processComplete(),
                500,
                TimeUnit.MICROSECONDS) ;
    }
}
