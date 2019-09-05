package com.xyt.common.base;

import com.google.common.collect.Lists;
import com.xyt.common.base.utils.ReflectionUtils;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;

public abstract class BaseServiceImpl<M extends BaseMapper<T>, T extends BaseEntity> implements BaseService<T> {
	
	private static final Logger logger = LoggerFactory.getLogger(BaseServiceImpl.class);
	/**
	 * 有效 1
	 */
	public static final Integer VALID = 1;
	/**
	 * 无效 0
	 */
	public static final Integer INVALID = 0;
	
	@Autowired
	private M mapper;

	protected void setMapper(M mapper) {
		this.mapper = mapper;
	}

	protected M getMapper() {
		return this.mapper;
	}

	private Class<T> clazz = null;

	@SuppressWarnings("unchecked")
	private Class<T> getEntityClass() {
		if (clazz == null) {
			clazz = (Class<T>) ReflectionUtils.getSuperClassGenericParameterizedType(getClass(), 1);
					//(Class<T>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[1];
		}
		return clazz;
	}

	private T getEntityInstance() {
		try {
			T entity = getEntityClass().newInstance();
			return entity;
		} catch (Exception e) {
			logger.error(e.getMessage());
		}
		return null;
	}

	/**
	 * @param entity
	 * @return
	 * @Description: 根据实体中的属性进行查询，只能有一个返回值，有多个结果是抛出异常，查询条件使用等号
	 */
	public T selectOne(final T entity) {
		entity.setValid(VALID);
		return mapper.selectOne(entity);
	}

	/**
	 * @param id
	 * @return
	 * @Description: 根据主键id查询返回一条数据，查询条件使用等号
	 */
	public T selectOneById(String id) {
		T entity = getEntityInstance();
	    entity.setId(id);
		return selectOne(entity);
	}


	/**
	 * @param entity
	 * @return
	 * @Description:根据实体中的属性值进行查询，查询条件使用等号
	 */
	public List<T> selectList(T entity) {
		entity.setValid(VALID);
		return mapper.select(entity);
	}
	/**
	 * @param entity
	 * @return
	 * @Description:根据实体中的属性值进行查询，查询条件使用等号
	 */
	public Map<Long,T> selectMapEntity(T entity){
		entity.setValid(VALID);
		return mapper.selectMapEntity(entity);
	}
	/**
     * 根据ids批量查
     * @param ids
     * @return
     */
	public List<T> selectListByIds(Collection<String> ids) {
		if (CollectionUtils.isEmpty(ids)) {
			return Lists.newArrayList();
		}
		
		Example example = new Example(getEntityClass());
		example.orderBy("id").desc();
		Criteria criteria = example.createCriteria();
		criteria.andEqualTo("valid", VALID);
		criteria.andIn("id", ids);
		
		return mapper.selectByExample(example);
	}
	
    public int removeByIds(Collection<String> ids) {
	    if (CollectionUtils.isEmpty(ids)) {
            return 0;
        }
	    //更新字段
	    T record = getEntityInstance();
	    record.setValid(INVALID);
        //where 条件
        Example example = new Example(getEntityClass());
        Criteria criteria = example.createCriteria();
        criteria.andIn("id", ids);
        return mapper.updateByExampleSelective(record, example);
    }
	
	/**
	 * @param entity
	 * @return
	 * @Description:根据实体中的属性查询总数，查询条件使用等号
	 */
	public Long count(T entity) {
		entity.setValid(VALID);
		return new Long(mapper.selectCount(entity));
	}

	
	
	public int countByCondition(Example example) {
		example.and().andEqualTo("valid", VALID);
		return mapper.selectCountByExample(example);
	}

	/**
	 * @param entity
	 * @return
	 * @Description:保存一个实体，null的属性不会保存，会使用数据库默认值
	 */
	@Transactional
	public int save(T entity) {
		entity.setValid(VALID);
		//entity.addBy();
		return mapper.insertSelective(entity);
	}

	/**
	 * @param entityList
	 * @return
	 * @Description:批量插入，支持批量插入的数据库可以使用，例如MySQL,H2等，另外该接口限制实体包含`id`属性并且必须为自增列
	 */
	@Transactional
	public int saveBatch(List<T> entityList) {
		return saveBatch(entityList, 30);
	}
	
	@Transactional
	public int saveBatch(List<T> entityList, int batchSize) {
		if (CollectionUtils.isEmpty(entityList)) {
			throw new IllegalArgumentException("Error: entityList must not be empty");
		}
		int retTotal = 0;
		try {
			int size = entityList.size();
			List<T> tempList = new ArrayList<T>();
			for (int i = 0; i < size; i++) {
				T entity = entityList.get(i);
				entity.setValid(VALID);
				//entity.addBy();
				tempList.add(entity);
				if (i >= 1 && i % batchSize == 0) {
					retTotal += mapper.insertList(tempList);
					tempList = new ArrayList<T>();
				}
			}
			
			retTotal += mapper.insertList(tempList);
			
		} catch (Throwable e) {
			throw new RuntimeException("Error: Cannot execute insertBatch Method. Cause", e);
		}
		return retTotal;
	}

	/**
	 * @param entity
	 * @return
	 * @Description:根据主键更新所有属性
	 */
	@Transactional
	public int update(T entity) {
		//entity.updateBy();
		return mapper.updateByPrimaryKey(entity);
	}
	
	/**
	 * @param entity
	 * @return
	 * @Description:根据主键更新属性不为null的值
	 */
	@Transactional
    public int updateSelective(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }
    
	/**
	 * @param id
	 * @return
	 * @Description:根据主键字段进行删除，方法参数必须包含完整的主键属性
	 */
	@Transactional
	public int deleteById(String id) {

		T entity = getEntityInstance();
		if (entity != null) {
			entity.setId(id);
			return deleteById((T) entity);
		}
		return 0;
	}


	/**
	 * @param entity
	 * @return
	 * @Description:根据主键软删除
	 */
	@Transactional
	protected int deleteById(T entity) {
		entity.setValid(INVALID);
		//entity.updateBy();
		return mapper.updateByPrimaryKeySelective(entity);
	}

}
