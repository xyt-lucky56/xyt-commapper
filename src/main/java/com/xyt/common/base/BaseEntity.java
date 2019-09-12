package com.xyt.common.base;

import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

public class BaseEntity implements Serializable {

	private static final long serialVersionUID = 2857908005615636686L;

	/**
	 * 版本号
	 */
	public static final Integer VERSION_CODE = 1;

	/** 主键id
	 *
	 */
	@ApiModelProperty(value = "主键id")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id")
	@OrderBy("DESC")
	protected String id;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}
}
