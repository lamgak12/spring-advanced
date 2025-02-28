package org.example.expert.domain.comment.controller;

import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentUpdateRequest;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.service.CommentService;
import org.example.expert.domain.common.annotation.Auth;
import org.example.expert.domain.common.dto.AuthUser;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/todos/{todoId}")
public class CommentController {

    private final CommentService commentService;

    @PostMapping("/comments")
    public ResponseEntity<CommentSaveResponse> saveComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @Valid @RequestBody CommentSaveRequest commentSaveRequest
    ) {
        return ResponseEntity.ok(commentService.saveComment(authUser, todoId, commentSaveRequest));
    }

    @GetMapping("/comments")
    public ResponseEntity<List<CommentResponse>> getComments(@PathVariable long todoId) {
        return ResponseEntity.ok(commentService.getComments(todoId));
    }

    @PatchMapping("/comments/{commentId}")
    public void updateComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @PathVariable long commentId,
            @Valid @RequestBody CommentUpdateRequest commentUpdateRequest
    ){
        commentService.updateComment(authUser.getId(), todoId, commentId, commentUpdateRequest);
    }

    @DeleteMapping("/comments/{commentId}")
    public void deleteComment(
            @Auth AuthUser authUser,
            @PathVariable long todoId,
            @PathVariable long commentId
    ){
        commentService.deleteComment(authUser.getId(), todoId, commentId);
    }
}
