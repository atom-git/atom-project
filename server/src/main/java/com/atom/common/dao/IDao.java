package com.atom.common.dao;

import com.atom.common.pojo.table.OrderData;
import com.atom.common.pojo.table.PageData;
import org.hibernate.criterion.DetachedCriteria;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author zr
 * @description hibernate通用dao实现类，包含了查询，新增，修改，删除，包含，(SQL,HQL)查询，记数，分页
 * @date 2020/3/10
 * @param <T>
 */
@SuppressWarnings("unused")
public interface IDao<T> {

	/**
	 * 生成离线查询条件排序
	 * @param dc 离线查询条件
	 * @param orderData 排序信息
	 * @return 返回离线查询条件
	 */
	DetachedCriteria addOrder(DetachedCriteria dc, OrderData orderData);

	/**
	 * 生成离线查询条件排序
	 * @param dc 离线查询条件
	 * @param orderData 排序信息
	 * @param defaultOrder 默认排序
	 * @return 返回离线查询条件
	 */
	DetachedCriteria addOrder (DetachedCriteria dc, OrderData orderData, OrderData defaultOrder);

	/**
	 * 根据主键查询一个实体
	 * @param id 序列化id
	 * @return 查询出的对象
	 */
	T findOne(final Serializable id);

	/**
	 * 查询所有实体
	 * @return 查询出的对象
	 */
	List<T> findAll();

	/**
	 * 离线查询条件查询
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	List<T> findByDC(DetachedCriteria dc);

	/**
	 * 离线查询条件查询
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	List<T> findByDC(DetachedCriteria dc, Integer maxResult);

	/**
	 * 查询所有并升序
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findAllAsc(String... orderFields);

	/**
	 * 查询所有并降序
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findAllDesc(String... orderFields);

	/**
	 * 离线查询条件查询一条
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	T findOneByDC(DetachedCriteria dc);

	/**
	 * 执行SQL查询返回惟一实体
	 * @param sql 查询sql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	Map<String, Object> findOneBySql(String sql, Object... values);

	/**
	 * 执行HQL查询返回惟一实体
	 * @param hql 查询hql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	T findOneByHql(String hql, Object... values);

	/**
	 * 按照某个字段查询惟一实体
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	T findOneByField(String fieldKey, Object value, String... showFields);

	/**
	 * 按照某个字段查询升序取惟一实体
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	T findOneByFieldAsc(String fieldKey, Object value, String... orderFields);

	/**
	 * 按照某个字段查询降序取惟一实体
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	T findOneByFieldDesc(String fieldKey, Object value, String... orderFields);

	/**
	 * 按照MAP查询惟一实体
	 * @param map 等值查询条件Map
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	T findOneByMap(Map<String, Object> map, String... showFields);

	/**
	 * 按照MAP查询升序惟一实体
	 * @param map 等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	T findOneByMapAsc(Map<String, Object> map, String... orderFields);

	/**
	 * 按照MAP查询降序惟一实体
	 * @param map 等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	T findOneByMapDesc(Map<String, Object> map, String... orderFields);

	/**
	 * 执行SQL查询返回实体集合
	 * @param sql 查询sql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	List<Map<String, Object> > findAllBySql(String sql, Object... values);

	/**
	 * 执行HQL查询返回实体集合
	 * @param hql 查询hql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	List<T> findAllByHql(String hql, Object... values);

	/**
	 * 按照某个字段查询实体集合
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	List<T> findAllByField(String fieldKey, Object value, String... showFields);

	/**
	 * 按照某个字段升序查询实体集合
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param orderFields 排序的属性列表
	 * @return orderFields
	 */
	List<T> findAllByFieldAsc(String fieldKey, Object value, String... orderFields);

	/**
	 * 按照某个字段降序查询实体集合
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findAllByFieldDesc(String fieldKey, Object value, String... orderFields);

	/**
	 * 按照MAP查询实体集合
	 * @param map 等值查询条件Map
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	List<T> findAllByMap(Map<String, Object> map, String... showFields);

	/**
	 * 按照MAP升序查询实体集合
	 * @param map 等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findAllByMapAsc(Map<String, Object> map, String... orderFields);

	/**
	 * 按照MAP降序查询实体集合
	 * @param map 等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findAllByMapDesc(Map<String, Object> map, String... orderFields);

	/**
	 * 无查询条件的分页查询
	 * @param pageData 分页条件
	 * @return 返回数据列表
	 */
	List<T> findPage(PageData pageData);

	/**
	 * 根据DC分页查询
	 * @param dc 离线查询条件
	 * @param pageData 分页查询结果包装类
	 * @return 查询出的对象
	 */
	List<T> findPage(DetachedCriteria dc, PageData pageData);

	/**
	 * 数据下载
	 * @return 返回要下载的数据
	 */
	List<T> download();

