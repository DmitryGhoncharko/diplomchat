package by.webproj.carshowroom.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@Getter
@EqualsAndHashCode
@ToString
public class Otz {
    private Long id;
    private User user;
    private String message;
}
