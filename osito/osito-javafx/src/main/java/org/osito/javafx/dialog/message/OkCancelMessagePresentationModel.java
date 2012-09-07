package org.osito.javafx.dialog.message;

import static org.osito.javafx.dialog.message.Option.NO;
import static org.osito.javafx.dialog.message.Option.YES;
import javafx.util.Callback;

import org.osito.javafx.dialog.OkCancelDialogPresentationModel;

class OkCancelMessagePresentationModel extends OkCancelDialogPresentationModel {

    private Callback<Option, ?> callback;
    
    OkCancelMessagePresentationModel(Callback<Option, ?> callback) {
        this.callback = callback;
    }
    
    @Override
    protected void cancel() {
        callback.call(NO);
    }

    @Override
    protected void ok() {
        callback.call(YES);
    }

}
