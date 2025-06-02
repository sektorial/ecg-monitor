<!DOCTYPE html>
<html lang="en">
<#assign title="Patient">
<#include "./fragments/head.ftlh">
<body>
<div class="container">
    <#include "fragments/nav-staff-back.ftlh">
    <h1>Patient Details</h1>
    <p>Patient ID: ${patientId}</p>
    <canvas id="ecgChart" width="900" height="320"></canvas>
    <p>(Patient details coming soon)</p>
    <#include "./fragments/footer.ftlh">
</div>
</body>
</html>
