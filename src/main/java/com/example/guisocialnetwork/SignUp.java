package com.example.guisocialnetwork;

import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.exceptions.ValidationException;
import com.example.guisocialnetwork.repository.BDRepository.FriendshipRepositoryDB;
import com.example.guisocialnetwork.repository.BDRepository.UserRepositoryDB;
import com.example.guisocialnetwork.repository.Repository;
import com.example.guisocialnetwork.service.UserService;
import com.example.guisocialnetwork.validation.UserValidation;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.exceptions.ValidationException;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Objects;
public class SignUp {
    private Repository<Long, User> userRepository = new UserRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");
    private Repository<Long, Friendship> friendshipRepository = new FriendshipRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");

    private final UserService service = new UserService(userRepository, friendshipRepository, new UserValidation());
    public TextField pass1;
    public TextField firstName;
    public TextField secondName;
    public TextField mail;
    public TextField pass2;
    public TextField username;

    public void signUp(ActionEvent event) throws ValidationException, RepositoryException, IOException {
        String p1 = pass1.getText();
        String p2 = pass2.getText();
        String firstn = firstName.getText();
        String secondn = secondName.getText();
        String email = mail.getText();
        String uname = username.getText();
        if(!p1.equals(p2))
            new Alert(Alert.AlertType.ERROR, "Passwords don't match!").show();
        else if(service.findUserByMail(email) != null)
            new Alert(Alert.AlertType.ERROR, "An account is already registered with your email address. Please log in!").show();
        else if(service.findUserByUsername(uname) != null)
            new Alert(Alert.AlertType.ERROR, "Username unavailable. Please create a new one!").show();
        else{
            try{
                service.addUser(uname, firstn, secondn, email, p1);
                new Alert(Alert.AlertType.ERROR, "Account created successfully. Please Log In!").show();
                Parent root;
                root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startScreen.fxml")));
                Stage stage;
                stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 400, 700);
                stage.setScene(scene);
                stage.show();

            }
            catch (ValidationException e) {
                new Alert(Alert.AlertType.ERROR, e.getMessage()).show();
            }
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
