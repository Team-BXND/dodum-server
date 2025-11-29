package BXND.dodum.domain.file.entity;

public enum FileEntityType {
    INFO_SHARE, // 정보공유 게시글
    CONTEST, // 대회정보 게시글
    PROFILE, // 사용자 프로필 (entityId = userId)
    ARCHIVE // 아카이브 항목/게시글
}
