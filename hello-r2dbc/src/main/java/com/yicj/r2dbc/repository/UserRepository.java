package com.yicj.r2dbc.repository;

import com.yicj.r2dbc.entity.UserInfo;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends ReactiveCrudRepository<UserInfo, Long> {

}
