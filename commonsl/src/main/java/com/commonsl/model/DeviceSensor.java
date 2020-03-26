package com.commonsl.model;

public class DeviceSensor implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/**  */
	protected Integer bodyInductor;
	
	/**  */
	protected String deviceId;
	
	/**  */
	protected Double humidity;
	
	/**  */
	protected Double illuminatingBrightness;
	
	/**  */
	protected String latlng;
	
	/**  */
	protected Double opticalInductor;
	
	/**  */
	protected Integer smokeSensors;

	/**  */
	protected Double temperature;

	/**  */
	protected Double pm25;


	public Double getPm25() {
		return pm25;
	}

	public void setPm25(Double pm25 ) {
		this.pm25=pm25 ;
	}

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
	
	public String getLatlng() {
		return latlng;
	}
	
	public void setLatlng(String latlng) {
		this.latlng = latlng;
	}
	
	public Double getOpticalInductor() {
		return opticalInductor;
	}
	
	public void setOpticalInductor(Double opticalInductor) {
		this.opticalInductor = opticalInductor;
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
		builder.append("deviceId = ").append(deviceId).append(", ");
		builder.append("humidity = ").append(humidity).append(", ");
		builder.append("illuminatingBrightness = ").append(illuminatingBrightness).append(", ");
		builder.append("latlng = ").append(latlng).append(", ");
		builder.append("opticalInductor = ").append(opticalInductor).append(", ");
		builder.append("smokeSensors = ").append(smokeSensors).append(", ");
		builder.append("temperature = ").append(temperature).append(", ");
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
		DeviceSensor other = (DeviceSensor) obj;
		if (id == null) {
			if (other.id != null) {
				return false;
			}
		} else if (!id.equals(other.id)) {
			return false;
		}
		return true;
	}

	public DeviceSensor deviceSensorClone() {
		DeviceSensor deviceSensor = new DeviceSensor();
		deviceSensor.setDeviceId(getDeviceId());
		deviceSensor.setId(getId());
		deviceSensor.setHumidity(getHumidity());
		deviceSensor.setTemperature(getTemperature());
		deviceSensor.setIlluminatingBrightness(getIlluminatingBrightness());
		deviceSensor.setBodyInductor(getBodyInductor());
		deviceSensor.setLatlng(getLatlng());
		deviceSensor.setOpticalInductor(getOpticalInductor());
		deviceSensor.setSmokeSensors(getSmokeSensors());


		return deviceSensor;
	}

}