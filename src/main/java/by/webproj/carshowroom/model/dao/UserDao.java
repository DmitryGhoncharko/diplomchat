package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;
import java.util.Optional;

public interface UserDao {
   User addUser(User user) throws DaoException;

   Optional<User> findUserByLogin(String login) throws DaoException;

   List<User> findAllClients() throws DaoException;

   boolean updateUserLoginAndNickName(Long id, String login, String nick) throws DaoException;
}
