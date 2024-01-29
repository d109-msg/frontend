package com.ssafy.msg.user.interceptor;


import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.ssafy.msg.user.exception.TokenInvalidException;
import com.ssafy.msg.user.util.JwtUtil;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import java.util.Enumeration;

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
		StringBuilder requestDetails = new StringBuilder();
		requestDetails.append("Request URL: ").append(request.getRequestURL()).append("\n");
		requestDetails.append("Request Method: ").append(request.getMethod()).append("\n");
		requestDetails.append("Request Headers: \n");
		Enumeration<String> headerNames = request.getHeaderNames();
		while (headerNames.hasMoreElements()) {
			String headerName = headerNames.nextElement();
			requestDetails.append(headerName).append(": ").append(request.getHeader(headerName)).append("\n");
		}
		requestDetails.append("Request Parameters: \n");
		request.getParameterMap().forEach((param, values) -> {
			requestDetails.append(param).append(": ");
			for (String value : values) {
				requestDetails.append(value).append(", ");
			}
			requestDetails.append("\n");
		});
		requestDetails.append("Other Request Information: \n");
		requestDetails.append("Remote Host: ").append(request.getRemoteHost()).append("\n");
		requestDetails.append("Content Type: ").append(request.getContentType()).append("\n");
		requestDetails.append("Character Encoding: ").append(request.getCharacterEncoding()).append("\n");
		requestDetails.append("Remote Address: ").append(request.getRemoteAddr());
		System.out.println("======================= Request =======================");
		System.out.println(requestDetails);
		System.out.println("=======================================================");
		
		// CORS preflight 요청은 통과
		if (HttpMethod.OPTIONS.matches(request.getMethod())) {
		    return true;
		}
		
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
