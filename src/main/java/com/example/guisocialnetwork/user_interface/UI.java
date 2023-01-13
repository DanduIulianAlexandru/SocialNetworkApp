package com.example.guisocialnetwork.user_interface;

import com.example.guisocialnetwork.domain.Friendship;
import com.example.guisocialnetwork.domain.User;
import com.example.guisocialnetwork.exceptions.RepositoryException;
import com.example.guisocialnetwork.exceptions.ValidationException;
import com.example.guisocialnetwork.repository.BDRepository.FriendshipRepositoryDB;
import com.example.guisocialnetwork.repository.BDRepository.UserRepositoryDB;
import com.example.guisocialnetwork.repository.FileRepository.FriendshipFileRepository;
import com.example.guisocialnetwork.repository.FileRepository.UserFileRepository;
import com.example.guisocialnetwork.repository.InMemoryRepository.FriendshipInMemoryRepository;
import com.example.guisocialnetwork.repository.InMemoryRepository.UserInMemoryRepository;
import com.example.guisocialnetwork.repository.Repository;
import com.example.guisocialnetwork.service.FriendshipService;
import com.example.guisocialnetwork.service.UserService;
import com.example.guisocialnetwork.validation.UserValidation;

import java.util.List;
import java.util.Scanner;

import static java.lang.Long.parseLong;

public class UI{
    private void addUser(UserService userService){
        Scanner scanner = new Scanner(System.in);
        String username, firstName, lastName, mail, password;

        System.out.println("Introduceti username ul utilizatorului: ");
        username = scanner.nextLine();
        System.out.println("Introduceti numele utilizatorului: ");
        firstName = scanner.nextLine();
        System.out.println("Introduceti prenumele utilizatorului: ");
        lastName = scanner.nextLine();
        System.out.println("Introduceti mail-ul utilizatorului: ");
        mail = scanner.nextLine();
        System.out.println("Introduceti parola utilizatorului: ");
        password = scanner.nextLine();

        try{
            userService.addUser(username, firstName, lastName, mail, password);
            System.out.println("User adaugat cu succes!\n");
        }catch(RepositoryException | ValidationException e){
            System.out.println(e.getMessage());
        }
    }
    private void deleteUser(UserService userService){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti id-ul user-ului pe care doriti sa-l stergeti: ");
        String idS;
        idS = scanner.nextLine();
        long id = 0;
        try {
            id = parseLong(idS);
        } catch (NumberFormatException e) {
            System.out.println("Id invalida!\n");
        }
        try {
            userService.deleteUser(id);
            System.out.println("User sters cu succces!\n");
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }

    }
    private void printAllUsers(UserService userService) {
        Iterable<User> users = userService.getAll();
        int contor = 0;
        for (User user : users) {
            System.out.println(user);
            contor++;
        }
        if (contor == 0)
            System.out.println("Lista de useri e goala\n");
    }
    private void updateUser(UserService userService){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti id-ul user-ului pe care doriti sa-l modificati: ");
        String idS;
        idS = scanner.nextLine();
        long id = 0;
        try {
            id = parseLong(idS);
        } catch (NumberFormatException e) {
            System.out.println("Valoare invalida!\n");
        }

        String firstName, lastName, mail, password;
        System.out.println("Introduceti noul nume: ");
        firstName = scanner.nextLine();
        System.out.println("Introduceti username ul utilizatorului: ");
        String username = scanner.nextLine();
        System.out.println("Introduceti noul prenume: ");
        lastName = scanner.nextLine();
        System.out.println("Introduceti noul mail: ");
        mail = scanner.nextLine();
        System.out.println("Introduceti noua parola: ");
        password = scanner.nextLine();
        try {
            userService.update(id, username, firstName, lastName, mail, password);
        } catch (RepositoryException | ValidationException e) {
            System.out.println(e.getMessage());
        }
    }

