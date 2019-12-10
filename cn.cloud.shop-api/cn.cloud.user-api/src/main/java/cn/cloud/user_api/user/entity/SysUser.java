package cn.cloud.user_api.user.entity;

import java.util.Date;
import javax.persistence.*;

import cn.cloud.user_api.utils.base.BaseModel;

@Table(name = "sys_user")
public class SysUser extends BaseModel {
    /**
     * 人员id
     */
    private Long personid;

    /**
     * 角色
     */
    private Integer roleid;

    /**
     * 姓名
     */
    private String name;

    /**
     * 登录名称
     */
    private String loginname;

    /**
     * 密码
     */
    private String password;

    /**
     * 创建时间
     */
    @Column(name = "createDate")
    private Date createdate;

    /**
     * 是否可用
     */
    private Boolean enabled;

    /**
     * 用户状态
     */
    private Byte status;

    /**
     * 更新时间
     */
    @Column(name = "updateDate")
    private Date updatedate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 更改人
     */
    private String updator;

    /**
     * 上次登录时间
     */
    @Column(name = "lastLonginTime")
    private Date lastlongintime;

    /**
     * 上次登录地点
     */
    @Column(name = "lastLonginPlace")
    private String lastlonginplace;

    /**
     * 获取人员id
     *
     * @return personid - 人员id
     */
    public Long getPersonid() {
        return personid;
    }

    /**
     * 设置人员id
     *
     * @param personid 人员id
     */
    public void setPersonid(Long personid) {
        this.personid = personid;
    }

    /**
     * 获取角色
     *
     * @return roleid - 角色
     */
    public Integer getRoleid() {
        return roleid;
    }

    /**
     * 设置角色
     *
     * @param roleid 角色
     */
    public void setRoleid(Integer roleid) {
        this.roleid = roleid;
    }

    /**
     * 获取姓名
     *
     * @return name - 姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置姓名
     *
     * @param name 姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取登录名称
     *
     * @return loginname - 登录名称
     */
    public String getLoginname() {
        return loginname;
    }

    /**
     * 设置登录名称
     *
     * @param loginname 登录名称
     */
    public void setLoginname(String loginname) {
        this.loginname = loginname == null ? null : loginname.trim();
    }

    /**
     * 获取密码
     *
     * @return password - 密码
     */
    public String getPassword() {
        return password;
    }

    /**
     * 设置密码
     *
     * @param password 密码
     */
    public void setPassword(String password) {
        this.password = password == null ? null : password.trim();
    }

    /**
     * 获取创建时间
     *
     * @return createDate - 创建时间
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * 设置创建时间
     *
     * @param createdate 创建时间
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
    }

    /**
     * 获取是否可用
     *
     * @return enabled - 是否可用
     */
    public Boolean getEnabled() {
        return enabled;
    }

    /**
     * 设置是否可用
     *
     * @param enabled 是否可用
     */
    public void setEnabled(Boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * 获取用户状态
     *
     * @return status - 用户状态
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置用户状态
     *
     * @param status 用户状态
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取更新时间
     *
     * @return updateDate - 更新时间
     */
    public Date getUpdatedate() {
        return updatedate;
    }

    /**
     * 设置更新时间
     *
     * @param updatedate 更新时间
     */
    public void setUpdatedate(Date updatedate) {
        this.updatedate = updatedate;
    }

    /**
     * 获取创建人
     *
     * @return creator - 创建人
     */
    public String getCreator() {
        return creator;
    }

    /**
     * 设置创建人
     *
     * @param creator 创建人
     */
    public void setCreator(String creator) {
        this.creator = creator == null ? null : creator.trim();
    }

    /**
     * 获取更改人
     *
     * @return updator - 更改人
     */
    public String getUpdator() {
        return updator;
    }

    /**
     * 设置更改人
     *
     * @param updator 更改人
     */
    public void setUpdator(String updator) {
        this.updator = updator == null ? null : updator.trim();
    }

    /**
     * 获取上次登录时间
     *
     * @return lastLonginTime - 上次登录时间
     */
    public Date getLastlongintime() {
        return lastlongintime;
    }

    /**
     * 设置上次登录时间
     *
     * @param lastlongintime 上次登录时间
     */
    public void setLastlongintime(Date lastlongintime) {
        this.lastlongintime = lastlongintime;
    }

    /**
     * 获取上次登录地点
     *
     * @return lastLonginPlace - 上次登录地点
     */
    public String getLastlonginplace() {
        return lastlonginplace;
    }

    /**
     * 设置上次登录地点
     *
     * @param lastlonginplace 上次登录地点
     */
    public void setLastlonginplace(String lastlonginplace) {
        this.lastlonginplace = lastlonginplace == null ? null : lastlonginplace.trim();
    }
}