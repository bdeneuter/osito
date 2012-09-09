package org.osito.javafx.dialog.message;

import static org.osito.javafx.dialog.message.Option.CANCEL;
import static org.osito.javafx.dialog.message.Option.NO;
import static org.osito.javafx.dialog.message.Option.OK;
import static org.osito.javafx.dialog.message.Option.YES;

public enum OptionType {

    YES_NO_OPTION(YES, NO), 
    OK_CANCEL_OPTION(OK, CANCEL);
    
    private Option okOption;
	private Option cancelOption;

	private OptionType(Option okOption, Option cancelOption) {
		this.okOption = okOption;
		this.cancelOption = cancelOption;
	}

	Option getOkOption() {
		return okOption;
	}
	
	Option getCancelOption() {
		return cancelOption;
	}
}
