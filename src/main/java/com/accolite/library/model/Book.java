package com.accolite.library.model;

public class Book {
	private int BookId;
	
	
	public int getBookId() {
		return BookId;
	}
	public void setBookId(int bookId) {
		BookId = bookId;
	}
	public int getTitleId() {
		return TitleId;
	}
	public void setTitleId(int titleId) {
		TitleId = titleId;
	}
	public String getAllocated() {
		return allocated;
	}
	public void setAllocated(String allocated) {
		this.allocated = allocated;
	}
	public int getLocationId() {
		return locationId;
	}
	public void setLocationId(int locationId) {
		this.locationId = locationId;
	}
	private int TitleId;
	private String allocated ;
	private int locationId;
	
	

}
