package com.xyt.common.base;

import java.util.List;

import org.apache.ibatis.annotations.InsertProvider;
import org.apache.ibatis.annotations.Options;

import tk.mybatis.mapper.provider.SpecialProvider;

public interface InsertPkIdListMapper<T> {
	@Options(useGeneratedKeys = true, keyProperty = "id")
	@InsertProvider(type = SpecialProvider.class, method = "dynamicSQL")
	int insertList(List<T> recordList);
}
