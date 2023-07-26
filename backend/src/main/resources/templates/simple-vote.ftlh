<!DOCTYPE html>
<html lang="en">
<#include "head.ftlh">
<body>
<#include "navigation-header.ftlh">

<main class="container mx-auto py-12">
  <#if message?? >
    <p>${message}</p>
  </#if>
  <#if proposals?? >
    <form action="/simple-vote" method="post">
      <select name="selected">
          <#list proposals as value>
            <option value="${value.id()}">${value.name()}</option>
          </#list>
      </select>
      <button>Vote</button>
    </form>
  </#if>
</main>
</body>
</html>