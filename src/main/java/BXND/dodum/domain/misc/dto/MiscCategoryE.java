package BXND.dodum.domain.misc.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum MiscCategoryE {
  LECTURE_RECOMMENDATION(0, "lecture"),
  TOOL_RECOMMENDATION(1, "tool"),
  PLATFORM_RECOMMENDATION(2, "platform"),
  SCHOOL_SUPPORT(3, "school_support");

  private final int code;
  private final String id;
}
