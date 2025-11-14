package BXND.dodum.domain.contest.service;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.contest.dto.request.CreateContestReq;
import BXND.dodum.domain.contest.dto.response.GetContestRes;
import BXND.dodum.domain.contest.dto.response.ViewContestRes;
import BXND.dodum.domain.contest.entity.Contest;
import BXND.dodum.domain.contest.exception.ContestException;
import BXND.dodum.domain.contest.exception.ContestStatusCode;
import BXND.dodum.domain.contest.repository.ContestRepository;
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

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ContestService {
    private final ContestRepository contestRepository;
    private final UsersRepository usersRepository;
    final int size = 10;
    final String sortBy = "id";

    @Transactional
    public ApiResponse<String> createContest(CreateContestReq request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        Users user = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new ContestException(ContestStatusCode.USER_NOT_FOUND));

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
        Sort sort = Sort.by(Sort.Direction.DESC, sortBy);

        Pageable pageable = PageRequest.of(page, size, sort);
        Page<Contest> contestPage = contestRepository.findAll(pageable);

        List<GetContestRes> responses = contestPage.getContent().stream()
                .map(contest -> new GetContestRes(
                        contest.getId(),
                        contest.getTitle(),
                        contest.getSubTitle(),
                        contest.getPlace(),
                        contest.getPhone(),
                        contest.getEmail(),
                        contest.getTime()
                ))
                .collect(Collectors.toList());
        return ApiResponse.ok(responses);
    }

    public ApiResponse<ViewContestRes> viewContest(Long id) {
        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ContestException(ContestStatusCode.CONTEST_NOT_FOUND));

        return ApiResponse.ok(ViewContestRes.of(contest));
    }

    public ApiResponse<String> updateContest(Long id, CreateContestReq request) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ContestException(ContestStatusCode.CONTEST_NOT_FOUND));
        Users users = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new ContestException(ContestStatusCode.USER_NOT_FOUND));
        Users user = contest.getAuthor();
        if(user.getUsername() == users.getUsername() || users.getRole().isAdminOrTeacher()) {
            contest.updateContest(request);
            contestRepository.save(contest);
            return ApiResponse.ok("대회정보가 수정되었습니다.");
        }
        throw new ContestException(ContestStatusCode.UNAUTHORIZED);
    }

    public ApiResponse<String> deleteContest(Long id) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        Contest contest = contestRepository.findById(id)
                .orElseThrow(() -> new ContestException(ContestStatusCode.CONTEST_NOT_FOUND));
        Users author = contest.getAuthor();
        Users user = usersRepository.findByUsername(auth.getName())
                .orElseThrow(() -> new ContestException(ContestStatusCode.USER_NOT_FOUND));
        if(author.getId() == user.getId() || user.getRole().isAdminOrTeacher()) {
            contestRepository.delete(contest);
            return ApiResponse.ok("대회정보가 삭제되었습니다.");
        }
        throw new ContestException(ContestStatusCode.UNAUTHORIZED);
    }
}
