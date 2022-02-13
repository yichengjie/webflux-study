package com.yicj.study.model;

import lombok.Data;

import java.io.Serializable;

@Data
public class TicketInfo implements Serializable {
    private String name ;
    private String address ;
}