	/**
	 * 数据下载
	 * @param allRecord 是否所有数据
	 * @return 返回要下载的数据
	 */
	List<T> download(boolean allRecord);

	/**
	 * 数据下载
	 * @param dc 离线查询条件
	 * @return 返回要下载的数据
	 */
	List<T> download(DetachedCriteria dc);

	/**
	 * 数据下载，如果有allRecord标识，则下载全部，否则最多下载1000条
	 * 写业务时需要注意这点
	 * @param dc 离线查询条件
	 * @param allRecord 是否所有数据
	 * @return 返回要下载的数据
	 */
	List<T> download(DetachedCriteria dc, boolean allRecord);

	/**
	 * 根据DC分页查询
	 * @param dc 离线查询条件
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @return 查询出的对象
	 */
	List<T> findPage(DetachedCriteria dc, int minResult, int maxResult);

	/**
	 * 按照某个字段升序分页查询
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findPageByFieldAsc(String fieldKey, Object value, int minResult, int maxResult, String... orderFields);

	/**
	 * 按照某个字段降序分页查询
	 * @param fieldKey 属性名
	 * @param value 属性值
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findPageByFieldDesc(String fieldKey, Object value, int minResult, int maxResult, String... orderFields);


	/**
	 * 按照MAP升序分页查询
	 * @param map 等值查询条件Map
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findPageByMapAsc(Map<String, Object> map, int minResult, int maxResult, String... orderFields);

	/**
	 * 按照MAP降序分页查询
	 * @param map 等值查询条件Map
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	List<T> findPageByMapDesc(Map<String, Object> map, int minResult, int maxResult, String... orderFields);

	/**
	 * 根据SQL分页查询
	 * @param sql 查询sql语句
	 * @param pageData 分页信息
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	List<Map<String, Object>> findPageBySql(String sql, PageData pageData, Object... values);

	/**
	 * 根据SQL分页查询
	 * @param sql 查询sql语句
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	List<Map<String, Object> > findPageBySql(String sql, int minResult, int maxResult, Object... values);

	/**
	 * 根据HQL分页查询
	 * @param hql 查询hql语句
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	List<T> findPageByHql(String hql, int minResult, int maxResult, Object... values);

	/**
	 * 返回总记录数
	 * @return 查询出的对象
	 */
	long count();

	/**
	 * 根据字段值返回记录数
	 * @param fieldKey 属性名 属性名
	 * @param value 属性值 属性值
	 * @return 查询出的对象
	 */
	long count(String fieldKey, Object value);

	/**
	 * 根据字段值Map返回记录数
	 * @param map 等值查询条件Map
	 * @return 查询出的对象
	 */
	long count(Map<String, Object> map);

	/**
	 * 根据离线查询条件返回记录数
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	long countByDC(DetachedCriteria dc);

	/**
	 * 根据SQL返回记录数
	 * @param sql 查询sql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	long countBySql(String sql, Object... values);

	/**
	 * 根据HQL返回记录数
	 * @param hql 查询hql语句
	 * @param values 查询hql占位符值
	 * @return 查询出的对象
	 */
	long countByHql(String hql, Object... values);

	/**
	 * 判断对象是否存在
	 * @param dc 离线查询条件
	 * @return 对象是否存在
	 */
	boolean exist(DetachedCriteria dc);

	/**
	 * 判断对象是否存在
	 * @param fieldKey 字段key
	 * @param value 字段值
	 * @return 对象是否存在
	 */
	boolean exist(String fieldKey, Object value);

	/**
	 * 根据sql判断对象是否存在
	 * @param sql 传入的sql
	 * @param values 参数
	 * @return 是否存在
	 */
	boolean exist(String sql, Object... values);

	/**
	 * 判断session中实体是否存在
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	boolean contains(final T entity);

	/**
	 * 从session中把对象赶出去
	 * @param entity 对象实例
	 */
	void evict(T entity);

	/**
	 * 保存实体并返回主键
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	Serializable save(final T entity);

	/**
	 * 批量保存实体
	 * @param entities 实体列表
	 */
	void save(final List<T> entities);

	/**
	 * 保存或者更新实体并返回实体
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	T saveOrUpdate(final T entity);

	/**
	 * 保存并返回实体
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	T update(final T entity);

	/**
	 * 更新并返回实体
	 * @param entities 对象实例数组
	 * @return 查询出的对象
	 */
	List<T> update(List<T> entities);

	/**
	 * 删除实体
	 * @param entity 对象实例
	 */
	void delete(final T entity);

	/**
	 * 根据主键删除实体
	 * @param id 序列化ID
	 */
	void deleteById(final Serializable id);

	/**
	 * 删除实体的集合
	 * @param entities 对象实例
	 */
	void deleteAll(Collection<T> entities);

	/**
	 * 刷新对象
	 * @param entity 对象实例
	 */
	void refresh(final T entity);

	/**
	 * 提交事务
	 */
	void commit();
}
