package com.yicj.study.hello.dao;

import com.yicj.study.hello.model.entity.Book;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class BookRepository {


    public Book save(Book book){
        log.info("===> BookRepository  save ...");
        return book ;
    }
}
