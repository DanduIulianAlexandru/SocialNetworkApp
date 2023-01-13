package com.example.guisocialnetwork;
import javafx.scene.control.TextField;
import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.repository.BDRepository.FriendshipRepositoryDB;
import com.example.guisocialnetwork.repository.BDRepository.UserRepositoryDB;
import com.example.guisocialnetwork.repository.Repository;
import com.example.guisocialnetwork.service.UserService;
import com.example.guisocialnetwork.validation.UserValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
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

public class LoggedIn {

    public Text mainUserText;
    public String mainUsername;
    private Repository<Long, User> userRepository = new UserRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");
    private Repository<Long, Friendship> friendshipRepository = new FriendshipRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");

    private final UserService service = new UserService(userRepository, friendshipRepository, new UserValidation());
    private String clickedUser;
    @FXML
    private TableView<User> searchTable;
    @FXML
    private TableColumn<User, String> usernameColumn;
    @FXML
    private TableColumn<User, String> emailColumn;
    @FXML
    private TextField searchBarTextField;
    @FXML
    private Button closeSearch;


    public void goBack(ActionEvent event) throws IOException {
        Parent root;
        root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("startScreen.fxml")));
        Stage stage;
        stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
        Scene scene = new Scene(root, 400, 700);
        stage.setScene(scene);
        stage.show();
    }

    public void initialize(ActionEvent event) throws IOException {
        searchUser();
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
                clickedUser = cell.getTableColumn().getCellObservableValue(index).getValue();
                FXMLLoader loader = new FXMLLoader(getClass().getResource("userProfile.fxml"));
                Parent root;
                try {
                    root = loader.load();
                } catch (IOException ex) {
                    throw new RuntimeException(ex);
                }
                UserProfile userProfile = loader.getController();
                userProfile.username = clickedUser;
                userProfile.loggedInUser = mainUsername;
                userProfile.setLoggedInUser(mainUsername);
                userProfile.setUsernameText(clickedUser);

                Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
                Scene scene = new Scene(root, 400, 700);
                stage.setScene(scene);
                stage.show();
            });
            return cell ;
        });

    }
    public void searchUser() throws IOException {
            searchTable.setOpacity(0.75);

            usernameColumn.setCellValueFactory(new PropertyValueFactory<User, String>("username"));
            emailColumn.setCellValueFactory(new PropertyValueFactory<User, String>("email"));
            List<User> ls = new ArrayList<>();
            for (User fr : service.getAll())
                ls.add(fr);
            ObservableList<User> users = FXCollections.observableArrayList(ls);
            searchTable.setItems(users);

            FilteredList<User> filteredList = new FilteredList<>(users, b -> true);

            searchBarTextField.textProperty().addListener((observable, oldValue, newValue) -> {
                filteredList.setPredicate(User -> {
                    if (newValue.isBlank() || newValue.isEmpty()) {
                        return true;
                    }
                    String searchKeyword = newValue.toLowerCase();
                    return User.getUsername().toLowerCase().contains(searchKeyword);
                });
            });

            SortedList<User> sortedData = new SortedList<>(filteredList);
            sortedData.comparatorProperty().bind(searchTable.comparatorProperty());
            searchTable.setItems(sortedData);
    }
    public void closeSearchBar(){
        searchTable.setOpacity(0);
    }
    public void setWelcomeUsername(String a){
        mainUserText.setText("Welcome Back, " + a + "!");
    }
}

