package BXND.dodum.domain.information.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.information.dto.request.CommentReq;
import BXND.dodum.domain.information.entity.Info;
import BXND.dodum.domain.information.entity.InfoComment;
import BXND.dodum.domain.information.exception.InfoException;
import BXND.dodum.domain.information.exception.InfoStatusCode;
import BXND.dodum.domain.information.repository.InfoCommentRepository;
import BXND.dodum.domain.information.repository.InfoRepository;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class CommentUseCase {
    private final InfoCommentRepository infoCommentRepository;
    private final InfoRepository infoRepository;
    private final UsersRepository usersRepository;

    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    private Users getUser() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
    }

    @Transactional
    public ApiResponse<String> createComment(Long id, CommentReq request) {
        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));

        Users author = getUser();
        String date = LocalDateTime.now().format(DATE_FORMATTER);

        InfoComment infoComment = InfoComment.builder()
                .content(request.comment())
                .author(author)
                .info(info)
                .createdAt(date)
                .build();

        infoCommentRepository.save(infoComment);
        return ApiResponse.ok("댓글이 작성되었습니다.");
    }
}
