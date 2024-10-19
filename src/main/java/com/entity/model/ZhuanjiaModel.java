package com.entity.model;

import com.entity.ZhuanjiaEntity;

import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;
import java.io.Serializable;


/**
 * 医生
 * 接收传参的实体类
 *（实际开发中配合移动端接口开发手动去掉些没用的字段， 后端一般用entity就够用了）
 * 取自ModelAndView 的model名称
 */
public class ZhuanjiaModel implements Serializable {
    private static final long serialVersionUID = 1L;




    /**
     * 主键
     */
    private Integer id;


    /**
     * 账户
     */
    private String username;


    /**
     * 密码
     */
    private String password;


    /**
     * 医生姓名
     */
    private String zhuanjiaName;


    /**
     * 医生手机号
     */
    private String zhuanjiaPhone;


    /**
     * 医生身份证号
     */
    private String zhuanjiaIdNumber;


    /**
     * 医生头像
     */
    private String zhuanjiaPhoto;


    /**
     * 性别
     */
    private Integer sexTypes;


    /**
     * 科室
     */
    private Integer keshiTypes;


    /**
     * 职位
     */
    private Integer zhiweiTypes;


    /**
     * 挂号费
     */
    private Double guahaoMoney;


    /**
     * 电子邮箱
     */
    private String zhuanjiaEmail;


    /**
     * 医生介绍
     */
    private String zhuanjiaContent;


    /**
     * 逻辑删除
     */
    private Integer zhuanjiaDelete;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date insertTime;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    private Date createTime;


    /**
	 * 获取：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 设置：主键
	 */
    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 获取：账户
	 */
    public String getUsername() {
        return username;
    }


    /**
	 * 设置：账户
	 */
    public void setUsername(String username) {
        this.username = username;
    }
    /**
	 * 获取：密码
	 */
    public String getPassword() {
        return password;
    }


    /**
	 * 设置：密码
	 */
    public void setPassword(String password) {
        this.password = password;
    }
    /**
	 * 获取：医生姓名
	 */
    public String getZhuanjiaName() {
        return zhuanjiaName;
    }


    /**
	 * 设置：医生姓名
	 */
    public void setZhuanjiaName(String zhuanjiaName) {
        this.zhuanjiaName = zhuanjiaName;
    }
    /**
	 * 获取：医生手机号
	 */
    public String getZhuanjiaPhone() {
        return zhuanjiaPhone;
    }


    /**
	 * 设置：医生手机号
	 */
    public void setZhuanjiaPhone(String zhuanjiaPhone) {
        this.zhuanjiaPhone = zhuanjiaPhone;
    }
    /**
	 * 获取：医生身份证号
	 */
    public String getZhuanjiaIdNumber() {
        return zhuanjiaIdNumber;
    }


    /**
	 * 设置：医生身份证号
	 */
    public void setZhuanjiaIdNumber(String zhuanjiaIdNumber) {
        this.zhuanjiaIdNumber = zhuanjiaIdNumber;
    }
    /**
	 * 获取：医生头像
	 */
    public String getZhuanjiaPhoto() {
        return zhuanjiaPhoto;
    }


    /**
	 * 设置：医生头像
	 */
    public void setZhuanjiaPhoto(String zhuanjiaPhoto) {
        this.zhuanjiaPhoto = zhuanjiaPhoto;
    }
    /**
	 * 获取：性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
    }


    /**
	 * 设置：性别
	 */
    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }
    /**
	 * 获取：科室
	 */
    public Integer getKeshiTypes() {
        return keshiTypes;
    }


    /**
	 * 设置：科室
	 */
    public void setKeshiTypes(Integer keshiTypes) {
        this.keshiTypes = keshiTypes;
    }
    /**
	 * 获取：职位
	 */
    public Integer getZhiweiTypes() {
        return zhiweiTypes;
    }


    /**
	 * 设置：职位
	 */
    public void setZhiweiTypes(Integer zhiweiTypes) {
        this.zhiweiTypes = zhiweiTypes;
    }
    /**
	 * 获取：挂号费
	 */
    public Double getGuahaoMoney() {
        return guahaoMoney;
    }


    /**
	 * 设置：挂号费
	 */
    public void setGuahaoMoney(Double guahaoMoney) {
        this.guahaoMoney = guahaoMoney;
    }
    /**
	 * 获取：电子邮箱
	 */
    public String getZhuanjiaEmail() {
        return zhuanjiaEmail;
    }


    /**
	 * 设置：电子邮箱
	 */
    public void setZhuanjiaEmail(String zhuanjiaEmail) {
        this.zhuanjiaEmail = zhuanjiaEmail;
    }
    /**
	 * 获取：医生介绍
	 */
    public String getZhuanjiaContent() {
        return zhuanjiaContent;
    }


    /**
	 * 设置：医生介绍
	 */
    public void setZhuanjiaContent(String zhuanjiaContent) {
        this.zhuanjiaContent = zhuanjiaContent;
    }
    /**
	 * 获取：逻辑删除
	 */
    public Integer getZhuanjiaDelete() {
        return zhuanjiaDelete;
    }


    /**
	 * 设置：逻辑删除
	 */
    public void setZhuanjiaDelete(Integer zhuanjiaDelete) {
        this.zhuanjiaDelete = zhuanjiaDelete;
    }
    /**
	 * 获取：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 设置：添加时间
	 */
    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    }
