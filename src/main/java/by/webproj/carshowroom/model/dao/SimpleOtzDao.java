package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Otz;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Slf4j
public class SimpleOtzDao implements OtzDao{
    private static final String SQL_ADD ="insert into otz(user_id,message) values(?,?)";
    private static final String  SQL_GET_ALL = "select otz_id, u.user_id, user_login, nickname,  message from otz " +
            "join user u on u.user_id = otz.user_id";
    private final ConnectionPool connectionPool;

    @Override
    public boolean addOtz(Long userId, String message) throws DaoException {
        try(Connection connection = connectionPool.getConnection(); PreparedStatement preparedStatement = connection.prepareStatement(SQL_ADD)){
            preparedStatement.setLong(1,userId);
            preparedStatement.setString(2,message);
            return preparedStatement.execute();
        }catch (SQLException e){
            log.error("cannot add otz",e);
            throw new DaoException("cannot add otz",e);
        }
    }

    @Override
    public List<Otz> getAll() throws DaoException {
        List<Otz> otzs = new ArrayList<>();
        try(Connection connection = connectionPool.getConnection(); Statement statement = connection.createStatement()){
            ResultSet resultSet = statement.executeQuery(SQL_GET_ALL);
            while (resultSet.next()){
                otzs.add(Otz.builder().
                        id(resultSet.getLong(1)).
                        user(User.builder().
                                id(resultSet.getLong(2)).
                                login(resultSet.getString(3)).
                                nickName(resultSet.getString(4)).
                                build()).
                        message(resultSet.getString(5)).build());
            }
        }catch (SQLException e){
            log.error("error",e);
            throw new DaoException("error",e);
        }
        return otzs;
    }
}
