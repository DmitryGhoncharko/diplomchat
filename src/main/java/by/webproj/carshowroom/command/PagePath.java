package by.webproj.carshowroom.command;

public enum PagePath {
    MAIN_PAGE("/WEB-INF/jsp/main.jsp"), LOGIN_PAGE("/WEB-INF/jsp/login.jsp"), INDEX_PATH("/"),
    REGISTRATION_PAGE("/WEB-INF/jsp/registration.jsp"),
    CABINET_PAGE("/WEB-INF/jsp/cab.jsp"), CHATS_PAGE("/WEB-INF/jsp/chats.jsp"), FIND_CHAT_PAGE("/WEB-INF/jsp/findChat.jsp"),
    CHAT_PAGE("/WEB-INF/jsp/chat.jsp"), OTZ_PAGE("/WEB-INF/jsp/otz.jsp"), CHAT_RESULT_PAGE_COMMAND("/WEB-INF/jsp/chatResult.jsp"),
    CREATE_CHAT_PAGE("/WEB-INF/jsp/createChat.jsp"), PASS_PAGE("/WEB-INF/jsp/pass.jsp");
    private final String path;

    PagePath(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
