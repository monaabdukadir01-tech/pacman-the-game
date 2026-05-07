package dk.sdu.imada.oop26;

import dk.sdu.imada.oop26.view.ViewHandler;
import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {

    @Override
    public void start(Stage stage) {
        ViewHandler handler = new ViewHandler(stage);
        handler.openStartScreen();
    }

    public static void main(String[] args) {
        launch();
    }
}
