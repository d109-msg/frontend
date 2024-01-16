package com.ssafy.msg.user.util;

import java.util.Date;
import java.util.Map;

import javax.crypto.SecretKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ssafy.msg.user.exception.TokenExpiredException;
import com.ssafy.msg.user.exception.TokenInvalidException;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class JwtUtil {
	
	@Value("${spring.application.name}")
	private String issuer;
		
    @Value("${service.jwt.access-expiration}")
    private Long accessExpiration;
	
    @Value("${service.jwt.refresh-expiration}")
    private Long refreshExpiration;
	
    private final SecretKey secretKey;
    
	public JwtUtil(@Value("${service.jwt.secret-key}") String secretKey) {
		this.secretKey = Keys.hmacShaKeyFor(Decoders.BASE64URL.decode(secretKey));
	}
    
	public String createAccessToken(String emailId) {
		return create(emailId, "access-token", accessExpiration);
	}
	
	public String createRefreshToken(String emailId) {
		return create(emailId, "refresh-token", refreshExpiration);
	}

	private String create(String emailId, String subject, long expiration) {
		return Jwts.builder()
				.header().add("typ", "JWT")
				.and()
				.subject(subject)
				.issuer(issuer)
				.claim("emailId", emailId)
				.issuedAt(new Date())
				.expiration(new Date(System.currentTimeMillis() + expiration))
				.signWith(secretKey)
				.compact();
	}
	
	public boolean verify(String token, String type) {
		try {
			log.info("verify() -> Start");
			Jws<Claims> jwsClaims = Jwts.parser()/*
				 * Copyright 2024 the original author or authors.
				 *
				 * Licensed under the Apache License, Version 2.0 (the "License");
				 * you may not use this file except in compliance with the License.
				 * You may obtain a copy of the License at
				 *
				 *      https://www.apache.org/licenses/LICENSE-2.0
				 *
				 * Unless required by applicable law or agreed to in writing, software
				 * distributed under the License is distributed on an "AS IS" BASIS,
				 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
				 * See the License for the specific language governing permissions and
				 * limitations under the License.
				 */
					.verifyWith(secretKey)
					.build()
					.parseSignedClaims(token);
			Map<String, Object> value = jwsClaims.getPayload();
			log.info("verify() -> Process value : {}", value);
			String sub = (String) value.get("sub");
			if (type.equals(sub)) {
				log.info("verify() -> Success");
				return true;
			} else {
				throw new TokenInvalidException();
			}
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		} catch (Exception e) {
			throw new TokenInvalidException();
		} finally {
			log.info("verify() -> End");
		}
	}
	
	public String getEmailId(String token) {
		log.info("getEmailId() -> Start");
		try {
			Jws<Claims> jwsClaims = Jwts.parser()/*
					 * Copyright 2024 the original author or authors.
					 *
					 * Licensed under the Apache License, Version 2.0 (the "License");
					 * you may not use this file except in compliance with the License.
					 * You may obtain a copy of the License at
					 *
					 *      https://www.apache.org/licenses/LICENSE-2.0
					 *
					 * Unless required by applicable law or agreed to in writing, software
					 * distributed under the License is distributed on an "AS IS" BASIS,
					 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
					 * See the License for the specific language governing permissions and
					 * limitations under the License.
					 */
						.verifyWith(secretKey)
						.build()
						.parseSignedClaims(token);
			Map<String, Object> value = jwsClaims.getPayload();
			String emailId = value.get("emailId").toString();
			log.info("getEmailId() -> Success");
			return emailId;
		} catch (ExpiredJwtException e) {
			throw new TokenExpiredException();
		} catch (Exception e) {
			throw new TokenInvalidException();
		} finally {
			log.info("getEmailId() -> End");
		}
	}
}
