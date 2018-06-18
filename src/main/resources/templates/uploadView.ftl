<#import "macros/page.ftl" as p>
<@p.page>

    <h5>Top 10 words in ${filename}</h5>
    <h3>${message!}</h3>
    <a href="/" class="btn btn-primary my-3">Back to main page</a>
    <#list topWords as word>
        <ul class="list-group">
        <li style="text-align: center; font-size: 3rem "  class="list-group-item">${word}</li>
        </ul>
    </#list>

</@p.page>