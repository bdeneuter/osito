package org.osito.javafx.dialog;

import static javafx.geometry.Pos.CENTER;
import static javafx.geometry.Pos.CENTER_RIGHT;
import static org.osito.javafx.MessageKey.key;
import static org.osito.javafx.MessageService.messageService;
import static org.osito.javafx.dialog.OkCancelDialogId.CANCEL_BUTTON;
import static org.osito.javafx.dialog.OkCancelDialogId.OK_BUTTON;
import static org.osito.javafx.dialog.message.FXOptionPane.showMessageDialog;
import static org.osito.javafx.dialog.message.MessageType.ERROR;
import static org.osito.javafx.dialog.message.OptionType.OK_CANCEL_OPTION;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonBuilder;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.BorderPaneBuilder;
import javafx.scene.layout.HBoxBuilder;

import org.osito.javafx.ApplicationException;
import org.osito.javafx.MessageKey;
import org.osito.javafx.dialog.message.OptionType;

public abstract class OkCancelDialog<T extends OkCancelDialogPresentationModel> extends Dialog {
    
    private T presenter;

    protected Button okButton;

    protected Button cancelButton;

    private OptionType optionType;

    public OkCancelDialog(T presenter) {
        this(presenter, OK_CANCEL_OPTION);
    }

    public OkCancelDialog(T presenter, OptionType optionType) {
        this.presenter = presenter;
        this.optionType = optionType;
    }

    protected T getPresenter() {
        return presenter;
    }
    
    @Override
    public final Parent createRoot() {
        okButton = ButtonBuilder.create()
                                .id(OK_BUTTON.name())
                                .text(messageService().getText(getOkLabel()))
                                .onAction(new EventHandler<ActionEvent>() {
                                    @Override
                                    public void handle(ActionEvent event) {
                                        try {
											presenter.handleOk();
											getStage().close();
										} catch (ApplicationException e) {
											showMessageDialog(e.getKey(), ERROR);
										}
                                    }
                                })
                                .build();
        cancelButton = ButtonBuilder.create()
                                    .id(CANCEL_BUTTON.name())
                                    .text(messageService().getText(getCancelLabel()))
                                    .onAction(new EventHandler<ActionEvent>() {
                                        @Override
                                        public void handle(ActionEvent event) {
                                            presenter.handleCancel();
                                            getStage().close();
                                        }
                                    })
                                    .build();
        
        BorderPane root = BorderPaneBuilder.create()
                                           .center(createContent())
                                           .bottom(HBoxBuilder.create()
                                                       .children(okButton, cancelButton)
                                                       .alignment(getAlignment())
                                                       .spacing(5)
                                                       .padding(new Insets(5, 5, 5, 5))
                                                       .build())
                                           .build();
        return root;
    }

    private Pos getAlignment() {
        switch (optionType) {
            case YES_NO_OPTION:
                return CENTER;
            default:
                return CENTER_RIGHT;
        }
    }

    private MessageKey getCancelLabel() {
        switch (optionType) {
            case YES_NO_OPTION:
                return key("no");
            default:
                return key("cancel");
        }
    }

    private MessageKey getOkLabel() {
        switch (optionType) {
            case YES_NO_OPTION:
                return key("yes");
            default:
                return key("ok");
        }
    }
    
    protected abstract Node createContent();

}
