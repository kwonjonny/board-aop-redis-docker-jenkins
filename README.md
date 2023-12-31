 <h1 tabindex="-1" dir="auto"><a id="user-content-file_folder-cucumber-market" class="anchor" aria-hidden="true" tabindex="-1" href="#file_folder-cucumber-market"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>📁 board-aop-redis-docker</h1>
 
<ul dir="auto">
<li>고객사의 요청에 따라 <strong>대용량 트래픽을 감당할수 있는 </strong> 서비스를 개발하는 프로젝트</li>
<li>웹 UI는 JSP 프론트엔드 부분은 최소화 <strong>백엔드에 초점</strong>을 맞춰 개발에 집중</li>
</ul>

 <h2 tabindex="-1" dir="auto"><a id="user-content-pushpin-개발중점" class="anchor" aria-hidden="true" tabindex="-1" href="#pushpin-개발중점"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>📌 개발중점</h2>
<li>단순 기능 구현 뿐 아니라 코드의 <strong>재사용성</strong> 및 <strong>유지보수성</strong>을 고려하여 구현하는 것을 목표로 개발</li>
<li><strong>확장성</strong>을 위한 <strong>객체지향</strong>의 기본 원리 <strong>DIP, OCP</strong> 준수</li>
<li>Spring framework의 <strong>IOC/DI , AOP</strong>의 활용</li>
<li>라이브러리 및 기능 추가 시 이유있는 선택과 <strong>사용 목적</strong> 고려</li>
<li>key-value 형태로 구성된 <strong>Redis 처리로 </strong> 서비스 응답 속도 향상</li>
<li>Runtime Exception <strong>예외처리</strong> 선정과 그 종류에 따른 <strong>Http Status</strong> 응답 고려</li>
<li><strong>대용량 트래픽</strong>을 고려한 응답 속도 향상 구현</li>
<li><strong>차후 유지보수를 고려한</strong>자바독 주석 커밋</li>
<li><strong>서버 부하 분산을 위한</strong>Master Slave MySQL Docker 적용</li>
<li>기능 검증을 위한 <strong>Controller, Service, Mapper 테스트 커버리지 100% 달성</strong></li>

<h2 tabindex="-1" dir="auto"><a id="user-content-wrench-사용-기술" class="anchor" aria-hidden="true" tabindex="-1" href="#wrench-사용-기술"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>🔧 사용 기술</h2>

<table>
<thead>
<tr>
<th>Category</th>
<th>Content</th>
</tr>
</thead>
<tbody>
<tr>
<td><strong>Language</strong></td>
<td>Java 17</td>
</tr>
<tr>
<td><strong>Framework</strong></td>
<td>Spring Boot 3.1.3</td>
</tr>
<tr>
<td><strong>RDBMS</strong></td>
<td>MySQL 8.0.x</td>
</tr>
<tr>
<td><strong>SQL Mapper</strong></td>
<td>Mybatis</td>
</tr>
<tr>
<td><strong>Session Server</strong></td>
<td>Redis</td>
</tr>
<tr>
<td><strong>Web Server</strong></td>
<td>Nginx</td>
</tr>
<tr>
<td><strong>Container</strong></td>
<td>Docker</td>
</tr>
<tr>
<td><strong>API Documentation</strong></td>
<td>Swagger Springdoc OpenAPI 2.1.0</td>
</tr>
<tr>
<td><strong>Boilerplate Code Library</strong></td>
<td>Lombok</td>
</tr>
<tr>
<td><strong>IDE</strong></td>
<td>IntelliJ IDEA, Vs Code</td>
</tr>
</tbody>
</table>

<h2 tabindex="-1" dir="auto"><a id="user-content-tv-web-application-ui" class="anchor" aria-hidden="true" tabindex="-1" href="#tv-web-application-ui"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>📺 Web Application UI
<img src="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/blob/main/42026D47-AF3B-44B7-A695-1D06ADDD441C.jpeg" alt="Application UI">
<img src="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/blob/main/84D62CCF-098E-4331-A674-1EC838466023.jpeg" alt="Application UI">
 </h2>



<h2 tabindex="-1" dir="auto"><a id="user-content-clapper-architecture" class="anchor" aria-hidden="true" tabindex="-1" href="#clapper-architecture"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>🎬 Architecture
 
<p dir="auto">👉 <a href="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/wiki/MySQL-Replication"><strong>MySQL 아키텍처 링크</strong></a>
<br>
<p dir="auto">👉 <a href="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/wiki/Redis-Master%E2%80%90Slave%E2%80%90Sentinel"><strong>Redis 아키텍처 링크</strong></a>

<img src="https://raw.githubusercontent.com/kwonjonny/board-aop-redis-docker-jenkins/main/DBEBD47E-2E34-4714-B1CF-8AA0E19EE380.jpeg" alt="아키텍처 이미지">
</h2>


<h2 tabindex="-1" dir="auto"><a id="user-content-floppy_disk-erd" class="anchor" aria-hidden="true" tabindex="-1" href="#floppy_disk-erd"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>💾 ERD</h2>
<img src="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/blob/main/5D218CC5-AB75-4A9F-A40C-A19AA10C8AA7_1_105_c.jpeg" alt="ERD 이미지">



<h2 tabindex="-1" dir="auto"><a id="user-content-page_with_curl-springfox-openapi-30" class="anchor" aria-hidden="true" tabindex="-1" href="#page_with_curl-springfox-openapi-30"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>📃 SpringDoc OpenAPI 2.1.0
<img src="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/blob/main/1F9FAA5F-0D0E-4B9C-850A-7FCC315F4A7A_1_201_a.jpeg" alt="Swagger 1">
<img src="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/blob/main/2BF59121-F1FB-4D6E-8324-F12443B939DB_1_201_a.jpeg" alt="Swaager 2">
<img src="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/blob/main/9F20B854-120F-42DF-BE2F-5CB1AD405542_1_201_a.jpeg" alt="Swaager 3">
</h2>


<h2 tabindex="-1" dir="auto"><a id="user-content-computer-주요-기능" class="anchor" aria-hidden="true" tabindex="-1" href="#computer-주요-기능"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>💻 주요 기능</h2>
<p dir="auto">👉 <a href="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/wiki/Business"><strong>각 기능별 비지니스 로직</strong></a><br>
👉 <a href="https://github.com/kwonjonny/board-aop-redis-docker-jenkins/wiki/Use-Case"><strong>각 기능별 Use Case</strong></a></p>

<p dir="auto">👥 <strong>사용자</strong>
<ul dir="auto">
<li>회원가입, 탈퇴</li>
<li>로그인, 로그아웃</li>
<li>마이페이지, 정보수정</li>
<li>자유게시판 등록, 수정, 삭제</li>
<li>댓글 대댓글 등록, 수정, 삭제</li>
<li>좋아요 기능</li>
<li>통합검색 기능, 날짜검색 기능</li>
<li>조회수 기능</li>
</ul>
</p>

<p dir="auto">💂‍♂️ <strong>관리자</strong>
<ul dir="auto">
<li>전체회원 조회</li>
<li>전체 공지사항 통합검색 기능, 날쩌검색 기능</li>
<li>서비스 관리를 위한 일/월 통계 데이터 기능</li>
<li>회원 관리를 위한 공지사항 탑앤배너(게시글 상단 고정 기능)</li>
</ul>
</p>
