package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class DeleteMessageCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String id = request.getParameter("id");
        String name = request.getParameter("name");
        chatDao.deleteMessageById(Long.valueOf(id));
        return requestFactory.createRedirectResponse("/controller?command=chatget&name=" + name);
    }
}
