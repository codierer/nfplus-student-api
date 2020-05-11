package com.nfplus.service;

import com.github.pagehelper.PageHelper;
import com.nfplus.bean.StudentAssess;
import com.nfplus.mapper.StudentAssessMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * @author zac
 * @description:
 * @data 2020 2020/3/11 17:29
 */

@Service
public class StudentAssessService {

    @Autowired
    private StudentAssessMapper studentAssessMapper;

    /**
     * 通过内容新增一条记录
     * @param studentAssess
     * @return
     */
    public int insert(StudentAssess studentAssess) {
        return studentAssessMapper.insert(studentAssess);
    }

    /**
     * 通过id查询原始记录，将新内容与对应字段修改
     * @param studentAssess
     * @return
     */
    public int update(StudentAssess studentAssess) {
        return studentAssessMapper.updateByPrimaryKey(studentAssess);
    }

    public StudentAssess queryById(int id){
        //Example example = new Example(StudentAssess.class);
        return studentAssessMapper.selectByPrimaryKey(id);
    }

    /**
     * 通过id删除数据
     * @param id
     * @return
     */
    public int deleteById(Integer id) {
        return studentAssessMapper.deleteByPrimaryKey(id);
    }


    /**
     * 关键词查询
     * @param key
     * @return
     */
    public List<StudentAssess> queryListByKey(String key, Integer page, Integer pageSize){
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        Example example = new Example(StudentAssess.class);
        Example.Criteria criteria= example.createCriteria();
        if ( key != null ){
            criteria.orEqualTo("schoolName",key).orEqualTo("studentName",key).orEqualTo("courseName", key);
        }
        example.orderBy("id").asc();
        return studentAssessMapper.selectByExample(example);
    }

    /**
     * 条件分页查询
     * @param studentAssess
     * @param page
     * @param pageSize
     * @return
     */
    public List<StudentAssess> queryListPage(StudentAssess studentAssess, Integer page, Integer pageSize) {
        if (page != null && pageSize != null) {
            PageHelper.startPage(page, pageSize);
        }
        Example example = new Example(StudentAssess.class);
        Example.Criteria criteria = example.createCriteria();
        criteria.andEqualTo("studentName", studentAssess.getStudentName());
        //String schoolName = studentAssess.getSchoolName();
        criteria.andEqualTo("schoolName", studentAssess.getSchoolName());
        criteria.andEqualTo("courseName", studentAssess.getCourseName());
        criteria.andEqualTo("score", studentAssess.getScore());
        example.orderBy("id").asc();
        List<StudentAssess> studentAssessList = studentAssessMapper.selectByExample(example);
        return studentAssessList;
    }
}
