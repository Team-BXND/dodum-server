package BXND.dodum.domain.information.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.file.entity.FileEntityType;
import BXND.dodum.domain.file.service.FileService;
import BXND.dodum.domain.information.dto.request.CreateInfoReq;
import BXND.dodum.domain.information.dto.response.GetInfoRes;
import BXND.dodum.domain.information.dto.response.ViewInfoRes;
import BXND.dodum.domain.information.entity.Info;
import BXND.dodum.domain.information.exception.InfoException;
import BXND.dodum.domain.information.exception.InfoStatusCode;
import BXND.dodum.domain.information.repository.InfoRepository;
import BXND.dodum.global.data.ApiResponse;
import BXND.dodum.global.util.SecurityUtil;
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

@Service
@RequiredArgsConstructor
public class InfoService {

    private final InfoRepository infoRepository;
    private final SecurityUtil securityUtil;
    private final FileService fileService;

    private static final int PAGE_SIZE = 10;
    private static final String SORT_BY = "id";
    private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    @Transactional
    public ApiResponse<String> createInfo(CreateInfoReq request) {
        Users user = securityUtil.getUser();
        if (user.getGrade() == 1) {
            throw new InfoException(InfoStatusCode.UNAUTHORIZED);
        }

        String date = LocalDateTime.now().format(DATE_FORMATTER);

        Info info = Info.builder()
                .title(request.title())
                .content(request.content())
                .createdAt(date)
                .author(user)
                .build();

        infoRepository.save(info);
        return ApiResponse.ok("새 글이 작성되었습니다.");
    }

    public ApiResponse<List<GetInfoRes>> getTrueAllInfo(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, SORT_BY);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        Page<Info> infoPage = infoRepository.findAllByIsApprovedTrue(pageable);
        List<GetInfoRes> responses = GetInfoRes.fromPage(infoPage);
        return ApiResponse.ok(responses);
    }

    public ApiResponse<List<GetInfoRes>> getFalseAllInfo(int page) {
        Users  user = securityUtil.getUser();
        if (!user.getRole().isAdminOrTeacher()) {
            throw new InfoException(InfoStatusCode.UNAUTHORIZED);
        }

        Sort sort = Sort.by(Sort.Direction.DESC, SORT_BY);
        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        Page<Info> infoPage = infoRepository.findAllByIsApprovedFalse(pageable);
        List<GetInfoRes> responses = GetInfoRes.fromPage(infoPage);
        return ApiResponse.ok(responses);
    }

    @Transactional
    public ApiResponse<ViewInfoRes> viewInfo(Long id) {
        Info info = infoRepository.findByIdAndIsApprovedTrue(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));

        Users author = info.getAuthor();
        String authorInfo = author.getDisplayedName();

        info.incrementViews();
        infoRepository.save(info);
        return ApiResponse.ok(ViewInfoRes.of(info, authorInfo));
    }

    @Transactional
    public ApiResponse<String> updateInfo(Long id, CreateInfoReq request) {
        Users user = securityUtil.getUser();
        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
        Users author = info.getAuthor();

        if (user.getRole().isAdminOrTeacher() || Objects.equals(author.getId(), user.getId())) {
            info.update(request.title(), request.content());
            infoRepository.save(info);
            return ApiResponse.ok("글이 수정되었습니다.");
        }
        throw new InfoException(InfoStatusCode.UNAUTHORIZED);
    }

    @Transactional
    public ApiResponse<String> deleteInfo(Long id) {
        Users user = securityUtil.getUser();

        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
        Users author = info.getAuthor();

        if (user.getRole().isAdminOrTeacher() || Objects.equals(author.getId(), user.getId())) {

            // 게시글에 attach된 파일들 같이 삭제
            fileService.deleteAllByEntity(
                    FileEntityType.INFO_SHARE,
                    String.valueOf(info.getId())
            );

            infoRepository.delete(info);
            return ApiResponse.ok("글이 삭제되었습니다.");
        }
        throw new InfoException(InfoStatusCode.UNAUTHORIZED);
    }

    @Transactional
    public ApiResponse<String> approveInfo(Long id) {
        Users user = securityUtil.getUser();

        if (!user.getRole().isAdminOrTeacher()) {
            throw new AuthException(AuthStatusCode.ACCESS_DENIED);
        }

        Info info = infoRepository.findById(id)
                .orElseThrow(() -> new InfoException(InfoStatusCode.INFO_NOT_FOUND));
        info.setApproved(true);
        infoRepository.save(info);

        return ApiResponse.ok("허용했습니다.");
    }
}
