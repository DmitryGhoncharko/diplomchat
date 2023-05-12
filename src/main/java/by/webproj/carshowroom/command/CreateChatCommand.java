package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.dao.ChatDao;
import lombok.RequiredArgsConstructor;

import java.util.Optional;

@RequiredArgsConstructor
public class CreateChatCommand implements Command{
    private final RequestFactory requestFactory;
    private final ChatDao chatDao;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String name = request.getParameter("name");
        String pass = request.getParameter("pass");
        Optional<Object> userOptional = request.retrieveFromSession("user");
        User user = (User) userOptional.get();
        if(pass!=null || pass.length() > 0){
            chatDao.createChat(name,true,pass,user.getId());
        }else {
            chatDao.createChat(name,false,null,user.getId());
        }
        return requestFactory.createRedirectResponse("/controller?command=main");
    }
}
