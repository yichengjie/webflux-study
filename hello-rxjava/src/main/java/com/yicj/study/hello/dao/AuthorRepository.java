package com.yicj.study.hello.dao;

import com.yicj.study.hello.model.entity.Author;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import java.util.Optional;

@Slf4j
@Component
public class AuthorRepository {

    public Optional<Author> findById(String authorId) {
        Author author = Author.builder()
                .id(authorId)
                .name("author name A")
                .build();
        log.info("===> AuthorRepository  findById ...");
        return Optional.of(author) ;
    }
}
