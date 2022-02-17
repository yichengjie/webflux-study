package com.yicj.study.hello.web.controller;

import com.yicj.study.hello.model.request.AddBookRequest;
import com.yicj.study.hello.model.response.BaseWebResponse;
import com.yicj.study.hello.service.HelloService;
import com.yicj.study.hello.web.request.AddBookWebRequest;
import io.reactivex.Single;
import io.reactivex.schedulers.Schedulers;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.net.URI;


@Slf4j
//@RestController
@Controller
public class HelloController {

    @Autowired
    private HelloService helloService ;

    @PostMapping(
        value = "/rx/hello",
        consumes = MediaType.APPLICATION_JSON_VALUE,
        produces = MediaType.APPLICATION_JSON_VALUE
    )
    public Single<ResponseEntity<BaseWebResponse>> addBook(@RequestBody AddBookWebRequest addBookWebRequest) {
        log.info("===> request main start");
        return helloService.addBook(toAddBookRequest(addBookWebRequest))
                .subscribeOn(Schedulers.io())
                .map(s ->{
                    log.info("===> ret s value : {}", s);
                    return ResponseEntity.created(URI.create("/api/books/" + s)).body(BaseWebResponse.successNoData()) ;
                    //return BaseWebResponse.successNoData() ;
                });
    }

    private AddBookRequest toAddBookRequest(AddBookWebRequest addBookWebRequest) {
        log.info("===> toAddBookRequest ...");
        AddBookRequest addBookRequest = new AddBookRequest();
        BeanUtils.copyProperties(addBookWebRequest, addBookRequest);
        return addBookRequest;
    }
}
