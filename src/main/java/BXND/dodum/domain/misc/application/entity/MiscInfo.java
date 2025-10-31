package BXND.dodum.domain.misc.application.entity;

import BXND.dodum.global.entity.Base;
import jakarta.persistence.Entity;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter @Setter
@Builder
@Entity
@RequiredArgsConstructor
@AllArgsConstructor
public class MiscInfo extends Base {
  @NotBlank
  String title;

  @NotBlank
  String content;

  @NotNull
  int likes;

  @Builder.Default
  boolean isApproved = false;

  // images: string[] 및 likes 릴레이션 분리
}
