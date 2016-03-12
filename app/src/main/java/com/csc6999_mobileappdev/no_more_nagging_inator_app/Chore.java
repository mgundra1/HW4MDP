package com.csc6999_mobileappdev.no_more_nagging_inator_app;
import android.graphics.Bitmap;
import android.text.format.Time;

@SuppressWarnings("deprecation")
public class Chore {
	private int choreID;
	private String choreName;
	private String assignedChildName;
	private String duration;
	private String repetition;

	public Chore() {
        choreID = -1;
	}

	public int getChoreID() {
		return choreID;
	}

	public void setChoreID(int i)
	{
        choreID = i;
	}

	public String getChoreName() {
		return choreName;
	}
	public void setChoreName(String s)
	{
        choreName = s;
	}

	public void setDuration(String i) {
		duration = i;
	}
	public String getDuration() {
		return duration;
	}

	public String getRepetition() {
		return repetition;
	}
	public void setRepetition(String s)
	{
		repetition = s;
	}


	public void setAssignedChildName(String s)
	{
		assignedChildName = s;
	}
	public String getAssignedChildName() {
		return assignedChildName;
	}
}
