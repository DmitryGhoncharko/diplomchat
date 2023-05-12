package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Chat;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class FindChatCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String chatName = request.getParameter("name");
        Optional<Chat> chatOptional = chatDao.getByName(chatName);
        if(chatOptional.isPresent()){
            request.addAttributeToJsp("chat",chatOptional.get());
        }

        return requestFactory.createForwardResponse(PagePath.CHAT_RESULT_PAGE_COMMAND.getPath());
    }
}
