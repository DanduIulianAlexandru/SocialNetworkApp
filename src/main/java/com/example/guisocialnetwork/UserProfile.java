package com.example.guisocialnetwork;
import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.repository.BDRepository.FriendshipRepositoryDB;
import com.example.guisocialnetwork.repository.BDRepository.UserRepositoryDB;
import com.example.guisocialnetwork.repository.Repository;
import com.example.guisocialnetwork.service.FriendshipService;
import com.example.guisocialnetwork.service.UserService;
import com.example.guisocialnetwork.validation.UserValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class UserProfile {
    private Repository<Long, User> userRepository = new UserRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");
    private Repository<Long, Friendship> friendshipRepository = new FriendshipRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");

    private final UserService serviceUser = new UserService(userRepository, friendshipRepository, new UserValidation());
    private final FriendshipService serviceFriendship = new FriendshipService(userRepository, friendshipRepository);
    @FXML
    private TableView<User> friendslist;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    public Text statusFriendshipText;
    @FXML
    public Text loggedInUserText;

    public String loggedInUser;
    public String username;
    public String firstname;
    public String lastname;
    public String email;
    @FXML
    public Text usernameText;
    @FXML
    public Text firstnameText;
    @FXML
    public Text lastnameText;
    @FXML
    public Text emailText;
    private User searchedUser = serviceUser.findUserByUsername(username);

    public void goBack(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("LoggedIn.fxml")));
        Stage stage;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 700);
        stage.setScene(scene);
        stage.show();
    }
    public void setUsernameText(String u){
        usernameText.setText("User: " + u);
        setFirstNameText();
        setSecondNameText();
        setEmailText();
    }

    public void setFirstNameText(){
        User a = null;
        for(User user : serviceUser.getAll())
            if(Objects.equals(user.getUsername(), username))
                a = user;
        firstnameText.setText("First Name: " + a.getFirstName());
    }
    public void setSecondNameText(){
        User a = null;
        for(User user : serviceUser.getAll())
            if(Objects.equals(user.getUsername(), username))
                a = user;
        lastnameText.setText("Last Name: " + a.getLastName());
    }
    public void setEmailText(){
        User a = null;
        for(User user : serviceUser.getAll())
            if(Objects.equals(user.getUsername(), username))
                a = user;
        emailText.setText("Email: " + a.getEmail());
    }
    public void setLoggedInUser(String u){
        loggedInUserText.setText("Logged in as: " + u);
    }

    public void initialize(ActionEvent event) throws RepositoryException {
        friendslist.setOpacity(0.75);
        usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
        emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
        for(User user : serviceUser.getAll())
            if(Objects.equals(user.getUsername(), username))
                searchedUser = user;

        List<User> ls = new ArrayList<>();
        for (User fr : serviceFriendship.getFriends(searchedUser))
            ls.add(fr);

        ObservableList<User> users = FXCollections.observableArrayList(ls);
        friendslist.setItems(users);

        usernameColumn.setCellFactory(tc -> {
            TableCell<User, String> cell = new TableCell<User, String>() {
                @Override
                protected void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty) ;
                    setText(empty ? null : item);
                }
            };
            cell.setOnMouseClicked(e -> {
                int index = cell.getIndex();
                String clickedUser = cell.getTableColumn().getCellObservableValue(index).getValue();

                FXMLLoader loader = new FXMLLoader(getClass().getResource("userProfile.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }

                UserProfile userProfile = loader.getController();
                userProfile.username = clickedUser;
                userProfile.setUsernameText(clickedUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 400, 700);
                stage.setScene(scene);
                stage.show();
            });
            return cell ;
        });
    }
    public void closeFriendList(){
        friendslist.setOpacity(0);
    }

    public void showStatus(){
        int status = -1;
        for(User user : serviceUser.getAll())
            if(Objects.equals(user.getUsername(), username))
                searchedUser = user;
        for(Friendship friendship: serviceFriendship.getAll()){
            if(friendship.getFirstFriend() == serviceUser.findUserByUsername(loggedInUser).getId())
                if(friendship.getSecondFriend() == searchedUser.getId())
                    status = friendship.statusToInt();

            if(friendship.getSecondFriend() == serviceUser.findUserByUsername(loggedInUser).getId())
                if(friendship.getFirstFriend() == searchedUser.getId())
                    status = friendship.statusToInt();
        }
        if(status == 0)
            statusFriendshipText.setText("pending");
        else if(status == 1)
            statusFriendshipText.setText("accepted");
        else
            statusFriendshipText.setText("not friends");
        statusFriendshipText.setOpacity(1);
    }
}
