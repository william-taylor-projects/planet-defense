package com.planetDefense;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;

public class ListBox implements Runnable {	
	private OnClickListener event;
	private String[] args;
	private String Title;
	private Boolean cancel = false;
	
	public ListBox(OnClickListener event) {	 
		this.event = event;
		Title = "ListBox";
		
		args = new String[12];
		args[0] = "Cancel";
		
		for(int i = 0; i < 11; i++) {
			args[i+1] =  String.valueOf(i);
		}
	}
	
	public ListBox(String[] params) {
		Title = "ListBox";
		args = params;
	}
	
	public void Show() {
		TaloniteGame.Activity.runOnUiThread(this);
	}
	
	public ListBox setTitle(String title) {
		this.Title = title;
		return this;
	}

	@Override
	public void run() {
		if(!cancel) {
			AlertDialog.Builder builder = new AlertDialog.Builder(TaloniteGame.Activity);
			
			builder.setCancelable(false);
			builder.setItems(args, event).setTitle(Title);
			
			Dialog box = builder.create();
	
			box.show();
		}
	}

	public void cancel() {
		cancel = true;
	}
}
