package com.atom.common.security;

import cn.hutool.core.date.DatePattern;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.lang.Validator;
import cn.hutool.core.util.ArrayUtil;
import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.annotation.JSONField;
import com.atom.common.pojo.AbsEntity;
import com.atom.common.pojo.mapper.PlatformType;
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
    @ApiModelProperty("手机号")
    private String phone;
    @ApiModelProperty("邮箱地址")
    private String email;
    @ApiModelProperty("用户昵称")
    private String name;
    @ApiModelProperty("格言")
    private String motto;
    @ApiModelProperty("位置编码")
    private String[] location;
    @ApiModelProperty("位置地址")
    private String[] locationName;
    @ApiModelProperty("头像Base64")
    private String head;
    @ApiModelProperty("第三方登录类型")
    private String thirdType;
    @ApiModelProperty("最后登录时间")
    private String lastLogin;
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
    @ApiModelProperty("平台类型")
    private PlatformType platformType;

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
        this.phone = sysUser.getPhone();
        this.email = sysUser.getEmail();
        this.name = sysUser.getName();
        this.motto = sysUser.getMotto();
        if (Validator.isNotEmpty(sysUser.getLocation())) {
            this.location = StrUtil.split(sysUser.getLocation(), "|").toArray(new String[]{});
        }
        if (Validator.isNotEmpty(sysUser.getLocationName())) {
            this.locationName = StrUtil.split(sysUser.getLocationName(), "|").toArray(new String[]{});
        }
        this.head = sysUser.getHead();
        this.thirdType = sysUser.getThirdType();
        this.lastLogin = DateUtil.format(sysUser.getLastLogin(), DatePattern.NORM_DATETIME_PATTERN);
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
        this.phone = sysUser.getPhone();
        this.email = sysUser.getEmail();
        this.name = sysUser.getName();
        this.motto = sysUser.getMotto();
        if (Validator.isNotEmpty(sysUser.getLocation())) {
            this.location = StrUtil.split(sysUser.getLocation(), "|").toArray(new String[]{});
        }
        if (Validator.isNotEmpty(sysUser.getLocationName())) {
            this.locationName = StrUtil.split(sysUser.getLocationName(), "|").toArray(new String[]{});
        }
        this.head = sysUser.getHead();
        this.thirdType = sysUser.getThirdType();
        this.lastLogin = DateUtil.format(sysUser.getLastLogin(), DatePattern.NORM_DATETIME_PATTERN);
    }

    /**
     * 根据系统用户刷新SessionUser
     * @param sysUser 系统用户
     */
    public void refresh(SysUser sysUser) {
        this.setHead(sysUser.getHead());
        this.setName(sysUser.getName());
        this.setPhone(sysUser.getPhone());
        this.setEmail(sysUser.getEmail());
        this.setMotto(sysUser.getMotto());
        if (Validator.isNotEmpty(sysUser.getLocation())) {
            this.location = StrUtil.split(sysUser.getLocation(), "|").toArray(new String[]{});
        }
        if (Validator.isNotEmpty(sysUser.getLocationName())) {
            this.locationName = StrUtil.split(sysUser.getLocationName(), "|").toArray(new String[]{});
        }
    }

    /**
     * 构建Session帐户实例
     * @param account 帐号信息
     */
    public SessionUser(String account) {
        this.account = account;
    }
}
