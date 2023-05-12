package by.webproj.carshowroom.model.service;

import by.webproj.carshowroom.entity.Role;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.UserDao;
import by.webproj.carshowroom.securiy.PasswordHasher;
import by.webproj.carshowroom.validator.UserValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;

public class SimpleUserService implements UserService {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserService.class);
    private final UserValidator userValidator;
    private final UserDao userDao;
    private final PasswordHasher passwordHasher;

    public SimpleUserService(UserValidator userValidator, UserDao userDao, PasswordHasher passwordHasher) {
        this.userValidator = userValidator;
        this.userDao = userDao;
        this.passwordHasher = passwordHasher;

    }

    @Override
    public boolean updateUserLoginAndNickName(Long id, String login, String nick) {
        try {
            return userDao.updateUserLoginAndNickName(id,login,nick);
        } catch (DaoException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User addUserAsAdmin(String login, String password, String secretKey, String nickName) {
        if (!userValidator.validateUserDataByLoginAndPasswordWithSecretKey(login, password, secretKey)) {
            throw new ServiceError("Invalid user data, userPassword: " + login + " userLogin: " + password + " secretKey: " + secretKey);
        }
        try {
            final String hashedPassword = passwordHasher.hashPassword(password);
            final User user = User.builder().
                    login(login).
                    password(hashedPassword).
                    userRole(Role.ADMIN).
                    nickName(nickName).
                    build();

            return userDao.addUser(user);
        } catch (DaoException daoException) {
            LOG.error("Cannot add new user, userLogin: " + login + " userPassword: " + password + " secretKey: " + secretKey, daoException);
            throw new ServiceError("Cannot add new user, userLogin: " + login + " userPassword: " + password + " secretKey: " + secretKey, daoException);
        }
    }

    @Override
    public boolean addUserAsClient(String login, String password, String nickName) {
        if (!userValidator.validateUserDataByLoginAndPassword(login, password)) {
            return false;
        }
        try {
            final String hashedPassword = passwordHasher.hashPassword(password);
            final User user = User.builder().
                    login(login).
                    password(hashedPassword).
                    userRole(Role.CLIENT).
                    nickName(nickName).
                    build();
            userDao.addUser(user);
        } catch (DaoException e) {
            LOG.error("Cannot add user as client", e);
            throw new ServiceError("Cannot add user as client", e);
        }
        return true;
    }

    @Override
    public Optional<User> authenticateIfAdmin(String login, String password) {
        if (!userValidator.validateUserDataByLoginAndPassword(login, password)) {
            return Optional.empty();
        }
        try {

            final Optional<User> userFromDB = userDao.findUserByLogin(login);
            if (userFromDB.isPresent()) {
                final User userInstance = userFromDB.get();
                final String hashedPasswordFromDB = userInstance.getPassword();
                if (userInstance.getUserRole().equals(Role.ADMIN) && passwordHasher.checkIsEqualsPasswordAndPasswordHash(password, hashedPasswordFromDB)) {
                    return userFromDB;
                }
            }
        } catch (DaoException daoException) {
            LOG.error("Cannot authorize user, userLogin: " + login + " userPassword :" + password, daoException);
            throw new ServiceError("Cannot authorize user, userLogin: " + login + " userPassword :" + password);
        }
        return Optional.empty();
    }

    @Override
    public Optional<User> authenticateIfClient(String login, String password, String nickName) {
        if (!userValidator.validateUserDataByLoginAndPassword(login, password)) {
            return Optional.empty();
        }
        try {

            final Optional<User> userFromDB = userDao.findUserByLogin(login);
            if (userFromDB.isPresent()) {
                final User userInstance = userFromDB.get();
                final String hashedPasswordFromDB = userInstance.getPassword();
                if (userInstance.getUserRole().equals(Role.CLIENT) && passwordHasher.checkIsEqualsPasswordAndPasswordHash(password, hashedPasswordFromDB)) {
                    return userFromDB;
                }
            }
        } catch (DaoException daoException) {
            LOG.error("Cannot authorize user, userLogin: " + login + " userPassword :" + password, daoException);
            throw new ServiceError("Cannot authorize user, userLogin: " + login + " userPassword :" + password);
        }
        return Optional.empty();
    }

    @Override
    public List<User> findAllClients() {
        try {
            return userDao.findAllClients();
        } catch (DaoException e) {
            LOG.error("Cannot find users as clients", e);
            throw new ServiceError("Cannot find users as clients", e);
        }
    }

    @Override
    public Optional<User> findUserByLogin(String login) {
        try {
            return userDao.findUserByLogin(login);
        } catch (DaoException e) {
            throw new ServiceError();
        }
    }
}
