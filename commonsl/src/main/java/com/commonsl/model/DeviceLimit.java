package com.commonsl.model;

public class DeviceLimit implements Entity {
	
	private static final long serialVersionUID = 1L;
	
	/**  */
	protected Integer id;
	
	/**  */
	protected Integer bodyInductoAlarm;
	
	/**  */
	protected String deviceId;
	
	/**  */
	protected Integer humidityAlarm;
	
	/**  */
	protected Double humidityMax;
	
	/**  */
	protected Double humidityMin;
	
	/**  */
	protected Integer opticalInductorAlarm;
	
	/**  */
	protected Integer pm25Alarm;
	
	/**  */
	protected Double pm25Max;
	
	/**  */
	protected Double pm25Min;
	
	/**  */
	protected Integer smokeSensorsAlarm;
	
	/**  */
	protected Integer temperatureAlarm;
	
	/**  */
	protected Double temperatureMax;
	
	/**  */
	protected Double temperatureMin;

	/**  */
	protected Double opticalInductorMin;

	/**  */
	protected Double opticalInductorMax;


	/** 	0 没有模式，1 夜间模式 */
	protected Integer mode;
	
 	public Integer getId() {
		return id;
	}
	
	public void setId(Integer id) {
		this.id = id;
	}
	
	public Integer getBodyInductoAlarm() {
		return bodyInductoAlarm;
	}
	
	public void setBodyInductoAlarm(Integer bodyInductoAlarm) {
		this.bodyInductoAlarm = bodyInductoAlarm;
	}
	
	public String getDeviceId() {
		return deviceId;
	}
	
	public void setDeviceId(String deviceId) {
		this.deviceId = deviceId;
	}
	
	public Integer getHumidityAlarm() {
		return humidityAlarm;
	}
	
	public void setHumidityAlarm(Integer humidityAlarm) {
		this.humidityAlarm = humidityAlarm;
	}
	
	public Double getHumidityMax() {
		return humidityMax;
	}
	
	public void setHumidityMax(Double humidityMax) {
		this.humidityMax = humidityMax;
	}
	
	public Double getHumidityMin() {
		return humidityMin;
	}
	
	public void setHumidityMin(Double humidityMin) {
		this.humidityMin = humidityMin;
	}
	
	public Integer getOpticalInductorAlarm() {
		return opticalInductorAlarm;
	}
	
	public void setOpticalInductorAlarm(Integer opticalInductorAlarm) {
		this.opticalInductorAlarm = opticalInductorAlarm;
	}
	
	public Integer getPm25Alarm() {
		return pm25Alarm;
	}
	
	public void setPm25Alarm(Integer pm25Alarm) {
		this.pm25Alarm = pm25Alarm;
	}
	
	public Double getPm25Max() {
		return pm25Max;
	}
	
	public void setPm25Max(Double pm25Max) {
		this.pm25Max = pm25Max;
	}
	
	public Double getPm25Min() {
		return pm25Min;
	}
	
	public void setPm25Min(Double pm25Min) {
		this.pm25Min = pm25Min;
	}
	
	public Integer getSmokeSensorsAlarm() {
		return smokeSensorsAlarm;
	}
	
	public void setSmokeSensorsAlarm(Integer smokeSensorsAlarm) {
		this.smokeSensorsAlarm = smokeSensorsAlarm;
	}
	
	public Integer getTemperatureAlarm() {
		return temperatureAlarm;
	}
	
	public void setTemperatureAlarm(Integer temperatureAlarm) {
		this.temperatureAlarm = temperatureAlarm;
	}
	
	public Double getTemperatureMax() {
		return temperatureMax;
	}
	
	public void setTemperatureMax(Double temperatureMax) {
		this.temperatureMax = temperatureMax;
	}
	
	public Double getTemperatureMin() {
		return temperatureMin;
	}
	
	public void setTemperatureMin(Double temperatureMin) {
		this.temperatureMin = temperatureMin;
	}

	public Integer getMode() {
		return mode;
	}

	public void setMode(Integer mode) {
		this.mode = mode;
	}

	public Double getOpticalInductorMin() {
		return opticalInductorMin;
	}

	public void setOpticalInductorMin(Double opticalInductorMin) {
		this.opticalInductorMin = opticalInductorMin;
	}

	public Double getOpticalInductorMax() {
		return opticalInductorMax;
	}

	public void setOpticalInductorMax(Double opticalInductorMax) {
		this.opticalInductorMax = opticalInductorMax;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("id = ").append(id).append(", ");
		builder.append("bodyInductoAlarm = ").append(bodyInductoAlarm).append(", ");
		builder.append("deviceId = ").append(deviceId).append(", ");
		builder.append("humidityAlarm = ").append(humidityAlarm).append(", ");
		builder.append("humidityMax = ").append(humidityMax).append(", ");
		builder.append("humidityMin = ").append(humidityMin).append(", ");
		builder.append("opticalInductorAlarm = ").append(opticalInductorAlarm).append(", ");
		builder.append("pm25Alarm = ").append(pm25Alarm).append(", ");
		builder.append("pm25Max = ").append(pm25Max).append(", ");
		builder.append("pm25Min = ").append(pm25Min).append(", ");
		builder.append("smokeSensorsAlarm = ").append(smokeSensorsAlarm).append(", ");
		builder.append("temperatureAlarm = ").append(temperatureAlarm).append(", ");
		builder.append("temperatureMax = ").append(temperatureMax).append(", ");
		builder.append("mode = ").append(mode).append(", ");
		builder.append("opticalInductorMin = ").append(opticalInductorMin).append(", ");
		builder.append("opticalInductorMax = ").append(opticalInductorMax).append(", ");
		builder.append("temperatureMin = ").append(temperatureMin);
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
		DeviceLimit other = (DeviceLimit) obj;
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