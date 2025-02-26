package org.example.expert.config;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class LoggingAspect {

    private final ObjectMapper objectMapper; // Json <-> java
    private final HttpServletRequest request; // 요청

    @Around("execution(* org.example.expert.domain.comment.controller.CommentAdminController.deleteComment(..)) || " +
            "execution(* org.example.expert.domain.user.controller.UserAdminController.changeUserRole(..))")
    public Object logAdminAPi(ProceedingJoinPoint joinPoint) throws Throwable{

        // 요청 시각
        LocalDateTime requestTime = LocalDateTime.now();

        // jwt 사용자 추출
        String userId = request.getAttribute("userId").toString();

        // 요청 URL 가져오기
        String uri = request.getRequestURI();

        // 요청 데이터 JSON 변환
        Object[] args = joinPoint.getArgs();
        String requestBody = objectMapper.writeValueAsString(args);

        log.info("[API 요청]: 요청한 시각:{}, 사용자 ID:{}, 요청 URL:{}, 요청 데이터{}"
                ,requestTime,userId, uri, requestBody);

        // 메서드 실행
        Object response = joinPoint.proceed();

        // 응답 시간
        LocalDateTime responseTime = LocalDateTime.now();

        // 응답 데이터 JSON 변환
        String responseBody = objectMapper.writeValueAsString(response);

        // 처리 시간 계산
        long between = ChronoUnit.MILLIS.between(requestTime, responseTime);

        log.info("[API 응답] 응답한 시간: {}, 사용자 ID: {}, 요청 URL: {}, 응답 데이터: {}",
                responseTime, userId, uri, responseBody);
        log.info("[처리 시간]: {}ms", between);

        return response;
    }
}
