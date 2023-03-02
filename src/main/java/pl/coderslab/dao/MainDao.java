package pl.coderslab.dao;

import pl.coderslab.model.User;

import java.util.List;
import java.util.Scanner;

public class MainDao {
    public static void main(String[] args) {
        UserDao dao = new UserDao();

        Scanner scanner = new Scanner(System.in);

        System.out.println("Witaj w naszej apce.");

        boolean isRunning = true;
        while (isRunning){
            System.out.println("Ponizej masz akcje do wyboru:\n LIST: Lista userow\n LIST_ID: szukaj po id\n CREATE: utworz nowego usera\n UPDATE: zaktualizuj istniejacego\n DELETE: usun usera");
            System.out.println("Podaj akcje: ");
            String line = scanner.nextLine();
            switch (line.trim().toLowerCase()){
                case "list": {
                    listAllUsers(dao);
                    break;
                } //OK.
                case "list_id": {
                    getUserById(scanner,dao);
                    break;
                } //OK.
                case "create": {
                    createUser(scanner, dao);
                    break;
                }
                case "update": {
                    updateUser(scanner, dao);
                    break;
                } // Nie działa, wyrzuca błędy.

                case "delete": {
                    deleteUserById(scanner,dao);
                    break;
                } //OK.
                case "exit":{
                    isRunning = false;
                    break;
                } //OK.

            }
        }
    }
    private static void listAllUsers(UserDao dao) {
        System.out.println("Lista userow to:");
        List<User> users = dao.findAll();
        for (User user: users) {
            System.out.println(String.format("id: %s, username: %s, email: %s", user.getId(), user.getUsername(), user.getEmail()));
        }
        System.out.println("=============");
    }
    private static void createUser(Scanner scanner, UserDao dao) {
        System.out.println("Podaj nazwe usera:");

        String username = scanner.nextLine();
        System.out.println("Podaj email usera:");
        String email = scanner.nextLine();
        System.out.println("Podaj haslo usera:");
        String password = scanner.nextLine();

        if(username.isBlank()==false && email.isBlank()==false && password.isBlank()==false){
            User user = new User(username,password,email);
            user = dao.create(user);
            System.out.println("Utworzono usera");
            System.out.println(String.format("id: %s, username: %s, email: %s", user.getId(), user.getUsername(), user.getEmail()));

        }else{
            System.out.println("wszystkie pola musza byc wypelnione");
        }

    }
    private static void updateUser(Scanner scanner, UserDao dao) {
        System.out.println("Podaj id usera:");
        while (!scanner.hasNextInt()){
            System.out.println("Nie podales mi liczby. Sprobuj ponownie");
            scanner.nextLine();
        }
        Long value = Long.parseLong(scanner.nextLine());
        User user = dao.findById(value);
        if(user==null){
            System.out.println(String.format("Brak usera o id: %s w bazie",value));
            return;
        }else {
            System.out.println(String.format("id: %s, username: %s, email: %s", user.getId(), user.getUsername(), user.getEmail()));

            System.out.println("Podaj nazwe usera:");
            String username = scanner.nextLine();
            System.out.println("Podaj email usera:");
            String email = scanner.nextLine();
            System.out.println("Podaj haslo usera:");
            String password = scanner.nextLine();
            if(username.isBlank()==false && email.isBlank()==false && password.isBlank()==false){
                user.setEmail(email);
                user.setUsername(username);
                user.setPassword(password);
                user = dao.update(user);
                System.out.println("Zaktualizowano usera");
                System.out.println(String.format("id: %s, username: %s, email: %s", user.getId(), user.getUsername(), user.getEmail()));
            }else{
                System.out.println("wszystkie pola musza byc wypelnione");
            }
        }
    }
    private static void getUserById(Scanner scanner, UserDao dao) {
        System.out.println("Podaj id usera:");
        while (!scanner.hasNextInt()){
            System.out.println("Nie podales mi liczby. Sprobuj ponownie");
            scanner.nextLine();
        }
        Long value = Long.parseLong(scanner.nextLine());
        User user = dao.findById(value);
        if(user==null){
            System.out.println(String.format("Brak usera o id: %s w bazie",value));
        }else {
            System.out.println(String.format("id: %s, username: %s, email: %s", user.getId(), user.getUsername(), user.getEmail()));
        }
    }

    private static void deleteUserById(Scanner scanner, UserDao dao) {
        System.out.println("Podaj id usera:");
        while (!scanner.hasNextInt()){
            System.out.println("Nie podales mi liczby. Sprobuj ponownie");
            scanner.nextLine();
        }
        Long value = Long.parseLong(scanner.nextLine());

        if(dao.delete(value)){
            System.out.println(String.format("Usunieto usera o id: %s",value));
        }else {
            System.out.println(String.format("Brak usera o id: %s w bazie", value));

        }
    }
}