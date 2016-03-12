package com.csc6999_mobileappdev.no_more_nagging_inator_app;
import android.graphics.Bitmap;
import android.text.format.Time;

@SuppressWarnings("deprecation")
public class Child {
	private int childID;
	private String childName;
	private String phoneNumber;
	private Bitmap picture;
	private Time birthday;

	public Child() {
        childID = -1;
		Time t = new Time();
		t.setToNow();
		birthday = t;
	}

	public int getChildID() {
		return childID;
	}

	public void setChildID(int i) {
        childID = i;
	}

	public String getChildName() {
		return childName;
	}
	public void setChildName(String s) {
        childName = s;
	}

	public void setPhoneNumber(String s) {
		phoneNumber = s;
	}
	public String getPhoneNumber() {
		return phoneNumber;
	}

	public void setPicture(Bitmap b) {
		picture = b;
	}
	public Bitmap getPicture() {
		return picture;
	}

	public Time getBirthday() {
		return birthday;
	}
	public void setBirthday(Time t) {
		birthday = t;
	}
}
