package com.yicj.mongo.dao;

import com.yicj.mongo.model.Student;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
@Document(collection = "student_info")//此注解对应mongodb集合
public interface StudentDao extends MongoRepository<Student, String> {

    Student getAllByStudentName(String studentName) ;
}
