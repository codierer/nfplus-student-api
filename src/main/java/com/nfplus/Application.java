package com.nfplus;



import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;


/**
 * @author zac
 * @description:程序启动入口
 * @data 2020 2020/3/11 11:54
 */
@SpringBootApplication
@MapperScan("com.nfplus.mapper")
public class Application {

    public static void main(String[] args){
        //默认启动方式
        SpringApplication.run(Application.class, args);

    }
}
