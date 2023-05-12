package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.DaoException;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.service.UserService;
import lombok.RequiredArgsConstructor;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.util.Optional;

@RequiredArgsConstructor
public class UpdateUserDataCommand implements Command{
    private static final String FILE_EXTENSION = ".png";
    private final RequestFactory requestFactory;
    private final UserService userService;

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError, DaoException {
        String login = request.getParameter("login");
        String nick = request.getParameter("nick");
        Optional<User> user = userService.findUserByLogin(login);
        userService.updateUserLoginAndNickName(user.get().getId(),login,nick);
        saveImage(request,user.get().getId());
        request.removeFromSession("user");
        request.clearSession();
        return requestFactory.createRedirectResponse("/controller?command=login");
    }
    private void saveImage(CommandRequest request, long imageId) {
        String uploadPath = "E:\\serverData";
        File uploadDir = new File(uploadPath);
        if (!uploadDir.exists()) {
            uploadDir.mkdir();
        }
        try {
            for (Part part : request.getParts()) {
                String fileName = part.getSubmittedFileName();
                if (fileName != null) {
                    part.write(uploadPath + File.separator + imageId + FILE_EXTENSION);
                }
            }
        } catch (IOException | ServletException e) {

        }
    }
}
