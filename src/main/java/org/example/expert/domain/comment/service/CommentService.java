package org.example.expert.domain.comment.service;

import lombok.RequiredArgsConstructor;
import org.example.expert.domain.comment.dto.request.CommentSaveRequest;
import org.example.expert.domain.comment.dto.request.CommentUpdateRequest;
import org.example.expert.domain.comment.dto.response.CommentResponse;
import org.example.expert.domain.comment.dto.response.CommentSaveResponse;
import org.example.expert.domain.comment.entity.Comment;
import org.example.expert.domain.comment.repository.CommentRepository;
import org.example.expert.domain.common.dto.AuthUser;
import org.example.expert.domain.common.exception.InvalidRequestException;
import org.example.expert.domain.todo.entity.Todo;
import org.example.expert.domain.todo.repository.TodoRepository;
import org.example.expert.domain.user.dto.response.UserResponse;
import org.example.expert.domain.user.entity.User;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CommentService {

    private final TodoRepository todoRepository;
    private final CommentRepository commentRepository;

    @Transactional
    public CommentSaveResponse saveComment(AuthUser authUser, long todoId, CommentSaveRequest commentSaveRequest) {
        User user = User.fromAuthUser(authUser);
        Todo todo = todoRepository.findById(todoId).orElseThrow(() ->
                new InvalidRequestException("Todo not found"));

        Comment newComment = new Comment(
                commentSaveRequest.getContents(),
                user,
                todo
        );

        Comment savedComment = commentRepository.save(newComment);

        return new CommentSaveResponse(
                savedComment.getId(),
                savedComment.getContents(),
                new UserResponse(user.getId(), user.getEmail())
        );
    }

    @Transactional(readOnly = true)
    public List<CommentResponse> getComments(long todoId) {
        List<Comment> commentList = commentRepository.findByTodoIdWithUser(todoId);

        List<CommentResponse> dtoList = new ArrayList<>();
        for (Comment comment : commentList) {
            User user = comment.getUser();
            CommentResponse dto = new CommentResponse(
                    comment.getId(),
                    comment.getContents(),
                    new UserResponse(user.getId(), user.getEmail())
            );
            dtoList.add(dto);
        }
        return dtoList;
    }

    @Transactional
    public void updateComment(Long authUserId, long todoId, long commentId, CommentUpdateRequest commentUpdateRequest) {

        // 댓글과 관련된 todo,user 조회
        Comment comment = commentRepository.findByIdWithTodoAndUser(commentId)
                .orElseThrow(() -> new InvalidRequestException("Comment not found"));

        // 댓글 작성자와 현재 인증된 사용자가 일치하는지 검증합니다.
        if (!comment.getUser().getId().equals(authUserId)) {
            throw new InvalidRequestException("자신이 작성한 댓글만 수정할 수 있습니다.");
        }

        // 댓글이 지정된 Todo에 속하는지 검증합니다.
        if (comment.getTodo().getId() != todoId) {
            throw new InvalidRequestException("해당 댓글은 지정된 Todo에 속하지 않습니다.");
        }

        // 요청에서 전달받은 내용을 이용하여 댓글을 업데이트합니다.
        comment.updateContent(commentUpdateRequest.getContents());
    }

    @Transactional
    public void deleteComment(Long authUserId, long todoId, long commentId) {
        // 댓글과 관련된 todo,user 조회
        Comment comment = commentRepository.findByIdWithTodoAndUser(commentId)
                .orElseThrow(() -> new InvalidRequestException("Comment not found"));

        // 댓글 작성자와 현재 인증된 사용자가 일치하는지 검증합니다.
        if (!comment.getUser().getId().equals(authUserId)) {
            throw new InvalidRequestException("자신이 작성한 댓글만 수정할 수 있습니다.");
        }

        // 댓글이 지정된 Todo에 속하는지 검증합니다.
        if (comment.getTodo().getId() != todoId) {
            throw new InvalidRequestException("해당 댓글은 지정된 Todo에 속하지 않습니다.");
        }

        commentRepository.delete(comment);
    }
}
