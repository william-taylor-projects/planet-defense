package com.planetDefense;

import android.app.AlertDialog;
import android.content.DialogInterface;

public class MessageBox {
	private UiEvent onAcceptEvent;
	private UiEvent onCancelEvent;
	private String message;
	private String title;
	private Boolean Yesno;
	
	public MessageBox() {
		message = "Yes/No ?";
		title = "Title";
		Yesno = false;
	}
	
	public void show(Boolean okOnly) {
		if(!okOnly && !Yesno) {
			TaloniteGame.Activity.runOnUiThread(new Runnable() {
		        public void run() {
		        	new AlertDialog.Builder(TaloniteGame.Activity)
				    .setTitle(title)
				    .setMessage(message)
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) { 
				        	if(onAcceptEvent != null) {
				        		onAcceptEvent.onUiEvent();
				        	}
				        	
				        	dialog.dismiss();
				        }
				     })
				     
				    .setNegativeButton(android.R.string.no, new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) {
				    		if(onCancelEvent != null) {
				    			onCancelEvent.onUiEvent();
				    		}
				    		
				        	dialog.dismiss();
				        }
				    	
				     }).show();
		        }
		    });
		} else if(Yesno) {
			TaloniteGame.Activity.runOnUiThread(new Runnable() {
		        public void run() {
		        	new AlertDialog.Builder(TaloniteGame.Activity)
				    .setTitle(title)
				    .setMessage(message)
				    .setPositiveButton("Yes", new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) { 
				        	if(onAcceptEvent != null) {
				        		onAcceptEvent.onUiEvent();
				        	}
				        	
				        	dialog.dismiss();
				        }
				     })
				     
				    .setNegativeButton("No", new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) {
				    		if(onCancelEvent != null) {
				    			onCancelEvent.onUiEvent();
				    		}
				    		
				        	dialog.dismiss();
				        }
				    	
				     }).show();
		        }
		    });
		} else {
			TaloniteGame.Activity.runOnUiThread(new Runnable() {
		        public void run() {
		        	new AlertDialog.Builder(TaloniteGame.Activity)
				    .setTitle(title)
				    .setMessage(message)
				    .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
				      
				    	public void onClick(DialogInterface dialog, int which) { 
				        	if(onAcceptEvent != null) {
				        		onAcceptEvent.onUiEvent();
				        	}
				        	
				        	dialog.dismiss();
				        }
				     }).show();
		        }
		    });
		}
	}
	
	public MessageBox setTitle(String title) {
		this.title = title;
		return this;
	}
	
	public MessageBox onAccept(UiEvent e) {
		onAcceptEvent = e;
		return this;
	}
	
	public MessageBox onCancel(UiEvent e) {
		onCancelEvent = e;
		return this;
	}
	
	public MessageBox setMessage(String message) {
		this.message = message;
		return this;
	}

	public MessageBox EnableYesNo() {
		this.Yesno = true;
		return this;
		
	}
}
