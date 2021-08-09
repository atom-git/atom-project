package com.atom.server.system.pojo.vo;

import com.atom.common.dao.Converter;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysRole;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import org.springframework.beans.BeanUtils;

import java.util.Date;

/**
 * @author zr
 * @description 系统角色响应实体
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("系统角色响应实体")
public class SysRoleVO extends AbsEntity {

    @ApiModelProperty("角色ID")
    private Integer id;
    @ApiModelProperty("角色名称")
    private String roleName;
    @ApiModelProperty("角色描述")
    private String roleDesc;
    @ApiModelProperty("角色修改时间")
    private Date modifyTime;
    @ApiModelProperty("是否默认角色1是，0否，最多只允许一个默认权限")
    private Integer ifDefault;

    public static class VOConverter extends Converter<SysRoleVO, SysRole> {

        @Override
        public SysRoleVO doForward(SysRole sysRole) {
            if (sysRole == null) {
                return null;
            }
            SysRoleVO sysRoleVO = new SysRoleVO();
            BeanUtils.copyProperties(sysRole, sysRoleVO);
            return sysRoleVO;
        }
    }
}
