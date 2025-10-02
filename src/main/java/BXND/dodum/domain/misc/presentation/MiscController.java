package BXND.dodum.domain.misc.presentation;

import BXND.dodum.domain.misc.application.MiscApplicationUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/misc")
@RequiredArgsConstructor
public class MiscController {
  private final MiscApplicationUseCase miscApplicationUseCase;

  @GetMapping("/hello")
  public String hello(){
    return miscApplicationUseCase.hello();
  }
}
