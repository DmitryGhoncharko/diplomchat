package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Chat;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class ShowChatsPageCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        Optional<Object> userO = request.retrieveFromSession("user");
        User user = (User) userO.get();
        List<Chat> chats = chatDao.getChatsByUserId(user.getId());
        request.addAttributeToJsp("chats",chats);
        return requestFactory.createForwardResponse(PagePath.CHATS_PAGE.getPath());
    }
}
