package com.ssafy.msg.user.controller;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import com.ssafy.msg.user.model.entity.RefreshTokenEntity;
import com.ssafy.msg.user.model.repo.RefreshTokenRepository;
import com.ssafy.msg.user.model.dto.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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
import org.springframework.web.multipart.MultipartFile;

import com.ssafy.msg.article.util.S3Util;
import com.ssafy.msg.user.exception.FollowException;
import com.ssafy.msg.user.exception.IdentifierException;
import com.ssafy.msg.user.exception.TokenInvalidException;
import com.ssafy.msg.user.exception.UserDuplicateException;
import com.ssafy.msg.user.exception.UserNotFoundException;
import com.ssafy.msg.user.model.service.UserService;
import com.ssafy.msg.user.util.JwtUtil;
import com.ssafy.msg.user.util.Oauth2Util;
import com.ssafy.msg.user.util.PasswordUtil;

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
 * Swagger 설정 1. @Tag로 API를 그룹화 (회원 CRUD API를 User로 그룹화, 게시물 CRUD API를 Article로
 * 그룹화 등)
 */
@Tag(name = "User", description = "회원 관련 API")
public class UserController {

	@Value("${header.authorization}")
	private String authorization;

	private final JwtUtil jwtUtil;

	private final PasswordUtil passwordUtil;

	private final Oauth2Util[] oauth2Utils;

	private final S3Util s3Util;

	private final UserService userService;

	private final RefreshTokenRepository refreshTokenRepository;

	/*
	 * 요청 처리 메소드 설정
	 * 1. @GetMapping, @PostMapping, @PatchMapping, @PutMapping, @DeleteMapping으로
	 * HTTP 요청 메소드와 URI 매핑
	 */
	@GetMapping("/sign-in/oauth2/{provider}")

