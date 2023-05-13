package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.dto.ChatDTO;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShowChatPageCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String name = request.getParameter("name");
        List<ChatDTO> chatDTOS = chatDao.getChatMessagesByName(name);
        request.addAttributeToJsp("chats",chatDTOS);
        return requestFactory.createForwardResponse(PagePath.CHAT_PAGE.getPath());
    }
}
