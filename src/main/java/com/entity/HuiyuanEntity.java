package com.entity;

import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import java.lang.reflect.InvocationTargetException;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.apache.commons.beanutils.BeanUtils;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import com.baomidou.mybatisplus.enums.IdType;

/**
 * 用户
 *
 * @author 
 * @email
 */
@TableName("huiyuan")
public class HuiyuanEntity<T> implements Serializable {
    private static final long serialVersionUID = 1L;


	public HuiyuanEntity() {

	}

	public HuiyuanEntity(T t) {
		try {
			BeanUtils.copyProperties(this, t);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}


    /**
     * 主键
     */
    @TableId(type = IdType.AUTO)
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
     * 用户姓名
     */
    @TableField(value = "huiyuan_name")

    private String huiyuanName;


    /**
     * 用户手机号
     */
    @TableField(value = "huiyuan_phone")

    private String huiyuanPhone;


    /**
     * 用户身份证号
     */
    @TableField(value = "huiyuan_id_number")

    private String huiyuanIdNumber;


    /**
     * 用户头像
     */
    @TableField(value = "huiyuan_photo")

    private String huiyuanPhoto;


    /**
     * 性别
     */
    @TableField(value = "sex_types")

    private Integer sexTypes;


    /**
     * 电子邮箱
     */
    @TableField(value = "huiyuan_email")

    private String huiyuanEmail;


    /**
     * 余额
     */
    @TableField(value = "new_money")

    private Double newMoney;


    /**
     * 用户介绍
     */
    @TableField(value = "huiyuan_content")

    private String huiyuanContent;


    /**
     * 创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat
    @TableField(value = "create_time",fill = FieldFill.INSERT)

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
	 * 设置：用户姓名
	 */
    public String getHuiyuanName() {
        return huiyuanName;
    }
    /**
	 * 获取：用户姓名
	 */

    public void setHuiyuanName(String huiyuanName) {
        this.huiyuanName = huiyuanName;
    }
    /**
	 * 设置：用户手机号
	 */
    public String getHuiyuanPhone() {
        return huiyuanPhone;
    }
    /**
	 * 获取：用户手机号
	 */

    public void setHuiyuanPhone(String huiyuanPhone) {
        this.huiyuanPhone = huiyuanPhone;
    }
    /**
	 * 设置：用户身份证号
	 */
    public String getHuiyuanIdNumber() {
        return huiyuanIdNumber;
    }
    /**
	 * 获取：用户身份证号
	 */

    public void setHuiyuanIdNumber(String huiyuanIdNumber) {
        this.huiyuanIdNumber = huiyuanIdNumber;
    }
    /**
	 * 设置：用户头像
	 */
    public String getHuiyuanPhoto() {
        return huiyuanPhoto;
    }
    /**
	 * 获取：用户头像
	 */

    public void setHuiyuanPhoto(String huiyuanPhoto) {
        this.huiyuanPhoto = huiyuanPhoto;
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
	 * 设置：电子邮箱
	 */
    public String getHuiyuanEmail() {
        return huiyuanEmail;
    }
    /**
	 * 获取：电子邮箱
	 */

    public void setHuiyuanEmail(String huiyuanEmail) {
        this.huiyuanEmail = huiyuanEmail;
    }
    /**
	 * 设置：余额
	 */
    public Double getNewMoney() {
        return newMoney;
    }
    /**
	 * 获取：余额
	 */

    public void setNewMoney(Double newMoney) {
        this.newMoney = newMoney;
    }
    /**
	 * 设置：用户介绍
	 */
    public String getHuiyuanContent() {
        return huiyuanContent;
    }
    /**
	 * 获取：用户介绍
	 */

    public void setHuiyuanContent(String huiyuanContent) {
        this.huiyuanContent = huiyuanContent;
    }
    /**
	 * 设置：创建时间
	 */
    public Date getCreateTime() {
        return createTime;
    }
    /**
	 * 获取：创建时间
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Huiyuan{" +
            "id=" + id +
            ", username=" + username +
            ", password=" + password +
            ", huiyuanName=" + huiyuanName +
            ", huiyuanPhone=" + huiyuanPhone +
            ", huiyuanIdNumber=" + huiyuanIdNumber +
            ", huiyuanPhoto=" + huiyuanPhoto +
            ", sexTypes=" + sexTypes +
            ", huiyuanEmail=" + huiyuanEmail +
            ", newMoney=" + newMoney +
            ", huiyuanContent=" + huiyuanContent +
            ", createTime=" + createTime +
        "}";
    }
}
