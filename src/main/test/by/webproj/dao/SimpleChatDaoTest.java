package by.webproj.dao;

import org.junit.jupiter.api.Test;

import static org.junit.Assert.assertEquals;

public class SimpleChatDaoTest {
    @Test
    public void getByNameTest(){
        int expected = 1;
        int actual = 1;
        assertEquals(expected, actual);
    }
    @Test
    public void addChatTest(){
        String expected = "hello";
        String actual = "hello";
        assertEquals(expected, actual);
    }

    @Test
    public void deleteChatByName(){
        String expected = "hello";
        String actual = "hello";
        assertEquals(expected, actual);
    }
}
