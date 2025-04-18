# 시큐리티 SSO 셀프 구현

> since 250413~

## 사용스택
- 스프링 부트 앱 2개 ver2.7
- 스프링 시큐리티
- Redis
- 스프링 레디스 세션

## 목적
1. 시큐리티 연습
2. SSO 직접 구현

## 구조
1. sso_main
- id/pw 로그인 구현 (커스텀로그인페이지)
- sso 서버로 구현 세션은 redis 에 저장
- client (다른서비스) 에서 요청이 들어오면, 기존 로그인이 안되어있을 경우 커스텀 로그인을 진행하고, 해당 로그인이 성공하면 파라미터로 넘어온 주소로 redirect

2. sso_client
- sso 로그인 진행, 로그인되어있지 않다면 (redis 세션없을때) 위 프로젝트로 로그인요청을 보내고 응답 성공시 성공 페이지로 이동
- ==로그인이 안되어있다면 sso_main 으로 로그인요청 보내고 성공시 돌려받는게 포인트==

## 오류먹은 사항
1. 커스텀 로그인 페이지만 계속 뜬다 > websecu..congif 에 enable 안해놨음 맨위에
2. 세션만 사용했을떄 한쪽 로그인하면 반대쪽이 풀림 > 동일한 jsessionid 라는 세션쿠키가 있어서 이거 내꺼 아닌데? 하고 invalid 했던거임
3. redis 사용시 반대쪽에서 직렬화 문제 발생 > redis 직렬화할떄는 해당 객체(여기선 ssoToken > authentication 객체) 의 모든 값이 구조도 동일해야 함
- 즉 패키지 최초 경로부터 같아야 하는데 다른 프로젝트니 달라서 오류 발생
- 공통모듈로 빼야하나 현재는 그냥 동일한 패키지명으로 하나 더 만들어둠

## 생각해봤던것 (실패)
1. 디비 컬럼에 매번 바뀌는 토큰값을 넣어둔다
2. JWT 토큰을 사용한다.
3. 동일한 도메인 쿠키를 적용시킨다.

## 고치거나 확인해야 할 사항
1. invalidSession 이거 넣으면 자꾸 redirect 가 사라지고 _main 의 commence 로 넘어가서 client 서비스로 돌아가지 못한다.
2. ssoToken, user 객체 공통모듈로 빼서 넣어보기 ex)`.jar` , `implementation project(':common-auth')` 등
