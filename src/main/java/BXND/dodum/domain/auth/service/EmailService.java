package BXND.dodum.domain.auth.service;

import BXND.dodum.domain.auth.exception.AuthException;
import BXND.dodum.domain.auth.exception.AuthStatusCode;
import BXND.dodum.global.config.RedisConfig;
import BXND.dodum.global.data.ApiResponse;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Objects;
import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender javaMailSender;
    private final RedisConfig redisConfig;
    private int authNum;

    @Value("${spring.mail.username}")
    private String serviceName;

    public void makeRandomNumber() {
        Random random = new Random();
        authNum = 100000 + random.nextInt(900000);
    }

    public void sendEmail(String setFrom, String toMail, String title, String content) {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage,true, "utf-8");
            helper.setFrom(setFrom);
            helper.setTo(toMail);
            helper.setSubject(title);
            helper.setText(content, true);
            javaMailSender.send(mimeMessage);
        } catch (Exception e) {
            log.error(e.getMessage());
            e.printStackTrace();
        }
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        valueOperations.set(toMail,Integer.toString(authNum), 5, TimeUnit.MINUTES);
    }

    public String joinEmail(String email) {
        makeRandomNumber();
        String customerMail = email;
        String title = "회원가입을 위한 인증코드입니다.";
        String content =
                "<div style='max-width:520px; margin:auto; background:#0f172a; font-family:\"Inter\", \"Noto Sans KR\", sans-serif; border-radius:24px; overflow:hidden; box-shadow:0 8px 24px rgba(0,0,0,0.3);'>" +

                        // HEADER
                        "<div style='background:linear-gradient(135deg, #10b981, #34d399); padding:40px 24px; text-align:center;'>" +
                        "<h1 style='color:#ffffff; font-size:30px; font-weight:800; margin:0;'>dodum</h1>" +
                        "<p style='color:rgba(255,255,255,0.9); font-size:15px; margin-top:8px;'>이메일 인증 요청</p>" +
                        "</div>" +

                        // BODY
                        "<div style='background:#1e293b; padding:40px 32px; text-align:center;'>" +
                        "<h2 style='color:#f8fafc; font-size:22px; font-weight:700; margin-bottom:16px;'>이메일을 인증해주세요</h2>" +
                        "<p style='color:#94a3b8; font-size:15px; line-height:1.7; margin-bottom:36px;'>회원님의 계정을 안전하게 보호하기 위해<br>아래의 인증번호를 입력해주세요.</p>" +

                        "<div style='display:inline-block; background:#0f172a; border:2px solid #10b981; border-radius:14px; padding:24px 36px; margin-bottom:32px;'>" +
                        "<span style='font-size:38px; font-weight:900; letter-spacing:10px; color:#10b981; font-family:\"SF Mono\", monospace;'>" +
                        authNum +
                        "</span>" +
                        "</div>" +

                        "<p style='font-size:14px; color:#10b981; background:rgba(16,185,129,0.1); display:inline-block; padding:12px 18px; border-radius:10px; margin-bottom:28px;'>" +
                        "⏰ 인증번호는 <strong>10분</strong> 동안만 유효합니다." +
                        "</p>" +

                        "<p style='font-size:13px; color:#94a3b8; margin-top:18px; line-height:1.6;'>본 메일은 <strong style='color:#10b981;'>dodum</strong> 서비스에서 자동으로 발송되었습니다.<br>답장이 필요하지 않습니다.</p>" +
                        "</div>" +

                        // FOOTER
                        "<div style='background:#0f172a; padding:18px; text-align:center; border-top:1px solid #1e293b;'>" +
                        "<p style='font-size:12px; color:#64748b; margin:0;'>© 2025 dodum. All rights reserved.</p>" +
                        "</div>" +
                        "</div>";
        sendEmail(serviceName,customerMail,title, content);
        return Integer.toString(authNum);
    }
    public ApiResponse<String> checkEmail(String email, String authNume) {
        ValueOperations<String, String> valueOperations = redisConfig.redisTemplate().opsForValue();
        String code = valueOperations.get(email);
        if(Objects.equals(code,authNume)){
            return ApiResponse.ok("이메일 인증 성공");

        }
        throw new AuthException(AuthStatusCode.EMAIL_VERIFICATION_FAILED);
    }
}
