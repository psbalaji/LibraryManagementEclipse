package com.accolite.library.model;

import javax.xml.bind.annotation.XmlRootElement;

import org.springframework.stereotype.Component;

@Component
@XmlRootElement
public class Resource {
	
	private int resourceId;
	private String TitleName;
	private int TitleId;
	private int allocated ;
	private int locationId;
	private int typeId;
	private String cityName;
	
	public String getCityName() {
		return cityName;
	}
	public void setCityName(String cityName) {
		this.cityName = cityName;
	}
	public int getResourceId() {
		return resourceId;
	}
	@Override
	public String toString() {
		return "Resource [resourceId=" + resourceId + ", TitleName=" + TitleName + ", TitleId=" + TitleId
				+ ", allocated=" + allocated + ", locationId=" + locationId + ", typeId=" + typeId + "]";
	}
	public void setResourceId(int resourceId) {
		this.resourceId = resourceId;
	}
	public String getTitleName() {
		return TitleName;
	}
	public void setTitleName(String titleName) {
		TitleName = titleName;
	}
	public int getTitleId() {
		return TitleId;
	}
	public void setTitleId(int titleId) {
		TitleId = titleId;
	}
	public int getAllocated() {
		return allocated;
	}
	public void setAllocated(int allocated) {
		this.allocated = allocated;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	public int getTypeId() {
		return typeId;
	}
	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

}
