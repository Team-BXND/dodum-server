package BXND.dodum.domain.misc.application.data;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MiscCategoryE {
  LECTURE_RECOMMENDATION("lecture"),
  TOOL_RECOMMENDATION("tool"),
  PLATFORM_RECOMMENDATION("platform");

  private final String id;
}
