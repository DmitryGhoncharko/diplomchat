package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class ShowChatPageCommand implements Command{
    private final RequestFactory requestFactory;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        return requestFactory.createForwardResponse(PagePath.CHAT_PAGE.getPath());
    }
}
