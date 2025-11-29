package BXND.dodum.domain.contest.controller;

import BXND.dodum.domain.contest.dto.request.CreateContestReq;
import BXND.dodum.domain.contest.dto.response.GetContestRes;
import BXND.dodum.domain.contest.dto.response.ViewContestRes;
import BXND.dodum.domain.contest.service.AlarmUseCase;
import BXND.dodum.domain.contest.service.ContestService;
import BXND.dodum.global.data.ApiResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contest")
@RequiredArgsConstructor
public class ContestController {
    private final ContestService contestService;
    private final AlarmUseCase alarmUseCase;

    @GetMapping
    public ApiResponse<List<GetContestRes>> findAll(@RequestParam(defaultValue = "0") int page) {
        return contestService.getAllContests(page);
    }

    @PostMapping
    public ApiResponse<String> createContest(@RequestBody CreateContestReq request) {
        return contestService.createContest(request);
    }

    @GetMapping("/{id}")
    public ApiResponse<ViewContestRes> getContest(@PathVariable Long id) {
        return contestService.viewContest(id);
    }

    @PutMapping("/{id}")
    public ApiResponse<String> updateContest(@PathVariable Long id, @RequestBody CreateContestReq request) {
        return contestService.updateContest(id, request);
    }

    @DeleteMapping("/{id}")
    public ApiResponse<String> deleteContest(@PathVariable Long id) {
        return contestService.deleteContest(id);
    }

    @PostMapping("/{id}/active")
    public ApiResponse<Boolean> toggleActive(@PathVariable Long id) {
        return alarmUseCase.toggleAlarm(id);
    }
}
