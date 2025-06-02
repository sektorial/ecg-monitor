<!DOCTYPE html>
<html lang="en">
<#assign title="Staff Landing">
<#include "./fragments/head.ftlh">
<body>
<div class="container">
    <h1>${msg}</h1>

    <#if patients?? && patients?size != 0>
        <table class="table">
            <thead>
            <tr>
                <th>Name</th>
            </tr>
            </thead>
            <tbody>
            <#list patients as patient>
                <tr>
                    <td>
                        <a href="/web/staff/patient/${patient.id}">${patient.name}</a>
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
