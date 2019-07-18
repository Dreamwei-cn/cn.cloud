package cn.cloud.common.base;

import java.io.Serializable;
import java.util.Date;

    
/**
 * @author Dream
 * @since 2019年7月15日09:40:10
 *        基础类
 */
public class BaseModel implements Serializable{
		
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private  int id;
	private boolean enabled;
	private String creatorString;
	
	private Date creatDate;
	
	
	public BaseModel() {}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public String getCreatorString() {
		return creatorString;
	}

	public void setCreatorString(String creatorString) {
		this.creatorString = creatorString;
	}

	public Date getCreatDate() {
		return creatDate;
	}

	public void setCreatDate(Date creatDate) {
		this.creatDate = creatDate;
	}
	
	
	

}
