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
    <form action="/vote" method="post">
      <p>
        <label for="privateKey">User DER Encoded Private Key</label>
        <input id="privateKey" name="privateKey" type="text"/>
      </p>
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