package BXND.dodum.domain.misc.application.entity;

import BXND.dodum.global.entity.Base;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@Builder
public class MiscInfo extends Base {
  @NotBlank
  String title;

  @NotBlank
  String content;

  @NotNull
  int likes;

  @NotNull
  boolean isApproved;

  // images: string[] 및 likes 릴레이션 분리
}
