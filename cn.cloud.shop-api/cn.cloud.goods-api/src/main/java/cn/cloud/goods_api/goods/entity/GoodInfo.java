package cn.cloud.goods_api.goods.entity;

import cn.cloud.goods_api.util.BaseModel;
import java.util.Date;
import javax.persistence.*;

@Table(name = "good_info")
public class GoodInfo extends BaseModel {
    /**
     * 名称
     */
    @Column(name = "Name")
    private String name;

    /**
     * 记录创建时间
     */
    @Column(name = "createDate")
    private Date createdate;

    /**
     * 创建人
     */
    private String creator;

    /**
     * 是否可用
     */
    private String enabled;

    /**
     * 价格
     */
    private Long price;

    /**
     * 制造商
     */
    private String mader;

    /**
     * 制造时间
     */
    @Column(name = "makeTime")
    private Date maketime;

    /**
     * 制造地
     */
    @Column(name = "makePlace")
    private String makeplace;

    /**
     * 数量
     */
    private Long num;

    /**
     * 是否在售
     */
    @Column(name = "isOpen")
    private String isopen;

    /**
     * 备注信息
     */
    private String remark;

    /**
     * 是否有子项
     */
    @Column(name = "isContains")
    private String iscontains;

    /**
     * 其他属性
     */
    private String content;

    /**
     * 销售商
     */
    private String solder;

    /**
     * 销售地
     */
    @Column(name = "localPlace")
    private String localplace;

    /**
     * 获取名称
     *
     * @return Name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取记录创建时间
     *
     * @return createDate - 记录创建时间
     */
    public Date getCreatedate() {
        return createdate;
    }

    /**
     * 设置记录创建时间
     *
     * @param createdate 记录创建时间
     */
    public void setCreatedate(Date createdate) {
        this.createdate = createdate;
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
     * 获取是否可用
     *
     * @return enabled - 是否可用
     */
    public String getEnabled() {
        return enabled;
    }

    /**
     * 设置是否可用
     *
     * @param enabled 是否可用
     */
    public void setEnabled(String enabled) {
        this.enabled = enabled == null ? null : enabled.trim();
    }

    /**
     * 获取价格
     *
     * @return price - 价格
     */
    public Long getPrice() {
        return price;
    }

    /**
     * 设置价格
     *
     * @param price 价格
     */
    public void setPrice(Long price) {
        this.price = price;
    }

    /**
     * 获取制造商
     *
     * @return mader - 制造商
     */
    public String getMader() {
        return mader;
    }

    /**
     * 设置制造商
     *
     * @param mader 制造商
     */
    public void setMader(String mader) {
        this.mader = mader == null ? null : mader.trim();
    }

    /**
     * 获取制造时间
     *
     * @return makeTime - 制造时间
     */
    public Date getMaketime() {
        return maketime;
    }

    /**
     * 设置制造时间
     *
     * @param maketime 制造时间
     */
    public void setMaketime(Date maketime) {
        this.maketime = maketime;
    }

    /**
     * 获取制造地
     *
     * @return makePlace - 制造地
     */
    public String getMakeplace() {
        return makeplace;
    }

    /**
     * 设置制造地
     *
     * @param makeplace 制造地
     */
    public void setMakeplace(String makeplace) {
        this.makeplace = makeplace == null ? null : makeplace.trim();
    }

    /**
     * 获取数量
     *
     * @return num - 数量
     */
    public Long getNum() {
        return num;
    }

    /**
     * 设置数量
     *
     * @param num 数量
     */
    public void setNum(Long num) {
        this.num = num;
    }

    /**
     * 获取是否在售
     *
     * @return isOpen - 是否在售
     */
    public String getIsopen() {
        return isopen;
    }

    /**
     * 设置是否在售
     *
     * @param isopen 是否在售
     */
    public void setIsopen(String isopen) {
        this.isopen = isopen == null ? null : isopen.trim();
    }

    /**
     * 获取备注信息
     *
     * @return remark - 备注信息
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置备注信息
     *
     * @param remark 备注信息
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取是否有子项
     *
     * @return isContains - 是否有子项
     */
    public String getIscontains() {
        return iscontains;
    }

    /**
     * 设置是否有子项
     *
     * @param iscontains 是否有子项
     */
    public void setIscontains(String iscontains) {
        this.iscontains = iscontains == null ? null : iscontains.trim();
    }

    /**
     * 获取其他属性
     *
     * @return content - 其他属性
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置其他属性
     *
     * @param content 其他属性
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }

    /**
     * 获取销售商
     *
     * @return solder - 销售商
     */
    public String getSolder() {
        return solder;
    }

    /**
     * 设置销售商
     *
     * @param solder 销售商
     */
    public void setSolder(String solder) {
        this.solder = solder == null ? null : solder.trim();
    }

    /**
     * 获取销售地
     *
     * @return localPlace - 销售地
     */
    public String getLocalplace() {
        return localplace;
    }

    /**
     * 设置销售地
     *
     * @param localplace 销售地
     */
    public void setLocalplace(String localplace) {
        this.localplace = localplace == null ? null : localplace.trim();
    }
}