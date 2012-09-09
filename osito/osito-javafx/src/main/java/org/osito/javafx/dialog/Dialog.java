package org.osito.javafx.dialog;

import javafx.application.Platform;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.SceneBuilder;
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
                        .build();
                stage = StageBuilder.create()
                                    .scene(scene)
                                    .build();
                stage.centerOnScreen();
                initStage(stage);
                stage.show();
            }
        });
    }
    
}
