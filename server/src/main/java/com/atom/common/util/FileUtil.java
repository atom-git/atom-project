package com.atom.common.util;

import cn.hutool.core.date.DateUtil;
import cn.hutool.poi.excel.BigExcelWriter;
import cn.hutool.poi.excel.ExcelUtil;
import cn.hutool.poi.excel.ExcelWriter;
import com.atom.common.pojo.GlobalConstant;
import com.atom.common.pojo.exception.BusException;
import com.atom.common.pojo.http.RestError;
import io.swagger.annotations.ApiModelProperty;
import org.apache.http.entity.ContentType;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.URLEncoder;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * @author zr
 * @description 文件工具类
 * @date 2020/6/4
 */
@SuppressWarnings("unused")
public class FileUtil {

	/**
	 * 生成单sheet页面的excel
	 * @param fileName 文件名称
	 * @param clazz 类型
	 * @param data 可写出的数据
	 * @param totalCnt 总数据量
	 * @param <T> 数据类型，建议为VO，会自动读取ApiModelProperty配置生成表头，否则使用fieldName
	 * @return 返回文件
	 */
	public static <T> String createExcel(String fileName, Class<T> clazz, Iterable<T> data, long totalCnt) {
		return createExcel(GlobalConstant.DEFAULT_FILE_PATH, fileName, fileName, clazz, data, totalCnt);
	}

	/**
	 * 生成单sheet页面的excel
	 * @param filePath 文件的路径
	 * @param fileName 文件名称
	 * @param sheetName sheet页名称
	 * @param clazz 类型
	 * @param data 可写出的数据
	 * @param totalCnt 总数据量
	 * @param <T> 数据类型，建议为VO，会自动读取ApiModelProperty配置生成表头，否则使用fieldName
	 * @return 返回文件
	 */
	public static <T> String createExcel(String filePath, String fileName, String sheetName, Class<T> clazz, Iterable<T> data, long totalCnt) {
		String targetFile = filePath + fileName + DateUtil.format(DateUtil.date(), "yyyyMMddHH:mm:ss.SSS") + ".xlsx";
		BigExcelWriter excelWriter = ExcelUtil.getBigWriter(targetFile, sheetName);
		Map<String, String> headers = parseHeader(clazz);
		// 写出总的记录数
		excelWriter.merge(headers.size() - 1, sheetName + "总记录数");
		excelWriter.merge(headers.size() - 1, totalCnt);
		// 重命名列
		for (Map.Entry<String, String> header : headers.entrySet()) {
			excelWriter.addHeaderAlias(header.getKey(), header.getValue());
		}
		// 写出数据
		excelWriter.write(data);
		excelWriter.close();
		return targetFile;
	}

	/**
	 * 生成单sheet页面的excel
	 * @param fileName 文件名称
	 * @param clazz 类型
	 * @param data 可写出的数据
	 * @param totalCnt 总数据量
	 * @param <T> 数据类型，建议为VO，会自动读取ApiModelProperty配置生成表头，否则使用fieldName
	 */
	public static <T> void downlodExcel(String fileName, Class<T> clazz, Iterable<T> data, long totalCnt, HttpServletResponse response) {
		downlodExcel(fileName, fileName, clazz, data, totalCnt, response);
	}

	/**
	 * 生成单sheet页面的excel
	 * @param fileName 文件名称
	 * @param sheetName sheet页名称
	 * @param clazz 类型
	 * @param data 可写出的数据
	 * @param totalCnt 总数据量
	 * @param <T> 数据类型，建议为VO，会自动读取ApiModelProperty配置生成表头，否则使用fieldName
	 */
	public static <T> void downlodExcel(String fileName, String sheetName, Class<T> clazz, Iterable<T> data, long totalCnt, HttpServletResponse response) {
		String targetFile = fileName + DateUtil.format(DateUtil.date(), "yyyyMMddHHmmssSSS") + ".xlsx";
		ExcelWriter excelWriter = ExcelUtil.getWriterWithSheet(sheetName);
		Map<String, String> headers = parseHeader(clazz);
		// 写出总的记录数
		excelWriter.merge(headers.size() - 1, sheetName + "总记录数");
		excelWriter.merge(headers.size() - 1, totalCnt + "", false);
		// 重命名列
		for (Map.Entry<String, String> header : headers.entrySet()) {
			excelWriter.addHeaderAlias(header.getKey(), header.getValue());
		}
		// 写出数据
		excelWriter.write(data, true);
		try {

			response.setContentType(ContentType.APPLICATION_OCTET_STREAM.toString());
			response.setHeader("Content-Disposition","attachment;filename=" + URLEncoder.encode(targetFile, "UTF-8"));
			ServletOutputStream out = response.getOutputStream();
			excelWriter.flush(out, true);
			excelWriter.close();
		} catch (IOException e) {
			throw new BusException(RestError.ERROR9000, "文件下载出错，请联系管理员！");
		}
	}

	/**
	 * 解析Bean的Header
	 * @param clazz 类
	 * @param <T> 类型
	 * @return 返回map的Header
	 */
	public static <T> Map<String, String> parseHeader (Class<T> clazz) {
		Map<String, String> headers = new LinkedHashMap<>();
		Field[] fields = clazz.getDeclaredFields();
		if (fields.length > 0) {
			for (Field field : fields) {
				String comment = field.getAnnotation(ApiModelProperty.class) == null ? field.getName() : field.getAnnotation(ApiModelProperty.class).value();
				headers.put(field.getName(), comment);
			}
		}
		return headers;
	}
}
