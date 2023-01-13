package com.example.guisocialnetwork;

import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.repository.BDRepository.FriendshipRepositoryDB;
import com.example.guisocialnetwork.repository.BDRepository.UserRepositoryDB;
import com.example.guisocialnetwork.repository.Repository;
import com.example.guisocialnetwork.service.UserService;
import com.example.guisocialnetwork.validation.UserValidation;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;

public class ForgotPassword {
    private Repository<Long, User> userRepository = new UserRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");
    private Repository<Long, Friendship> friendshipRepository = new FriendshipRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");

    private final UserService service = new UserService(userRepository, friendshipRepository, new UserValidation());
    public Button resetButton;
    public TextField email;

    public void resetPass(ActionEvent event) throws IOException {
        String mail = email.getText();
        if(mail.equals(""))
            new Alert(Alert.AlertType.ERROR, "Please enter your email!").show();
        else if(service.findUserByMail(mail) == null)
            new Alert(Alert.AlertType.ERROR, "This account doesn't exist!").show();
        else{
            Parent root;
            root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("forgotPassNewScreen.fxml")));
            Stage stage;
            stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            Scene scene = new Scene(root, 400, 700);
            stage.setScene(scene);
            stage.show();
        }
    }
    public void goBack(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startScreen.fxml")));
        Stage stage;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 700);
        stage.setScene(scene);
        stage.show();
    }
}
