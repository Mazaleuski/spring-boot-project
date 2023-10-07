<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" %>
<html>
<head>
    <title>
        Регистрация
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
<body><h2 style="text-align:center">Регистрация</h2>
<p></p>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
<div class="container">
    <c:if test="${not empty info}">
            <p style="text-align: center" class="text-danger">${info}</p>
        </c:if>
    <div class="row">
        <div class="col-md-8 offset-md-5">
            <form method="POST" action="${contextPath}/registration">
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="name" placeholder="Имя"
                           name="name">
                    <span class="error">${nameError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="surname" placeholder="Фамилия"
                           name="surname">
                    <span class="error">${surnameError}</span>
                </div>
                <div class="form-group">
                    <label for="birthday">Дата рождения</label>
                    <input type="text" class="form-control w-25 datepicker" id="birthday" placeholder="YYYY-mm-dd"
                           name="birthday">
                    <span class="error">${birthdayError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="email" placeholder="Введите email"
                           name="email">
                    <span class="error">${emailError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="password" placeholder="Введите пароль"
                           name="password">
                    <span class="error">${passwordError}</span>
                </div>
                <div class="form-group">
                    <label for="birthday">Адрес</label>
                    <input type="text" class="form-control w-25" id="address" placeholder="Город Улица дом-кв"
                           name="address">
                    <span class="error">${addressError}</span>
                </div>
                <div class="form-group">
                    <input type="text" class="form-control w-25" id="phoneNumber" placeholder="Введите телефон"
                           name="phoneNumber">
                    <span class="error">${phoneNumberError}</span>
                </div>
                <button type="submit" class="btn btn-success">Регистрация</button>
            </form>
        </div>
    </div>
</div>
</body>
</html>