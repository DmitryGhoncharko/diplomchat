package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class SendMessageCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String id = request.getParameter("id");
        String mes = request.getParameter("mes");
        String name = request.getParameter("name");
        Optional<Object> user = request.retrieveFromSession("user");
        User user1 = (User) user.get();
        chatDao.addMessage(Long.valueOf(id),user1.getId(), mes);
        return requestFactory.createRedirectResponse("/controller?command=chatget&name=" + name);
    }
}
