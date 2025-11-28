package BXND.dodum.domain.contest.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.contest.dto.request.CreateContestReq;
import BXND.dodum.domain.contest.dto.response.GetContestRes;
import BXND.dodum.domain.contest.dto.response.ViewContestRes;
import BXND.dodum.domain.contest.entity.Contest;
import BXND.dodum.domain.contest.exception.ContestException;
import BXND.dodum.domain.contest.exception.ContestStatusCode;
import BXND.dodum.domain.contest.repository.ContestRepository;
import BXND.dodum.global.data.ApiResponse;
import BXND.dodum.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;
    private final SecurityUtil securityUtil;

    private static final int PAGE_SIZE = 10;
    private static final String SORT_BY = "id";

    @Transactional
    public ApiResponse<String> createContest(CreateContestReq request) {
        Users user = securityUtil.getUser();

        Contest contest = Contest.builder()
                .title(request.title())
                .subTitle(request.subTitle())
                .place(request.place())
                .phone(request.phone())
                .email(request.email())
                .time(request.time())
                .content(request.content())
                .author(user)
                .build();

        contestRepository.save(contest);
        return ApiResponse.ok("대회가 등록되었습니다.");
    }

    @Transactional
    public ApiResponse<List<GetContestRes>> getAllContests(int page) {
        Sort sort = Sort.by(Sort.Direction.DESC, SORT_BY);

        Pageable pageable = PageRequest.of(page, PAGE_SIZE, sort);
        Page<Contest> contestPage = contestRepository.findAll(pageable);

        List<GetContestRes> responses = GetContestRes.ofList(contestPage.getContent());
        return ApiResponse.ok(responses);
    }

    public ApiResponse<ViewContestRes> viewContest(Long id) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ContestException(ContestStatusCode.CONTEST_NOT_FOUND));

        return ApiResponse.ok(ViewContestRes.of(contest));
    }

    @Transactional
    public ApiResponse<String> updateContest(Long id, CreateContestReq request) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ContestException(ContestStatusCode.CONTEST_NOT_FOUND));

        Users user = securityUtil.getUser();
        Users author = contest.getAuthor();

        if(Objects.equals(author.getId(), user.getId()) || user.getRole().isAdminOrTeacher()) {
            contest.updateContest(request);
            contestRepository.save(contest);
            return ApiResponse.ok("대회정보가 수정되었습니다.");
        }
        throw new ContestException(ContestStatusCode.UNAUTHORIZED);
    }

    @Transactional
    public ApiResponse<String> deleteContest(Long id) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ContestException(ContestStatusCode.CONTEST_NOT_FOUND));

        Users user = securityUtil.getUser();
        Users author = contest.getAuthor();

        if(Objects.equals(author.getId(), user.getId()) || user.getRole().isAdminOrTeacher()) {
            contestRepository.delete(contest);
            return ApiResponse.ok("대회정보가 삭제되었습니다.");
        }
        throw new ContestException(ContestStatusCode.UNAUTHORIZED);
    }
}
