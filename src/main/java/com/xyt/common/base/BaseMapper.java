package com.xyt.common.base;

import com.xyt.common.base.mapper.SelectMapEntityMapper;
import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.common.special.InsertUseGeneratedKeysMapper;

/**
 * 
 * @ClassName:  AbstractMapper   
 * @Description:TODO(TK 基础Mapper)   
 * @author: 信运通
 * @date:   2018年3月9日 上午11:52:28   
 * @param <T>
 */
public interface BaseMapper<T> extends Mapper<T>, InsertPkIdListMapper<T>,InsertUseGeneratedKeysMapper<T>, SelectMapEntityMapper<T> {
    //TODO
    //FIXME 特别注意，该接口不能被扫描到，否则会出错
}
