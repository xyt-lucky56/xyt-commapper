package com.xyt.common.base;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2857908005615636686L;

	/**
	 * 有效 1
	 */
	public static final Integer VALID = 1;
	/**
	 * 无效 0
	 */
	public static final Integer INVALID = 0;

	/**
	 * 可用 1
	 */
	public static final Integer USE = 1;
	/**
	 * 不可用 2
	 */
	public static final Integer UNUSE = 2;

	/**
	 * 版本号
	 */
	public static final Integer VERSION_CODE = 1;

	/** 主键id */
	@ApiModelProperty(value = "主键id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@OrderBy("DESC")
	protected String id;
	/** 是否有效1：有效0：无效 */
	@ApiModelProperty(value = "删除标识", hidden = true)
	protected Integer valid;
	/** 创建时间 */
	@ApiModelProperty(value = "创建时间", hidden = true)
	@Column(name = "create_time")
	protected Date createTime;
	/**
	 *
	 * <pre>
	 * 修改时设置当前用户和IP
	 * </pre>
	 *
	 */
/*	public void updateBy() {
		try {
//			UserInfo userInfo = SecurityInfoUtil.getUserInfo();
			this.modifierId =  -1L;
			this.modifier = "系统_user";
			this.modifyTime = new Date();
			this.modifierIp = "127.0.0.1";
		} catch (Exception e) {
			this.modifierId = -1L;
			this.modifier = "系统_user";
			this.modifyTime = new Date();
			this.modifierIp = "127.0.0.1";
		}
	}*/

	/**
	 *
	 * <pre>
	 * 添加时设置当前用户和IP
	 * </pre>
	 *
	 */
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Integer getValid() {
		return valid;
	}

	public void setValid(Integer valid) {
		this.valid = valid;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
/*	public void addBy() {
		try {
//			UserInfo userInfo = SecurityInfoUtil.getUserInfo();
			this.createId =  -1L;
			this.operator = "系统_user";
			this.createTime = new Date();
			this.deleted = VALID;
			this.updateBy();
		} catch (Exception e) {
			this.createId = -1L;
			this.operator = "系统_user";
			this.createTime = new Date();
			this.deleted = VALID;
			this.updateBy();
		}

	}*/

}
