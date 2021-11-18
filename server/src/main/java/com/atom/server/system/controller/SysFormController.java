package com.atom.server.system.controller;

import com.atom.common.pojo.annotation.Permission;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author zr
 * @description 系统自定义表单管理，表单新增，编辑，修改，禁用，记录日志
 * @date 2021/11/18
 */
@RestController
@RequestMapping("/system/form")
@Api("系统自定义表单管理")
@Permission
public class SysFormController {
}
