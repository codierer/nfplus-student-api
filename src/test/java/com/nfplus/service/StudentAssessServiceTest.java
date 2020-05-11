package com.nfplus.service;


import com.nfplus.bean.StudentAssess;
import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.platform.engine.discovery.MethodSelector;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zac
 * @description:测试CUID 功能
 * @data 2020 2020/3/12 14:46
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@DisplayName("Test StudentAssessServiceTest")
public class StudentAssessServiceTest {

    @Autowired
    //private StudentAssessMapper studentAssessMapper;
    private StudentAssessService studentAssessService;

    private static StudentAssess sa;

    @BeforeAll
    static void init(){
        sa = new StudentAssess();
        sa.setCourseName("计算机组成原理");
        sa.setScore(99);
        sa.setSchoolName("广西师范大学");
        sa.setStudentName("李白");
    }

    @Test
    @Order(1)
    @DisplayName("Insert StudentAssess")
    void insertTest(){
        assertEquals(1, studentAssessService.insert(sa));
    }

    @Test
    @DisplayName("Delete StudentAssess")
    void deleteByIdTest(){
        assertAll("Delete StudentAssess Success",
                () -> assertEquals(0, studentAssessService.deleteById(-1)),
                () -> assertEquals(1,studentAssessService.deleteById(1)));
    }

    @Test
    @DisplayName("Update StudentAssess")
    void updateTest(){
        assertEquals(0,studentAssessService.update(sa));
        sa.setId(2);
        assertEquals(1,studentAssessService.update(sa));
    }

    @Test
    @DisplayName("Query StudentAssess")
    void queryByIdTest(){
        assertAll("Query By Id Success",
                () -> assertNull(studentAssessService.queryById(-1)),
                () -> assertNotNull(studentAssessService.queryById(1)));
    }

    @Test
    @DisplayName("Query List Page StudentAssess")
    void queryListPageTest(){
        StudentAssess studentAssess = new StudentAssess();
        studentAssess.setStudentName("张三");
        assertNotNull(studentAssessService.queryListPage(studentAssess,1,8));
        assertEquals(9,studentAssessService.queryListPage(studentAssess, 1, 10).size());
        assertEquals(2,studentAssessService.queryListPage(studentAssess,0,2).size());
    }

    @Test
    @DisplayName("Query List StudentAssess")
    void queryListByKeyTest(){
        assertNotNull(studentAssessService.queryListByKey("李白",1,2));
        assertEquals(9, studentAssessService.queryListByKey("张三",1,10).size());
    }

}
