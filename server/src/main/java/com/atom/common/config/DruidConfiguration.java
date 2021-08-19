package com.atom.common.config;

import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author zr
 * @description druid监控配置，访问地址：http://ip:port/${serverContent}/druid 帐号密码参见：atom.druid.login.username、atom.druid.login.password
 * @date 2020/3/10
 */
@Configuration
public class DruidConfiguration {

	/**
	 * 全局的配置这个初始化比较靠前不能使用GlobalConfig
	 */
	@Resource
	private Environment environment;

	/**
	 * 注册一个Druid StatViewServletBean
	 * @return ServletRegistrationBean
	 */
	@Bean
	public ServletRegistrationBean<StatViewServlet> druidStatViewServletBean() {
		//后台的路径
		ServletRegistrationBean<StatViewServlet> statViewServletRegistrationBean = new ServletRegistrationBean<>(new StatViewServlet(), "/druid/*");
		Map<String,String> params = new HashMap<>();
		//账号密码，是否允许重置数据
		params.put("loginUsername", environment.getProperty("atom.druid.login.username"));
		params.put("loginPassword", environment.getProperty("atom.druid.login.password"));
		params.put("resetEnable", environment.getProperty("atom.druid.reset.enable"));
		statViewServletRegistrationBean.setInitParameters(params);
		return statViewServletRegistrationBean;
	}

	/**
	 * 注册一个 Druid FilterRegistrationBean
	 * @return FilterRegistrationBean
	 */
	@Bean
	public FilterRegistrationBean<WebStatFilter> druidStatFilterBean() {
		FilterRegistrationBean<WebStatFilter> druidStatFilterBean = new FilterRegistrationBean<>(new WebStatFilter());
		List<String> urlPattern=new ArrayList<>();
		urlPattern.add("/*");
		druidStatFilterBean.setUrlPatterns(urlPattern);
		Map<String,String> initParams=new HashMap<>();
		initParams.put("exclusions", "*.js,*.gif,*.jpg,*.bmp,*.png,*.css,*.ico,/druid/*");
		druidStatFilterBean.setInitParameters(initParams);
		return druidStatFilterBean;
	}
}
