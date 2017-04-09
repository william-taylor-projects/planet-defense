package com.framework.dialogs;

import android.app.AlertDialog;
import android.content.DialogInterface;

import com.framework.IUiEvent;
import com.framework.core.GameObject;

public class MessageBox {
    private IUiEvent onAcceptEvent;
    private IUiEvent onCancelEvent;
    private Boolean yesno;
    private String positiveText;
    private String negativeText;
    private String message;
    private String title;

    public MessageBox() {
        positiveText = "Yes";
        negativeText = "No";
        message = "Yes/No ?";
        title = "Title";
        yesno = false;
    }

    public void show(Boolean okOnly) {
        if(!okOnly && !yesno) {
            GameObject.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(GameObject.Activity)
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
        } else if(yesno) {
            GameObject.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(GameObject.Activity)
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
            GameObject.Activity.runOnUiThread(new Runnable() {
                public void run() {
                    new AlertDialog.Builder(GameObject.Activity)
                    .setTitle(title)
                    .setMessage(message)
                    .setPositiveButton(positiveText, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(onAcceptEvent != null) {
                                onAcceptEvent.onUiEvent();
                            }

                            dialog.dismiss();
                        }
                     })

                    .setNegativeButton(negativeText, new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int which) {
                            if(onCancelEvent != null) {
                                onCancelEvent.onUiEvent();
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

    public MessageBox onAccept(IUiEvent e) {
        onAcceptEvent = e;
        return this;
    }

    public MessageBox onCancel(IUiEvent e) {
        onCancelEvent = e;
        return this;
    }

    public MessageBox setMessage(String message) {
        this.message = message;
        return this;
    }

    public MessageBox EnableYesNo() {
        this.yesno = true;
        return this;
    }
}
