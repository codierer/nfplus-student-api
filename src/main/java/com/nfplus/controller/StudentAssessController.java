package com.nfplus.controller;

import com.nfplus.annotation.MyLog;
import com.nfplus.bean.StudentAssess;
import com.nfplus.service.StudentAssessService;
import com.nfplus.util.ComUtil;
import com.nfplus.vo.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author zac
 * @description:
 * @data 2020 2020/3/11 17:40
 */
@RestController
public class StudentAssessController {

    @Autowired
    private StudentAssessService studentAssessService;

    //@MyLog(value = "查看student_assess记录")
    @RequestMapping("query/{id}")
    @ResponseBody
    public Result queryById(@PathVariable int id){
        return Result.ok(studentAssessService.queryById(id));
    }

    /**
     * 增加用户
     * @param stuName
     * @param corName
     * @param schName
     * @return
     */
    //@MyLog(value = "新增student_assess记录")
    @RequestMapping(value = "insert")
    @ResponseBody
    public Result insert(String stuName,
        @RequestParam(required = false) String corName,
        @RequestParam(required = false) String schName, Integer score){
        StudentAssess studentAssess = new StudentAssess();
        studentAssess.setStudentName(stuName);
        studentAssess.setCourseName(corName);
        studentAssess.setSchoolName(schName);
        studentAssess.setScore(score);
        studentAssess.setVersion(1);
        if (studentAssessService.queryListPage(studentAssess,null,null).size() == 0){
            studentAssessService.insert(studentAssess);
        }
        return Result.ok("新增成功");
    }

    //@MyLog(value = "删除student_assess记录")
    @RequestMapping(value = "delete")
    @ResponseBody
    public Result delete(Integer id){
        studentAssessService.deleteById(id);
        return Result.ok("删除成功");
    }

    /**
     * 更新数据,通过id判断更改数据的原始记录
     * @return
     */
    //@MyLog(value = "修改student_assess记录")
    @RequestMapping(value = "update")
    @ResponseBody
    public Result update(Integer id, String stuName,
        @RequestParam(required = false) String corName,
        @RequestParam(required = false) String schName, Integer score, Integer version){
        StudentAssess studentAssess = new StudentAssess();
        studentAssess.setId(id);
        studentAssess.setStudentName(stuName);
        studentAssess.setCourseName(corName);
        studentAssess.setSchoolName(schName);
        studentAssess.setScore(score);
        studentAssess.setVersion(version);
        //更新接口幂等
        //if (ComUtil.compareObject(studentAssess, studentAssessService.queryById(id))) {
            studentAssessService.update(studentAssess);
        //}
        return Result.ok("更新成功");
    }

    /**
     * 接受查询条件，和page分页项，以及 分页大小pagesize 分页查询
     * @param id
     * @param stuName
     * @param corName
     * @param schName
     * @param page
     * @param pageSize default=10
     * @return
     */
    //@MyLog(value = "查询student_assess记录")
    @RequestMapping(value = "querylist", method = RequestMethod.GET)
    @ResponseBody
    public Result queryList(Integer id, String stuName,
         @RequestParam(required = false) String corName,
         @RequestParam(required = false) String schName,
         @RequestParam(required = false) String key, Integer page, Integer pageSize) {
        if (page == null) {
            page = 1;
        }
        if (pageSize == null) {
            pageSize = 10;
        }
        StudentAssess studentAssess = new StudentAssess();
        studentAssess.setId(id);
        studentAssess.setStudentName(stuName);
        studentAssess.setCourseName(corName);
        studentAssess.setSchoolName(schName);
        List<StudentAssess> studentAssessList;
        if (key == null) {
            studentAssessList = studentAssessService.queryListPage(studentAssess, page, pageSize);
        } else {
            studentAssessList = studentAssessService.queryListByKey(key, page, pageSize);
        }
        return Result.ok(studentAssessList);
    }




}
