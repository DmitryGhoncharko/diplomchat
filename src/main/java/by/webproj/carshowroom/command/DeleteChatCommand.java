package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class DeleteChatCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String id = request.getParameter("id");
        Optional<Object> data = request.retrieveFromSession("user");
        User user = (User) data.get();
        chatDao.deleteChatByUserIdAndChatID(user.getId(), Long.valueOf(id));
        return requestFactory.createRedirectResponse("/controller?command=chats");
    }
}
