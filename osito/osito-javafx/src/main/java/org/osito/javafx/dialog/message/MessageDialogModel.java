package org.osito.javafx.dialog.message;

import javafx.util.Callback;

import org.osito.javafx.dialog.OkCancelDialogModel;

class MessageDialogModel extends OkCancelDialogModel {
    
	MessageDialogModel(final Callback<Option, ?> callback, final OptionType optionType) {
    	super(
    			new Callback<Void, Void>() {
    				@Override
    				public Void call(Void arg) {
    					callback.call(optionType.getOkOption());
    					return null;
    				}

    			},
    			new Callback<Void, Void>() {
    				@Override
    				public Void call(Void arg) {
    					callback.call(optionType.getCancelOption());
    					return null;
    				}
				}
		);
    }
    
}
