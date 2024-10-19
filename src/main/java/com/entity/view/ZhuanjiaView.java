package com.entity.view;

import com.entity.ZhuanjiaEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 医生
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("zhuanjia")
public class ZhuanjiaView extends ZhuanjiaEntity implements Serializable {
    private static final long serialVersionUID = 1L;

		/**
		* 性别的值
		*/
		private String sexValue;
		/**
		* 科室的值
		*/
		private String keshiValue;
		/**
		* 职位的值
		*/
		private String zhiweiValue;



	public ZhuanjiaView() {

	}

	public ZhuanjiaView(ZhuanjiaEntity zhuanjiaEntity) {
		try {
			BeanUtils.copyProperties(this, zhuanjiaEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 性别的值
			*/
			public String getSexValue() {
				return sexValue;
			}
			/**
			* 设置： 性别的值
			*/
			public void setSexValue(String sexValue) {
				this.sexValue = sexValue;
			}
			/**
			* 获取： 科室的值
			*/
			public String getKeshiValue() {
				return keshiValue;
			}
			/**
			* 设置： 科室的值
			*/
			public void setKeshiValue(String keshiValue) {
				this.keshiValue = keshiValue;
			}
			/**
			* 获取： 职位的值
			*/
			public String getZhiweiValue() {
				return zhiweiValue;
			}
			/**
			* 设置： 职位的值
			*/
			public void setZhiweiValue(String zhiweiValue) {
				this.zhiweiValue = zhiweiValue;
			}










}
