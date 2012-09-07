package org.osito.javafx.dialog;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import javafx.stage.StageBuilder;

public abstract class Dialog {

    private Stage stage;

    protected Stage getStage() {
        return stage;
    }
    
    protected abstract Parent createRoot();
    
    protected abstract void initStage(Stage stage);
    
    public void showDialog() {
        final Parent root = createRoot();
        Platform.runLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = SceneBuilder.create()
                        .root(root)
                        .stylesheets("layout/realtime.css")
                        .build();
                stage = StageBuilder.create()
                                    .scene(scene)
                                    .icons(getIconImage())
                                    .build();
                stage.centerOnScreen();
                initStage(stage);
                stage.show();
            }
        });
    }
    
    private Image getIconImage() {
        return new Image(this.getClass().getResourceAsStream("/images/a173_icon.png"));
    }
    
}
