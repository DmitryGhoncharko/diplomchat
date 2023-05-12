package by.webproj.carshowroom.model.dao;


import by.webproj.carshowroom.entity.Role;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class SimpleUserDao implements UserDao {
    private static final Logger LOG = LoggerFactory.getLogger(SimpleUserDao.class);
    private static final String SQL_ADD_USER = "insert into user(user_login, user_password, user_role_id, nickname) values (?,?,?, ?)";
    private static final String SQL_FIND_USER_BY_LOGIN = "select user_id, user_login, user_password, r.role_name, nickname  from  user " +
            "left join role r on r.role_id = user.user_role_id " +
            "where user_login = ?";
    private static final String SQL_FIND_ALL_CLIENTS  = "select user_id, user_login, user_password, r.role_name, nickname  from  user" +
            " left join role r on user.user_role_id = r.role_id";

    private static final String  SQL_UPDATE_USER_LOGIN_AND_NICK = "update user set user_login = ?, nickname = ? where user_id = ?";
    private final ConnectionPool connectionPool;

    public SimpleUserDao(ConnectionPool connectionPool) {
        this.connectionPool = connectionPool;
    }

    @Override
    public boolean updateUserLoginAndNickName(Long id, String login, String nick) throws DaoException {
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_UPDATE_USER_LOGIN_AND_NICK)){
            preparedStatement.setString(1,login);
            preparedStatement.setString(2,nick);
            preparedStatement.setLong(3,id);
            return preparedStatement.executeUpdate()>0;
        }catch (SQLException e){
            LOG.error("Cannot update user",e);
            throw new DaoException("Cannot update user",e);
        }
    }

    @Override
    public User addUser(User user) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD_USER, Statement.RETURN_GENERATED_KEYS)) {
            preparedStatement.setString(1, user.getLogin());
            preparedStatement.setString(2, user.getPassword());
            preparedStatement.setLong(3, user.getUserRole().ordinal());
            preparedStatement.setString(4,user.getNickName());
            final int countCreatedRows = preparedStatement.executeUpdate();
            final ResultSet generatedKeys = preparedStatement.getGeneratedKeys();
            if (countCreatedRows > 0 && generatedKeys.next()) {
                return User.builder().
                        id(generatedKeys.getLong(1)).
                        login(user.getLogin()).
                        password(user.getPassword()).
                        userRole(user.getUserRole()).
                        nickName(user.getNickName()).
                        build();
            }
        } catch (SQLException sqlException) {
            LOG.error("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole(), sqlException);
            throw new DaoException("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole());
        }
        LOG.error("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole());
        throw new DaoException("Cannot add user userLogin: " + user.getLogin() + " userPassword: " + user.getPassword() + " userRole: " + user.getUserRole());
    }

    @Override
    public Optional<User> findUserByLogin(String login) throws DaoException {
        try (final Connection connection = connectionPool.getConnection(); final PreparedStatement preparedStatement = connection.prepareStatement(SQL_FIND_USER_BY_LOGIN)) {
            preparedStatement.setString(1, login);
            final ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(User.builder().
                        id(resultSet.getLong(1)).
                        login(resultSet.getString(2)).
                        password(resultSet.getString(3)).
                        userRole(Role.valueOf(resultSet.getString(4))).
                        nickName(resultSet.getString(5)).
                        build());
            }
        } catch (SQLException sqlException) {
            LOG.error("Cannot find user by login, login: " + login, sqlException);
            throw new DaoException("Cannot find user by login, login: " + login, sqlException);
        }
        LOG.error("Cannot find user by login, login: " + login);
        return Optional.empty();
    }

    @Override
    public List<User> findAllClients() throws DaoException {
        final List<User> users = new ArrayList<>();
        try(final Connection connection = connectionPool.getConnection(); final Statement statement = connection.createStatement()){
            final ResultSet resultSet = statement.executeQuery(SQL_FIND_ALL_CLIENTS);
            while (resultSet.next()){
                final User user = User.builder().
                        id(resultSet.getLong(1)).
                        login(resultSet.getString(2)).
                        password(resultSet.getString(3)).
                        userRole(Role.valueOf(resultSet.getString(4))).
                        nickName(resultSet.getString(5)).
                        build();
                users.add(user);
            }
        }catch (SQLException e){
            LOG.error("Cannot find users as clients", e);
            throw new DaoException("Cannot find users as clients", e);
        }
        return users;
    }
}
