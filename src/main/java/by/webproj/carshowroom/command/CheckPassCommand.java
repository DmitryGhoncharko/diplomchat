package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.dto.ChatDTO;
import by.webproj.carshowroom.entity.Chat;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class CheckPassCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        Optional<Chat> chat = chatDao.getByName(name);
        Chat chat1 = chat.get();
        if(chat1.getPassword().equals(pass)){
            List<ChatDTO> chatDTOS = chatDao.getChatMessagesByName(name);
            request.addAttributeToJsp("chats",chatDTOS);
            request.addAttributeToJsp("id",chatDTOS.get(0).getChat().getId());
            return requestFactory.createForwardResponse(PagePath.CHAT_PAGE.getPath());
        }else {
            return requestFactory.createForwardResponse(PagePath.INDEX_PATH.getPath());
        }
    }
}
