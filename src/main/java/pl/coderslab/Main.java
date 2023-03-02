package pl.coderslab;

import pl.coderslab.dao.UserDao;
import pl.coderslab.model.User;

public class Main {
    public static void main(String[] args) {

//        User Filip = new User();
//        Filip.setUsername("Filip");
//        Filip.setEmail("Filip3@asdasd.pl");
//        Filip.setPassword("123");
//
        UserDao userDao = new UserDao();
//        userDao.create(Filip);
//
//        User arek = new User();
//        arek.setId(1L);
//
//        userDao.delete(arek);
        User result = userDao.findById(3L);
//        System.out.println(result.toString());
    }
}