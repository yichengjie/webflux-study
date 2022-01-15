package com.yicj.mongo.dao;

import com.yicj.mongo.model.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Component;

@Component
public class UserInfoDao {
    @Autowired
    private MongoTemplate mongoTemplate ;

    public void saveUserInfo(UserInfo userInfo){
        mongoTemplate.save(userInfo) ;
    }

    public UserInfo findUserInfoByName(String username) {
        Query query = new Query(Criteria.where("name").is(username)) ;
        return mongoTemplate.findOne(query, UserInfo.class) ;
    }

    public void updateUserInfo(UserInfo userInfo){
        Query query = new Query(Criteria.where("id").is(userInfo.getId())) ;
        Update update = new Update().set("age", userInfo.getAge()) ;
        // 更新查询返回结果的第一条记录
        mongoTemplate.updateFirst(query, update, UserInfo.class) ;
        // 更新查询返回结果集的所有
        //mongoTemplate.updateMulti(query, update, UserInfo.class) ;
    }

    public void deleteUserInfo(Integer id){
        Query query = new Query(Criteria.where("id").is(id)) ;
        mongoTemplate.remove(query, UserInfo.class) ;
    }
}
