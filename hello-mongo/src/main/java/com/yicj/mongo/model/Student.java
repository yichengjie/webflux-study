package com.yicj.mongo.model;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;
import java.util.Date;

@Data
public class Student implements Serializable {
    @Id// 必须指定id列
    private String studentId;

    private String studentName;

    private Integer studentAge;

    private Double studentScore;

    private Date studentBirthday;
}
