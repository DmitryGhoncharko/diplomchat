package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.entity.User;
import by.webproj.carshowroom.exception.ServiceError;
import by.webproj.carshowroom.model.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;

public class RegistrationCommand implements Command {
    private static final String FILE_EXTENSION = ".png";
    private static final Logger LOG = LoggerFactory.getLogger(RegistrationCommand.class);
    private final UserService userService;
    private final RequestFactory requestFactory;

    public RegistrationCommand(UserService userService, RequestFactory requestFactory) {
        this.userService = userService;
        this.requestFactory = requestFactory;
    }

    @Override
    public CommandResponse execute(CommandRequest request) throws ServiceError {
        final String login = request.getParameter("login");
        final String password = request.getParameter("password");
        final String secretCode = request.getParameter("secretcode");
        final String nickName = request.getParameter("nick");
        User user = userService.addUserAsAdmin(login, password, secretCode,nickName);
        saveImage(request,user.getId());
        return requestFactory.createRedirectResponse(PagePath.INDEX_PATH.getPath());
    }
    private void saveImage(CommandRequest request, long imageId) {
        String uploadPath = "G:\\serverData";
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
            LOG.debug(e.getMessage(), e);
        }
    }
}
