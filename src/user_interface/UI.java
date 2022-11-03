package user_interface;

import domain.Friendship;
import domain.User;
import exceptions.RepositoryException;
import exceptions.ValidationException;
import repository.FileRepository.FriendshipFileRepository;
import repository.FileRepository.UserFileRepository;
import repository.Repository;
import service.FriendshipService;
import service.UserService;
import validation.UserValidation;

import java.util.List;
import java.util.Scanner;

import static java.lang.Long.parseLong;

public class UI{
    private void addUser(UserService userService){
        Scanner scanner = new Scanner(System.in);
        String firstName, lastName;
        System.out.println("Introduceti numele utilizatorului: ");
        firstName = scanner.nextLine();
        System.out.println("Introduceti prenumele utilizatorului: ");
        lastName = scanner.nextLine();
        try{
            userService.addUser(firstName, lastName);
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
                4 - Adauga o prietenie\s
                5 - Sterge o prietenie\s
                6 - Afiseaza lista de prietenii\s
                7 - Afiseaza numarul de comunitati din retea\s
                8 - Afiseaza cea mai sociabila comunitate din retea\s
                help - reafiseaza meniul\s
                exit - iesiti din aplicatie
                """
        );
    }
    public void run() {
        String fileName = "data/users.csv";
        String fileName1 = "data/friends.csv";

        //Repository<Long, User> userRepository = new UserInMemoryRepository();
        //Repository<Long, Friendship> friendshipRepository = new FriendshipInMemoryRepository();
        Repository<Long, User> userRepository = new UserFileRepository(fileName);
        Repository<Long, Friendship> friendshipRepository = new FriendshipFileRepository(fileName1);
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
                case "4" -> addFriendship(friendshipService);
                case "5" -> deleteFriendship(friendshipService);
                case "6" -> printAllFriendships(friendshipService);
                case "7" -> printTheNumberOfComunities(friendshipService);
                case "8" -> printTheMostSociableComunity(friendshipService);
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
