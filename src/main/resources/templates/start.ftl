<#import "macros/page.ftl" as p>
<@p.page>
<div class="form-group">
    <form method="post" action="/saveFile" enctype="multipart/form-data">
        <div class="custom-file">
            <input type="file" name="file" class="custom-file-input" id="customFileLang" lang="es">
            <label class="custom-file-label" for="customFileLang">Select file</label>
        </div>
        <div class="form-group mt-3">
            <button class="btn btn-primary" type="submit">Add file</button>
        </div>
    </form>
</div>
<div class="card-columns">
<#list texts as text>
<div class="card my-5" style="width: 21rem">
    <div class="card-header">
        ${text.filenameNormally}
    </div>
    <div class="card-body">
        <h5 class="card-title">Test project for *instinctools</h5>
        <p class="card-text">Text analyzer</p>
        <a href="/information/${text.id}" class="btn btn-primary">Top words in text</a>
        <a href="/check/${text.id}" class="btn btn-primary">Correct brackets</a>
    </div>
</div>
</#list>
</div>
</@p.page>