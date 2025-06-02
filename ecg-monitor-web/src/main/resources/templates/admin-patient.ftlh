<!DOCTYPE html>
<html lang="en">
<#assign title="Patient Management">
<#include "./fragments/head.ftlh">
<body>
<div class="container">
    <#include "fragments/nav-admin-back.ftlh">
    <h1>${msg}</h1>

    <form action="/web/admin/patient/add" method="post" style="margin-bottom:20px;">
        <input type="text" name="name" placeholder="Patient name" required>
        <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
        <button type="submit">Add Patient</button>
    </form>


    <#if patients?size gt 0>
        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
                <th>ID (UUID)</th>
                <th>Action</th>
            </tr>
            </thead>
            <tbody>
            <#list patients as patient>
                <tr>
                    <td>${patient.name}</td>
                    <td>${patient.id}</td>
                    <td>
                        <form action="/web/admin/patient/remove" method="post" style="display:inline;">
                            <input type="hidden" name="id" value="${patient.id}">
                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}">
                            <button type="submit" onclick="return confirm('Delete patient?')">Remove</button>
                        </form>

                    </td>
                </tr>
            </#list>
            </tbody>
        </table>
    <#else>
        <p>No patients found.</p>
    </#if>

    <#include "./fragments/footer.ftlh">
</div>
</body>
</html>
