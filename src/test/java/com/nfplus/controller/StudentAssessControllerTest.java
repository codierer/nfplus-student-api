package com.nfplus.controller;


import com.alibaba.fastjson.JSONObject;
import com.nfplus.bean.StudentAssess;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author zac
 * @description:测试CUID 功能
 * @data 2020 2020/3/12 14:46
 */

@ExtendWith(SpringExtension.class)
@SpringBootTest
@Transactional
@DisplayName("Test StudentAssessController")
@WebAppConfiguration
public class StudentAssessControllerTest {

    @Autowired
    private WebApplicationContext webApplicationContext;
    private MockMvc mockMvc;
    private static StudentAssess sa;

    @BeforeEach
    public void request() throws Exception{
        mockMvc = MockMvcBuilders.webAppContextSetup(webApplicationContext).build();
    }


    @Test
    @DisplayName("Test queryById...")
    void queryByIdTest() throws Exception {

        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/query/1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int statue = mvcResult.getResponse().getStatus();
        int length = mvcResult.getResponse().getContentLength();
        String type = mvcResult.getResponse().getContentType();
        String strResonse = mvcResult.getResponse().getContentAsString();
        JSONObject jsonResponse = JSONObject.parseObject(strResonse);
        String data = jsonResponse.getString("data");
        StudentAssess sa =  JSONObject.parseObject(data, StudentAssess.class);
        assertEquals(statue, 200);
        assertEquals("application/json",type);
        assertEquals(1, sa.getId());

    }

    @Test
    @DisplayName("Test Delete...")
    void deleteTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/delete?id=1")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int statue = mvcResult.getResponse().getStatus();
        int length = mvcResult.getResponse().getContentLength();
        String type = mvcResult.getResponse().getContentType();
        assertEquals(200, statue);
    }

    @Test
    @DisplayName("Test Update...")
    void updateTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/update?id=1&stuName=测评")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int statue = mvcResult.getResponse().getStatus();
        assertEquals(200, statue);
    }

    @Test
    @DisplayName("Test Insert")
    void insertTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/insert?schName=大学&corName=化学&stuName=测评")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int statue = mvcResult.getResponse().getStatus();
        int length = mvcResult.getResponse().getContentLength();
        String type = mvcResult.getResponse().getContentType();
        assertEquals(200, statue);
    }

    @Test
    @DisplayName("Test queryList...")
    void queryListTest() throws Exception {
        MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.get("/querylist?key=张三&pageSize=3")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andDo(MockMvcResultHandlers.print())
                .andReturn();
        int statue = mvcResult.getResponse().getStatus();
        int length = mvcResult.getResponse().getContentLength();
        String type = mvcResult.getResponse().getContentType();
        String strResonse = mvcResult.getResponse().getContentAsString();
        JSONObject jsonResponse = JSONObject.parseObject(strResonse);
        String data = jsonResponse.getString("data");
        List listData = JSONObject.parseObject(data, List.class);
        assertEquals(3, listData.size());
    }


}
