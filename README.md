# 1) 클론
git clone https://github.com/TripInMe/TripInMe.git
cd TripInMe

# 2) 백엔드 (DB 없이 우선 실행)
# application.properties 에 아래 줄이 있어야 DB 없이 실행됩니다:
# spring.autoconfigure.exclude=org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration,org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration
# server.port=8080
./gradlew bootRun     
# Windows는 
.\gradlew bootRun

# 3) 프론트엔드
cd src/main/frontend
npm ci
npm run dev           
# http://localhost:5173

# (Vite proxy: /api -> http://localhost:8080 설정 필수 --> 프록시 설정 따로 해두었으니 신경안쓰셔도 됩니다. )

환경 변수/비밀값 가이드

# 저장소에 커밋:

src/main/resources/application-template.properties (DB 값 비워둔 템플릿)

src/main/frontend/.env.example

# 각자 로컬에만:

application-local.properties, .env.local

# 절대 커밋 금지: .env*, application-*-properties(local/prod), 키/토큰
