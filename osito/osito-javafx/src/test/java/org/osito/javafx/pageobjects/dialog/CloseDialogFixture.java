package org.osito.javafx.pageobjects.dialog;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.osito.javafx.dialog.NodeId.CLOSE_BUTTON;
import static org.osito.javafx.pageobjects.ButtonFixture.buttonForId;

import org.osito.javafx.pageobjects.WindowFixture;


public class CloseDialogFixture<T extends CloseDialogFixture<T>> extends WindowFixture<T> {

    protected CloseDialogFixture() {
    }

    @SuppressWarnings("rawtypes")
    public static CloseDialogFixture<?> closeDialog() {
        return new CloseDialogFixture();
    }
    
    public CloseDialogFixture<T> clickClose() {
        clickButton(CLOSE_BUTTON);
        return this;
    }
    
    public CloseDialogFixture<T> assertFrenchLabels() {
        boolean cancelButtonLabelInFrench = buttonForId(CLOSE_BUTTON).hasLabel("Fermer");
        assertThat(cancelButtonLabelInFrench).isTrue();
        return this;
    }
    
    public CloseDialogFixture<T> assertDutchLabels() {
        boolean cancelButtonLabelInDutch = buttonForId(CLOSE_BUTTON).hasLabel("Sluiten");
        assertThat(cancelButtonLabelInDutch).isTrue();
        return this;
    }
    
}