package view;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.net.URL;

public class UILoader {
    private static Scene logInScene;
    private static Scene mainScene;
    public static Scene getLogInScene() throws IOException {
        if(logInScene==null){
            Parent root= FXMLLoader.load(UILoader.class.getResource("./login.fxml"));
            logInScene=new Scene(root);
        }
        return logInScene;
    }

}
