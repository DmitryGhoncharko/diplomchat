package by.webproj.carshowroom.model.service;

import by.webproj.carshowroom.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {
    User addUserAsAdmin(String login, String password, String secretKey, String nickName);

    Optional<User> authenticateIfAdmin(String login, String password);

    Optional<User> authenticateIfClient(String login, String password, String nickName);

    boolean addUserAsClient(String login, String password, String nickName);

    List<User> findAllClients();

    Optional<User> findUserByLogin(String login);

    boolean updateUserLoginAndNickName(Long id, String login, String nick);
}
