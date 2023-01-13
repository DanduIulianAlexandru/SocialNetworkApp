package com.example.guisocialnetwork;
import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.repository.BDRepository.FriendshipRepositoryDB;
import com.example.guisocialnetwork.repository.BDRepository.UserRepositoryDB;
import com.example.guisocialnetwork.repository.Repository;
import  com.example.guisocialnetwork.service.UserService;
import com.example.guisocialnetwork.validation.UserValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class startScreen {
    private Repository<Long, User> userRepository = new UserRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");
    private Repository<Long, Friendship> friendshipRepository = new FriendshipRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");

    private final UserService service = new UserService(userRepository, friendshipRepository, new UserValidation());
    @FXML
    private TextField usernameField;
    @FXML
    private TextField passwordField;

    private Stage stage;
    private Scene scene;
    private Parent root;
    public void login(ActionEvent event) throws IOException {
        String username = usernameField.getText();
        String pass = passwordField.getText();

        if(username.equals(""))
            new Alert(Alert.AlertType.ERROR, "Please enter your username!").show();
        else if (service.findUserByUsername(username) == null)
            new Alert(Alert.AlertType.ERROR, "This account doesn t exist!").show();
        else if (service.findUserByUsername(username).getPassword().equals(pass)) {
            FXMLLoader loader = new FXMLLoader(getClass().getResource("LoggedIn.fxml"));
            Parent root;
            try {
                root = loader.load();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
            LoggedIn loggedIn = loader.getController();
            loggedIn.mainUsername = username;
            loggedIn.setWelcomeUsername(username);

            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 400, 700);
            stage.setScene(scene);
            stage.show();
        }
        else
            new Alert(Alert.AlertType.ERROR, "The password is wrong!").show();
    }

    public void signUp(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("signUp.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
    public void forgotPassword(ActionEvent event) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("forgotPassword.fxml"));
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        scene = new Scene(root);
        stage.setScene(scene);
        stage.show();
    }

}
