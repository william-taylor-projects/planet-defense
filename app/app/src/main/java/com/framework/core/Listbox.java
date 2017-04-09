package com.framework.core;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface.OnClickListener;

public class Listbox implements Runnable {
    private Boolean cancel = false;
    private OnClickListener event;
    private String[] args;
    private String title;

    public Listbox(OnClickListener event) {
        this.event = event;
        title = "ListBox";

        args = new String[12];
        args[0] = "Cancel";

        for(int i = 0; i < 11; i++) {
            args[i+1] =  String.valueOf(i);
        }
    }

    public Listbox(String[] params) {
        title = "ListBox";
        args = params;
    }

    public void Show() {
        GameObject.Activity.runOnUiThread(this);
    }

    public Listbox setTitle(String title) {
        this.title = title;
        return this;
    }

    @Override
    public void run() {
        if(!cancel) {
            AlertDialog.Builder builder = new AlertDialog.Builder(GameObject.Activity);
            builder.setCancelable(false);
            builder.setItems(args, event).setTitle(title);

            Dialog box = builder.create();
            box.show();
        }
    }

    public void cancel() {
        cancel = true;
    }
}
