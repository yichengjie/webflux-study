package com.yicj.study.hello.model.entity;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Author {
    private String id ;
    private String name ;
}
