package BXND.dodum.domain.misc.entity;

import BXND.dodum.domain.auth.entity.Users;
import BXND.dodum.domain.misc.dto.MiscCategoryE;
import BXND.dodum.global.entity.Base;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
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

  @ManyToOne
  @JoinColumn(name="author_id")
  Users author;

  // images: string[] 및 likes 릴레이션 분리
}
