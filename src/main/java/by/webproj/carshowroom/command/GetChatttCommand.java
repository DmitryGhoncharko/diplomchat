package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.dto.ChatDTO;
import by.webproj.carshowroom.entity.Chat;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import by.webproj.carshowroom.util.Steganography;
import lombok.RequiredArgsConstructor;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
public class GetChatttCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;

    private final Steganography steganography;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String name = request.getParameter("name");
        Optional<Chat> chat = chatDao.getByName(name);
        Steganography.unhideText(null,"message");
        if(chat.isPresent() && chat.get().getPasswordNeed()){
            request.addAttributeToJsp("chat",chat.get());
            return requestFactory.createForwardResponse(PagePath.PASS_PAGE.getPath());
        }
        List<ChatDTO> chatDTOS = chatDao.getChatMessagesByName(name);
        request.addAttributeToJsp("chats",chatDTOS);
        request.addAttributeToJsp("id",chatDTOS.get(0).getChat().getId());
        request.addAttributeToJsp("name",chatDTOS.get(0).getChat().getName());
        return requestFactory.createForwardResponse(PagePath.CHAT_PAGE.getPath());
    }
}
