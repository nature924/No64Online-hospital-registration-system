package com.entity.view;

import com.entity.ForumEntity;
import com.baomidou.mybatisplus.annotations.TableName;
import org.apache.commons.beanutils.BeanUtils;
import java.lang.reflect.InvocationTargetException;
import org.springframework.format.annotation.DateTimeFormat;
import com.fasterxml.jackson.annotation.JsonFormat;
import java.io.Serializable;
import java.util.Date;

/**
 * 论坛
 * 后端返回视图实体辅助类
 * （通常后端关联的表或者自定义的字段需要返回使用）
 */
@TableName("forum")
public class ForumView extends ForumEntity implements Serializable {
    private static final long serialVersionUID = 1L;

		/**
		* 帖子类型的值
		*/
		private String forumValue;
		/**
		* 帖子状态的值
		*/
		private String forumStateValue;



		//级联表 huiyuan
			/**
			* 用户姓名
			*/
			private String huiyuanName;
			/**
			* 用户手机号
			*/
			private String huiyuanPhone;
			/**
			* 用户身份证号
			*/
			private String huiyuanIdNumber;
			/**
			* 用户头像
			*/
			private String huiyuanPhoto;
			/**
			* 电子邮箱
			*/
			private String huiyuanEmail;
			/**
			* 余额
			*/
			private Double newMoney;
			/**
			* 用户介绍
			*/
			private String huiyuanContent;

		//级联表 zhuanjia
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
			* 科室
			*/
			private Integer keshiTypes;
				/**
				* 科室的值
				*/
				private String keshiValue;
			/**
			* 职位
			*/
			private Integer zhiweiTypes;
				/**
				* 职位的值
				*/
				private String zhiweiValue;
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

		//级联表 users
			/**
			* 用户名
			*/
			private String uusername;
			/**
			* 密码
			*/
			private String upassword;
			/**
			* 角色
			*/
			private String urole;
			/**
			* 新增时间
			*/
			private Date uaddtime;

	public ForumView() {

	}

