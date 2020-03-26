package com.commonsl.model;

public class ReportForms implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/** 人体感应 */
	protected Integer bodyInductor;
	
	/**  */
	protected java.util.Date createDate;
	
	/**  */
	protected String deviceId;
	
	/** 湿度 */
	protected Double humidity;
	
	/** 照明亮度 */
	protected Double illuminatingBrightness;
	
	/**  */
	protected Double opticalInductor;
	
	/**  */
	protected Double pm25;
	
	/** 烟感 */
	protected Integer smokeSensors;
	
	/** 温度 */
	protected Double temperature;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getBodyInductor() {
		return bodyInductor;
	}
	
	public void setBodyInductor(Integer bodyInductor) {
		this.bodyInductor = bodyInductor;
	}
	
	public java.util.Date getCreateDate() {
		return createDate;
	}
	
	public void setCreateDate(java.util.Date createDate) {
		this.createDate = createDate;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public Double getHumidity() {
		return humidity;
	}
	
	public void setHumidity(Double humidity) {
		this.humidity = humidity;
	}
	
	public Double getIlluminatingBrightness() {
		return illuminatingBrightness;
	}
	
	public void setIlluminatingBrightness(Double illuminatingBrightness) {
		this.illuminatingBrightness = illuminatingBrightness;
	}
	
	public Double getOpticalInductor() {
		return opticalInductor;
	}
	
	public void setOpticalInductor(Double opticalInductor) {
		this.opticalInductor = opticalInductor;
	}
	
	public Double getPm25() {
		return pm25;
	}
	
	public void setPm25(Double pm25) {
		this.pm25 = pm25;
	}
	
	public Integer getSmokeSensors() {
		return smokeSensors;
	}
	
	public void setSmokeSensors(Integer smokeSensors) {
		this.smokeSensors = smokeSensors;
	}
	
	public Double getTemperature() {
		return temperature;
	}
	
	public void setTemperature(Double temperature) {
		this.temperature = temperature;
	}
	
 	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id = ").append(id).append(", ");
		builder.append("bodyInductor = ").append(bodyInductor).append(", ");
		builder.append("createDate = ").append(createDate).append(", ");
		builder.append("deviceId = ").append(deviceId).append(", ");
		builder.append("humidity = ").append(humidity).append(", ");
		builder.append("illuminatingBrightness = ").append(illuminatingBrightness).append(", ");
		builder.append("opticalInductor = ").append(opticalInductor).append(", ");
		builder.append("pm25 = ").append(pm25).append(", ");
		builder.append("smokeSensors = ").append(smokeSensors).append(", ");
		builder.append("temperature = ").append(temperature);
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
		ReportForms other = (ReportForms) obj;
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