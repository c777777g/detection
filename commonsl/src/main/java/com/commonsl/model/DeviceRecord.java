package com.commonsl.model;

public class DeviceRecord implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/** 创建时间 */
	protected java.util.Date createTime;
	
	/** 设备地址 */
	protected String deviceAddress;
	
	/** 设备地址 */
	protected String deviceId;
	
	/** 设备名称 */
	protected String deviceName;
	
	/** 坐标 */
	protected String latlng;
	
	/** 1.在线， 2.离线 */
	protected Integer state;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public java.util.Date getCreateTime() {
		return createTime;
	}
	
	public void setCreateTime(java.util.Date createTime) {
		this.createTime = createTime;
	}
	
	public String getDeviceAddress() {
		return deviceAddress;
	}
	
	public void setDeviceAddress(String deviceAddress) {
		this.deviceAddress = deviceAddress;
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
	
	public String getLatlng() {
		return latlng;
	}
	
	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}
	
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
 	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id = ").append(id).append(", ");
		builder.append("createTime = ").append(createTime).append(", ");
		builder.append("deviceAddress = ").append(deviceAddress).append(", ");
		builder.append("deviceId = ").append(deviceId).append(", ");
		builder.append("deviceName = ").append(deviceName).append(", ");
		builder.append("latlng = ").append(latlng).append(", ");
		builder.append("state = ").append(state);
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
		DeviceRecord other = (DeviceRecord) obj;
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