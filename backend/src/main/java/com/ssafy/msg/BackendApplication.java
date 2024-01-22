package com.ssafy.msg;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

import com.ssafy.msg.test.model.dto.TestDto;
import com.ssafy.msg.test.model.repo.TestRepository;

/*
 * Git Commit Message Convention
 * https://udacity.github.io/git-styleguide/
 * feat: 새로운 기능 추가
 * fix: 버그 수정
 * docs: 문서 수정
 * style: 코드 포맷팅, 세미콜론 누락, 코드 변경이 없는 경우
 * refactor: 코드 리팩토링
 * test: 테스트 코드, 리팩토링 테스트 코드 추가
 * chore: 빌드 업무 수정, 패키지 매니저 수정
 */

/*
 * Swagger Annotation
 * https://springdoc.org/
 * @Tag: API에 대한 그룹화 (주로 Controller 단위)
 * @Operation: API에 대한 설명 (주로 Method 단위)
 * @Parameter: API 파라미터에 대한 설명 (주로 Parameter 단위)
 * @Schema: Model에 대한 설명 (주로 DTO 단위)
 * @ApiResponse: API 응답에 대한 설명 (주로 Response 단위)
 * @ApiResponses: 여러 종류의 @ApiResponse를 그룹화하여 설명할 때 사용 (주로 Method 단위; 하나의 Operation 즉 하나의 Method에서 성공, 실패 등 여러 종류의 응답이 있을 때)
 */

/*
 * Spring Boot Annotation
 * https://docs.spring.io/spring-framework/docs/current/javadoc-api/overview-summary.html
 * @Bean: 스프링 컨테이너에 Bean을 등록할 때 사용 (외부 라이브러리를 Bean으로 등록할 때 사용)
 * @Component: 스프링 컨테이너에 Bean을 등록할 때 사용 (직접 작성한 클래스를 Bean으로 등록할 때 사용)
 * @Controller: 스프링 MVC에서 Controller 역할을 할 때 사용
 * @RestController: 스프링 MVC에서 Controller 역할을 할 때 사용 (Controller + ResponseBody; REST API에서 사용)
 * @Service: 스프링 MVC에서 Service 역할을 할 때 사용
 * @Mapper: MyBatis에서 Mapper 역할을 할 때 사용
 * @RequestMapping: 스프링 MVC에서 Request URL을 매핑할 때 사용
 * @RequestParam: 스프링 MVC에서 Request Parameter를 매핑할 때 사용
 * @PathVariable: 스프링 MVC에서 URL Path Variable을 매핑할 때 사용
 * @RequestBody: 스프링 MVC에서 Request Body를 매핑할 때 사용
 * @ResponseBody: 스프링 MVC에서 Response Body를 매핑할 때 사용 (RestController에서는 별도로 사용하지 않아도 됨)
 * @ResponseEntity: 스프링 MVC에서 Response Entity를 매핑할 때 사용 (HttpStatus, HttpHeader, HttpBody를 포함)
 * @Configuration: 클래스에 대한 설정 파일임을 명시할 때 사용 (@Configration 클래스 내에서 @Bean을 사용하여 Bean을 직접 등록할 수 있음)
 */

/* 
 * Lombok Annotation (Lombok은 자바 코드를 자동으로 생성해주는 라이브러리)
 * https://projectlombok.org/features/all
 * @Slf4j: Logger를 사용할 때 사용 (로그 출력에 사용)
 * @Getter: Getter를 사용할 때 사용
 * @Setter: Setter를 사용할 때 사용
 * @Data: Getter, Setter, ToString, EqualsAndHashCode를 사용할 때 사용
 * @Builder: Builder를 사용할 때 사용 (빌더 패턴으로 객체 생성 가능)
 * @AllArgsConstructor: 모든 필드를 파라미터로 받는 생성자를 사용할 때 사용
 * @NoArgsConstructor: 파라미터가 없는 생성자를 사용할 때 사용
 * @RequiredArgsConstructor: final이나 @NonNull인 필드만 파라미터로 받는 생성자를 사용할 때 사용
 */

/*
 * Mybatis xml Mapper (현재 DatabaseConfig에서 DB의 snake_case를 자바의 camelCase로 변환하는 설정을 해놔서 resultMap을 사용하지 않아도 됨)
 * https://mybatis.org/mybatis-3/ko/sqlmap-xml.html
 * select: 조회
 * insert: 삽입
 * update: 수정
 * delete: 삭제
 * resultMap: 조회 결과를 매핑할 때 사용 (DTO 변수명과 DB 컬럼명이 다를 때 사용)
 * parameterType: 파라미터 타입을 명시할 때 사용
 * resultType: 결과 타입을 명시할 때 사용
 * include: 다른 xml 파일을 포함할 때 사용
 * selectKey: 삽입 시 자동으로 생성되는 id를 조회할 때 사용
 * if: 단일 조건문에서 사용
 * choose, when, otherwise: 다중 조건문에서 사용
 * sql: 재사용할 쿼리를 정의할 때 사용
 * trim: 쿼리 앞뒤에 붙이거나 제거할 문자를 정의할 때 사용
 */

/* DB Table Naming Convention
 * 테이블명은 복수형 소문자 snake_case로 작성
 * 컬럼명은 단수형 소문자 sanke_case로 작성 (FK인 컬럼명이 아닌 이상 별도로 테이블명을 컬럼명의 prefix로 사용하지 않음)
 * N:M 관계의 이름은 {테이블명1}_{테이블명2}이 아니라 따로 작성 (예를 들어 users 테이블과 articles 테이블 사이에는 likes 테이블이 있을 수 있음)
 * FK는 {테이블명}_{컬럼명}으로 작성
 * N:M 관계에서만 관계가 명시적으로 테이블로 나타남
 */

@EnableAutoConfiguration(exclude = { DataSourceAutoConfiguration.class })
@SpringBootApplication
public class BackendApplication implements CommandLineRunner {

	@Autowired
	private TestRepository repository;

	public static void main(String[] args) {
		SpringApplication.run(BackendApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {

		repository.deleteAll();

		repository.save(TestDto.builder().firstName("Alice").lastName("Smith").build());
		repository.save(TestDto.builder().firstName("Bob").lastName("Smith").build());
		
		System.out.println("TestDtos found with findAll():");
		System.out.println("-------------------------------");
		for (TestDto testDto : repository.findAll()) {
			System.out.println(testDto);
		}
		System.out.println();

		System.out.println("TestDto found with findByFirstName('Alice'):");
		System.out.println("--------------------------------");
		System.out.println(repository.findByFirstName("Alice"));

		System.out.println("TestDtos found with findByLastName('Smith'):");
		System.out.println("--------------------------------");
		for (TestDto testDto : repository.findByLastName("Smith")) {
			System.out.println(testDto);
		}

	}
}
