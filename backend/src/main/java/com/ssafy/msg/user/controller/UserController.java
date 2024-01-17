package com.ssafy.msg.user.controller;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.ssafy.msg.user.exception.TokenInvalidException;
import com.ssafy.msg.user.exception.UserDuplicateException;
import com.ssafy.msg.user.exception.UserNotFoundException;
import com.ssafy.msg.user.model.dto.AccessTokenDto;
import com.ssafy.msg.user.model.dto.Oauth2Dto;
import com.ssafy.msg.user.model.dto.SignInDto;
import com.ssafy.msg.user.model.dto.SignUpDto;
import com.ssafy.msg.user.model.dto.TokenDto;
import com.ssafy.msg.user.model.dto.UpdateDto;
import com.ssafy.msg.user.model.dto.UserDto;
import com.ssafy.msg.user.model.dto.UserInfoDto;
import com.ssafy.msg.user.model.service.UserService;
import com.ssafy.msg.user.util.JwtUtil;
import com.ssafy.msg.user.util.Oauth2Util;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

/*
 * Controller 설정
 * 1. @RestController로 REST API 컨트롤러 등록
 * 2. @RequestMapping으로 URI 매핑
 * 3. 의존성 주입이 필요한 경우 @RequiredArgsConstructor로 생성자 주입 (UserController에서는 jwtUtil, oauth2Utils, userService에 대한 의존성 주입)
 * 4. @Slf4j로 Logger 사용 (log.info, log.error, log.error 등으로 로그 출력)
 */
@RestController
@RequestMapping("/user")
@RequiredArgsConstructor
@Slf4j

/* 
 * Swagger 설정
 * 1. @Tag로 API를 그룹화 (회원 CRUD API를 User로 그룹화, 게시물 CRUD API를 Article로 그룹화 등)
 */
@Tag(name="User", description="회원 API")
public class UserController {
	
	@Value("${header.authorization}")
	private String authorization;

	private final JwtUtil jwtUtil;

	private final Oauth2Util[] oauth2Utils;
	
	private final UserService userService;

	/*
	 * 요청 처리 메소드 설정
	 * 1. @GetMapping, @PostMapping, @PatchMapping, @PutMapping, @DeleteMapping으로 HTTP 요청 메소드와 URI 매핑
	 */
	@GetMapping("/sign-in/oauth2/{provider}")
	
