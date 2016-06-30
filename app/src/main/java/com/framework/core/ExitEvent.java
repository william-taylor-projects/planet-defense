package com.framework.core;


import com.framework.IEvent;
import com.framework.IUiEvent;
import com.framework.dialogs.MessageBox;

public class ExitEvent implements IEvent, IUiEvent {
    @Override
    public void onActivate(Object data) {
        MessageBox messageBox = new MessageBox();

        messageBox.setTitle("Quit ?");
        messageBox.setMessage("Are you sure ?");
        messageBox.onAccept(this);
        messageBox.EnableYesNo();
        messageBox.show(false);
    }

    @Override
    public void onUiEvent() {
        System.exit(0);
    }

    @Override
    public void update() {
        ;
    }
}