    private void addFriendship(FriendshipService friendshipService){
        Scanner scanner = new Scanner(System.in);
        long firstFriend = 0, secondFriend = 0;
        String firstFriendS, secondFriendS;
        System.out.println("Introduceti id-ul primului user: ");
        firstFriendS = scanner.nextLine();
        try {
            firstFriend = parseLong(firstFriendS);
        } catch (NumberFormatException e) {
            System.out.println("Id-ul introdus este invalid!\n");
        }
        System.out.println("Introduceti id-ul celui de al doilea user: ");
        secondFriendS = scanner.nextLine();
        try {
            secondFriend = parseLong(secondFriendS);
        } catch (NumberFormatException e) {
            System.out.println("Id-ul introdus este invalid!\n");
        }

        try {
            friendshipService.addFriendship(firstFriend, secondFriend);
            System.out.println("Relatie de prietenie adaugata cu succes!\n");
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }
    private void deleteFriendship(FriendshipService friendshipService) {
        Scanner scanner = new Scanner(System.in);
        long firstFriend = 0, secondFriend = 0;
        String firstFriendS, secondFriendS;
        System.out.println("Introduceti id-ul primului user: ");
        firstFriendS = scanner.nextLine();
        try {
            firstFriend = parseLong(firstFriendS);
        } catch (NumberFormatException e) {
            System.out.println("Id-ul introdus este invalid!\n");
        }
        System.out.println("Introduceti id-ul celui de al doilea user: ");
        secondFriendS = scanner.nextLine();
        try {
            secondFriend = parseLong(secondFriendS);
        } catch (NumberFormatException e) {
            System.out.println("Id-ul introdus este invalid!\n");
        }
        try {
            friendshipService.deleteFriendship(firstFriend, secondFriend);
            System.out.println("Relatie de prietenie stearsa cu succes!\n");
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }

    }
    private void printAllFriendships(FriendshipService friendshipService) {
        int contor = 0;
        for (Friendship friendship : friendshipService.getAll()) {
            System.out.println(friendship);
            contor++;
        }
        if (contor == 0)
            System.out.println("Lista de prieteni este goala! \n");
    }
    private void updateFriendship(FriendshipService friendshipService){
        Scanner scanner = new Scanner(System.in);
        System.out.println("Introduceti id-ul prieteniei pe care doriti sa o modificati: ");
        String idS;
        idS = scanner.nextLine();
        long id = 0;
        long firstFriend = 0;
        long secondFriend = 0;
        try {
            id = parseLong(idS);
        } catch (NumberFormatException e) {
            System.out.println("Valoare invalida!\n");
        }
        String firstFriendS, secondFriendS;
        System.out.println("Introduceti id-ul primului user");
        firstFriendS = scanner.nextLine();
        try {
            firstFriend = parseLong(firstFriendS);
        } catch (NumberFormatException e) {
            System.out.println("Valoare invalida!\n");
        }
        System.out.println("Introduceti id-ul celui de-al doilea user");
        secondFriendS = scanner.nextLine();
        try {
            secondFriend = parseLong(secondFriendS);
        } catch (NumberFormatException e) {
            System.out.println("Valoare invalida!\n");
        }
        try {
            friendshipService.update(id, firstFriend, secondFriend);
        } catch (RepositoryException e) {
            System.out.println(e.getMessage());
        }
    }
    private void printTheNumberOfComunities(FriendshipService friendshipService) {
        int theNumberOfComunities = friendshipService.theNumberOfComunities();
        System.out.println("Numarul de comunitati din retea = " + theNumberOfComunities);
    }
    private void printTheMostSociableComunity(FriendshipService friendshipService) {
        List<Long> theMostSociableComunity = friendshipService.theMostSociableComunity();
        System.out.println("Cea mai sociabila comunitate = " + theMostSociableComunity);
    }
    private static void meniu() {
        System.out.println("""
                1 - Adauga user\s
                2 - Sterge user\s
                3 - Afiseaza lista de useri\s
                4 - Modifica un user\s
                5 - Adauga o prietenie\s
                6 - Sterge o prietenie\s
                7 - Afiseaza lista de prietenii\s
                8 - Modifica o prietenie\s
                9 - Afiseaza numarul de comunitati din retea\s
                10 - Afiseaza cea mai sociabila comunitate din retea\s
                help - reafiseaza meniul\s
                exit - iesiti din aplicatie
                """
        );
    }
    public void run() {
        String fileName = "data/users.csv";
        String fileName1 = "data/friends.csv";

        Repository<Long, User> userRepository = new UserRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");
        Repository<Long, Friendship> friendshipRepository = new FriendshipRepositoryDB("jdbc:postgresql://localhost:5432/network", "postgres", "Iulian2003");

        //Repository<Long, User> userRepository = new UserInMemoryRepository();
        //Repository<Long, Friendship> friendshipRepository = new FriendshipInMemoryRepository();
        //Repository<Long, User> userRepository = new UserFileRepository(fileName);
        //Repository<Long, Friendship> friendshipRepository = new FriendshipFileRepository(fileName1);
        UserValidation userValidation = new UserValidation();
        UserService userService = new UserService(userRepository, friendshipRepository, userValidation);
        FriendshipService friendshipService = new FriendshipService(userRepository, friendshipRepository);
        meniu();
        while(true){
            Scanner scanner = new Scanner(System.in);
            String cmd;
            System.out.println("dati o comanda");
            cmd = scanner.nextLine();
            switch (cmd) {
                case "1" -> addUser(userService);
                case "2" -> deleteUser(userService);
                case "3" -> printAllUsers(userService);
                case "4" -> updateUser(userService);
                case "5" -> addFriendship(friendshipService);
                case "6" -> deleteFriendship(friendshipService);
                case "7" -> printAllFriendships(friendshipService);
                case "8" -> updateFriendship(friendshipService);
                case "9" -> printTheNumberOfComunities(friendshipService);
                case "10" -> printTheMostSociableComunity(friendshipService);
                case "help" -> meniu();
                case "exit" -> {
                    System.out.println("byee");
                    return;
                }
                default -> System.out.println("comanda eronata!");
            }
        }
    }
}
