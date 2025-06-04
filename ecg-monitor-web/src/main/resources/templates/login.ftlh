<!DOCTYPE html>
<html lang="en">
<#assign title="Authentication">
<#include "./fragments/head.ftlh">
<body>
<div class="container">
    <form action="/login" method="post" style="display:inline;">
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
        <div>
            <label for="login-username">Username: </label>
            <input id="login-username" type="text" name="username"/>
        </div>
        <div>
            <label for="login-password">Password: </label>
            <input id="login-password" type="password" name="password"/>
        </div>
        <div>
            <button type="submit">Login</button>
        </div>
    </form>
</div>
</body>
</html>
