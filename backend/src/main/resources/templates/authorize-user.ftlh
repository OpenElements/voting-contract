<!DOCTYPE html>
<html lang="en">
<head>
    <#include "head-meta.ftlh">
</head>
<body>
<#include "header.ftlh">

<main class="container mx-auto py-12">
  <#if message?? >
    <p>${message}</p>
  </#if>

  <form action="/authorize-user" method="post">
    <p>
      <label for="admin">Admin-Account</label>
      <input id="admin" name="admin" type="text"/>
    </p>
    <p>
      <label for="account">User-Account</label>
      <input id="account" name="account" type="text"/>
    </p>
    <button>Authorize 123</button>
  </form>
</main>
</body>
</html>