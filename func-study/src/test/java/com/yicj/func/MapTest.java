package com.yicj.func;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.yicj.func.model.StudentScore;
import com.yicj.func.service.StudentScoreService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @program: webflux-study
 * @description:
 * @author: yicj1
 * @create: 2022-01-19 08:48
 **/
@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = FuncApplication.class)
public class MapTest {

    @Autowired
    private StudentScoreService studentScoreService ;
    @Autowired
    private ObjectMapper objectMapper ;

    @Test
    public void merge() throws Exception {
        List<StudentScore> studentScoreList = studentScoreService.buildStudentScoreList() ;

        Map<String, Integer> studentScoreMap2 = new HashMap<>();
        studentScoreList.forEach(
                studentScore -> studentScoreMap2.merge(
                    studentScore.getStuName(),
                    studentScore.getScore(),
                    Integer::sum
                )
        );
        log.info("===> {}", objectMapper.writeValueAsString(studentScoreMap2));
    }

    @Test
    public void compute(){
        Map<String,String> map = new HashMap<>() ;
        map.put("张三","张三测试数据...") ;
        map.put("李四","李四测试数据...") ;
        map.put("王五","王五测试数据...") ;
        map.put("赵六","赵六测试数据...") ;
        map.compute("李四", (key, value) -> {
//            if (value == null){
//                value = "" ;
//            }else {
//                value = value +"1" ;
//            }
//            return value ;
            return null ;
        });

        log.info("map : {}", map);
    }

}
