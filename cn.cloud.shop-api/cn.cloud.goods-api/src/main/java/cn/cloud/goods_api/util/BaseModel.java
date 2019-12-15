package cn.cloud.goods_api.util;

import java.io.Serializable;

import javax.persistence.Id;



/**
 * @author Dream
 * 类
 */
public class BaseModel implements Serializable ,Cloneable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Id
	protected Long id;
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	

}
