package by.webproj.carshowroom.command;

import by.webproj.carshowroom.controller.RequestFactory;
import by.webproj.carshowroom.controller.SimpleRequestFactory;
import by.webproj.carshowroom.model.connection.ConnectionPool;
import by.webproj.carshowroom.model.connection.HikariCPConnectionPool;
import by.webproj.carshowroom.model.dao.*;
import by.webproj.carshowroom.model.service.*;
import by.webproj.carshowroom.securiy.BcryptWithSaltHasherImpl;
import by.webproj.carshowroom.securiy.PasswordHasher;
import by.webproj.carshowroom.util.Steganography;
import by.webproj.carshowroom.validator.SimpleUserValidator;
import by.webproj.carshowroom.validator.UserValidator;

public class InitialContext {
    private final ConnectionPool hikariCPConnectionPool = new HikariCPConnectionPool();
    private final UserDao simplePageDao = new SimpleUserDao(hikariCPConnectionPool);
    private final UserValidator simplePageServiceValidator = new SimpleUserValidator();
    private final PasswordHasher bcryptWithSaltHasher = new BcryptWithSaltHasherImpl();
    private final UserService simpleUserService = new SimpleUserService(simplePageServiceValidator, simplePageDao, bcryptWithSaltHasher);
    private final RequestFactory simpleRequestFactory = new SimpleRequestFactory();
    private final ChatDao chatDao = new SimpleChatDao(hikariCPConnectionPool);
    private final OtzDao otzDao = new SimpleOtzDao(hikariCPConnectionPool);
    private final Steganography steganography = new Steganography();
    public Command lookup(String commandName) {

        switch (commandName) {
            case "logincmnd":
                return new LoginCommand(simpleUserService, simpleRequestFactory);
            case "login":
                return new ShowLoginPageCommand(simpleRequestFactory);
            case "logout":
                return new LogoutCommand(simpleRequestFactory);
            case "registration":
                return new ShowRegistrationPageCommand(simpleRequestFactory);
            case "registrationcmnd":
                return new RegistrationCommand(simpleUserService, simpleRequestFactory);
            case "updateUserData":
                return new UpdateUserDataCommand(simpleRequestFactory,simpleUserService);
            case "cab":
                return new ShowCabinetPageCommand(simpleRequestFactory);
            case "chats":
                return new ShowChatsPageCommand(simpleRequestFactory, chatDao);
            case "findChat":
                return new ShowFindChatPageCommand(simpleRequestFactory);
            case "ch":
                return new ShowChatPageCommand(simpleRequestFactory);
            case "otz":
                return new ShowOtzPageCommand(simpleRequestFactory, otzDao);
            case "findChatt":
                return new FindChatCommand(simpleRequestFactory,chatDao);
            case "createChat":
                return new ShowCreateChatPage(simpleRequestFactory);
            case "createChatt":
                return new CreateChatCommand(simpleRequestFactory,chatDao);
            case "chatget":
                return new GetChatCommand(simpleRequestFactory,chatDao,steganography);
            case "chatgett":
           return new GetChatttCommand(simpleRequestFactory,chatDao,steganography);
            case "checkPass":
                return new CheckPassCommand(simpleRequestFactory,chatDao);
            case "del":
                return new DeleteMessageCommand(simpleRequestFactory,chatDao);
            case "send":
                return new SendMessageCommand(simpleRequestFactory,chatDao);
            case "addotz":
                return new AddOtzCommand(simpleRequestFactory,otzDao);
            default:
                return new ShowMainPageCommand(simpleRequestFactory);
        }

    }
}
