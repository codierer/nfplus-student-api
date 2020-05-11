package com.nfplus.util;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.MySqlMapper;

/**
 * @author zac
 * @description:
 * @data 2020 2020/3/11 22:20
 */
public interface MyMapper<T> extends Mapper<T>, MySqlMapper<T> {
    
}
