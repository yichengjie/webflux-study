package com.yicj.study.hello.service;


import com.yicj.study.hello.model.request.AddBookRequest;
import io.reactivex.Single;

public interface HelloService {
     Single<String> addBook(AddBookRequest addBookRequest)  ;
}
