<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        Вход
    </title>
    <meta charset="utf-8">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <style>
        span.error {
            color: red;
        }
    </style>
</head>
<body><h2 style="text-align:center">Вход</h2>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<p style="text-align:center">Введите имя пользователя и пароль</p>
<div class="container">
    <div class="row">
        <div class="col-md-8 offset-md-5">
            <form method="POST" action="${contextPath}/login">
                <div class="form-group">
                    <label for="email">Пользователь:</label>
                    <input type="text" class="form-control w-25" id="email" placeholder="Введите почту"
                           name="email">
                    <span class="error">${emailError}</span>
                </div>
                <div class="form-group">
                    <label for="password">Пароль:</label>
                    <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль"
                           name="password">
                    <span class="error">${passwordError}</span>
                </div>
                <button type="submit" class="btn btn-success">Вход</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>