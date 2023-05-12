package by.webproj.carshowroom.model.dao;

import by.webproj.carshowroom.entity.Otz;
import by.webproj.carshowroom.exception.DaoException;

import java.util.List;

public interface OtzDao {
    boolean addOtz(Long userId, String message) throws DaoException;

    List<Otz> getAll() throws DaoException;
}
