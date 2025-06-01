<!DOCTYPE html>
<html lang="en">
<head>
    <title>Hello World</title>
    <#if hrmEnabled?? && hrmEnabled>
        <script src="http://localhost:3000/bundle.js" type="text/javascript" defer></script>
    <#else>
        <script src="${resources.getForLookupPath("/static/bundle.js")}" type="text/javascript" defer></script>
    </#if>
</head>
<body>
<h1>${msg}</h1>
<form action="/logout" method="post" style="display: inline">
    <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}"/>
    <button type="submit">Logout</button>
</form>
</body>
</html>
