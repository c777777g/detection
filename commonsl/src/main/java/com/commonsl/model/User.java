package com.commonsl.model;

public class User implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/** 别名 */
	protected String alias;
	
	/** 创建时间 */
	protected java.util.Date creationTime;
	
	/**  */
	protected String email;
	
	/** 上次登陆时间 */
	protected java.util.Date landingTime;
	
	/** 备注 */
	protected String note;
	
	/**  */
	protected String phone;
	
	/** 身份 */
	protected String role;
	
	/** 状态 */
	protected String state;
	
	/** 用户账号 */
	protected String userName;
	
	/**  */
	protected String userPassword;
	
	/** 微信号 */
	protected String wxAccount;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getAlias() {
		return alias;
	}
	
	public void setAlias(String alias) {
		this.alias = alias;
	}
	
	public java.util.Date getCreationTime() {
		return creationTime;
	}
	
	public void setCreationTime(java.util.Date creationTime) {
		this.creationTime = creationTime;
	}
	
	public String getEmail() {
		return email;
	}
	
	public void setEmail(String email) {
		this.email = email;
	}
	
	public java.util.Date getLandingTime() {
		return landingTime;
	}
	
	public void setLandingTime(java.util.Date landingTime) {
		this.landingTime = landingTime;
	}
	
	public String getNote() {
		return note;
	}
	
	public void setNote(String note) {
		this.note = note;
	}
	
	public String getPhone() {
		return phone;
	}
	
	public void setPhone(String phone) {
		this.phone = phone;
	}
	
	public String getRole() {
		return role;
	}
	
	public void setRole(String role) {
		this.role = role;
	}
	
	public String getState() {
		return state;
	}
	
	public void setState(String state) {
		this.state = state;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getUserPassword() {
		return userPassword;
	}
	
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
	public String getWxAccount() {
		return wxAccount;
	}
	
	public void setWxAccount(String wxAccount) {
		this.wxAccount = wxAccount;
	}
	
 	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id = ").append(id).append(", ");
		builder.append("alias = ").append(alias).append(", ");
		builder.append("creationTime = ").append(creationTime).append(", ");
		builder.append("email = ").append(email).append(", ");
		builder.append("landingTime = ").append(landingTime).append(", ");
		builder.append("note = ").append(note).append(", ");
		builder.append("phone = ").append(phone).append(", ");
		builder.append("role = ").append(role).append(", ");
		builder.append("state = ").append(state).append(", ");
		builder.append("userName = ").append(userName).append(", ");
		builder.append("userPassword = ").append(userPassword).append(", ");
		builder.append("wxAccount = ").append(wxAccount);
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (getClass() != obj.getClass()) {
			return false;
		}
		User other = (User) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

}