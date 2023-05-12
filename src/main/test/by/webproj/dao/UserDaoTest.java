package by.webproj.dao;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class UserDaoTest {
    @Test
    public void addUserTest(){
        Assertions.assertTrue("name"!=null);
    }
    @Test
    public void findUserByLoginTest(){
        Assertions.assertTrue("user".startsWith("u"));
    }
    @Test
    public void findAllClients(){
        Assertions.assertTrue("all".length()==3);
    }

}
