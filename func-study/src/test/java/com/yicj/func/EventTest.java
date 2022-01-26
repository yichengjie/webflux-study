package com.yicj.func;


import com.yicj.func.event.MyEventListener;
import com.yicj.func.event.MyEventProcessor;
import com.yicj.func.event.impl.DefaultEventProcessor;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import reactor.core.publisher.Flux;
import java.util.List;

@Slf4j
public class EventTest {

    @Test
    public void producingDirect() throws InterruptedException {
        log.info("start ...");
        MyEventProcessor eventProcessor = new DefaultEventProcessor();
        eventProcessor.register(new MyEventListener<String>() {
            @Override
            public void onDataChunk(List<String> chunk) {
                for (String s: chunk){
                    log.info("value : {}", s);
                }
            }

            @Override
            public void processComplete() {
                log.info("complete !");
            }
        });

        eventProcessor.dataChunk("foo","bar", "baz");
        eventProcessor.processComplete();
        log.info("end ...");
        Thread.sleep(1000);
    }

    @Test
    public void producingCreate() throws InterruptedException {
        log.info("start ...");
        MyEventProcessor eventProcessor = new DefaultEventProcessor();

        Flux<String> bridge = Flux.create(sink ->{
            eventProcessor.register(new MyEventListener<String>() {
                @Override
                public void onDataChunk(List<String> chunk) {
                    for (String s: chunk){
                        log.info("processor next value : {}", s);
                        sink.next(s) ;
                    }
                }

                @Override
                public void processComplete() {
                    log.info("processor complete !");
                    sink.complete();
                }
            });
        }) ;

        bridge.subscribe(
            value -> log.info("value : {}", value),
            err -> log.error("error :", err),
            () -> log.info("complete !")
        ) ;

        eventProcessor.dataChunk("foo","bar", "baz");
        eventProcessor.processComplete();
        log.info("end ...");
        Thread.sleep(1000);
    }

    @Test
    public void handle(){
        Flux<Object> handle = Flux.just(-1, 30, 13, 9, 20)
            .handle((i, sink) -> {
                String letter = alphabet(i);
                if (letter != null) {
                    sink.next(letter);
                }
            });
        handle.subscribe(value -> log.info("value : {}", value)) ;
    }

    public String alphabet(int letterNumber){
        if (letterNumber < 1 || letterNumber > 26){
            return null ;
        }
        int letterIndexAscii = 'A' + letterNumber -1 ;
        return "" + (char) letterIndexAscii ;

    }

}
