package com.entity.vo;

import com.entity.ZhuanjiaEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 医生
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("zhuanjia")
public class ZhuanjiaVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 账户
     */

    @TableField(value = "username")
    private String username;


    /**
     * 密码
     */

    @TableField(value = "password")
    private String password;


    /**
     * 医生姓名
     */

    @TableField(value = "zhuanjia_name")
    private String zhuanjiaName;


    /**
     * 医生手机号
     */

    @TableField(value = "zhuanjia_phone")
    private String zhuanjiaPhone;


    /**
     * 医生身份证号
     */

    @TableField(value = "zhuanjia_id_number")
    private String zhuanjiaIdNumber;


    /**
     * 医生头像
     */

    @TableField(value = "zhuanjia_photo")
    private String zhuanjiaPhoto;


    /**
     * 性别
     */

    @TableField(value = "sex_types")
    private Integer sexTypes;


    /**
     * 科室
     */

    @TableField(value = "keshi_types")
    private Integer keshiTypes;


    /**
     * 职位
     */

    @TableField(value = "zhiwei_types")
    private Integer zhiweiTypes;


    /**
     * 挂号费
     */

    @TableField(value = "guahao_money")
    private Double guahaoMoney;


    /**
     * 电子邮箱
     */

    @TableField(value = "zhuanjia_email")
    private String zhuanjiaEmail;


    /**
     * 医生介绍
     */

    @TableField(value = "zhuanjia_content")
    private String zhuanjiaContent;


    /**
     * 逻辑删除
     */

    @TableField(value = "zhuanjia_delete")
    private Integer zhuanjiaDelete;


    /**
     * 添加时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间  show1 show2 photoShow
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "create_time")
    private Date createTime;


    /**
	 * 设置：主键
	 */
    public Integer getId() {
        return id;
    }


    /**
	 * 获取：主键
	 */

    public void setId(Integer id) {
        this.id = id;
    }
    /**
	 * 设置：账户
	 */
    public String getUsername() {
        return username;
    }


    /**
	 * 获取：账户
	 */

    public void setUsername(String username) {
        this.username = username;
    }
    /**
	 * 设置：密码
	 */
    public String getPassword() {
        return password;
    }


    /**
	 * 获取：密码
	 */

    public void setPassword(String password) {
        this.password = password;
    }
    /**
	 * 设置：医生姓名
	 */
    public String getZhuanjiaName() {
        return zhuanjiaName;
    }


    /**
	 * 获取：医生姓名
	 */

    public void setZhuanjiaName(String zhuanjiaName) {
        this.zhuanjiaName = zhuanjiaName;
    }
    /**
	 * 设置：医生手机号
	 */
    public String getZhuanjiaPhone() {
        return zhuanjiaPhone;
    }


    /**
	 * 获取：医生手机号
	 */

    public void setZhuanjiaPhone(String zhuanjiaPhone) {
        this.zhuanjiaPhone = zhuanjiaPhone;
    }
    /**
	 * 设置：医生身份证号
	 */
    public String getZhuanjiaIdNumber() {
        return zhuanjiaIdNumber;
    }


    /**
	 * 获取：医生身份证号
	 */

    public void setZhuanjiaIdNumber(String zhuanjiaIdNumber) {
        this.zhuanjiaIdNumber = zhuanjiaIdNumber;
    }
    /**
	 * 设置：医生头像
	 */
    public String getZhuanjiaPhoto() {
        return zhuanjiaPhoto;
    }


    /**
	 * 获取：医生头像
	 */

    public void setZhuanjiaPhoto(String zhuanjiaPhoto) {
        this.zhuanjiaPhoto = zhuanjiaPhoto;
    }
    /**
	 * 设置：性别
	 */
    public Integer getSexTypes() {
        return sexTypes;
    }


    /**
	 * 获取：性别
	 */

    public void setSexTypes(Integer sexTypes) {
        this.sexTypes = sexTypes;
    }
    /**
	 * 设置：科室
	 */
    public Integer getKeshiTypes() {
        return keshiTypes;
    }


    /**
	 * 获取：科室
	 */

    public void setKeshiTypes(Integer keshiTypes) {
        this.keshiTypes = keshiTypes;
    }
    /**
	 * 设置：职位
	 */
    public Integer getZhiweiTypes() {
        return zhiweiTypes;
    }


    /**
	 * 获取：职位
	 */

    public void setZhiweiTypes(Integer zhiweiTypes) {
        this.zhiweiTypes = zhiweiTypes;
    }
    /**
	 * 设置：挂号费
	 */
    public Double getGuahaoMoney() {
        return guahaoMoney;
    }


    /**
	 * 获取：挂号费
	 */

    public void setGuahaoMoney(Double guahaoMoney) {
        this.guahaoMoney = guahaoMoney;
    }
    /**
	 * 设置：电子邮箱
	 */
    public String getZhuanjiaEmail() {
        return zhuanjiaEmail;
    }


    /**
	 * 获取：电子邮箱
	 */

    public void setZhuanjiaEmail(String zhuanjiaEmail) {
        this.zhuanjiaEmail = zhuanjiaEmail;
    }
    /**
	 * 设置：医生介绍
	 */
    public String getZhuanjiaContent() {
        return zhuanjiaContent;
    }


    /**
	 * 获取：医生介绍
	 */

    public void setZhuanjiaContent(String zhuanjiaContent) {
        this.zhuanjiaContent = zhuanjiaContent;
    }
    /**
	 * 设置：逻辑删除
	 */
    public Integer getZhuanjiaDelete() {
        return zhuanjiaDelete;
    }


    /**
	 * 获取：逻辑删除
	 */

    public void setZhuanjiaDelete(Integer zhuanjiaDelete) {
        this.zhuanjiaDelete = zhuanjiaDelete;
    }
    /**
	 * 设置：添加时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：添加时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间  show1 show2 photoShow
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间  show1 show2 photoShow
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
