package by.webproj.carshowroom.entity;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class Chat {
    private Long id;
    private String name;
    private Boolean passwordNeed;
    private String password;
}
