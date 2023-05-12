package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.OtzDao;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class AddOtzCommand implements Command{
    private final RequestFactory requestFactory;
    private final OtzDao otzDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String mes = request.getParameter("name");
        Optional<Object> o = request.retrieveFromSession("user");
        User user = (User) o.get();
        otzDao.addOtz(user.getId(),mes);
        return requestFactory.createRedirectResponse("/controller?command=otz");
    }
}
