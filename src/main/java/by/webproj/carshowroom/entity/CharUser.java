package by.webproj.carshowroom.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class CharUser {
    private Long id;
    private User user;
    private Chat chat;
}
