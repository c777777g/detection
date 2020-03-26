package com.commonsl.model;

public class Log implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/**  */
	protected String companyName;
	
	/**  */
	protected java.util.Date createTime;
	
	/**  */
	protected String deviceId;
	
	/**  */
	protected String deviceName;
	
	/**  */
	protected String message;
	
	/** 1视频警报，2传感器报警，3配置，4控制 */
	protected Integer type;
	
	/**  */
	protected String userName;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCompanyName() {
		return companyName;
	}
	
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public String getDeviceName() {
		return deviceName;
	}
	
	public void setDeviceName(String deviceName) {
		this.deviceName = deviceName;
	}
	
	public String getMessage() {
		return message;
	}
	
	public void setMessage(String message) {
		this.message = message;
	}
	
	public Integer getType() {
		return type;
	}
	
	public void setType(Integer type) {
		this.type = type;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
 	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id = ").append(id).append(", ");
		builder.append("companyName = ").append(companyName).append(", ");
		builder.append("createTime = ").append(createTime).append(", ");
		builder.append("deviceId = ").append(deviceId).append(", ");
		builder.append("deviceName = ").append(deviceName).append(", ");
		builder.append("message = ").append(message).append(", ");
		builder.append("type = ").append(type).append(", ");
		builder.append("userName = ").append(userName);
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
		Log other = (Log) obj;
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