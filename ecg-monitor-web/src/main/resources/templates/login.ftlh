<!DOCTYPE html>
<html lang="en">
<head>
    <title>Authentication</title>
</head>
<body>
<form action="/login" method="post">
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
        <input id="login-submit" type="submit" value="Login"/>
    </div>
</form>
</body>
</html>
