package com.swb.springcloud.userservice.service;

import com.swb.springcloud.userservice.Utils.YZMHelper;
import com.swb.springcloud.userservice.exception.EmailException;
import com.swb.springcloud.userservice.exception.EmailException.Type;
import com.swb.springcloud.userservice.exception.RedisException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.internet.MimeMessage;
import java.util.concurrent.TimeUnit;

@Service
public class EmailServiceImpl {

    @Value("${spring.mail.username}")
    private String emailFrom;

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    private TemplateEngine templateEngine;

    @Autowired
    private StringRedisTemplate redisTemplate;

    public void sendRegisterYZM(String sendTo) {
        //简单邮件发送  纯文本
        try {
            if (redisTemplate.getExpire("email" + sendTo) > 0) {
                redisTemplate.expire("email" + sendTo, 5, TimeUnit.MINUTES);
                throw new EmailException(Type.EMAIL_REPEAT_SENT, "重复向" + sendTo + "发送邮件");
            }
        } catch (Exception e) {
            throw new RedisException(RedisException.Type.Redis_Error, "redis error");
        }
        String yzm = YZMHelper.verifyCode();
        try {
            redisTemplate.opsForValue().set("email" + sendTo, yzm, 5, TimeUnit.MINUTES);
        } catch (Exception e) {
            throw new RedisException(RedisException.Type.Redis_Error, "redis error");
        }
        MimeMessage mimeMessage = null;
        try {
            mimeMessage = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            Context context = new Context();
            context.setVariable("yzm", yzm);
            String emailContext = templateEngine.process("yzm", context);
            helper.setFrom(emailFrom);
            helper.setTo(sendTo);
            helper.setSubject("花间花舍注册验证码");
            helper.setText(emailContext, true);
            mailSender.send(mimeMessage);
        } catch (Exception e) {
            redisTemplate.delete("email" + sendTo);
            throw new EmailException(Type.EMAIL_SEND_FAILED, "email send failed form " + sendTo);
        }
    }

    public boolean yzEmailYZM(String email, String yzm, int i) {
        if (redisTemplate.getExpire("email" + email) > 0) {
            String redisTZM = redisTemplate.opsForValue().get("email" + email);
            if (!redisTZM.equals(yzm)) {
                throw new EmailException(EmailException.Type.YZM_ERROR, email + "的验证码错误");
            }
            if (i == 1) {
                redisTemplate.expire("email" + email, 5, TimeUnit.MINUTES);
            } else redisTemplate.delete("email" + email);
        } else {
            throw new EmailException(EmailException.Type.YZM_INVALID, email + "的验证码失效");
        }
        return true;
    }

}
