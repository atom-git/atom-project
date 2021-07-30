package com.atom.server.system.service.impl;

import cn.hutool.core.lang.Validator;
import com.atom.common.pojo.annotation.Permission;
import com.atom.common.pojo.mapper.ActionType;
import com.atom.common.pojo.mapper.GrantType;
import com.atom.server.system.dao.ISysActionDao;
import com.atom.server.system.dao.ISysActionTopicDao;
import com.atom.server.system.entity.SysAction;
import com.atom.server.system.entity.SysActionTopic;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;

import javax.annotation.Resource;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @author zr
 * @description 后台权限管理服务
 * @date 2021/4/22
 */
@Service
@Transactional
public class PermissionService implements ApplicationContextAware {

	/**
	 * 动作资源dao
	 */
	@Resource
	private ISysActionDao sysActionDao;

	/**
	 * 动作资源主题dao
	 */
	@Resource
	private ISysActionTopicDao sysActionTopicDao;

	/**
	 * 根据applicationContext注入权限上下文，同时根据注解持久化权限数据，动作资源与菜单的关联关系由前端进行维护
	 * @param applicationContext 应用上下文
	 * @throws BeansException Bean异常
	 */
	@Override
	public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
		String[] beanNames = applicationContext.getBeanNamesForAnnotation(Permission.class);
		if (beanNames.length > 0) {
			List<SysAction> sysActionList = new ArrayList<>();
			// 主题集
			Set<String> topicSet = new HashSet<>();
			for (String beanName : beanNames) {
				Object bean = applicationContext.getBean(beanName);
				// 动作资源主题取Api的value信息
				String topicName = beanName;
				Api api = AnnotationUtils.findAnnotation(bean.getClass(), Api.class);
				if (api != null && Validator.isNotEmpty(api.value())) {
					topicName = api.value();
				}
				topicSet.add(topicName);
				RequestMapping mapping = AnnotatedElementUtils.findMergedAnnotation(bean.getClass(), RequestMapping.class);
				String[] rootPaths = null;
				if (mapping != null) {
					rootPaths = mapping.value();
				}
				// 查找bean下面的方法有哪些有注解
				Method[] methods = bean.getClass().getMethods();
				if (methods.length > 0) {
					for (Method method : methods) {
						// 查找当前链接的认证配置
						Permission permission = AnnotationUtils.findAnnotation(method, Permission.class);
						if (permission != null) {
							ActionType[] actionTypes = permission.actionType();
							GrantType grantType = permission.grantType();
							ApiOperation apiOperation = AnnotationUtils.findAnnotation(method, ApiOperation.class);
							String actionName = apiOperation == null ? null : apiOperation.value();
							mapping = AnnotatedElementUtils.findMergedAnnotation(method, RequestMapping.class);
							// 最底层的请求地址是
							String[] childPaths = mapping != null ? mapping.value() : null;
							// 构建动作权限的数据
							List<SysAction> childActionList = this.buildSysAction(topicName, actionName, rootPaths, actionTypes, grantType, childPaths);
							sysActionList.addAll(childActionList);
						}
					}
				}
			}
			// TODO 多节点时需要考虑同时更新数据的问题
			// 更新动作资源主题信息
			List<SysActionTopic> sysActionTopicList = sysActionTopicDao.saveOrUpdate(topicSet);
			// 保存动作资源信息
			sysActionDao.saveOrUpdate(sysActionTopicList, sysActionList);
		}
	}

	/**
	 * 构建动作权限的数据
	 * @param actionName 动作名称
	 * @param rootPaths 根路由
	 * @param actionTypes 动作类型 ActionType
	 * @param grantType 授权类型 GrantType
	 * @param childPaths 子路由
	 * @return 返回动作权限数据
	 */
	private List<SysAction> buildSysAction(String topicName, String actionName, String[] rootPaths, ActionType[] actionTypes, GrantType grantType, String[] childPaths) {
		List<SysAction> sysActionList = new ArrayList<>();
		// 当路由不存在时，直接返回
		if ((rootPaths == null || rootPaths.length <= 0) && (childPaths == null || childPaths.length <= 0)) {
			return sysActionList;
		}
		// 做空值容错处理
		if (rootPaths == null || rootPaths.length <= 0) {
			rootPaths = new String[]{""};
		}
		if (childPaths == null || childPaths.length <= 0) {
			childPaths = new String[]{""};
		}
		// 拼接菜单路由
		Set<String> pathSet = new HashSet<>();
		for (String rootPath : rootPaths) {
			for (String childPath : childPaths) {
				pathSet.add((rootPath + "/" + childPath).replaceAll("/+", "/"));
			}
		}
		// 由于Permission注解存在默认值，因此actionTypes, grantType不存在空的情况
		pathSet.forEach(path -> {
			// 去除容错填加进来的部分
			if (!path.equals("/")) {
				for (ActionType actionType : actionTypes) {
					sysActionList.add(new SysAction(topicName, actionType.getDisplay() + "-" + actionName, path, actionType.getCode(), grantType.getCode()));
				}
			}
		});
		return sysActionList;
	}

	/**
	 * PreAuthorize 权限认证的自定义方式， 同时起着定义动作权限的作用
	 * @return 返回是否校验通过
	 */
	public boolean hasPermission() {
		try {
			return true;
		} catch (NullPointerException e) {
			return true;
		}
	}
}
