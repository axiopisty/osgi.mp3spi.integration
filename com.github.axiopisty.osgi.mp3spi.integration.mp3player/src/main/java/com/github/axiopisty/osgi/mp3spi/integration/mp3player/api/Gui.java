package com.github.axiopisty.osgi.mp3spi.integration.mp3player.api;

import com.github.axiopisty.osgi.mp3spi.integration.mp3player.internal.MP3Player;
import javafx.application.Application;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;

public class Gui extends Application {

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Example: OSGi mp3spi integration");
    stage.setScene(new Scene(getRoot(stage), 400, 300));
    stage.show();
  }

  private Parent getRoot(Stage stage) {
    final Label label = new Label("Select an MP3 file:");
    final Button submit = new Button("Browse");
    submit.setOnMouseClicked(e -> {
      FileChooser chooser = new FileChooser();
      chooser.setTitle("Select .mp3 file");
      chooser.setInitialDirectory(new File(System.getProperty("user.dir")));
      chooser.getExtensionFilters().addAll(new FileChooser.ExtensionFilter("MP3 Files", "*.mp3"));
      File mp3File = chooser.showOpenDialog(stage);
      if(mp3File != null && mp3File.exists() && mp3File.canRead()) {
        try {
          Thread player = new Thread(new MP3Player(mp3File));
          player.setDaemon(true);
          player.start();
        } catch (Exception ex) {
          ex.printStackTrace();
        }
      }
    });
    return new StackPane(new HBox(label, submit));
  }

}