	public ForumView(ForumEntity forumEntity) {
		try {
			BeanUtils.copyProperties(this, forumEntity);
		} catch (IllegalAccessException | InvocationTargetException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}



			/**
			* 获取： 帖子类型的值
			*/
			public String getForumValue() {
				return forumValue;
			}
			/**
			* 设置： 帖子类型的值
			*/
			public void setForumValue(String forumValue) {
				this.forumValue = forumValue;
			}
			/**
			* 获取： 帖子状态的值
			*/
			public String getForumStateValue() {
				return forumStateValue;
			}
			/**
			* 设置： 帖子状态的值
			*/
			public void setForumStateValue(String forumStateValue) {
				this.forumStateValue = forumStateValue;
			}












				//级联表的get和set huiyuan

					/**
					* 获取： 用户姓名
					*/
					public String getHuiyuanName() {
						return huiyuanName;
					}
					/**
					* 设置： 用户姓名
					*/
					public void setHuiyuanName(String huiyuanName) {
						this.huiyuanName = huiyuanName;
					}

					/**
					* 获取： 用户手机号
					*/
					public String getHuiyuanPhone() {
						return huiyuanPhone;
					}
					/**
					* 设置： 用户手机号
					*/
					public void setHuiyuanPhone(String huiyuanPhone) {
						this.huiyuanPhone = huiyuanPhone;
					}

					/**
					* 获取： 用户身份证号
					*/
					public String getHuiyuanIdNumber() {
						return huiyuanIdNumber;
					}
					/**
					* 设置： 用户身份证号
					*/
					public void setHuiyuanIdNumber(String huiyuanIdNumber) {
						this.huiyuanIdNumber = huiyuanIdNumber;
					}

					/**
					* 获取： 用户头像
					*/
					public String getHuiyuanPhoto() {
						return huiyuanPhoto;
					}
					/**
					* 设置： 用户头像
					*/
					public void setHuiyuanPhoto(String huiyuanPhoto) {
						this.huiyuanPhoto = huiyuanPhoto;
					}

					/**
					* 获取： 电子邮箱
					*/
					public String getHuiyuanEmail() {
						return huiyuanEmail;
					}
					/**
					* 设置： 电子邮箱
					*/
					public void setHuiyuanEmail(String huiyuanEmail) {
						this.huiyuanEmail = huiyuanEmail;
					}

					/**
					* 获取： 余额
					*/
					public Double getNewMoney() {
						return newMoney;
					}
					/**
					* 设置： 余额
					*/
					public void setNewMoney(Double newMoney) {
						this.newMoney = newMoney;
					}

					/**
					* 获取： 用户介绍
					*/
					public String getHuiyuanContent() {
						return huiyuanContent;
					}
					/**
					* 设置： 用户介绍
					*/
					public void setHuiyuanContent(String huiyuanContent) {
						this.huiyuanContent = huiyuanContent;
					}









				//级联表的get和set zhuanjia

					/**
					* 获取： 医生姓名
					*/
					public String getZhuanjiaName() {
						return zhuanjiaName;
					}
					/**
					* 设置： 医生姓名
					*/
					public void setZhuanjiaName(String zhuanjiaName) {
						this.zhuanjiaName = zhuanjiaName;
					}

					/**
					* 获取： 医生手机号
					*/
					public String getZhuanjiaPhone() {
						return zhuanjiaPhone;
					}
					/**
					* 设置： 医生手机号
					*/
					public void setZhuanjiaPhone(String zhuanjiaPhone) {
						this.zhuanjiaPhone = zhuanjiaPhone;
					}

					/**
					* 获取： 医生身份证号
					*/
					public String getZhuanjiaIdNumber() {
						return zhuanjiaIdNumber;
					}
					/**
					* 设置： 医生身份证号
					*/
					public void setZhuanjiaIdNumber(String zhuanjiaIdNumber) {
						this.zhuanjiaIdNumber = zhuanjiaIdNumber;
					}

					/**
					* 获取： 医生头像
					*/
					public String getZhuanjiaPhoto() {
						return zhuanjiaPhoto;
					}
					/**
					* 设置： 医生头像
					*/
					public void setZhuanjiaPhoto(String zhuanjiaPhoto) {
						this.zhuanjiaPhoto = zhuanjiaPhoto;
					}

					/**
					* 获取： 科室
					*/
					public Integer getKeshiTypes() {
						return keshiTypes;
					}
					/**
					* 设置： 科室
					*/
					public void setKeshiTypes(Integer keshiTypes) {
						this.keshiTypes = keshiTypes;
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
					* 获取： 职位
					*/
					public Integer getZhiweiTypes() {
						return zhiweiTypes;
					}
					/**
					* 设置： 职位
					*/
					public void setZhiweiTypes(Integer zhiweiTypes) {
						this.zhiweiTypes = zhiweiTypes;
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

					/**
					* 获取： 挂号费
					*/
					public Double getGuahaoMoney() {
						return guahaoMoney;
					}
					/**
					* 设置： 挂号费
					*/
					public void setGuahaoMoney(Double guahaoMoney) {
						this.guahaoMoney = guahaoMoney;
					}

					/**
					* 获取： 电子邮箱
					*/
					public String getZhuanjiaEmail() {
						return zhuanjiaEmail;
					}
					/**
					* 设置： 电子邮箱
					*/
					public void setZhuanjiaEmail(String zhuanjiaEmail) {
						this.zhuanjiaEmail = zhuanjiaEmail;
					}

					/**
					* 获取： 医生介绍
					*/
					public String getZhuanjiaContent() {
						return zhuanjiaContent;
					}
					/**
					* 设置： 医生介绍
					*/
					public void setZhuanjiaContent(String zhuanjiaContent) {
						this.zhuanjiaContent = zhuanjiaContent;
					}

					/**
					* 获取： 逻辑删除
					*/
					public Integer getZhuanjiaDelete() {
						return zhuanjiaDelete;
					}
					/**
					* 设置： 逻辑删除
					*/
					public void setZhuanjiaDelete(Integer zhuanjiaDelete) {
						this.zhuanjiaDelete = zhuanjiaDelete;
					}













			//级联表的get和set users

					/**
					* 获取： 用户名
					*/
					public String getUusername() {
						return uusername;
					}
					/**
					* 设置： 用户名
					*/
					public void setUusername(String uusername) {
						this.uusername = uusername;
					}

					/**
					* 获取： 密码
					*/
					public String getUpassword() {
						return upassword;
					}
					/**
					* 设置： 密码
					*/
					public void setUpassword(String upassword) {
						this.upassword = upassword;
					}

					/**
					* 获取： 角色
					*/
					public String getUrole() {
						return urole;
					}
					/**
					* 设置： 角色
					*/
					public void setUrole(String urole) {
						this.urole = urole;
					}

					/**
					* 获取： 新增时间
					*/
					public Date getUaddtime() {
						return uaddtime;
					}
					/**
					* 设置： 新增时间
					*/
					public void setUaddtime(Date uaddtime) {
						this.uaddtime = uaddtime;
					}
}
