package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.dto.response.UserResponse;

import java.time.LocalDateTime;

@Getter
@RequiredArgsConstructor
public class TodoResponse {

    private final Long id;
    private final String title;
    private final String contents;
    private final String weather;
    private final UserResponse user;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;

}
