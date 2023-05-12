package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.Otz;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.OtzDao;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public class ShowOtzPageCommand implements Command{
    private final RequestFactory requestFactory;
    private final OtzDao otzDao;
    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        List<Otz> otzs = otzDao.getAll();
        request.addAttributeToJsp("data",otzs);
        return requestFactory.createForwardResponse(PagePath.OTZ_PAGE.getPath());
    }
}
