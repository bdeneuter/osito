package org.osito.javafx.dialog;

public abstract class OkCancelDialogPresentationModel {

    protected final OkCancelCallback callback;

    public OkCancelDialogPresentationModel() {
        this(new OkCancelAdapter());
    }
    
    public OkCancelDialogPresentationModel(OkCancelCallback callback) {
        this.callback = callback;
    }
    
    public void handleCancel() {
        cancel();
        callback.handleCancelCalled();
    }
    
    protected abstract void cancel();
    
    public void handleOk() {
        ok();
        callback.handleOkCalled();
    }    
    
    protected abstract void ok();
    
}
