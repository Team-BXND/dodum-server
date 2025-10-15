package BXND.dodum.domain.misc.application.data;

import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum MiscCategoryE {
  LECTURE_RECOMMENDATION("lecture"),
  TOOL_RECOMMENDATION("tool"),
  PLATFORM_RECOMMENDATION("platform");

  private final String id;
}
