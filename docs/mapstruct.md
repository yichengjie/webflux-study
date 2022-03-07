1. 引入依赖
    ```xml
     <dependencies>
      <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct</artifactId>
            <version>1.4.2.Final</version>
        </dependency>
    
        <dependency>
            <groupId>org.mapstruct</groupId>
            <artifactId>mapstruct-processor</artifactId>
            <version>1.4.2.Final</version>
        </dependency>
    
        <dependency>
            <groupId>org.projectlombok</groupId>
            <artifactId>lombok</artifactId>
            <version>1.18.20</version>
        </dependency>
    
         <!-- javax.annotation.Generated 类找不到错误-->
        <dependency>
            <groupId>javax.annotation</groupId>
            <artifactId>javax.annotation-api</artifactId>
            <version>1.3.2</version>
        </dependency>
     </dependencies>
    ```
2. 添加plugin
   ```xml
   <plugin>
       <groupId>org.apache.maven.plugins</groupId>
       <artifactId>maven-compiler-plugin</artifactId>
       <version>3.8.1</version>
       <configuration>
           <source>1.8</source>
           <target>1.8</target>
           <annotationProcessorPaths>
               <path>
                   <groupId>org.projectlombok</groupId>
                   <artifactId>lombok</artifactId>
                   <version>1.18.20</version>
               </path>
               <path>
                   <groupId>org.mapstruct</groupId>
                   <artifactId>mapstruct-processor</artifactId>
                   <version>${mapstruct.version}</version>
               </path>
           </annotationProcessorPaths>
       </configuration>
   </plugin>
   ```
3. 编写do与bo实体
   ```java
   // do 实体
   @Data // 新增
   @Accessors(chain = true)
   public class UserDO {
       // 用户编号
       private Integer id;
       // 用户名
       private String username;
       // 密码
       private String password;
   }
   // bo 实体
   @Data // 新增
   @Accessors(chain = true)
   public class UserBO {
      // 用户编号
      private Integer id;
      // 用户名
      private String username;
      // 密码
      private String password;
   }
   ```
4. 编写convertor转换类
   ```java
   @Mapper // <1>
   public interface UserConvert {
       UserConvert INSTANCE = Mappers.getMapper(UserConvert.class); // <2>
   
       UserBO convert(UserDO userDO);
   }
   ```
5. 单元测试
   ```java
   @Slf4j
   public class UserBOTest {
       @Test
       public void hello() {
           // 创建 UserDO 对象
           UserDO userDO = new UserDO()
                   .setId(1)
                   .setUsername("yudaoyuanma")
                   .setPassword("buzhidao");
           // <X> 进行转换
           UserBO userBO = UserConvert.INSTANCE.convert(userDO);
           System.out.println(userBO.getId());
           System.out.println(userBO.getUsername());
           System.out.println(userBO.getPassword());
       }
   }
   ```