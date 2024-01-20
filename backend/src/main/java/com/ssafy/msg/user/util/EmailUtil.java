package com.ssafy.msg.user.util;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import com.ssafy.msg.user.model.dto.EmailDto;
import com.ssafy.msg.user.model.dto.UserDto;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class EmailUtil {
	
	@Value("${spring.mail.username}")
	private String fromAddress;

	private final JavaMailSender javaMailSender;

	public void sendTempPassword(UserDto userDto) {
		EmailDto emailDto = EmailDto.builder()
				.emailId(userDto.getEmailId())
				.title("[Mafia in SNS Group] 임시 비밀번호 발급")
				.content("임시 비밀번호는 " + userDto.getEmailPassword() + " 입니다.").build();
		send(emailDto);
	}
	
	public void send(EmailDto emailDto) {
		SimpleMailMessage message = new SimpleMailMessage();
		message.setTo(emailDto.getEmailId());
		message.setFrom(fromAddress);
		message.setSubject(emailDto.getTitle());
		message.setText(emailDto.getContent());
		javaMailSender.send(message);
	}

}
