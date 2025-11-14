package BXND.dodum.domain.information.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.information.dto.request.CommentReq;
import BXND.dodum.domain.information.dto.request.CreateInfoReq;
import BXND.dodum.domain.information.dto.response.CommentRes;
import BXND.dodum.domain.information.dto.response.GetInfoRes;
import BXND.dodum.domain.information.dto.response.ViewInfoRes;
import BXND.dodum.domain.information.entity.Info;
import BXND.dodum.domain.information.entity.InfoComment;
import BXND.dodum.domain.information.exception.InfoException;
import BXND.dodum.domain.information.exception.InfoStatusCode;
import BXND.dodum.domain.information.entity.InfoLike;
import BXND.dodum.domain.information.repository.InfoCommentRepository;
import BXND.dodum.domain.information.repository.InfoLikeRepository;
import BXND.dodum.domain.information.repository.InfoRepository;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class InfoService {

    private final InfoRepository infoRepository;
    private final UsersRepository usersRepository;
    private final InfoCommentRepository infoCommentRepository;
    private final InfoLikeRepository infoLikeRepository;

    final int size = 10;
    final String sortBy = "id";

    @Transactional
    public ApiResponse<String> createInfo(CreateInfoReq request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter);

        Info info = Info.builder()
                .title(request.title())
                .subTitle(request.subTitle())
                .content(request.content())
                .createdAt(date)
                .author(user)
                .build();

        infoRepository.save(info);
        return ApiResponse.ok("새 글이 작성되었습니다.");
    }

    public ApiResponse<List<GetInfoRes>> getAllInformation(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Info> infoPage = infoRepository.findAllByIsApprovedTrue(pageable);

        List<GetInfoRes> responses = infoPage.getContent().stream()
                .map(info -> {
                    Users author = info.getAuthor();
                    String authorInfo = author.getAuthor();
                    return new GetInfoRes(
                            info.getId(),
                            info.getTitle(),
                            authorInfo,
                            info.getLikesCount(),
                            info.getViews(),
                            info.getCommentCount(),
                            info.getImageUrls(),
                            info.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());

        return ApiResponse.ok(responses);
    }

    public ApiResponse<ViewInfoRes> viewInformation(Long id) {
        Info info = infoRepository.findByIdAndIsApprovedTrue(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));

        Users author = info.getAuthor();
        String authorInfo = author.getAuthor();

        List<CommentRes> comments = info.getInfoComments().stream()
                .map(comment -> {
                    Users commentAuthor = comment.getAuthor();
                    String commentAuthorInfo = commentAuthor.getAuthor();
                    return new CommentRes(
                            comment.getContent(),
                            commentAuthorInfo,
                            comment.getCreatedAt()
                    );
                })
                .collect(Collectors.toList());

        info.incrementViews();
        infoRepository.save(info);
        return ApiResponse.ok(ViewInfoRes.of(info, authorInfo, comments));
    }

    @Transactional
    public ApiResponse<String> updateInfo(Long id, CreateInfoReq request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
        Users author = info.getAuthor();

        if (user.getRole().isAdminOrTeacher() || Objects.equals(author.getId(), user.getId())) {
            info.update(request.title(), request.subTitle(), request.content());

            infoRepository.save(info);

            return ApiResponse.ok("글이 수정되었습니다.");
        }
        throw new InfoException(InfoStatusCode.INFO_NO_PERMISSION);
    }

    @Transactional
    public ApiResponse<String> deleteInfo(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
        Users author = info.getAuthor();

        if (user.getRole().isAdminOrTeacher() || Objects.equals(author.getId(), user.getId())) {
            infoRepository.delete(info);

            return ApiResponse.ok("글이 삭제되었습니다.");

        }
        throw new InfoException(InfoStatusCode.INFO_NO_PERMISSION);
    }

    @Transactional
    public ApiResponse<String> InfoComment(Long id, CommentReq request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String date = LocalDateTime.now().format(formatter);

        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
        Users author = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));

        InfoComment infoComment = InfoComment.builder()
                .content(request.comment())
                .author(author)
                .info(info)
                .createdAt(date)
                .build();

        infoCommentRepository.save(infoComment);
        return ApiResponse.ok("댓글이 작성되었습니다.");
    }

    @Transactional
    public ApiResponse<String> isApproved(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));

        if (!user.getRole().isAdminOrTeacher()) {
            throw new AuthException(AuthStatusCode.ACCESS_DENIED);
        }

        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
        info.setApproved(true);
        infoRepository.save(info);

        return ApiResponse.ok("허용했습니다.");
    }

    @Transactional
    public ApiResponse<String> toggleLike(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Users user = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new AuthException(AuthStatusCode.USER_NOT_FOUND));
        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));

        boolean alreadyLiked = infoLikeRepository.existsByInfoAndUser(info, user);

        if (alreadyLiked) {
            InfoLike infoLike = infoLikeRepository.findByInfoAndUser(info, user)
                    .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
            infoLikeRepository.delete(infoLike);
            return ApiResponse.ok("좋아요를 취소했습니다.");
        }
            InfoLike infoLike = InfoLike.builder()
                    .info(info)
                    .user(user)
                    .build();
            infoLikeRepository.save(infoLike);
            return ApiResponse.ok("좋아요를 눌렀습니다.");
    }
}