	/*
	 * Swagger 설정
	 * 1. @Operation으로 API 요약과 설명 작성
	 * 2. @ApiResponses로 ApiResponse 그룹화
	 * 3. @ApiResponse로 HTTP 응답 코드, 설명, 응답 DTO 작성
	 * 4. 데이터를 DTO로 받는 것이 아니라 URL의 PathVariable이나 RequestParam으로 받는 경우 @Parameter로 설명 작성
	 */
	@Operation(summary = "소셜 로그인", description = "기존 회원일 경우 로그인, 신규 회원일 경우 회원가입 후 로그인")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "로그인 성공", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)) }),
			@ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content) })
	public ResponseEntity<?> signInWithOauth2(@Parameter(description = "Oauth2 제공자 종류 (google/naver/kakao)") @PathVariable("provider") String provider, @Parameter(description = "Oauth2 제공자로부터 액세스 토큰을 발급받을 때 사용하는 인가 코드") @RequestParam("code") String code) {
		/*
		 * 1. 메소드 시작 시 로그 출력
		 * 2. 받은 파라미터 출력 (Receive code, Receive provider)
		 */
		log.info("signInWithOauth2() -> Start");
		log.info("signInWithOauth2() -> Receive provider : {}", provider);
		log.info("signInWithOauth2() -> Receive code : {}", code);
		
		/*
		 * 1. 에러 발생할 수 있을 때 try-catch-finally 구문 사용
		 * 2. 중간중간 로그 출력은 하면 좋지만 너무 많으면 가독성 떨어지므로 적당히 하는 것이 좋을 듯함 (애매하면 그냥 처음과 끝에만 출력)
		 * 3. 에러 처리 시에는 중요도에 따라 log.warn, log.error로 나눠서 로그 출력
		 */
		try {
			Oauth2Util oauth2Util = Arrays.stream(oauth2Utils)
					.filter(oauth2 -> oauth2.getClass().getSimpleName().toLowerCase().contains(provider)).findFirst()
					.orElse(null);
			
			String oauth2AccessToken = oauth2Util.getAccessToken(code);
			log.info("signInWithOauth2() -> Receive oauth2AccessToken : {}", oauth2AccessToken);
			Oauth2Dto oauth2Dto = oauth2Util.getUserInfo(oauth2AccessToken);
			log.info("signInWithOauth2() -> Receive oauth2Dto : {}", oauth2Dto);

			String accessToken = jwtUtil.createAccessToken(oauth2Dto.getEmailId());
			log.info("signInWithOauth2() -> Create accessToken : {}", accessToken);
			String refreshToken = jwtUtil.createRefreshToken(oauth2Dto.getEmailId());
			log.info("signInWithOauth2() -> Create refreshToken : {}", refreshToken);

			UserDto userDto = userService.findUserByEmailId(oauth2Dto.getEmailId());
			
			oauth2Dto.setRefreshToken(refreshToken);
			if (userDto == null) {
				userService.signUpWithOauth2(oauth2Dto);
			} else {
				if (!provider.equals(userDto.getProvider())) {
					throw new UserDuplicateException();
				}
				userService.signInWithOauth2(oauth2Dto);
			}

			TokenDto tokenDto = TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
			HttpStatus httpStauts = HttpStatus.CREATED;
			log.info("signInWithOauth2() -> Success");
			return new ResponseEntity<>(tokenDto, httpStauts);
		} catch (Exception e) {
			log.error("signInWithOauth2() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("signInWithOauth2() -> End");
		}
	}
	
	@Operation(summary = "사이트 자체 회원가입", description = "이메일 아이디, 이메일 비밀번호, 닉네임으로 회원가입")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "회원가입 성공", content = @Content),
			@ApiResponse(responseCode = "400", description = "회원가입 실패", content = @Content) })
	@PostMapping("/sign-up")
	public ResponseEntity<?> signUp(@Valid @RequestBody SignUpDto signUpDto) {
		log.info("signUp() -> Start");
		log.info("signUp() -> Receive signUpDto : {}", signUpDto);

		try {
			userService.signUp(signUpDto);
			log.info("signUp() -> Success");
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("signUp() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("signUp() -> End");
		}
	}
	
	@Operation(summary = "사이트 자체 로그인", description = "이메일 아이디, 이메일 비밀번호로 로그인")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "로그인 성공", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)) }),
			@ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content) })
	@PostMapping("/sign-in")
	public ResponseEntity<?> signIn(@Valid @RequestBody SignInDto signInDto) {
		log.info("signIn() -> Start");
		log.info("signIn() -> Receive signInDto : {}", signInDto);

		UserDto userDto = null;
		try {
			userDto = userService.findUserByEmailId(signInDto.getEmailId());
		} catch (Exception e) {
			log.error("signIn() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}
		
		if (userDto == null) {
			throw new UserNotFoundException();
		}
		
		try {
			String accessToken = jwtUtil.createAccessToken(userDto.getEmailId());
			log.info("signIn() -> Create accessToken : {}", accessToken);
			String refreshToken = jwtUtil.createRefreshToken(userDto.getEmailId());
			log.info("signIn() -> Create refreshToken : {}", refreshToken);

			userDto.setRefreshToken(refreshToken);
			userService.signIn(signInDto, userDto);

			TokenDto tokenDto = TokenDto.builder().accessToken(accessToken).refreshToken(refreshToken).build();
			HttpStatus httpStauts = HttpStatus.CREATED;
			log.info("signIn() -> Success");
			return new ResponseEntity<>(tokenDto, httpStauts);
		} catch (Exception e) {
			log.error("signIn() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("signIn() -> End");
		}
	}
	
	@Operation(summary = "액세스 토큰 재발급", description = "리프레시 토큰으로 액세스 토큰 재발급")
	@ApiResponses(value = { @ApiResponse(responseCode = "201", description = "액세스 토큰 재발급 성공", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = AccessTokenDto.class)) }),
			@ApiResponse(responseCode = "400", description = "액세스 토큰 재발급 실패", content = @Content) })
	@GetMapping("/token")
	public ResponseEntity<?> reissueAccessToken(HttpServletRequest request) {
		String header = request.getHeader(authorization);
		if (header == null || !header.startsWith("Bearer ")) {
			throw new TokenInvalidException();
		}
		
		String refreshToken = header.replace("Bearer ", "");
		jwtUtil.verify(refreshToken, "refresh-token");
		
		String emailId = jwtUtil.getEmailId(refreshToken);
		
		UserDto userDto = null;
		try {
			userDto = userService.findUserByEmailId(emailId);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
		
		String savedRefreshToken = userDto.getRefreshToken();
		if (!refreshToken.equals(savedRefreshToken)) {
			throw new TokenInvalidException();
		}
		
		String accessToken = jwtUtil.createAccessToken(emailId);
		AccessTokenDto accessTokenDto = AccessTokenDto.builder().accessToken(accessToken).build();
		
		return new ResponseEntity<>(accessTokenDto, HttpStatus.CREATED);
	}
	
	@Operation(summary = "회원정보 조회", description = "액세스 토큰으로 회원정보 조회")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "회원정보 조회 성공", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDto.class)) }),
			@ApiResponse(responseCode = "404", description = "회원정보 조회 실패", content = @Content) })
	@GetMapping("/info")
	public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
		
		String emailId = (String) request.getAttribute("emailId");

		UserInfoDto userInfoDto = null;
		try {
			userInfoDto = userService.getUserInfo(emailId);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}

		return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
	}
	
	@Operation(summary = "로그아웃", description = "액세스 토큰으로 로그아웃")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "로그아웃 실패", content = @Content) })
	@GetMapping("/sign-out")
	public ResponseEntity<?> signOut(HttpServletRequest request) {
		
		String emailId = (String) request.getAttribute("emailId");
		
		try {
			userService.signOut(emailId);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}
		
		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Operation(summary = "회원탈퇴", description = "액세스 토큰으로 회원탈퇴")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "회원탈퇴 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "회원탈퇴 실패", content = @Content) })
	@DeleteMapping
	public ResponseEntity<?> withdraw(HttpServletRequest request) {

		String emailId = (String) request.getAttribute("emailId");

		try {
			userService.withdraw(emailId);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
	
	@Operation(summary = "회원정보 수정", description = "액세스 토큰으로 회원정보 수정")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "회원정보 수정 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "회원정보 수정 실패", content = @Content) })
	@PatchMapping
	public ResponseEntity<?> updateUserInfo(HttpServletRequest request, @RequestBody UpdateDto updateDto) {

		String emailId = (String) request.getAttribute("emailId");

		try {
			UserDto userDto = UserDto.builder().emailId(emailId).nickname(updateDto.getNickname()).build();
			userService.updateUserInfo(userDto);
		} catch (Exception e) {
			throw new UserNotFoundException();
		}

		return new ResponseEntity<>(HttpStatus.OK);
	}
}
