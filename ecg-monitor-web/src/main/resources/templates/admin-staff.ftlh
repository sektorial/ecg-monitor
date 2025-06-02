<!DOCTYPE html>
<html lang="en">
<#assign title="Staff Management">
<#include "./fragments/head.ftlh">
<body>
<div class="container">
    <h1>${msg}</h1>
    <#include "fragments/nav-admin-back.ftlh">

    <table class="table">
        <thead>
        <tr>
            <th>Username</th>
            <th>Password</th>
            <th>Action</th>
        </tr>
        </thead>
        <tbody>
        <#if staff?? && staff?size gt 0>
            <#list staff as staff>
                <tr>
                    <td>${staff.username}</td>
                    <td>${staff.password}</td>
                    <td>
                        <form action="/web/admin/staff/remove" method="post" style="display:inline;">
                            <input type="hidden" name="username" value="${staff.username}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button type="submit" onclick="return confirm('Delete staff?')">Remove</button>
                        </form>
                    </td>
                </tr>
            </#list>
        <#else>
            <tr>
                <td colspan="3">No staff yet</td>
            </tr>
        </#if>
        </tbody>
    </table>

    <form action="/web/admin/staff/add" method="post" style="margin-top: 30px;">
        <input type="text" name="username" placeholder="Username" required>
        <input type="text" name="password" placeholder="Password" required>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">Add Staff</button>
    </form>

    <#include "./fragments/footer.ftlh">
</div>
</body>
</html>