	/*
	 * Swagger 설정 1. @Operation으로 API 요약과 설명 작성 2. @ApiResponses로 ApiResponse 그룹화
	 * 3. @ApiResponse로 HTTP 응답 코드, 설명, 응답 DTO 작성 4. 데이터를 DTO로 받는 것이 아니라 URL의
	 * PathVariable이나 RequestParam으로 받는 경우 @Parameter로 설명 작성
	 */
	@Operation(summary = "소셜 로그인", description = "기존 회원일 경우 로그인, 신규 회원일 경우 회원가입 후 로그인")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "로그인 성공", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = TokenDto.class)) }),
			@ApiResponse(responseCode = "400", description = "로그인 실패", content = @Content) })
	public ResponseEntity<?> signInWithOauth2(
			@Parameter(description = "Oauth2 제공자 종류 (google/naver/kakao)") @PathVariable("provider") String provider,
			@Parameter(description = "Oauth2 제공자로부터 액세스 토큰을 발급받을 때 사용하는 인가 코드") @RequestParam("code") String code) {
		/*
		 * 1. 메소드 시작 시 로그 출력 2. 받은 파라미터 출력 (Receive code, Receive provider)
		 */
		log.info("signInWithOauth2() -> Start");
		log.info("signInWithOauth2() -> Receive provider : {}", provider);
		log.info("signInWithOauth2() -> Receive code : {}", code);

		/*
		 * 1. 에러 발생할 수 있을 때 try-catch-finally 구문 사용 2. 중간중간 로그 출력은 하면 좋지만 너무 많으면 가독성
		 * 떨어지므로 적당히 하는 것이 좋을 듯함 (애매하면 그냥 처음과 끝에만 출력) 3. 에러 처리 시에는 중요도에 따라 log.warn,
		 * log.error로 나눠서 로그 출력
		 */
		try {
			Oauth2Util oauth2Util = Arrays.stream(oauth2Utils)
					.filter(oauth2 -> oauth2.getClass().getSimpleName().toLowerCase().contains(provider)).findFirst()
					.orElse(null);

			String oauth2AccessToken = oauth2Util.getAccessToken(code);
			log.info("signInWithOauth2() -> Receive oauth2AccessToken : {}", oauth2AccessToken);
			Oauth2Dto oauth2Dto = oauth2Util.getUserInfo(oauth2AccessToken);
			log.info("signInWithOauth2() -> Receive oauth2Dto : {}", oauth2Dto);

			UserDto userDto = userService.findUserByEmailId(oauth2Dto.getEmailId());
			
			boolean flag = false;
			if (userDto == null) {
				userDto = UserDto.builder().nickname(oauth2Dto.getNickname()).emailId(oauth2Dto.getEmailId())
						.provider(provider).build();
				flag = true;			
			} else {
				if (!provider.equals(userDto.getProvider())) {
					throw new UserDuplicateException();
				}
			}
			
			String accessToken = jwtUtil.createAccessToken(userDto.getId());
			log.info("signInWithOauth2() -> Create accessToken : {}", accessToken);
//			String refreshToken = jwtUtil.createRefreshToken(userDto.getId());
			String refreshToken = UUID.randomUUID().toString();
			log.info("signInWithOauth2() -> Create refreshToken : {}", refreshToken);
//			userDto.setRefreshToken(refreshToken);
			
			if (flag) {
				userService.signUpWithOauth2(userDto);
			} else {
				userService.signInWithOauth2(userDto);
			}

			RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().userId(userDto.getId()).refreshToken(refreshToken).build();
			refreshTokenRepository.save(refreshTokenEntity);

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
			if (userService.findUserByEmailId(signUpDto.getEmailId()) != null) {
				throw new UserDuplicateException();
			}
			
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
			String accessToken = jwtUtil.createAccessToken(userDto.getId());
			log.info("signIn() -> Create accessToken : {}", accessToken);
//			String refreshToken = jwtUtil.createRefreshToken(userDto.getId());
			String refreshToken = UUID.randomUUID().toString();
			log.info("signIn() -> Create refreshToken : {}", refreshToken);

//			userDto.setRefreshToken(refreshToken);
			userService.signIn(signInDto, userDto);

			RefreshTokenEntity refreshTokenEntity = RefreshTokenEntity.builder().userId(userDto.getId()).refreshToken(refreshToken).build();
			refreshTokenRepository.save(refreshTokenEntity);

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
	@GetMapping("/refresh")
	public ResponseEntity<?> reissueAccessToken(HttpServletRequest request) {
		log.info("reissueAccessToken() -> Start");

		String header = request.getHeader(authorization);
		if (header == null || !header.startsWith("Bearer ")) {
			throw new TokenInvalidException();
		}
		String refreshToken = header.replace("Bearer ", "");

//		jwtUtil.verify(refreshToken, "refresh-token");
//		int id = jwtUtil.getId(refreshToken);
//
//		UserDto userDto = null;
//		try {
//			userDto = userService.findUserById(id);
//		} catch (Exception e) {
//			throw new UserNotFoundException();
//		}
//
//		String savedRefreshToken = userDto.getRefreshToken();
//		if (!refreshToken.equals(savedRefreshToken)) {
//			throw new TokenInvalidException();
//		}

		RefreshTokenEntity refreshTokenEntity = refreshTokenRepository.findById(refreshToken).orElse(null);
		if (refreshTokenEntity == null) {
			throw new TokenInvalidException();
		}

		String accessToken = jwtUtil.createAccessToken(refreshTokenEntity.getUserId());
		AccessTokenDto accessTokenDto = AccessTokenDto.builder().accessToken(accessToken).build();

		log.info("reissueAccessToken() -> Success");
		log.info("reissueAccessToken() -> End");
		return new ResponseEntity<>(accessTokenDto, HttpStatus.CREATED);
	}

	@Operation(summary = "본인정보 조회", description = "액세스 토큰으로 회원정보 조회")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "회원정보 조회 성공", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDto.class)) }),
			@ApiResponse(responseCode = "404", description = "회원정보 조회 실패", content = @Content) })
	@GetMapping("/info")
	public ResponseEntity<?> getUserInfo(HttpServletRequest request) {
		log.info("getUserInfo() -> Start");

		int id = (int) request.getAttribute("id");

		UserInfoDto userInfoDto = null;
		try {
			userInfoDto = userService.getUserInfo(id);
			System.out.println(userInfoDto);
			log.info("getUserInfo() -> Success");
			return new ResponseEntity<>(userInfoDto, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getUserInfo() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("getUserInfo() -> End");
		}
	}

	@Operation(summary = "타인정보 조회", description = "액세스 토큰으로 타인정보 조회")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "200", description = "타인정보 조회 성공", content = {
					@Content(mediaType = "application/json", schema = @Schema(implementation = UserInfoDto.class)) }),
			@ApiResponse(responseCode = "404", description = "타인정보 조회 실패", content = @Content) })
	@GetMapping("/info/{id}")
	public ResponseEntity<?> getOtherUserInfo(HttpServletRequest request, @PathVariable("id") int targetId) {
		log.info("getOtherUserInfo() -> Start");

		int id = (int) request.getAttribute("id");

		TargetUserInfoDto targetUserInfoDto = null;
		try {
			targetUserInfoDto = userService.getTargetUserInfo(targetId, id);
			log.info("getOtherUserInfo() -> Success");
			return new ResponseEntity<>(targetUserInfoDto, HttpStatus.OK);
		} catch (Exception e) {
			log.error("getOtherUserInfo() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("getOtherUserInfo() -> End");
		}
	}


	@Operation(summary = "로그아웃", description = "액세스 토큰으로 로그아웃")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "로그아웃 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "로그아웃 실패", content = @Content) })
	@GetMapping("/sign-out")
	public ResponseEntity<?> signOut(HttpServletRequest request) {
		log.info("signOut() -> Start");

		String header = request.getHeader(authorization);
		if (header == null || !header.startsWith("Bearer ")) {
			throw new TokenInvalidException();
		}
		String refreshToken = header.replace("Bearer ", "");

		try {
//			userService.signOut(id);
			refreshTokenRepository.deleteById(refreshToken);
			log.info("signOut() -> Success");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("signOut() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("signOut() -> End");
		}
	}

	@Operation(summary = "회원탈퇴", description = "액세스 토큰으로 회원탈퇴")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "회원탈퇴 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "회원탈퇴 실패", content = @Content) })
	@DeleteMapping
	public ResponseEntity<?> withdraw(HttpServletRequest request) {
		log.info("withdraw() -> Start");

		int id = (int) request.getAttribute("id");

		try {
			userService.withdraw(id);
			log.info("withdraw() -> Success");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("withdraw() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("withdraw() -> End");
		}
	}

	@Operation(summary = "닉네임 수정", description = "액세스 토큰으로 닉네임 수정")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "닉네임 수정 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "닉네임 수정 실패", content = @Content) })
	@PatchMapping("/nickname")
	public ResponseEntity<?> updateNickname(HttpServletRequest request, @RequestBody NicknameDto nicknameDto) {
		log.info("updateNickname() -> Start");
		log.info("updateNickname() -> Receive updateDto : {}", nicknameDto);

		int id = (int) request.getAttribute("id");
		UserDto userDto = UserDto.builder().id(id).nickname(nicknameDto.getNickname()).build();

		try {
			userService.updateNickname(userDto);
			log.info("updateNickname() -> Success");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("updateNickname() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("updateNickname() -> End");
		}
	}

	@Operation(summary = "식별자 수정", description = "액세스 토큰으로 식별자 수정")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "식별자 수정 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "식별자 수정 실패", content = @Content) })
	@PatchMapping("/identifier")
	public ResponseEntity<?> updateIdentifier(HttpServletRequest request, @RequestBody IdentifierDto identifierDto) {
		log.info("updateIdentifier() -> Start");
		log.info("updateIdentifier() -> Receive updateDto : {}", identifierDto);

		int id = (int) request.getAttribute("id");

		UserDto userDto = null;
		try {
			userDto = userService.findUserById(id);
		} catch (Exception e) {
			log.error("updateIdentifier() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		if (userDto.getFlagIdentifier() == 1)
			throw new IdentifierException();

		try {
			userDto.setIdentifier(identifierDto.getIdentifier());
			userDto.setFlagIdentifier(1);
			userService.updateIdentifier(userDto);
			log.info("updateIdentifier() -> Success");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("updateIdentifier() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("updateIdentifier() -> End");
		}
	}

	@Operation(summary = "비밀번호 수정", description = "액세스 토큰과 현재 비밀번호로 비밀번호 수정")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "비밀번호 수정 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "비밀번호 수정 실패", content = @Content) })
	@PatchMapping("/password")
	public ResponseEntity<?> updatePassword(HttpServletRequest request,
			@RequestBody UpdatePasswordDto updatePasswordDto) {
		log.info("updatePassword() -> Start");
		log.info("updatePassword() -> Receive updatePasswordDto : {}", updatePasswordDto);

		int id = (int) request.getAttribute("id");

		UserDto userDto = null;
		try {
			userDto = userService.findUserById(id);
			userService.updatePassword(updatePasswordDto, userDto);
			log.info("updatePassword() -> Success");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("updatePassword() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("updatePassword() -> End");
		}
	}

	@Operation(summary = "프로필 이미지 수정", description = "액세스 토큰으로 프로필 이미지 수정")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "프로필 이미지 수정 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "프로필 이미지 수정 실패", content = @Content) })
	@PatchMapping(value = "/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<?> updateImage(HttpServletRequest request, @RequestParam("image") MultipartFile imageFile) {
		log.info("updateImage() -> Start");
		log.info("updateImage() -> Receive imageFile : {}", imageFile);

		int id = (int) request.getAttribute("id");
		try {
			String imageUuid = s3Util.saveFile(imageFile);
			log.info("updateImage() -> Receive imageUuid : {}", imageUuid);
			String imageUrl = s3Util.getUrl(imageUuid);
			log.info("updateImage() -> Receive imageUrl : {}", imageUrl);

			ProfileImageDto profileImageDto = ProfileImageDto.builder().imageUuid(imageUuid).imageUrl(imageUrl)
					.id(id).build();
			userService.updateImage(profileImageDto);
			log.info("updateImage() -> Success");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("updateImage() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("updateImage() -> End");
		}
	}

	@Operation(summary = "임시 비밀번호 발송", description = "기존 가입된 이메일에 임시 비밀번호 발송")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "임시 비밀번호 발송 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "임시 비밀번호 발송 실패", content = @Content) })
	@PostMapping("/password/reset")
	public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordDto resetPasswordDto) {
		log.info("resetPassword() -> Start");
		log.info("resetPassword() -> Receive getId : {}", resetPasswordDto.getEmailId());

		try {
			UserDto userDto = userService.findUserByEmailId(resetPasswordDto.getEmailId());
			if (userDto == null || userDto.getProvider() != null) {
				throw new UserNotFoundException();
			}

			String randomPassword = passwordUtil.generateRandomPassword();
			log.info("resetPassword() -> Create randomPassword : {}", randomPassword);

			userDto.setEmailPassword(randomPassword);

			userService.resetPassword(userDto, randomPassword);
			log.info("resetPassword() -> Success");

			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("resetPassword() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("resetPassword() -> End");
		}
	}

	@Operation(summary = "회원 팔로우/취소", description = "액세스 토큰과 대상 이메일 아이디로 회원 팔로우/취소")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "회원 팔로우/취소 성공", content = @Content),
			@ApiResponse(responseCode = "404", description = "회원 팔로우/취소 실패", content = @Content) })
	@PostMapping("/follow")
	public ResponseEntity<?> followOrUnfollow(HttpServletRequest request, @RequestBody FollowDto followDto) {
		log.info("followOrUnfollow() -> Start");
		log.info("followOrUnfollow() -> Receive followDto : {}", followDto);

		int id = (int) request.getAttribute("id");
		if (id == followDto.getId()) {
			throw new FollowException();
		}

		try {
			FollowDetailDto followDetailDto = FollowDetailDto.builder().fromUserId(id)
					.toUserId(followDto.getId()).build();
			FollowFindDto followFindDto = userService.findFollow(followDetailDto);

			if (followFindDto == null) {
				userService.follow(followDetailDto);
				log.info("follow() -> Success");
			} else {
				userService.unfollow(followDetailDto);
				log.info("unfollow() -> Success");
			}

			log.info("followOrUnfollow() -> Success");
			return new ResponseEntity<>(HttpStatus.OK);
		} catch (Exception e) {
			log.error("followOrUnfollow() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("followOrUnfollow() -> End");
		}
	}

	@Operation(summary = "회원 검색"
			, description = "입력된 검색어로 일치되는 user nickname이나 email이 일치하는 user를 찾아 내가 팔로우 하는지 여부, 나를 팔로우 하는지 여부, userId 순서로 결과를 페이지네이션하여 보여줍니다.")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "회원 목록 조회 성공", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = SearchUsersResponseDto.class)) }),
			@ApiResponse(responseCode = "400", description = "회원 목록 조회 실패", content = @Content) })
	@GetMapping("/list")
	public ResponseEntity<?> searchUsers(HttpServletRequest request,
			 @Parameter(description = "키워드") @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
			 @Parameter(description = "Offset") @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
			 @Parameter(description = "페이지당 타겟 개수") @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit) {
		log.info("searchUsers() start");

		String currentUrl = request.getRequestURL().toString();
		Integer userId = (Integer) request.getAttribute("id");

		log.info("searchUsers() userId : {}", userId);
		log.info("searchUsers() keyword : {}", keyword);
		log.info("searchUsers() offset : {}", offset);
		log.info("searchUsers() limit : {}", limit);

		SearchUsersParamDto searchUsersParamDto = SearchUsersParamDto.builder()
				.userId(userId)
				.keyword(keyword)
				.limit(limit)
				.offset(offset)
				.build();

		try {
			List<SearchedUserDto> userList = userService.searchUsers(searchUsersParamDto);

			if(userList.isEmpty()) {
				return new ResponseEntity<>(HttpStatus.OK);
			}
			else {
				log.info("searchUsers() searched userList : {}", userList);
				int nextOffset = offset + limit;
				String nextUrl = currentUrl + "?keyword=" + keyword + "?offset=" + nextOffset + "?limit=" + limit;
				SearchUsersResponseDto result = SearchUsersResponseDto.builder()
						.searchResult(userList)
						.nextUrl(nextUrl)
						.build();

				return new ResponseEntity<>(result ,HttpStatus.OK);
			}

		} catch (Exception e) {
			log.error("searchUsers() -> Exception : {}", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("searchUsers() -> end");
		}
	}

	@Operation(summary = "회원 팔로워(to)/팔로잉(from) 목록 조회", description = "액세스 토큰으로 회원 팔로워(to)/팔로잉(from) 목록 조회")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "회원 팔로워(to)/팔로잉(from) 목록 조회 성공", content = {
			@Content(mediaType = "application/json", schema = @Schema(implementation = FollowUserDto[].class)) }),
			@ApiResponse(responseCode = "404", description = "회원 팔로우(to)/팔로잉(from) 목록 조회 실패", content = @Content) })
	@GetMapping("/follow")
	public ResponseEntity<?> getFollowList(HttpServletRequest request,
	        @Parameter(description = "키워드") @RequestParam(value = "keyword", required = false, defaultValue = "") String keyword,
	        @Parameter(description = "마지막으로 로딩한 타겟") @RequestParam(value = "offset", required = false, defaultValue = "0") Integer offset,
	        @Parameter(description = "페이지당 타겟 개수") @RequestParam(value = "limit", required = false, defaultValue = "10") Integer limit,
	        @Parameter(description = "팔로워(to)/팔로잉(from) 여부") @RequestParam(value = "type", required = true) String type) {
	    log.info("getFollowList() -> Start");
	    log.info("getFollowList() -> Receive keyword : {}", keyword);
	    log.info("getFollowList() -> Receive offset : {}", offset);
	    log.info("getFollowList() -> Receive limit : {}", limit);
	    log.info("getFollowList() -> Receive type (to/from) : {}", type);

	    // Use lastItemId to determine the starting point for the next batch
	    FollowParamDto followParamDto = FollowParamDto.builder()
	            .id((int) request.getAttribute("id"))
	            .keyword(keyword)
	            .offset(offset)
	            .limit(limit)
	            .type(type)
	            .build();

	    try {
	        List<FollowUserDto> followList = userService.getFollowList(followParamDto);
			// 더이상 보여줄 리스트가 없으면 null 리턴
			if (followList.isEmpty()) {
				log.info("(UserController) 팔로우리스트 null 반환");
				return null;
			}

	        int lastId = followList.get(followList.size() - 1).getId();
	        
	        String currentUrl = request.getRequestURL().toString();
	        String nextUrl = currentUrl + "?offset=" + lastId + "&limit=" + limit+ "&type=" + type + "&keyword=" + keyword;

	        FollowResponseDto followResponseDto = FollowResponseDto.builder()
	                .followUserList(followList)
	                .nextUrl(nextUrl)
	                .build();

	        log.info("getFollowList() -> Success");
	        return new ResponseEntity<>(followResponseDto, HttpStatus.OK);
	    } catch (Exception e) {
	        log.error("getFollowList() -> Exception : {}", e);
	        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
	    } finally {
	        log.info("getFollowList() -> End");
	    }
	}

	// 자기소개 작성하기
	@PatchMapping(value = "/bio")
	@Operation(summary = "자기소개 작성", description = "자기소개 작성하기")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "자기소개 작성 성공" , content ={
					@Content(mediaType = "application/json", schema = @Schema(implementation = BioDto.class)) }),
			@ApiResponse(responseCode = "400", description = "자기소개 작성 실패", content = @Content) })
	public ResponseEntity<?> updateBio(HttpServletRequest request, @RequestBody BioDto bioDto) {

		bioDto.setUserId((int) request.getAttribute("id"));

		try {
			userService.updateBio(bioDto);
			return new ResponseEntity<>(HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("(UserController) 자기소개 작성 실패", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("(UserController) 끝");
		}
	}

	@GetMapping(value = "/bio")
	@Operation(summary = "자기소개 작성", description = "자기소개 작성하기")
	@ApiResponses(value = {
			@ApiResponse(responseCode = "201", description = "자기소개 작성 성공" , content ={
					@Content(mediaType = "application/json", schema = @Schema(implementation = BioDto.class)) }),
			@ApiResponse(responseCode = "400", description = "자기소개 작성 실패", content = @Content) })
	public ResponseEntity<?> getBio(@RequestParam("bio") String bio,
									@RequestParam("userId") int userId) {

		BioDto bioDto = BioDto.builder()
				.userId(userId)
				.bio(bio)
				.build();

		try {
			return new ResponseEntity<>(userService.getBio(bioDto), HttpStatus.CREATED);
		} catch (Exception e) {
			log.error("(UserController) 자기소개 작성 실패", e);
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		} finally {
			log.info("(UserController) 끝");
		}
	}

}
