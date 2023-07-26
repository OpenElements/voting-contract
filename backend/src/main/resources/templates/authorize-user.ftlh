<!DOCTYPE html>
<html lang="en">
<#include "head.ftlh">
<body>
<#include "navigation-header.ftlh">

<main class="container mx-auto py-12">
  <#if message?? >
    <p>${message}</p>
  </#if>

  <form action="/authorize-user" method="post">
    <p>
      <label for="evmAddress">User EVM Address</label>
      <input id="evmAddress" name="evmAddress" type="text"/>
    </p>
    <button>Authorize</button>
  </form>
</main>
</body>
</html>