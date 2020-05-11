package com.nfplus.service;

import com.nfplus.bean.SysOpLog;
import com.nfplus.mapper.SysOpLogMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author zac
 * @description:
 * @data 2020 2020/3/17 11:51
 */
@Service
public class SysOpLogService {

    @Autowired
    private SysOpLogMapper sysOpLogMapper;

    public int insert(SysOpLog sysOpLog){
        return sysOpLogMapper.insert(sysOpLog);
    }
}
