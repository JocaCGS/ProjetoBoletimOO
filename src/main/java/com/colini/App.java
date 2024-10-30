package com.colini;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * JavaFX App
 */
public class App extends Application {

    private static Scene scene;

    @Override
    public void start(Stage stage) throws IOException {
        FXMLLoader fxTela = loadFXML("./views/telaListaAluno.fxml");
        Parent root = fxTela.load();
        scene = new Scene(root, 789, 441);
        stage.initStyle((javafx.stage.StageStyle.UNDECORATED));
        stage.setTitle("Sistema de Boletim");
        stage.setScene(scene);
        stage.show();
    }

    public static FXMLLoader loadFXML(String caminho) {
        return new FXMLLoader(App.class.getResource(caminho));
    }

    public static void mudaTela(String caminho) {
        try {
            FXMLLoader fxTela = loadFXML(caminho);
            Parent root = fxTela.load();
            scene.setRoot(root);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        launch();
    }

}