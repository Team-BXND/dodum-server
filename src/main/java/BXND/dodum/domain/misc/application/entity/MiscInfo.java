package BXND.dodum.domain.misc.application.entity;

import BXND.dodum.domain.misc.application.data.MiscCategoryE;
import BXND.dodum.global.entity.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

  @Column(nullable = false)
  @Convert(converter = MiscCategoryConverter.class)
  MiscCategoryE category;

  // images: string[] 및 likes 릴레이션 분리
}
