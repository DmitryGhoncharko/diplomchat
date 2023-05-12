package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.dto.ChatDTO;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import by.webproj.carshowroom.util.Steganography;
import lombok.RequiredArgsConstructor;
import java.util.List;

@RequiredArgsConstructor
public class GetChatCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;

    private final Steganography steganography;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String name = request.getParameter("name");
        List<ChatDTO> chatDTOS = chatDao.getChatMessagesByName(name);
        Steganography.unhideText(null,"message");
        request.addAttributeToJsp("chats",chatDTOS);
        request.addAttributeToJsp("id",chatDTOS.get(0).getChat().getId());
        Steganography.hideText(null,"message");
        request.addAttributeToJsp("name",chatDTOS.get(0).getChat().getName());
        return requestFactory.createForwardResponse(PagePath.CHAT_PAGE.getPath());
    }
}
