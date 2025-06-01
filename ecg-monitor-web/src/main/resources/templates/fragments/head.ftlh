<head>
    <title>${title!''}</title>
    <#if hrmEnabled?? && hrmEnabled>
        <script src="http://localhost:3000/bundle.js" type="text/javascript" defer></script>
        <link rel="stylesheet" href="http://localhost:3000/main.css">
    <#else>
        <script src="${resources.getForLookupPath('/static/bundle.js')}" type="text/javascript" defer></script>
        <link rel="stylesheet" href="${resources.getForLookupPath('/static/main.css')}"
    </#if>
</head>
