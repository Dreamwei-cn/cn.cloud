package cn.cloud.common.model;

import java.util.Date;

import cn.cloud.common.base.BaseModel;

public class User extends BaseModel{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	private String userName;
	private String userNum;
	private String userPhone;
	private String sex;
	private int level;
	private int age;
	
	private long localId;

	private int roleId;
	private String perName;
	
	private String email;
	
	private String passWord;
	
	private String salt;
	
	private Date lastlogDate;
	
	private Long lastAddrId;
	
    private Long lastIp;
    
    
    public User() {}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserNum() {
		return userNum;
	}

	public void setUserNum(String userNum) {
		this.userNum = userNum;
	}

	public String getUserPhone() {
		return userPhone;
	}

	public void setUserPhone(String userPhone) {
		this.userPhone = userPhone;
	}

	public String getSex() {
		return sex;
	}

	public void setSex(String sex) {
		this.sex = sex;
	}

	public int getLevel() {
		return level;
	}

	public void setLevel(int level) {
		this.level = level;
	}

	public int getAge() {
		return age;
	}

	public void setAge(int age) {
		this.age = age;
	}

	public long getLocalId() {
		return localId;
	}

	public void setLocalId(long localId) {
		this.localId = localId;
	}

	public int getRoleId() {
		return roleId;
	}

	public void setRoleId(int roleId) {
		this.roleId = roleId;
	}

	public String getPerName() {
		return perName;
	}

	public void setPerName(String perName) {
		this.perName = perName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassWord() {
		return passWord;
	}

	public void setPassWord(String passWord) {
		this.passWord = passWord;
	}

	public String getSalt() {
		return salt;
	}

	public void setSalt(String salt) {
		this.salt = salt;
	}

	public Date getLastlogDate() {
		return lastlogDate;
	}

	public void setLastlogDate(Date lastlogDate) {
		this.lastlogDate = lastlogDate;
	}

	public Long getLastAddrId() {
		return lastAddrId;
	}

	public void setLastAddrId(Long lastAddrId) {
		this.lastAddrId = lastAddrId;
	}

	public Long getLastIp() {
		return lastIp;
	}

	public void setLastIp(Long lastIp) {
		this.lastIp = lastIp;
	}
    
    



}
