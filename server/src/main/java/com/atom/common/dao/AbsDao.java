package com.atom.common.dao;

import com.atom.common.pojo.table.OrderData;
import com.atom.common.pojo.table.PageData;
import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.*;
import org.hibernate.query.Query;
import org.hibernate.query.internal.NativeQueryImpl;
import org.hibernate.transform.Transformers;
import org.springframework.orm.hibernate5.HibernateCallback;
import org.springframework.orm.hibernate5.HibernateTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.math.BigInteger;
import java.util.*;

/**
 * @param <T>
 * @author zr
 * @description hibernate通用dao实现类，包含了查询，新增，修改，删除，包含，(SQL,HQL)查询，记数，分页
 * @date 2020/3/10
 */
@Transactional
@Repository
@SuppressWarnings("unchecked")
public abstract class AbsDao<T> implements IDao<T> {

	/**
	 * 批处理的大小
	 */
	private final Integer BATCH_MAX_ROW = 1000;

	/**
	 * 传入的对象
	 */
	private final Class<T> clazz;

	@Resource
	private SessionFactory sessionFactory;

	/**
	 * 无参构造器用于自动注入对象
	 */
	protected AbsDao() {
		//反射泛型
		Class<?> tClass = this.getClass();
		//获得参数化父类类型
		ParameterizedType parType = (ParameterizedType) tClass.getGenericSuperclass();
		this.clazz = (Class<T>) parType.getActualTypeArguments()[0];
		//验证clazz是否为空，如果为空则抛出空指针错误
		Objects.requireNonNull(this.clazz);
	}

	/**
	 * hibernate操作模板
	 */
	private HibernateTemplate hibernateTemplate;

	/**
	 * 返回当前的session
	 * @return 返回当前的session
	 */
	protected Session getCurSession() {
		Session session = this.sessionFactory.getCurrentSession();
		if (session == null) {
			session = this.sessionFactory.openSession();
		}
		session.setHibernateFlushMode(FlushMode.COMMIT);
		return session;
	}

	/**
	 * 初始化hibernateTemplate对象
	 * @return 返回hibernateTemplate对象
	 */
	private HibernateTemplate getHibernateTemplate() {
		if (this.hibernateTemplate == null) {
			this.hibernateTemplate = new HibernateTemplate(sessionFactory);
		}
		return this.hibernateTemplate;
	}

	/**
	 * 生成离线查询条件排序
	 * @param dc        离线查询条件
	 * @param orderData 排序信息
	 * @return 返回离线查询条件
	 */
	@Override
	public DetachedCriteria addOrder(DetachedCriteria dc, OrderData orderData) {
		return this.addOrder(dc, orderData, null);
	}

	/**
	 * 生成离线查询条件排序
	 * @param dc           离线查询条件
	 * @param orderData    排序信息
	 * @param defaultOrder 默认排序
	 * @return 返回离线查询条件
	 */
	@Override
	public DetachedCriteria addOrder(DetachedCriteria dc, OrderData orderData, OrderData defaultOrder) {
		if (orderData != null) {
			if (OrderData.ASC.equalsIgnoreCase(orderData.getOrderType())) {
				dc.addOrder(Order.asc(orderData.getOrderColumn()));
			}
			if (OrderData.DESC.equalsIgnoreCase(orderData.getOrderType())) {
				dc.addOrder(Order.desc(orderData.getOrderColumn()));
			}
		} else if (defaultOrder != null) {
			if (OrderData.ASC.equalsIgnoreCase(defaultOrder.getOrderType())) {
				dc.addOrder(Order.asc(defaultOrder.getOrderColumn()));
			}
			if (OrderData.DESC.equalsIgnoreCase(defaultOrder.getOrderType())) {
				dc.addOrder(Order.desc(defaultOrder.getOrderColumn()));
			}
		}
		return dc;
	}

	/**
	 * 根据主键查询一个实体
	 * @param id 序列化id
	 * @return 查询出的对象
	 */
	@Override
	public T findOne(Serializable id) {
		if (id == null)
			return null;
		return this.getHibernateTemplate().get(clazz, id);
	}

