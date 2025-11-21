package BXND.dodum.global.storage;
import org.springframework.core.io.Resource;
import org.springframework.web.multipart.MultipartFile;

public interface StorageService {
    String upload(MultipartFile file); // 업로드 후 접근 URL 반환
    void delete(String url); // 필요 시 삭제
    Resource loadAsResource(String keyName); // 파일명으로 Resource로 파일 로드
}