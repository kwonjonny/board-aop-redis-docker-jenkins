<%@ page contentType="text/html;charset=UTF-8" language="java" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<!DOCTYPE html>
<html lang="kr">
<head>
<meta charset="utf-8">
<title>Error Page</title>
<meta content="width=device-width, initial-scale=1.0" name="viewport">
<meta content="" name="keywords">
<meta content="" name="description">
<!-- Favicon -->
<link rel="shortcut icon" href="/imgs/favicon.ico" type="image/x-icon">
<!-- Google Web Fonts -->
<link rel="preconnect" href="https://fonts.googleapis.com">
<link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
<link href="https://fonts.googleapis.com/css2?family=Heebo:wght@400;500;600;700&display=swap" rel="stylesheet">
<!-- Icon Font Stylesheet -->
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.10.0/css/all.min.css" rel="stylesheet">
<link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css" rel="stylesheet">
<!-- Customized Bootstrap Stylesheet -->
<link href="/css/bootstrap.min.css" rel="stylesheet">
<!-- Template Stylesheet -->
<link href="/css/style.css" rel="stylesheet">
    <style>
        .error-image {
            height: 30vh;
            display: flex; 
            align-items: center;
            justify-content: center;
        }

        .error-image img {
            max-height: 680px;
            width: 1500px;
        }
        
        .sigin_content {
            margin-top: 20px;
        }
        .message-content {
            max-height: 100px;  /* 원하는 최대 높이 설정 */
            overflow-y: auto;  /* 수직 스크롤 활성화 */
        }
    </style>
</head>
<body style="background: #fff;">
    <div class="sigin_wrap">
        <div class="col-sm-5 col-xl-5 sigin_content">
            <!-- <div class="error-image">
                <c:choose>
                    <c:when test="${fn:substring(status, 0, 3) eq '400'}">
                        <img src="/imgs/깃허브404.jpg" alt="404 error" />
                    </c:when>
                    <c:when test="${fn:substring(status, 0, 3) eq '500'}">
                        <img src="/imgs/깃허브505.jpg" alt="505 error" />
                    </c:when>
                </c:choose>
            </div> -->
            <h1><img src="/imgs/login_logo.png" alt="logo" /></h1>
            <div class="bg-body rounded p-4">
                <div style="text-align: center; font-size: 20px;">
                    <h3 style="font-size: 40px; margin-bottom: 20px;">Error Page</h3>
                    <h2 style="font-size: 35px; margin-bottom: 30px;">Error Occurred</h2>
                    <div style="margin-bottom: 20px;">
                        <strong style="display: inline-block; width: 60px;">Time:</strong> ${time}
                    </div>
                    <div style="margin-bottom: 20px;">
                        <strong style="display: inline-block; width: 60px; margin-right: 10px;">Status:</strong> ${status}
                    </div>
                    <div tyle="margin-bottom: 20px;">
                        <strong style="display: inline-block; width: 60px; margin-right: 30px;">Message:</strong> ${message}
                    </div>
                </div>
            </div>
        </div>
    </div>
<!-- Update Complete Message End -->
<!-- JavaScript Libraries -->
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://code.jquery.com/jquery-3.5.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/axios/dist/axios.min.js"></script>
<script src="https://stackpath.bootstrapcdn.com/bootstrap/4.5.0/js/bootstrap.min.js"></script>
<script src="https://code.jquery.com/jquery-3.4.1.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/popper.js@1.12.9/dist/umd/popper.min.js" integrity="sha384-ApNbgh9B+Y1QKtv3Rn7W3mgPxhU9K/ScQsAP7hUibX39j7fakFPskvXusvfa0b4Q" crossorigin="anonymous"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js" integrity="sha384-geWF76RCwLtnZ8qwWowPQNguL3RmwHVBC9FhGdlKrxdiJJigb/j/68SIy3Te4Bkz" crossorigin="anonymous"></script>
<!-- Template Javascript -->
<script src="/js/main.js"></script>
</body>
</html>