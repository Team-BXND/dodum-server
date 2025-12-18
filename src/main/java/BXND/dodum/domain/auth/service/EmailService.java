package BXND.dodum.domain.auth.service;

import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.global.config.RedisConfig;
import BXND.dodum.global.data.ApiResponse;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.FileSystemResource;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisConfig redisConfig;
    private final TemplateEngine templateEngine;

    @Value("${spring.mail.username}")
    private String serviceName;

    public void sendEmail(String setFrom, String toMail, String title, String content, int authNum) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true, "UTF-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            Path logoPath = Paths.get(System.getProperty("user.dir"), "assets", "logo.png");

            if (Files.exists(logoPath) && Files.isRegularFile(logoPath)) {
                FileSystemResource logo = new FileSystemResource(logoPath.toFile());
                String contentType = Files.probeContentType(logoPath);
                helper.addInline("serviceLogo", logo, contentType != null ? contentType : "image/png");
            } else {
                log.warn("Email logo not found: {}", logoPath.toAbsolutePath());
            }

            javaMailSender.send(mimeMessage);

        } catch (Exception e) {
            log.error("sendEmail failed", e);
            return;
        }

        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        valueOperations.set(toMail, Integer.toString(authNum), 5, TimeUnit.MINUTES);
    }

    public String joinEmail(String email) {
        Random random = new Random();
        int authNum = 100000 + random.nextInt(900000);

        String title = "회원가입을 위한 인증코드입니다.";

        Context context = new Context();
        context.setVariable("authNum", authNum);

        String content = templateEngine.process("EmailAuth", context);
        sendEmail(serviceName, email, title, content, authNum);

        return Integer.toString(authNum);
    }

    public ApiResponse<Boolean> checkEmail(String email, String authNum) {
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        String code = valueOperations.get(email);

        if (Objects.equals(code, authNum)) {
            redisConfig.redisTemplate().delete(email);
            return ApiResponse.ok(Boolean.TRUE);
        }
        throw new AuthException(AuthStatusCode.EMAIL_VERIFICATION_FAILED);
    }
}
