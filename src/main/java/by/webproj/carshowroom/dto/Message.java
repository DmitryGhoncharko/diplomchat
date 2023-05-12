package by.webproj.carshowroom.dto;

import by.webproj.carshowroom.entity.User;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class Message {
    private Long id;
    private User user;

    private String message;
}
