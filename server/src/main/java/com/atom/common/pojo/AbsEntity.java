package com.atom.common.pojo;

import cn.hutool.core.date.DatePattern;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeConfig;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.SimpleDateFormatSerializer;

import java.io.Serializable;
import java.util.Date;

/**
 * @author zr
 * @description 用于实体的格式化展示，对象序列化以及toString方法，重写toString()方法
 * @date 2020/3/10
 */
public abstract class AbsEntity implements Serializable {
	/**
	 * 序列化ID
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * 序列化配置
	 */
	private static final SerializeConfig serializeConfig = new SerializeConfig();
	static {
		serializeConfig.put(Date.class, new SimpleDateFormatSerializer(DatePattern.NORM_DATETIME_PATTERN));
	}

	/**
	 * 重写toString方法
	 * SerializerFeature.DisableCircularReferenceDetect 循环引用false
	 * @return 返回fastJson格式化
	 */
	@Override
	public String toString() {
		return JSON.toJSONString(this, serializeConfig, SerializerFeature.DisableCircularReferenceDetect);
	}

}
