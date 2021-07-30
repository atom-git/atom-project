package com.atom.common.dao;

import com.atom.common.pojo.exception.ConvertException;
import com.atom.common.pojo.http.RestError;

/**
 * @author zr
 * @description 类之间的抽象转化类 多用于传输模型 -> 业务模型的转化
 * @date 2019/5/27
 */
public abstract class Converter<Target, Source> {

    /**
     * 逆向转换
     *
     * @param target 目标
     * @return 源数据
     */
    public Source doBackward(Target target) {
        throw new ConvertException(RestError.ERROR9002);
    }

    /**
     * 正向转换
     *
     * @param source 源数据
     * @return 目标
     */
    public Target doForward(Source source) {
        throw new ConvertException(RestError.ERROR9002);
    }
}
