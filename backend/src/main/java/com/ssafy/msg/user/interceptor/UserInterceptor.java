package com.ssafy.msg.user.interceptor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.msg.user.exception.TokenInvalidException;
import com.ssafy.msg.user.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
@RequiredArgsConstructor
public class UserInterceptor implements HandlerInterceptor {
	
	@Value("${header.authorization}")
	private String authorization;
	
	private final JwtUtil jwtUtil;
	
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		
		log.info("preHandle() -> Start");
		
		String header = request.getHeader(authorization);
		if (header == null || !header.startsWith("Bearer ")) {
			log.info("preHandle() -> End");
			throw new TokenInvalidException();
		}
		
		String accessToken = header.replace("Bearer ", "");
		jwtUtil.verify(accessToken, "access-token");
		
		int id = jwtUtil.getId(accessToken);
		request.setAttribute("id", id);
		log.info("preHandle() -> End");
		return true;
	}
}