	/**
	 * 查询所有实体
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAll() {
		return this.getHibernateTemplate().execute(session -> {
			Query<T> query = session.createQuery("from " + clazz.getName());
			return query.list();
		});
	}

	/**
	 * 离线查询条件查询
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findByDC(DetachedCriteria dc) {
		if (dc == null)
			return new ArrayList<>();
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc);
	}

	/**
	 * 离线查询条件查询
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findByDC(DetachedCriteria dc, Integer maxResult) {
		if (dc == null)
			return new ArrayList<>();
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, 0, maxResult);
	}

	/**
	 * 查询所有并升序
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllAsc(String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.asc(field));
			}
		}
		return this.findByDC(dc);
	}

	/**
	 * 查询所有并降序
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllDesc(String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.desc(field));
			}
		}
		return this.findByDC(dc);
	}

	/**
	 * 离线查询条件查询一条
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByDC(DetachedCriteria dc) {
		List<T> list = this.findByDC(dc, 1);
		return list == null || list.size() < 1 ? null : list.get(0);
	}

	/**
	 * 执行SQL查询返回惟一实体
	 * @param sql    查询sql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public Map<String, Object> findOneBySql(String sql, Object... values) {
		if (ObjectUtils.isEmpty(sql)) {
			return null;
		}
		Session session;
		Map<String, Object> map = null;
		try {
			session = this.getCurSession();
			Query<Map<String, Object>> query = session.createNativeQuery(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
			}
			query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			map = query.getSingleResult();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return map;
	}

	/**
	 * 执行HQL查询返回惟一实体
	 * @param hql    查询hql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByHql(String hql, Object... values) {
		if (ObjectUtils.isEmpty(hql))
			return null;

		return this.getHibernateTemplate().execute(session -> {
			Query<T> query = session.createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			return query.getSingleResult();
		});
	}

	/**
	 * 按照某个字段查询惟一实体
	 * @param fieldKey   属性名
	 * @param value      属性值
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByField(String fieldKey, Object value, String... showFields) {
		String alias = null;
		ProjectionList projList = null;
		if (showFields != null && showFields.length > 0) {
			alias = clazz.getSimpleName() + "_";
			projList = Projections.projectionList();
			for (String field : showFields) {
				projList.add(Projections.property(alias + "." + field).as(field));
			}
		}

		DetachedCriteria dc;
		if (alias != null) {
			dc = DetachedCriteria.forClass(clazz, alias);
			dc.setProjection(projList);
			dc.setResultTransformer(Transformers.aliasToBean(clazz));
		} else {
			dc = DetachedCriteria.forClass(clazz);
		}
		if (!ObjectUtils.isEmpty(fieldKey)) {
			if (value == null) {
				dc.add(Restrictions.isNull(fieldKey));
			} else if (value instanceof Set && ((Set<?>) value).size() > 0) {
				dc.add(Restrictions.in(fieldKey, value));
			} else {
				dc.add(Restrictions.eq(fieldKey, value));
			}
		}
		List<T> list = this.findByDC(dc, 1);
		return list == null || list.size() < 1 ? null : list.get(0);
	}

	/**
	 * 按照某个字段查询升序取惟一实体
	 * @param fieldKey    属性名
	 * @param value       属性值
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByFieldAsc(String fieldKey, Object value, String... orderFields) {
		DetachedCriteria dc;
		dc = DetachedCriteria.forClass(clazz);
		if (!ObjectUtils.isEmpty(fieldKey)) {
			if (value == null) {
				dc.add(Restrictions.isNull(fieldKey));
			} else if (value instanceof Set && ((Set<?>) value).size() > 0) {
				dc.add(Restrictions.in(fieldKey, value));
			} else {
				dc.add(Restrictions.eq(fieldKey, value));
			}
		}
		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.asc(field));
			}
		}

		List<T> list = this.findByDC(dc, 1);
		return list == null || list.size() < 1 ? null : list.get(0);
	}

	/**
	 * 按照某个字段查询降序取惟一实体
	 * @param fieldKey    属性名
	 * @param value       属性值
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByFieldDesc(String fieldKey, Object value, String... orderFields) {
		DetachedCriteria dc;
		dc = DetachedCriteria.forClass(clazz);
		if (!ObjectUtils.isEmpty(fieldKey)) {
			if (value == null) {
				dc.add(Restrictions.isNull(fieldKey));
			} else if (value instanceof Set && ((Set<?>) value).size() > 0) {
				dc.add(Restrictions.in(fieldKey, value));
			} else {
				dc.add(Restrictions.eq(fieldKey, value));
			}
		}

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.desc(field));
			}
		}

		List<T> list = this.findByDC(dc, 1);
		return list == null || list.size() < 1 ? null : list.get(0);
	}

	/**
	 * 按照MAP查询惟一实体
	 * @param map        等值查询条件Map
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByMap(Map<String, Object> map, String... showFields) {
		String alias = null;
		ProjectionList projList = null;
		if (showFields != null && showFields.length > 0) {
			alias = clazz.getSimpleName() + "_";
			projList = Projections.projectionList();
			for (String field : showFields) {
				projList.add(Projections.property(alias + "." + field).as(field));
			}
		}

		DetachedCriteria dc;
		if (alias != null) {
			dc = DetachedCriteria.forClass(clazz, alias);
			dc.setProjection(projList);
			dc.setResultTransformer(Transformers.aliasToBean(clazz));
		} else {
			dc = DetachedCriteria.forClass(clazz);
		}
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));
		List<T> list = this.findByDC(dc, 1);
		return list == null || list.size() < 1 ? null : list.get(0);
	}

	/**
	 * 按照MAP查询升序惟一实体
	 * @param map         等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByMapAsc(Map<String, Object> map, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.asc(field));
			}
		}

		List<T> list = this.findByDC(dc, 1);
		return list == null || list.size() < 1 ? null : list.get(0);
	}

	/**
	 * 按照MAP查询降序惟一实体
	 * @param map         等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public T findOneByMapDesc(Map<String, Object> map, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.desc(field));
			}
		}

		List<T> list = this.findByDC(dc, 1);
		return list == null || list.size() < 1 ? null : list.get(0);
	}

	/**
	 * 执行SQL查询返回实体集合
	 * @param sql    查询sql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public List<Map<String, Object>> findAllBySql(String sql, Object... values) {
		if (ObjectUtils.isEmpty(sql))
			new ArrayList<>();
		Session session;
		List<Map<String, Object>> list = null;
		try {
			session = this.getCurSession();
			Query<Map<String, Object>> query = session.createNativeQuery(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
			}
			query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
		}

		return list;
	}

	/**
	 * 执行HQL查询返回实体集合
	 * @param hql    查询hql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllByHql(String hql, Object... values) {
		if (ObjectUtils.isEmpty(hql))
			new ArrayList<>();
		return this.getHibernateTemplate().execute(session -> {
			Query<T> query = session.createQuery(hql);
			if (values != null) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			return query.list();
		});
	}

	/**
	 * 按照某个字段查询实体集合
	 * @param fieldKey   属性名
	 * @param value      属性值
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllByField(String fieldKey, Object value, String... showFields) {
		String alias = null;
		ProjectionList projList = null;
		if (showFields != null && showFields.length > 0) {
			alias = clazz.getSimpleName() + "_";
			projList = Projections.projectionList();
			for (String field : showFields) {
				projList.add(Projections.property(alias + "." + field).as(field));
			}
		}

		DetachedCriteria dc;
		if (alias != null) {
			dc = DetachedCriteria.forClass(clazz, alias);
			dc.setProjection(projList);
			dc.setResultTransformer(Transformers.aliasToBean(clazz));
		} else {
			dc = DetachedCriteria.forClass(clazz);
		}
		if (!ObjectUtils.isEmpty(fieldKey)) {
			if (value == null) {
				dc.add(Restrictions.isNull(fieldKey));
			} else if (value instanceof Set && ((Set<?>) value).size() > 0) {
				dc.add(Restrictions.in(fieldKey, value));
			} else {
				dc.add(Restrictions.eq(fieldKey, value));
			}
		}

		return this.findByDC(dc);
	}

	/**
	 * 按照某个字段升序查询实体集合
	 * @param fieldKey    属性名
	 * @param value       属性值
	 * @param orderFields 排序的属性列表
	 * @return orderFields
	 */
	@Override
	public List<T> findAllByFieldAsc(String fieldKey, Object value, String... orderFields) {
		DetachedCriteria dc;
		dc = DetachedCriteria.forClass(clazz);
		if (!ObjectUtils.isEmpty(fieldKey)) {
			if (value == null) {
				dc.add(Restrictions.isNull(fieldKey));
			} else if (value instanceof Set && ((Set<?>) value).size() > 0) {
				dc.add(Restrictions.in(fieldKey, value));
			} else {
				dc.add(Restrictions.eq(fieldKey, value));
			}
		}

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.asc(field));
			}
		}

		return this.findByDC(dc);
	}

	/**
	 * 按照某个字段降序查询实体集合
	 * @param fieldKey    属性名
	 * @param value       属性值
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllByFieldDesc(String fieldKey, Object value, String... orderFields) {
		DetachedCriteria dc;
		dc = DetachedCriteria.forClass(clazz);
		if (!ObjectUtils.isEmpty(fieldKey)) {
			if (value == null) {
				dc.add(Restrictions.isNull(fieldKey));
			} else if (value instanceof Set && ((Set<?>) value).size() > 0) {
				dc.add(Restrictions.in(fieldKey, value));
			} else {
				dc.add(Restrictions.eq(fieldKey, value));
			}
		}

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.desc(field));
			}
		}

		return this.findByDC(dc);
	}

	/**
	 * 按照MAP查询实体集合
	 * @param map        等值查询条件Map
	 * @param showFields 展示的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllByMap(Map<String, Object> map, String... showFields) {
		String alias = null;
		ProjectionList projList = null;
		if (showFields != null && showFields.length > 0) {
			alias = clazz.getSimpleName() + "_";
			projList = Projections.projectionList();
			for (String field : showFields) {
				projList.add(Projections.property(alias + "." + field).as(field));
			}
		}

		DetachedCriteria dc;
		if (alias != null) {
			dc = DetachedCriteria.forClass(clazz, alias);
			dc.setProjection(projList);
			dc.setResultTransformer(Transformers.aliasToBean(clazz));
		} else {
			dc = DetachedCriteria.forClass(clazz);
		}
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));
		return this.findByDC(dc);
	}

	/**
	 * 按照MAP升序查询实体集合
	 * @param map         等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllByMapAsc(Map<String, Object> map, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.asc(field));
			}
		}

		return this.findByDC(dc);
	}

	/**
	 * 按照MAP降序查询实体集合
	 * @param map         等值查询条件Map
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findAllByMapDesc(Map<String, Object> map, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.desc(field));
			}
		}

		return this.findByDC(dc);
	}

	/**
	 * 无查询条件的分页查询
	 * @param pageData 分页条件
	 * @return 返回数据列表
	 */
	@Override
	public List<T> findPage(PageData pageData) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		return this.findPage(dc, pageData);
	}

	/**
	 * 根据DC分页查询
	 * @param dc       离线查询条件
	 * @param pageData 分页查询结果包装类
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findPage(DetachedCriteria dc, PageData pageData) {
		if (dc == null)
			return new ArrayList<>();
		if (pageData.getDownload()) {
			return this.download(dc, pageData.isAllRecord());
		} else {
			int minResult = pageData.getStartIndex();
			int maxResult = pageData.getPageSize();
			return (List<T>) this.getHibernateTemplate().findByCriteria(dc, minResult, maxResult);
		}
	}

	/**
	 * 数据下载，如果有allRecord标识，则下载全部，否则最多下载1000条
	 * 写业务时需要注意这点
	 * @return 返回要下载的数据
	 */
	@Override
	public List<T> download() {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		return this.download(dc);
	}

	/**
	 * 数据下载，如果有allRecord标识，则下载全部，否则最多下载1000条
	 * 写业务时需要注意这点
	 * @return 返回要下载的数据
	 */
	@Override
	public List<T> download(boolean allRecord) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		return this.download(dc, allRecord);
	}

	/**
	 * 数据下载，如果有allRecord标识，则下载全部，否则最多下载1000条
	 * 写业务时需要注意这点
	 * @param dc 离线查询条件
	 * @return 返回要下载的数据
	 */
	@Override
	public List<T> download(DetachedCriteria dc) {
		return this.download(dc, false);
	}

	/**
	 * 数据下载，如果有allRecord标识，则下载全部，否则最多下载1000条
	 * 写业务时需要注意这点
	 * @param dc 离线查询条件
	 * @param allRecord 是否查询全部数据
	 * @return 返回要下载的数据
	 */
	@Override
	public List<T> download(DetachedCriteria dc, boolean allRecord) {
		if (allRecord) {
			return this.findByDC(dc);
		} else {
			return this.findPage(dc, new PageData(1, BATCH_MAX_ROW));
		}
	}

	/**
	 * 根据DC分页查询
	 * @param dc        离线查询条件
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findPage(DetachedCriteria dc, int minResult, int maxResult) {
		if (dc == null)
			return new ArrayList<>();
		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, minResult, maxResult);
	}

	/**
	 * 按照某个字段升序分页查询
	 * @param fieldKey    属性名
	 * @param value       属性值
	 * @param minResult   最小记录index
	 * @param maxResult   最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findPageByFieldAsc(String fieldKey, Object value, int minResult, int maxResult, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (value instanceof Set) {
			dc.add(Restrictions.in(fieldKey, value));
		} else if (!ObjectUtils.isEmpty(fieldKey)) {
			dc.add(Restrictions.eq(fieldKey, value));
		} else if (value == null) {
			dc.add(Restrictions.isNull(fieldKey));
		}

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.asc(field));
			}
		}

		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, minResult, maxResult);
	}

	/**
	 * 按照某个字段降序分页查询
	 * @param fieldKey    属性名
	 * @param value       属性值
	 * @param minResult   最小记录index
	 * @param maxResult   最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findPageByFieldDesc(String fieldKey, Object value, int minResult, int maxResult, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (value instanceof Set) {
			dc.add(Restrictions.in(fieldKey, value));
		} else if (!ObjectUtils.isEmpty(fieldKey)) {
			dc.add(Restrictions.eq(fieldKey, value));
		} else if (value == null) {
			dc.add(Restrictions.isNull(fieldKey));
		}

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.desc(field));
			}
		}

		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, minResult, maxResult);
	}

	/**
	 * 按照MAP升序分页查询
	 * @param map         等值查询条件Map
	 * @param minResult   最小记录index
	 * @param maxResult   最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findPageByMapAsc(Map<String, Object> map, int minResult, int maxResult, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.asc(field));
			}
		}

		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, minResult, maxResult);
	}

	/**
	 * 按照MAP降序分页查询
	 * @param map         等值查询条件Map
	 * @param minResult   最小记录index
	 * @param maxResult   最大记录index
	 * @param orderFields 排序的属性列表
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findPageByMapDesc(Map<String, Object> map, int minResult, int maxResult, String... orderFields) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));

		if (orderFields != null && orderFields.length > 0) {
			for (String field : orderFields) {
				dc.addOrder(Order.desc(field));
			}
		}

		return (List<T>) this.getHibernateTemplate().findByCriteria(dc, minResult, maxResult);
	}

	/**
	 * 根据SQL分页查询
	 * @param sql      查询sql语句
	 * @param pageData 分页信息
	 * @param values   查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public List<Map<String, Object>> findPageBySql(String sql, PageData pageData, Object... values) {
		if (pageData.isAllRecord()) {
			return this.findAllBySql(sql, values);
		} else {
			return findPageBySql(sql, pageData.getStartIndex(), pageData.getPageSize(), values);
		}
	}

	/**
	 * 根据SQL分页查询
	 * @param sql       查询sql语句
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param values    查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public List<Map<String, Object>> findPageBySql(String sql, int minResult, int maxResult, Object... values) {
		if (ObjectUtils.isEmpty(sql)) {
			return new ArrayList<>();
		}
		Session session;
		List<Map<String, Object>> list = null;
		try {
			session = this.getCurSession();
			Query<Map<String, Object>> query = session.createNativeQuery(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
			}
			query.unwrap(NativeQueryImpl.class).setResultTransformer(Transformers.ALIAS_TO_ENTITY_MAP);
			list = query.setFirstResult(minResult).setMaxResults(maxResult).list();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return list;
	}

	/**
	 * 根据HQL分页查询
	 * @param hql       查询hql语句
	 * @param minResult 最小记录index
	 * @param maxResult 最大记录index
	 * @param values    查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public List<T> findPageByHql(String hql, int minResult, int maxResult, Object... values) {
		if (ObjectUtils.isEmpty(hql)) {
			return new ArrayList<>();
		}
		Session session = null;
		List<T> list = null;
		try {
			session = this.getCurSession();
			Query<T> query = session.createQuery(hql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i, values[i]);
				}
			}
			list = query.setFirstResult(minResult).setMaxResults(maxResult).list();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			if (session != null) {
				session.close();
			}
		}
		return list;
	}

	/**
	 * 返回总记录数
	 * @return 查询出的对象
	 */
	@Override
	public long count() {
		String hql = "select count(*) from " + clazz.getName();
		return (long) this.getHibernateTemplate().execute((HibernateCallback<Object>) session -> {
			Query<Object> query = session.createQuery(hql);
			List<Object> list = query.list();
			return list == null || list.size() <= 0 ? 0L : Long.parseLong(list.get(0).toString());
		});
	}

	/**
	 * 根据字段值返回记录数
	 * @param fieldKey 属性名 属性名
	 * @param value    属性值 属性值
	 * @return 查询出的对象
	 */
	@Override
	public long count(String fieldKey, Object value) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		dc.add(Restrictions.eq(fieldKey, value));
		dc.setProjection(Projections.rowCount());
		return Long.parseLong(this.getHibernateTemplate().findByCriteria(dc).get(0).toString());
	}

	/**
	 * 根据字段值Map返回记录数
	 * @param map 等值查询条件Map
	 * @return 查询出的对象
	 */
	@Override
	public long count(Map<String, Object> map) {
		DetachedCriteria dc = DetachedCriteria.forClass(clazz);
		if (map != null && map.keySet().size() > 0)
			dc.add(Restrictions.allEq(map));
		dc.setProjection(Projections.rowCount());
		return Long.parseLong(this.getHibernateTemplate().findByCriteria(dc).get(0).toString());
	}

	/**
	 * 根据离线查询条件返回记录数
	 * @param dc 离线查询条件
	 * @return 查询出的对象
	 */
	@Override
	public long countByDC(DetachedCriteria dc) {
		if (dc == null)
			return 0L;
		dc.setProjection(Projections.rowCount());
		return Long.parseLong(this.getHibernateTemplate().findByCriteria(dc, 0, 1).get(0).toString());
	}

	/**
	 * 根据SQL返回记录数
	 * @param sql    查询sql语句
	 * @param values 查询sql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public long countBySql(String sql, Object... values) {
		if (ObjectUtils.isEmpty(sql))
			return 0L;
		Session session;
		long rowCnt = 0L;
		try {
			session = this.getCurSession();
			Query<Object> query = session.createNativeQuery(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
			}
			rowCnt = ((BigInteger) query.list().get(0)).longValue();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return rowCnt;
	}

	/**
	 * 根据HQL返回记录数
	 * @param hql    查询hql语句
	 * @param values 查询hql占位符值
	 * @return 查询出的对象
	 */
	@Override
	public long countByHql(String hql, Object... values) {
		if (ObjectUtils.isEmpty(hql))
			return 0L;
		Query<Object> query = this.getCurSession().createQuery(hql);
		if (values != null && values.length > 0) {
			for (int i = 0; i < values.length; i++) {
				query.setParameter(i, values[i]);
			}
		}
		return (long) query.list().get(0);
	}

	/**
	 * 判断对象是否存在
	 * @param dc 离线查询条件
	 * @return 对象是否存在
	 */
	@Override
	public boolean exist(DetachedCriteria dc) {
		return this.findOne(dc) != null;
	}

	/**
	 * 判断对象是否存在
	 * @param fieldKey 字段key
	 * @param value 字段值
	 * @return 对象是否存在
	 */
	@Override
	public boolean exist(String fieldKey, Object value) {
		return this.findOneByField(fieldKey, value) != null;
	}

	/**
	 * 根据sql判断对象是否存在
	 * @param sql    传入的sql
	 * @param values 参数
	 * @return 是否存在
	 */
	@Override
	public boolean exist(String sql, Object... values) {
		if (ObjectUtils.isEmpty(sql)) {
			return false;
		}
		Session session;
		List<Object> list;
		try {
			session = this.getCurSession();
			Query<Object> query = session.createNativeQuery(sql);
			if (values != null && values.length > 0) {
				for (int i = 0; i < values.length; i++) {
					query.setParameter(i + 1, values[i]);
				}
			}
			list = query.list();
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
		return list != null && list.size() > 0;
	}

	/**
	 * 判断session中实体是否存在
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	@Override
	public boolean contains(T entity) {
		return entity != null && this.getHibernateTemplate().contains(entity);
	}

	/**
	 * 从session中把对象赶出去
	 * @param entity 对象实例
	 */
	@Override
	public void evict(T entity) {
		if (entity == null)
			return;
		try (Session session = this.getCurSession()) {
			session.evict(entity);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * 保存实体并返回主键
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	@Override
	public Serializable save(T entity) {
		if (entity != null) {
			return this.getHibernateTemplate().save(entity);
		} else {
			return null;
		}
	}

	/**
	 * 批量保存实体
	 * @param entities 实体列表
	 */
	@Override
	public void save(List<T> entities) {
		if (entities != null && entities.size() > 0) {
			HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
			hibernateTemplate.execute((HibernateCallback<Object>) session -> {
				for (int i = 0; i < entities.size(); i++) {
					session.save(entities.get(i));
					if (i % BATCH_MAX_ROW == 0) {
						session.flush();
						session.clear();
					}
				}
				session.flush();
				session.clear();
				return entities.size();
			});
		}
	}

	/**
	 * 保存或者更新实体并返回实体
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	@Override
	public T saveOrUpdate(T entity) {
		if (entity != null) {
			this.getHibernateTemplate().saveOrUpdate(entity);
			return entity;
		} else {
			return null;
		}
	}

	/**
	 * 保存并返回实体
	 * @param entity 对象实例
	 * @return 查询出的对象
	 */
	@Override
	public T update(T entity) {
		if (entity != null) {
			this.getHibernateTemplate().update(entity);
			return entity;
		} else {
			return null;
		}
	}

	/**
	 * 更新并返回实体
	 * @param entities 对象实例数组
	 * @return 查询出的对象
	 */
	@Override
	public List<T> update(List<T> entities) {
		if (entities != null && entities.size() > 0) {
			HibernateTemplate hibernateTemplate = this.getHibernateTemplate();
			hibernateTemplate.execute((HibernateCallback<Object>) session -> {
				for (int i = 0; i < entities.size(); i++) {
					session.update(entities.get(i));
					if (i % BATCH_MAX_ROW == 0) {
						session.flush();
						session.clear();
					}
				}
				session.flush();
				session.clear();
				return entities.size();
			});
		}
		return entities;
	}

	/**
	 * 删除实体
	 * @param entity 对象实例
	 */
	@Override
	public void delete(T entity) {
		if (entity != null) {
			this.getHibernateTemplate().delete(entity);
		}
	}

	/**
	 * 根据主键删除实体
	 * @param id 序列化ID
	 */
	@Override
	public void deleteById(Serializable id) {
		T entity = this.findOne(id);
		if (entity != null) {
			this.delete(entity);
		}
	}

	/**
	 * 删除实体的集合
	 * @param entities 对象实例
	 */
	@Override
	public void deleteAll(Collection<T> entities) {
		if (entities != null && entities.size() > 0) {
			this.getHibernateTemplate().deleteAll(entities);
		}
	}

	/**
	 * 刷新对象
	 * @param entity 对象实例
	 */
	@Override
	public void refresh(T entity) {
		this.getHibernateTemplate().refresh(entity);
	}

	/**
	 * 提交事务
	 */
	@Override
	public void commit() {
		this.getCurSession().getTransaction().commit();
	}

}
