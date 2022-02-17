package com.yicj.study.hello.service.impl;

import com.yicj.study.hello.dao.AuthorRepository;
import com.yicj.study.hello.dao.BookRepository;
import com.yicj.study.hello.exception.EntityNotFoundException;
import com.yicj.study.hello.model.entity.Author;
import com.yicj.study.hello.model.entity.Book;
import com.yicj.study.hello.model.request.AddBookRequest;
import com.yicj.study.hello.service.HelloService;
import io.reactivex.Single;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;


@Service
public class HelloServiceImpl implements HelloService {

    @Autowired
    private AuthorRepository authorRepository ;

    @Autowired
    private BookRepository bookRepository ;

    public Single<String> addBook(AddBookRequest addBookRequest) {
        return saveBookToRepository(addBookRequest);
    }


    private Single<String> saveBookToRepository(AddBookRequest addBookRequest) {
        return Single.create(singleSubscriber -> {
            Optional<Author> optionalAuthor = authorRepository.findById(addBookRequest.getAuthorId());
            if (!optionalAuthor.isPresent())
                singleSubscriber.onError(new EntityNotFoundException());
            else {
                String addedBookId = bookRepository.save(toBook(addBookRequest)).getId();
                singleSubscriber.onSuccess(addedBookId);
            }
        });
    }
    private Book toBook(AddBookRequest addBookRequest) {
        Book book = new Book();
        BeanUtils.copyProperties(addBookRequest, book);
        book.setId(UUID.randomUUID().toString());
        book.setAuthor(Author.builder()
                .id(addBookRequest.getAuthorId())
                .build());
        return book;
    }
}
