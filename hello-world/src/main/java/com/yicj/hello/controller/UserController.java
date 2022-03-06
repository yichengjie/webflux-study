package com.yicj.hello.controller;


import com.yicj.hello.dto.UserAddDTO;
import com.yicj.hello.dto.UserUpdateDTO;
import com.yicj.hello.vo.UserVO;
import org.reactivestreams.Publisher;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/users")
public class UserController {

    @GetMapping("/list")
    public Flux<UserVO> list(){
        // 查询列表
        List<UserVO> result = new ArrayList<>() ;
        result.add(new UserVO().setId(1).setUsername("zhangsan"));
        result.add(new UserVO().setId(2).setUsername("lisi"));
        result.add(new UserVO().setId(3).setUsername("wangwu"));
        // 返回列表
        return Flux.fromIterable(result) ;
    }


    /**
     * 获得指定用户编码的用户
     * @param id 用户编码
     * @return 用户
     */
    @GetMapping("/get")
    public Mono<UserVO> get(@RequestParam("id") Integer id){
        // 查询用户
        UserVO user = new UserVO().setId(id).setUsername("userName: " + id) ;
        // 返回
        return Mono.just(user) ;
    }

    /**
     * 添加用户
     * @param addDTO 添加用户信息dto
     * @return 添加成功的用户编号
     */
    @PostMapping("/add")
    public Mono<Integer> add(@RequestBody Publisher<UserAddDTO> addDTO){
        // 插入用户记录，返回编号
        Integer returnId = 1 ;
        // 返回用户编号
        return Mono.just(returnId) ;
    }


    /**
     * 添加用户
     * @param addDTO 添加用户信息 dto
     * @return 添加成功的用户编号
     */
    @PostMapping("/add2")
    public Mono<Integer> add2(Mono<UserAddDTO> addDTO){
        // 插入用户记录，返回编号
        Integer returnId = UUID.randomUUID().hashCode() ;
        // 返回用户编号
        return Mono.just(returnId) ;
    }

    /**
     * 更新指定用户编号的用户
     * @param updateDTO 更新用户信息dto
     * @return 是否修改成功
     */
    @PostMapping("/update")
    public Mono<Boolean> update(@RequestBody Publisher<UserUpdateDTO> updateDTO){
        // 更新用户记录
        Boolean success = true ;
        // 返回更新是否成功
        return Mono.just(success) ;
    }

    /**
     * 删除指定用户编号的用户
     * @param id 用户编号
     * @return 是否删除成功
     */
    @PostMapping("/delete") // url 修改为delete，RequestMethod修改成delete
    public Mono<Boolean> delete(@RequestParam("id") Integer id){
        // 删除用户记录
        Boolean success = false ;
        // 返回是否更新成功
        return Mono.just(success) ;
    }
}
