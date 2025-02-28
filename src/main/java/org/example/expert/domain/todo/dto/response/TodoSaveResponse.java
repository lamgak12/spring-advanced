package org.example.expert.domain.todo.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.user.dto.response.UserResponse;

@Getter
@RequiredArgsConstructor
public class TodoSaveResponse {

    private final Long id;
    private final String title;
    private final String contents;
    private final String weather;
    private final UserResponse user;

}
