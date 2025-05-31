<!DOCTYPE html>
<html>
<head>
    <title>Hello World</title>
</head>
<body>
<h1>${msg}</h1>
<form action="/logout" method="post" style="display: inline">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Logout</button>
</form>
</body>
</html>
