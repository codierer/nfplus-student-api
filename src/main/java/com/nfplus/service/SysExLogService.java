package com.nfplus.service;

import com.nfplus.bean.SysExLog;
import com.nfplus.mapper.SysExLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zac
 * @description:
 * @data 2020 2020/3/17 17:14
 */
@Service
public class SysExLogService {

    @Autowired
    private SysExLogMapper sysExLogMapper;

    /**
     * 新增异常写入
     * @param sysExLog
     * @return
     */
    public int insert(SysExLog sysExLog){
        return sysExLogMapper.insert(sysExLog);
    }
}
