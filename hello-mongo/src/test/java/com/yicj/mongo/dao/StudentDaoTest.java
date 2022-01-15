package com.yicj.mongo.dao;

import com.yicj.mongo.MongoApplication;
import com.yicj.mongo.model.Student;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = MongoApplication.class)
public class StudentDaoTest {

    @Autowired
    private StudentDao studentDao ;

    @Test
    public void addOne(){
        for (Integer count = 0 ; count < 10; count++){
            Student student = new Student() ;
            student.setStudentId("student_" + count);
            student.setStudentName("Godfery" + count);
            student.setStudentAge(count);
            student.setStudentScore(98.5-count);
            student.setStudentBirthday(new Date());
            studentDao.save(student) ;
        }
    }

    @Test
    public void deleteById(){
        studentDao.deleteById("student_0");
    }

    @Test
    public void findById(){
        Optional<Student> student = studentDao.findById("student_1");
        log.info("====> student: {}" , student);
    }

    @Test
    public void getAll(){
        List<Student> list = studentDao.findAll();
        list.forEach(ele -> log.info("item : {}", ele));
    }
}
