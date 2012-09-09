package org.osito.javafx.pageobjects.dialog;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.osito.javafx.dialog.NodeId.CANCEL_BUTTON;
import static org.osito.javafx.dialog.NodeId.OK_BUTTON;
import static org.osito.javafx.pageobjects.ButtonFixture.buttonForId;

import org.osito.javafx.pageobjects.WindowFixture;


public class OkCancelDialogFixture<T extends OkCancelDialogFixture<T>> extends WindowFixture<T> {

    protected OkCancelDialogFixture() {
    }

    @SuppressWarnings("rawtypes")
    public static OkCancelDialogFixture<?> okCancelDialog() {
        return new OkCancelDialogFixture();
    }
    
    public OkCancelDialogFixture<T> clickCancel() {
        clickButton(CANCEL_BUTTON);
        return this;
    }

    public OkCancelDialogFixture<T> clickOk() {
        clickButton(OK_BUTTON);
        return this;
    }
    
    public OkCancelDialogFixture<T> assertFrenchLabels() {
        boolean cancelButtonLabelInFrench = buttonForId(CANCEL_BUTTON).hasLabel("Annuler");
        assertThat(cancelButtonLabelInFrench).isTrue();
        return this;
    }
    
    public OkCancelDialogFixture<T> assertDutchLabels() {
        boolean cancelButtonLabelInFrench = buttonForId(CANCEL_BUTTON).hasLabel("Annuleren");
        assertThat(cancelButtonLabelInFrench).isTrue();
        return this;
    }
    
}
