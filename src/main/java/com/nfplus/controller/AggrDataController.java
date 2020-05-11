package com.nfplus.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.nfplus.annotation.MyLog;
import com.nfplus.bean.StudentAssess;
import com.nfplus.service.StudentAssessService;
import com.nfplus.vo.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * @author zac
 * @description: 数据统计接口
 * @data 2020 2020/3/13 17:11
 */

@RestController
@RequestMapping("/aggrdata")
public class AggrDataController {

    private static final Logger logger = LoggerFactory.getLogger(AggrDataController.class);

    @Autowired
    private StudentAssessService studentAssessService;

    /**
     * 根据参数查询数据并按要求得到封装后的统计数据
     * 检索满足满足条件的数据返回List<StudentAssess> 类型
     * @return
     */
    @MyLog(value = "统计数据接口")
    @RequestMapping("/statistic")
    public Result staticData(Integer id, String stuName, String corName, String schName){
        StudentAssess sa = new StudentAssess();
        sa.setId(id);
        sa.setStudentName(stuName);
        sa.setCourseName(corName);
        sa.setSchoolName(schName);
        List<StudentAssess> studentAssessList= studentAssessService.queryListPage(sa,1,10);
        if (studentAssessList.size()==0){
            return Result.ok("查询无数据");
        }
        //listSA.parallelStream().map(StudentAssess::getCourseName).forEach(System.out::println);
        //提取schoolname 通过set集合可以获取唯一不同项
        Set<String> schSet = studentAssessList.stream().map(StudentAssess::getSchoolName).collect(Collectors.toSet());
        //schSet.stream().filter(s->sa.getSchoolName()==s).forEach(System.out::println);
        long count = studentAssessList.stream().map(StudentAssess::getScore).collect(Collectors.counting());
        Double sum = studentAssessList.stream().collect(Collectors.summingDouble(StudentAssess::getScore));
        Map<String, Map<String, List<StudentAssess>>> map = studentAssessList
                .stream()
                .collect(Collectors.groupingBy(StudentAssess::getSchoolName, Collectors.groupingBy(StudentAssess::getStudentName)));
        Set<String> saSet = studentAssessList.stream().map(s->s.getSchoolName()).collect(Collectors.toSet());
        List<Map<String, Object>> schList = new LinkedList<>();
        for (String school:saSet){
            List<StudentAssess> sameSch = studentAssessList.stream().filter(s->s.getSchoolName().equals(school)).collect(Collectors.toList());
            //遍历学生
            Set<String> stuSet = sameSch.stream().map(s->s.getStudentName()).collect(Collectors.toSet());
            List<Map<String, Object>> stuList = new LinkedList<>();
            for (String stu:stuSet){
                List<StudentAssess> sameStu = sameSch.stream().filter(s->s.getStudentName().equals(stu)).collect(Collectors.toList());
                List<Map<String, Object>> listCour = sameStu.stream().map(s->{
                    Map<String,Object> courMap = new HashMap<>();
                    courMap.put("courseName",s.getCourseName());
                    courMap.put("score",s.getScore());
                    return courMap;
                }).collect(Collectors.toList());
                Map<String, Object> stuMap = new HashMap<>();
                stuMap.put("name", stu);
                stuMap.put("totalScore", sameStu.stream().collect(Collectors.summingDouble(StudentAssess::getScore)));
                stuMap.put("course",listCour);
                stuList.add(stuMap);
            }
            Map<String, Object> schMap = new HashMap<>();
            schMap.put("students", stuList);//将相同学生信息合并
            schMap.put("totalScore", sameSch.stream().collect(Collectors.summingDouble(StudentAssess::getScore)));
            schMap.put("schoolName",school);
            schList.add(schMap);//不能直接添加等式右边
        }
        String schJSON = JSON.toJSONString(schList);
        return Result.ok(schList);
    }
}
