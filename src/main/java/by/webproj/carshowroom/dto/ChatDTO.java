package by.webproj.carshowroom.dto;

import by.webproj.carshowroom.entity.Chat;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;

import java.util.List;

@Builder
@EqualsAndHashCode
@ToString
@Getter
public class ChatDTO {
    private Chat chat;
    private Message message;
}
