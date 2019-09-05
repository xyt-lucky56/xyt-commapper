package com.xyt.common.base;

import java.util.Collection;
import java.util.List;
import java.util.Map;

import tk.mybatis.mapper.entity.Example;


public interface BaseService<T> {
	/**
	 * @param entity
	 * @return
	 * @Description: 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 */
	public T selectOne(final T entity);

	/**
	 * @param id
	 * @return
	 * @Description: 根据主键id查询返回一条数据，查询条件使用等号
	 */
	public T selectOneById(String id);

	/**
	 * @param entity
	 * @return
	 * @Description:根据实体中的属性值进行查询，查询条件使用等号
	 */
	public List<T> selectList(T entity);
	
	/**
	 * @param entity
	 * @return
	 * @Description:根据实体中的属性值进行查询，查询条件使用等号
	 */
	public Map<Long,T> selectMapEntity(T entity);
	/**
	 * 根据ids批量查
	 * @param ids
	 * @return
	 */
	public List<T> selectListByIds(Collection<String> ids);


	/**
	 * @param entity
	 * @return
	 * @Description:根据实体中的属性查询总数，查询条件使用等号
	 */
	public Long count(T entity);


	public int countByCondition(Example example);

	/**
	 * @param entity
	 * @return
	 * @Description:保存一个实体，null的属性不会保存，会使用数据库默认值
	 */
	public int save(T entity);

	/**
	 * @param entityList
	 * @return
	 * @Description:批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
	 */
	public int saveBatch(List<T> entityList);

	public int saveBatch(List<T> entityList, int batchSize);

	/**
	 * @param entity
	 * @return
	 * @Description:根据主键更新所有属性
	 */
	public int update(T entity);

	/**
	 * @param entity
	 * @return
	 * @Description:根据主键更新属性不为null的值
	 */
	public int updateSelective(T entity);

	/**
	 * @param id
	 * @return
	 * @Description:根据主键字段进行删除，方法参数必须包含完整的主键属性
	 */
	public int deleteById(String id);
	
	/**
	 * 根据主键字段进行批量删除
	 * @param ids
	 * @return
	 */
	int removeByIds(Collection<String> ids);
}
