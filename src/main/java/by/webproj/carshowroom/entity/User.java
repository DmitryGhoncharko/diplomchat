package by.webproj.carshowroom.entity;


import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@EqualsAndHashCode
@ToString
@Builder
@Getter
public class User {
    private final long id;
    private final String login;
    private final String password;

    private final Role userRole;

    private final String nickName;

}
