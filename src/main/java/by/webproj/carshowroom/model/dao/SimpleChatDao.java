package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.dto.ChatDTO;
import by.webproj.carshowroom.dto.Message;
import by.webproj.carshowroom.entity.Chat;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Slf4j
public class SimpleChatDao implements ChatDao {
    private static final String SQL_GET_BY_NAME = "select chat_id, chat_name, password, password_val from chat where chat_name = ?";
    private static final String SQL_GET_BY_ID = "select chat_id, chat_name, password, password_val from chat where chat_id = ?";

    private static final String SQL_ADD_CHAT = "insert into chat(chat_name,password,password_val) values(?,?,?)";

    private static final String SQL_CREATE_CHAT_USER = "insert into chat_user(user_id,chat_id) values(?,?)";

    private static final String SQL_GET_CHATS_BY_USER_ID = "select chat.chat_id, chat_name, password, password_val from chat " + "join chat_user cu on chat.chat_id = cu.chat_id " + "where user_id = ?";

    private static final String SQL_DELETE_CHAT = "delete from chat where chat_name = ?";

    private static final String SQL_GET_CHAT_WITH_MESSAGES = "select chat.chat_id, chat_name, password, password_val, message_id, u.user_id, user_login, user_password, nickname, message_val   from chat\n" +
            "left join message m on chat.chat_id = m.chat_id\n" +
            "left join user u on u.user_id = m.user_id\n" +
            "where chat.chat_name = ?";
    private static final String SQL_DELETE_MESSAGE_BY_ID = "delete from message where message_id = ?";
    private static final String SQL_ADD_MESSAGE = "insert into message(chat_id,user_id,message_val) values(?,?,?)";

    private static final String SQL_DELETE_CHAT_1  = "delete from chat_user where user_id = ? and chat_id = ?";
    private final ConnectionPool connectionPool;

    @Override
    public Optional<Chat> getByName(String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_NAME)) {
            preparedStatement.setString(1, name);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Chat.builder().id(resultSet.getLong(1)).name(resultSet.getString(2)).passwordNeed(resultSet.getBoolean(3)).password(resultSet.getString(4)).build());
            }
        } catch (SQLException e) {
            log.error("Cannot find by name", e);
            throw new DaoException("Cannot find by name", e);
        }
        return Optional.empty();
    }

    @Override
    public Optional<Chat> getById(Long id) throws DaoException {
        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_BY_ID)) {
            preparedStatement.setLong(1, id);
            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(Chat.builder().id(resultSet.getLong(1)).name(resultSet.getString(2)).passwordNeed(resultSet.getBoolean(3)).password(resultSet.getString(4)).build());
            }
        } catch (SQLException e) {
            log.error("Cannot find by id", e);
            throw new DaoException("Cannot find by id", e);
        }
        return Optional.empty();
    }

    @Override
    public Long addChat(String name, boolean pass, String password) throws DaoException {
        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_CHAT, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, name);
            preparedStatement.setBoolean(2, pass);
            preparedStatement.setString(3, password);
            int countRowsAdded = preparedStatement.executeUpdate();
            if (countRowsAdded > 0) {
                ResultSet resultSet = preparedStatement.getGeneratedKeys();
                if (resultSet.next()) {
                    return resultSet.getLong(1);
                }
            }
        } catch (SQLException e) {
            log.error("Cannot add chat", e);
            throw new DaoException("Cannot add chat", e);
        }
        throw new DaoException("Cannot add chat");
    }

    @Override
    public boolean createChat(String name, boolean pass, String password, Long userId) throws DaoException {
        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_CREATE_CHAT_USER)) {
            Long id = addChat(name, pass, password);
            preparedStatement.setLong(1, userId);
            preparedStatement.setLong(2, id);
            return preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Cannot create chat", e);
            throw new DaoException("Cannot create chat", e);
        }
    }

    @Override
    public List<Chat> getChatsByUserId(Long userId) throws DaoException {
        List<Chat> chats = new ArrayList<>();
        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_CHATS_BY_USER_ID)) {
            preparedStatement.setLong(1, userId);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                chats.add(Chat.builder().id(resultSet.getLong(1)).name(resultSet.getString(2)).passwordNeed(resultSet.getBoolean(3)).password(resultSet.getString(4)).build());
            }
        } catch (SQLException e) {
            log.error("Cannot get chats", e);
            throw new DaoException("Cannot get chats", e);
        }
        return chats;
    }

    @Override
    public boolean deleteChatByName(String name) throws DaoException {
        try (Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_CHAT)) {
            preparedStatement.setString(1, name);
            return preparedStatement.execute();
        } catch (SQLException e) {
            log.error("Cannot delete chat",e);
            throw new DaoException("Cannot delete chat",e);
        }
    }

    @Override
    public List<ChatDTO> getChatMessagesByName(String name) throws DaoException {
        List<ChatDTO> chatDTOS = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_GET_CHAT_WITH_MESSAGES)){
            preparedStatement.setString(1,name);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()){
                chatDTOS.add(ChatDTO.
                        builder().
                        chat(Chat.builder().
                                id(resultSet.getLong(1)).
                                name(resultSet.getString(2)).
                                passwordNeed(resultSet.getBoolean(3)).
                                password(resultSet.getString(4)).
                                build()).
                        message(Message.builder().
                                id(resultSet.getLong(5)).
                                user(User.builder().
                                        id(resultSet.getLong(6)).
                                        login(resultSet.getString(7)).
                                        password(resultSet.getString(8)).
                                        nickName(resultSet.getString(9)).
                                        build()).
                                message(resultSet.getString(10)).build()).
                        build()
                        );
            }
        }catch (SQLException e){
            log.error("cannot complete",e);
            throw new DaoException("cannot complete",e);
        }
        return chatDTOS;
    }

    @Override
    public boolean deleteMessageById(Long id) throws DaoException {
        try(Connection connection= connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_MESSAGE_BY_ID)){
            preparedStatement.setLong(1,id);
            return preparedStatement.execute();
        }catch (SQLException e){
            log.error("error",e);
            throw new DaoException("error",e);
        }
    }

    @Override
    public boolean addMessage(Long chatId, Long userId, String message) throws DaoException {
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_MESSAGE)){
            preparedStatement.setLong(1,chatId);
            preparedStatement.setLong(2,userId);
            preparedStatement.setString(3,message);
            return preparedStatement.execute();
        }catch (SQLException e){
            log.error("error",e);
            throw new DaoException("error",e);
        }
    }

    @Override
    public boolean deleteChatByUserIdAndChatID(Long userId, Long chatId) throws DaoException {
        try(Connection connection = connectionPool.getConnection();PreparedStatement preparedStatement = connection.prepareStatement(SQL_DELETE_CHAT_1)){
            preparedStatement.setLong(1,userId);
            preparedStatement.setLong(2,chatId);
            return preparedStatement.execute();
        }catch (SQLException e){
            log.error("error",e);
            throw new DaoException("error",e);
        }
    }
}
