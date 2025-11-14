package BXND.dodum.domain.auth.entity;

public enum Role {
    STUDENT, // 1학년
    TEACHER, // 선생님
    SENIOR, // 2, 3학년 학생
    GRADUATE, // 졸업생
    ADMIN; // 관리자

    public boolean isAdminOrTeacher() {
        return this == TEACHER || this == ADMIN;
    }
}
