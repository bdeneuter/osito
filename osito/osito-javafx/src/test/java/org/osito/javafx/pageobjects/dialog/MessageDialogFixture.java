package org.osito.javafx.pageobjects.dialog;

import static org.fest.assertions.api.Assertions.assertThat;
import static org.osito.javafx.dialog.NodeId.LABEL;
import static org.osito.javafx.dialog.NodeId.OK_BUTTON;
import static org.osito.javafx.pageobjects.ButtonFixture.buttonForId;
import static org.osito.javafx.pageobjects.LabelFixture.labelForId;

import org.osito.javafx.pageobjects.WindowFixture;


public class MessageDialogFixture extends WindowFixture<MessageDialogFixture> {

	private MessageDialogFixture() {
    }

    public static MessageDialogFixture messageDialog() {
		return new MessageDialogFixture();
    }
    
    public MessageDialogFixture clickOk() {
        clickButton(OK_BUTTON);
        return this;
    }
    
    public MessageDialogFixture assertButtonTextIsCorrect() {
        assertThat(buttonForId(OK_BUTTON).hasLabel("OK")).isTrue();
        return this;
    }
    
    public MessageDialogFixture assertLabelText(String text) {
        assertThat(labelForId(LABEL).hasText(text)).isTrue();
        return this;
    }

}