package com.commonsl.model;

public class Device implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/**  */
	protected String bluetoothId;
	
	/**  */
	protected String companyName;
	
	/**  */
	protected java.util.Date createTime;
	
	/**  */
	protected String deviceAddress;
	
	/**  */
	protected String deviceId;
	
	/**  */
	protected String deviceName;
	
	/**  */
	protected String district;
	
	/**  */
	protected String ip;
	
	/**  */
	protected Integer ipSetStatic;
	
	/**  */
	protected String ssid;
	
	/**  */
	protected Integer state;
	
	/**  */
	protected String staticGateway;
	
	/**  */
	protected String staticIp;
	
	/**  */
	protected String staticSubnetMask;
	
	/**  */
	protected String userName;
	
	/**  */
	protected String wifiPassword;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getBluetoothId() {
		return bluetoothId;
	}
	
	public void setBluetoothId(String bluetoothId) {
		this.bluetoothId = bluetoothId;
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
	
	public String getDistrict() {
		return district;
	}
	
	public void setDistrict(String district) {
		this.district = district;
	}
	
	public String getIp() {
		return ip;
	}
	
	public void setIp(String ip) {
		this.ip = ip;
	}
	
	public Integer getIpSetStatic() {
		return ipSetStatic;
	}
	
	public void setIpSetStatic(Integer ipSetStatic) {
		this.ipSetStatic = ipSetStatic;
	}
	
	public String getSsid() {
		return ssid;
	}
	
	public void setSsid(String ssid) {
		this.ssid = ssid;
	}
	
	public Integer getState() {
		return state;
	}
	
	public void setState(Integer state) {
		this.state = state;
	}
	
	public String getStaticGateway() {
		return staticGateway;
	}
	
	public void setStaticGateway(String staticGateway) {
		this.staticGateway = staticGateway;
	}
	
	public String getStaticIp() {
		return staticIp;
	}
	
	public void setStaticIp(String staticIp) {
		this.staticIp = staticIp;
	}
	
	public String getStaticSubnetMask() {
		return staticSubnetMask;
	}
	
	public void setStaticSubnetMask(String staticSubnetMask) {
		this.staticSubnetMask = staticSubnetMask;
	}
	
	public String getUserName() {
		return userName;
	}
	
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getWifiPassword() {
		return wifiPassword;
	}
	
	public void setWifiPassword(String wifiPassword) {
		this.wifiPassword = wifiPassword;
	}
	
 	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id = ").append(id).append(", ");
		builder.append("bluetoothId = ").append(bluetoothId).append(", ");
		builder.append("companyName = ").append(companyName).append(", ");
		builder.append("createTime = ").append(createTime).append(", ");
		builder.append("deviceAddress = ").append(deviceAddress).append(", ");
		builder.append("deviceId = ").append(deviceId).append(", ");
		builder.append("deviceName = ").append(deviceName).append(", ");
		builder.append("district = ").append(district).append(", ");
		builder.append("ip = ").append(ip).append(", ");
		builder.append("ipSetStatic = ").append(ipSetStatic).append(", ");
		builder.append("ssid = ").append(ssid).append(", ");
		builder.append("state = ").append(state).append(", ");
		builder.append("staticGateway = ").append(staticGateway).append(", ");
		builder.append("staticIp = ").append(staticIp).append(", ");
		builder.append("staticSubnetMask = ").append(staticSubnetMask).append(", ");
		builder.append("userName = ").append(userName).append(", ");
		builder.append("wifiPassword = ").append(wifiPassword);
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
		Device other = (Device) obj;
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