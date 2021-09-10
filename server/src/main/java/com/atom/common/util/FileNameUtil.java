package com.atom.common.util;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.io.FileUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.StrUtil;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

/**
 * @author zr
 * @description 文件名工具类
 * @date 2021/9/4
 */
public class FileNameUtil {

	/**
	 * 根据文件名称构建FileName
	 * @param fileName 文件名
	 * @return 返回FileName
	 */
	public static FileName getFileName(String fileName) {
		return getFileName(fileName, "");
	}

	/**
	 * 根据文件名称构建FileName
	 * @param fileName 文件名
	 * @return 返回FileName
	 */
	public static FileName getFileName(String fileName, String keyPrefix) {
		// 对文件名称做容错
		String mainName, suffix, key, urlKey;
		String timeKey = DateUtil.format(DateUtil.date(), DatePattern.PURE_DATETIME_MS_PATTERN);
		if (Validator.isEmpty(fileName)) {
			fileName = timeKey;
			mainName = FileUtil.getName(fileName);
			suffix = "";
		} else {
			mainName = FileUtil.mainName(fileName);
			suffix = "." + FileUtil.getSuffix(fileName);
		}
		key = StrUtil.concat(true, mainName, timeKey, suffix);
		urlKey = StrUtil.concat(true, keyPrefix, mainName, timeKey, suffix);

		return new FileName(fileName, mainName, suffix, key, urlKey);
	}

	/**
	 * 文件名
	 */
	@Getter
	@Setter
	@AllArgsConstructor
	public static class FileName {
		/**
		 * 原始名称
		 */
		private String name;
		/**
		 * 无后缀的原始名称
		 */
		private String mainName;
		/**
		 * 后缀
		 */
		private String suffix;
		/**
		 * 文件key
		 */
		private String key;
		/**
		 * 文件url的key
		 */
		private String urlKey;
	}
}
