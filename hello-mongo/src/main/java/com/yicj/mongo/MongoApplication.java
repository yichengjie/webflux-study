package com.yicj.mongo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//https://my.oschina.net/u/3121956/blog/2874109

@SpringBootApplication
public class MongoApplication {

    public static void main(String[] args) {
        /**
         * db.createUser(
         *  {
         *   user:"yicj",
         *   pwd:"123456",
         *   roles:["dbAdminAnyDatabase","userAdmin","dbAdmin","readWriteAnyDatabase"]
         *  }
         * )
         */
        //db.createUser({ user: 'admin', pwd: 'admin', roles: [ { role: "root", db: "admin" } ] });
        SpringApplication.run(MongoApplication.class, args) ;
    }
}
