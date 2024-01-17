# Mafia in Social Group (MSG)

## 1. Git 규칙

### 1.1. Commit

- feat: 새로운 기능 추가  
  fix: 버그 수정  
  docs: 문서 수정  
  test: 테스트 코드 추가  
  style: 코드 포맷팅  
  refactor: 코드 리팩토링  
  chore: 빌드 설정 수정
- 타입은 영어 소문자로 작성하고 콜론을 바로 뒤에 붙이고 한 칸 띄운 뒤 제목을 한글로 작성한다.
- 가능하다면 제목뿐만 아니라 본문도 작성한다.
- 마지막에 Jira 이슈 번호를 소괄호 안에 작성한다.

```
# 제목만 적는 예시
$ git commit -m "feat: 로그인 화면 구현 (S10P12D109-00)"
```

### 1.2. Branch

- main: 제품으로 출시될 수 있는 안정된 버전의 브랜치  
  hotfix: 출시 버전에서 발생한 버그를 수정하는 브랜치  
  dev: 다음 출시 버전을 개발하는 브랜치  
  feat: 기능을 개발하는 브랜치
- 프론트엔드와 백엔드가 하나의 리포지토리에서 작업해야 하므로 브랜치 앞에 fe 혹은 be를 붙이고 슬래시로 구분한다.
- main 브랜치에서 fe/dev 브랜치와 be/dev 브랜치로 나눠서 관리한다.
- feat 브랜치는 구체적으로 어떤 기능에 대한 브랜치인지 슬래시 뒤에 명시한다.
- 브랜치명에 기능을 명시할 때 최대한 한 단어로 간략하게 작성하되, 불가능하다면 kebap case로 작성한다.
- fe/dev 브랜치에서 로그인 관련 기능을 개발하는 경우 fe/dev에서 fe/feat/sign-in 브랜치를 생성하여 진행한다.
- be/dev 브랜치에서 인증 관련 기능을 개발하는 경우 be/dev에서 be/feat/auth 브랜치를 생성하여 진행한다.
- 기능 개발이 완료되면 merge 후 feat 브랜치는 삭제한다.
- main, dev 브랜치에는 바로 merge, push를 하지 않고 아래 작업 과정을 따라 진행한다.

### 1.3. Merge

- Title은 아래와 같이 작업 내용을 요약하여 간략하게 적고 이슈 번호가 있다면 마지막 소괄호 안에 작성한다.

```
소셜 로그인 화면 구현 (S10P12D109-01)
```

- Description은 아래 형식에 맞춰 작성한다.

```
# 해결 문제
- 소셜 로그인 관련 로그인 화면을 구현했습니다.

# 해결 방법
- 소셜 로그인 과정 중에 인가 코드를 받을 콜백 주소를 생성했습니다.
- 그 이후에 백엔드에 인가 코드를 보내 발급받은 액세스 토큰과 리프레시 토큰을 상태 관리 라이브러리를 이용해 저장했습니다.
- 액세스 토큰이 만료되면 리프레시 토큰을 보내 액세스 토큰을 재발급받도록 했습니다.
- 로그인 상태를 액세스 토큰을 가지고 있는 상태로 정의했습니다.
- 서버로부터 리프레시 토큰까지 만료되었다는 응답을 받으면 저장되어 있던 액세스 토큰과 리프레시 토큰을 삭제하고 자동 로그아웃 처리를 했습니다.

# 첨부
- (참고할 사진이나 자료가 있다면 첨부)
```

- Assignee에서 담당자는 MR 작성자 본인으로 작성한다.
- Reviewer는 해당 내용을 이전에 작업한 사람이나 작업한 내용에 영향이 있는 사람을 지정한다.
- Label은 승인, 대기, 거절 중 하나에 해당되는데 처음에는 대기 상태에서 리뷰가 끝나면 승인 혹은 거절로 바꾼다.

### 1.4. 작업 과정

1. 프로젝트 clone

```
$ git clone https://lab.ssafy.com/s10-webmobile2-sub2/S10P12D109.git
```

2. dev 브랜치로 이동

```
# frontend
$ git checkout fe/dev

# backend
$ git checkout be/dev
```

3. 개발을 위한 feat 브랜치 생성

```
# frontend
$ git branch fe/dev/sign-in

# backend
$ git branch be/dev/auth
```

4. 개발을 위한 feat 브랜치로 이동

```
# frontend
$ git checkout fe/dev/sign-in

# backend
$ git checkout be/dev/auth
```

4. feat 브랜치에서 개발 및 커밋
5. 개발 완료 후 dev 브랜치 최신화

```
# frontend
$ git checkout fe/dev
$ git pull origin fe/dev

# backend
$ git checkout be/dev
$ git pull origin be/dev
```

6. 개발 완료한 feat 브랜치로 이동 후 dev 브랜치 merge

```
# frontend
$ git checkout fe/feat/sign-in
$ git merge fe/dev

# backend
$ git checkout be/feat/auth
$ git merge be/dev
```

7. conflict 해결 후 merge request 생성

8. 코드 리뷰

9. MR 완료 및 작업 브랜치 삭제

10. Jira 이슈 완료 처리
