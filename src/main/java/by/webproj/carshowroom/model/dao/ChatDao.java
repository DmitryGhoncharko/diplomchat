package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.dto.ChatDTO;
import by.webproj.carshowroom.entity.Chat;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface ChatDao {
    Optional<Chat> getByName(String name) throws DaoException;

    Optional<Chat> getById(Long id) throws DaoException;

    Long addChat(String name, boolean pass, String password) throws DaoException;

    boolean createChat(String name, boolean pass, String password, Long userId) throws DaoException;

    List<Chat> getChatsByUserId(Long userId) throws DaoException;

    boolean deleteChatByName(String name) throws DaoException;

    List<ChatDTO> getChatMessagesByName(String name) throws DaoException;

    boolean addMessage(Long chatId, Long userId, String message) throws DaoException;

    boolean deleteMessageById(Long id) throws DaoException;

    boolean deleteChatByUserIdAndChatID(Long userId, Long chatId) throws DaoException;
}
