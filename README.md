 <h1 tabindex="-1" dir="auto"><a id="user-content-file_folder-cucumber-market" class="anchor" aria-hidden="true" tabindex="-1" href="#file_folder-cucumber-market"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>📁 board-aop-redis-docker</h1>
 
<ul dir="auto">
<li>사용자 경험 위주의  <strong>게시판을 </strong> 개발하는 토이프로젝트</li>
<li>웹 UI는 카카오 오븐으로 대체하여 프론트엔드 부분은 생략하고 <strong>백엔드에 초점</strong>을 맞춰 개발에 집중</li>
</ul>

 <h2 tabindex="-1" dir="auto"><a id="user-content-pushpin-개발중점" class="anchor" aria-hidden="true" tabindex="-1" href="#pushpin-개발중점"><svg class="octicon octicon-link" viewBox="0 0 16 16" version="1.1" width="16" height="16" aria-hidden="true"><path d="m7.775 3.275 1.25-1.25a3.5 3.5 0 1 1 4.95 4.95l-2.5 2.5a3.5 3.5 0 0 1-4.95 0 .751.751 0 0 1 .018-1.042.751.751 0 0 1 1.042-.018 1.998 1.998 0 0 0 2.83 0l2.5-2.5a2.002 2.002 0 0 0-2.83-2.83l-1.25 1.25a.751.751 0 0 1-1.042-.018.751.751 0 0 1-.018-1.042Zm-4.69 9.64a1.998 1.998 0 0 0 2.83 0l1.25-1.25a.751.751 0 0 1 1.042.018.751.751 0 0 1 .018 1.042l-1.25 1.25a3.5 3.5 0 1 1-4.95-4.95l2.5-2.5a3.5 3.5 0 0 1 4.95 0 .751.751 0 0 1-.018 1.042.751.751 0 0 1-1.042.018 1.998 1.998 0 0 0-2.83 0l-2.5 2.5a1.998 1.998 0 0 0 0 2.83Z"></path></svg></a>📌 개발중점</h2>
<li>단순 기능 구현 뿐 아니라 코드의 <strong>재사용성</strong> 및 <strong>유지보수성</strong>을 고려하여 구현하는 것을 목표로 개발</li>
<li><strong>확장성</strong>을 위한 <strong>객체지향</strong>의 기본 원리 <strong>DIP, OCP</strong> 준수</li>
<li>Spring framework의 <strong>IOC/DI , AOP</strong>의 활용</li>
<li>라이브러리 및 기능 추가 시 이유있는 선택과 <strong>사용 목적</strong> 고려</li>
<li>key-value 형태로 구성된 <strong>Redis 처리로 </strong> 서비스 응답 속도 향상</li>
<li>Runtime Exception <strong>예외처리</strong> 선정과 그 종류에 따른 <strong>Http Status</strong> 응답 고려</li>
<li><strong>대용량 트래픽</strong>을 고려한 응답 속도 향상 구현</li>

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
<td>Swagger Springdoc OpenAPI 3.0</td>
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
