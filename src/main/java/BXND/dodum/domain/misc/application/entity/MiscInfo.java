package BXND.dodum.domain.misc.application.entity;

import BXND.dodum.global.entity.Base;
import jakarta.persistence.Column;
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
  @Column(nullable = false)
  String title;

  @Column(nullable = false)
  String content;

  int likes;

  @Builder.Default
  boolean isApproved = false;

  // images: string[] 및 likes 릴레이션 분리
}
