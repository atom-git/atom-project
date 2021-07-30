package com.atom.server.system.entity;

import cn.hutool.core.collection.CollUtil;
import com.alibaba.fastjson.JSONObject;
import com.atom.common.pojo.http.RestRequest;
import com.atom.common.pojo.mapper.LogType;
import com.atom.common.security.SessionUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author zr
 * @description 系统日志
 * @date 2021/4/22
 */
@Entity
@Table(name = "sys_log")
@DynamicInsert
@DynamicUpdate
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class SysLog {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	@Column(name = "account")
	private String account;
	@Column(name = "name")
	private String name;
	@Column(name = "type")
	private Integer type;
	@Column(name = "action_type")
	private String actionType;
	@Column(name = "create_time")
	private Long createTime;
	@Column(name = "request_url")
	private String requestUrl;
	@Column(name = "request_params")
	private String requestParams;
	@Column(name = "platform_type")
	private Integer platformType;
	@Column(name = "result_status")
	private Integer resultStatus;
	@Column(name = "execution_time")
	private Long executionTime;
	@Column(name = "exception")
	private String exception;
	@Column(name = "result")
	private String result;

	/**
	 * 构建日志
	 * @param sessionUser 系统用户
	 * @param logType 日志类型
	 * @param restRequest 请求相关信息
	 */
	public SysLog(SessionUser sessionUser, LogType logType, RestRequest restRequest) {
		this.account = sessionUser != null ? sessionUser.getAccount() : "";
		this.name = sessionUser != null ? sessionUser.getName() : "";
		this.type = logType.getCode();
		List<Integer> actions = new ArrayList<>();
		if(null!=restRequest.getActionTypes()){
			Arrays.stream(restRequest.getActionTypes()).forEach(action -> actions.add(action.getCode()));
		}
		this.actionType = CollUtil.join(actions, ",");
		this.createTime = System.currentTimeMillis();
		this.requestUrl = restRequest.getUrl();
		this.requestParams = JSONObject.toJSONString(restRequest.getParams());
		this.platformType = restRequest.getPlatformType().getCode();
		this.resultStatus = restRequest.getStatus();
		this.executionTime = restRequest.getExecutionTime();
		this.exception = restRequest.getException();
		this.result = JSONObject.toJSONString(restRequest.getResult());
	}
}
