package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.service.UserService;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class ShowCabinetPageCommand implements Command{
    private final RequestFactory requestFactory;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        return requestFactory.createForwardResponse(PagePath.CABINET_PAGE.getPath());
    }
}
