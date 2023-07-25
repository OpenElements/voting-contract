<!DOCTYPE html>
<html lang="en">
<head>
    <#include "head-meta.ftlh">
</head>
<body>
<#include "header.ftlh">

<main class="container mx-auto py-12">
  <h1>ERROR</h1>
  <p>${stacktrace!UNKNOWN}</p>
</main>
</body>
</html>