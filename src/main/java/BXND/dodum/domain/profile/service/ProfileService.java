package BXND.dodum.domain.profile.service;

import BXND.dodum.domain.archive.dto.response.ArchiveItemRes;
import BXND.dodum.domain.archive.repository.ArchiveRepository;
import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.auth.repository.UsersRepository;
import BXND.dodum.domain.contest.dto.response.GetContestRes;
import BXND.dodum.domain.contest.repository.ContestRepository;
import BXND.dodum.domain.information.dto.response.GetInfoRes;
import BXND.dodum.domain.information.repository.InfoRepository;
import BXND.dodum.domain.profile.dto.request.UpdateProfileReq;
import BXND.dodum.domain.profile.dto.response.MyPostsRes;
import BXND.dodum.domain.profile.dto.response.ProfileRes;
import BXND.dodum.global.data.ApiResponse;
import BXND.dodum.global.util.SecurityUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProfileService {
    private final UsersRepository usersRepository;
    private final SecurityUtil securityUtil;
    private final InfoRepository infoRepository;
    private final ContestRepository contestRepository;
    private final ArchiveRepository archiveRepository;

    public ApiResponse<ProfileRes> getProfile() {
        Users user = securityUtil.getUser();

        return ApiResponse.ok(ProfileRes.of(user));
    }


    public ApiResponse<String> updateProfile(UpdateProfileReq request) {
        Users user = securityUtil.getUser();
        user.updateProfile(request);
        usersRepository.save(user);
        return ApiResponse.ok("프로필이 수정되었습니다.");
        }

    public ApiResponse<MyPostsRes> getMyPosts() {
        Users user = securityUtil.getUser();

        List<GetInfoRes> informations = infoRepository.findByAuthor(user)
                .stream()
                .map(GetInfoRes::from)
                .toList();

        List<GetContestRes> contests = contestRepository.findByAuthor(user)
                .stream()
                .map(GetContestRes::of)
                .toList();

        List<ArchiveItemRes> archives = archiveRepository.findByAuthorIdAndDeletedFalseOrderByCreatedAtDesc(user.getUsername())
                .stream()
                .map(ArchiveItemRes::from)
                .toList();

        MyPostsRes response = new MyPostsRes(informations, contests, archives);
        return ApiResponse.ok(response);
    }

}
