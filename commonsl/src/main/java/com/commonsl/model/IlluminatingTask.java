package com.commonsl.model;

public class IlluminatingTask implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/**  */
	protected String closeTime;
	
	/**  */
	protected String deviceId;
	
	/**  */
	protected Integer flash;
	
	/**  */
	protected String groupName;
	
	/**  */
	protected Double illuminatingBrightness;
	
	/**  */
	protected String illuminatingTaskName;
	
	/**  */
	protected String openTime;
	
	/**  */
	protected Integer playDate;
	
	/**  */
	protected Integer state;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public String getCloseTime() {
		return closeTime;
	}
	
	public void setCloseTime(String closeTime) {
		this.closeTime = closeTime;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public Integer getFlash() {
		return flash;
	}
	
	public void setFlash(Integer flash) {
		this.flash = flash;
	}
	
	public String getGroupName() {
		return groupName;
	}
	
	public void setGroupName(String groupName) {
		this.groupName = groupName;
	}
	
	public Double getIlluminatingBrightness() {
		return illuminatingBrightness;
	}
	
	public void setIlluminatingBrightness(Double illuminatingBrightness) {
		this.illuminatingBrightness = illuminatingBrightness;
	}
	
	public String getIlluminatingTaskName() {
		return illuminatingTaskName;
	}
	
	public void setIlluminatingTaskName(String illuminatingTaskName) {
		this.illuminatingTaskName = illuminatingTaskName;
	}
	
	public String getOpenTime() {
		return openTime;
	}
	
	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}
	
	public Integer getPlayDate() {
		return playDate;
	}
	
	public void setPlayDate(Integer playDate) {
		this.playDate = playDate;
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
		builder.append("closeTime = ").append(closeTime).append(", ");
		builder.append("deviceId = ").append(deviceId).append(", ");
		builder.append("flash = ").append(flash).append(", ");
		builder.append("groupName = ").append(groupName).append(", ");
		builder.append("illuminatingBrightness = ").append(illuminatingBrightness).append(", ");
		builder.append("illuminatingTaskName = ").append(illuminatingTaskName).append(", ");
		builder.append("openTime = ").append(openTime).append(", ");
		builder.append("playDate = ").append(playDate).append(", ");
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
		IlluminatingTask other = (IlluminatingTask) obj;
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