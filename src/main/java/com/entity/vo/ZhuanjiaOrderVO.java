package com.entity.vo;

import com.entity.ZhuanjiaOrderEntity;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableName;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.util.Date;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;

/**
 * 医生挂号订单
 * 手机端接口返回实体辅助类
 * （主要作用去除一些不必要的字段）
 */
@TableName("zhuanjia_order")
public class ZhuanjiaOrderVO implements Serializable {
    private static final long serialVersionUID = 1L;


    /**
     * 主键
     */

    @TableField(value = "id")
    private Integer id;


    /**
     * 号
     */

    @TableField(value = "zhuanjia_order_uuid_number")
    private String zhuanjiaOrderUuidNumber;


    /**
     * 医生
     */

    @TableField(value = "zhuanjia_id")
    private Integer zhuanjiaId;


    /**
     * 用户
     */

    @TableField(value = "huiyuan_id")
    private Integer huiyuanId;


    /**
     * 挂号日期
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "guahao_time")
    private Date guahaoTime;


    /**
     * 时间段
     */

    @TableField(value = "shijianduan_types")
    private Integer shijianduanTypes;


    /**
     * 状态
     */

    @TableField(value = "zhuanjia_order_yesno_types")
    private Integer zhuanjiaOrderYesnoTypes;


    /**
     * 审核意见
     */

    @TableField(value = "zhuanjia_order_yesno_text")
    private String zhuanjiaOrderYesnoText;


    /**
     * 订单创建时间
     */
    @JsonFormat(locale="zh", timezone="GMT+8", pattern="yyyy-MM-dd HH:mm:ss")
	@DateTimeFormat

    @TableField(value = "insert_time")
    private Date insertTime;


    /**
     * 创建时间 show3
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
	 * 设置：号
	 */
    public String getZhuanjiaOrderUuidNumber() {
        return zhuanjiaOrderUuidNumber;
    }


    /**
	 * 获取：号
	 */

    public void setZhuanjiaOrderUuidNumber(String zhuanjiaOrderUuidNumber) {
        this.zhuanjiaOrderUuidNumber = zhuanjiaOrderUuidNumber;
    }
    /**
	 * 设置：医生
	 */
    public Integer getZhuanjiaId() {
        return zhuanjiaId;
    }


    /**
	 * 获取：医生
	 */

    public void setZhuanjiaId(Integer zhuanjiaId) {
        this.zhuanjiaId = zhuanjiaId;
    }
    /**
	 * 设置：用户
	 */
    public Integer getHuiyuanId() {
        return huiyuanId;
    }


    /**
	 * 获取：用户
	 */

    public void setHuiyuanId(Integer huiyuanId) {
        this.huiyuanId = huiyuanId;
    }
    /**
	 * 设置：挂号日期
	 */
    public Date getGuahaoTime() {
        return guahaoTime;
    }


    /**
	 * 获取：挂号日期
	 */

    public void setGuahaoTime(Date guahaoTime) {
        this.guahaoTime = guahaoTime;
    }
    /**
	 * 设置：时间段
	 */
    public Integer getShijianduanTypes() {
        return shijianduanTypes;
    }


    /**
	 * 获取：时间段
	 */

    public void setShijianduanTypes(Integer shijianduanTypes) {
        this.shijianduanTypes = shijianduanTypes;
    }
    /**
	 * 设置：状态
	 */
    public Integer getZhuanjiaOrderYesnoTypes() {
        return zhuanjiaOrderYesnoTypes;
    }


    /**
	 * 获取：状态
	 */

    public void setZhuanjiaOrderYesnoTypes(Integer zhuanjiaOrderYesnoTypes) {
        this.zhuanjiaOrderYesnoTypes = zhuanjiaOrderYesnoTypes;
    }
    /**
	 * 设置：审核意见
	 */
    public String getZhuanjiaOrderYesnoText() {
        return zhuanjiaOrderYesnoText;
    }


    /**
	 * 获取：审核意见
	 */

    public void setZhuanjiaOrderYesnoText(String zhuanjiaOrderYesnoText) {
        this.zhuanjiaOrderYesnoText = zhuanjiaOrderYesnoText;
    }
    /**
	 * 设置：订单创建时间
	 */
    public Date getInsertTime() {
        return insertTime;
    }


    /**
	 * 获取：订单创建时间
	 */

    public void setInsertTime(Date insertTime) {
        this.insertTime = insertTime;
    }
    /**
	 * 设置：创建时间 show3
	 */
    public Date getCreateTime() {
        return createTime;
    }


    /**
	 * 获取：创建时间 show3
	 */

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

}
