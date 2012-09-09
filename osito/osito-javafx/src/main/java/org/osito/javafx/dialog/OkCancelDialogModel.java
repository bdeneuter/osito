package org.osito.javafx.dialog;

import javafx.util.Callback;

public class OkCancelDialogModel {

    protected final Callback<?, ?> okCallback;
	private Callback<?, ?> cancelCallback;
    
    public OkCancelDialogModel(Callback<?, ?> okCallback, Callback<?, ?> cancelCallback) {
        this.okCallback = okCallback;
		this.cancelCallback = cancelCallback;
    }
    
    public void handleCancel() {
        cancelCallback.call(null);
    }
    
    public void handleOk() {
    	okCallback.call(null);
    }    
        
}
