package com.yicj.func.event;

public interface MyEventProcessor {

    void register(MyEventListener eventListener) ;

    void dataChunk(String ... values) ;

    void processComplete() ;

}
