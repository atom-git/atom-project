package com.atom.common.security;

import com.alibaba.fastjson.annotation.JSONField;
import com.atom.common.pojo.AbsEntity;
import com.atom.server.system.entity.SysUser;
import com.atom.server.system.pojo.vo.SysDeptVO;
import com.atom.server.system.pojo.vo.SysMenuVO;
import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author zr
 * @description 缓存用户信息 结合SpringSecurity框架使用 可直接在Controller层作为参数注入
 * @date 2021/4/22
 */
@Getter
@Setter
@ApiModel("用户登录信息")
@NoArgsConstructor
public class SessionUser extends AbsEntity implements Authentication {

    @ApiModelProperty("用户ID")
    private Integer id;
    @ApiModelProperty("令牌信息")
    private String token;
    @ApiModelProperty("账号信息，即手机号")
    private String account;
    @JSONField(serialize = false)
    @ApiModelProperty("帐户密码")
    private String password;
    @ApiModelProperty("用户昵称")
    private String name;
    @ApiModelProperty("头像地址")
    private String headUrl;
    @ApiModelProperty("所属组织机构")
    private SysDeptVO sysDept;
    @ApiModelProperty("所属组织及子集ids")
    private Set<Integer> sysDeptIdSet;
    @ApiModelProperty("拥有的角色ids")
    private Set<Integer> sysRoleIdSet = new HashSet<>();
    @ApiModelProperty("拥有的菜单树")
    private List<SysMenuVO> sysMenuTree;
    @ApiModelProperty("拥有的资源列表")
    private List<String> sysActionList;
    @ApiModelProperty("App应用配置")
    private String appConfig;
    @ApiModelProperty("是否认证成功")
    private boolean authenticated = false;

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.sysRoleIdSet.stream()
                .map(role -> new SimpleGrantedAuthority(role.toString()))
                .collect(Collectors.toList());
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public Object getCredentials() {
        return this.getPassword();
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public Object getDetails() {
        return null;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public Object getPrincipal() {
        return this.getAccount();
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public boolean isAuthenticated() {
        return authenticated;
    }

    @JSONField(serialize = false)
    @JsonIgnore
    @Override
    public void setAuthenticated(boolean authenticated) throws IllegalArgumentException {
        this.authenticated = authenticated;
    }

    /**
     * 构建SessionUser
     * @param sysUser 系统用户
     * @param sysDeptVO 用户所属组织
     * @param sysDeptIdSet 用户组织数据权限
     * @param sysRoleIdSet 用户角色ids
     * @param sysMenuTree 用户菜单树
     * @param actionList 用户资源权限
     */
    public SessionUser(SysUser sysUser, SysDeptVO sysDeptVO, Set<Integer> sysDeptIdSet, Set<Integer> sysRoleIdSet,
                       List<SysMenuVO> sysMenuTree, List<String> actionList) {
        this.id = sysUser.getId();
        this.account = sysUser.getAccount();
        this.name = sysUser.getName();
        // TODO 这里按需改成url
        this.headUrl = sysUser.getHead();
        this.sysDept = sysDeptVO;
        this.sysDeptIdSet = sysDeptIdSet;
        this.sysRoleIdSet = sysRoleIdSet;
        this.sysMenuTree = sysMenuTree;
        this.sysActionList = actionList;
        this.appConfig = sysUser.getAppConfig();
    }

    /**
     * 构建SessionUser
     * @param sysUser 系统用户
     */
    public SessionUser(SysUser sysUser) {
        this.id = sysUser.getId();
        this.account = sysUser.getAccount();
        this.name = sysUser.getName();
        // TODO 这里按需改成url
        this.headUrl = sysUser.getHead();
    }

    /**
     * 构建Session帐户实例
     * @param account 帐号信息
     */
    public SessionUser(String account) {
        this.account = account;
    }
}
