<!DOCTYPE html>
<html lang="en">
<head>
    <#include "head-meta.ftlh">
</head>
<body>
<#include "header.ftlh">

<main class="container mx-auto py-12">
  <section>
    <h2>Say Hello</h2>
    <p>
      <span><b>Current message:</b></span> <span>${message!UNKNOWN}</span>
    </p>
  </section>
  <section>
    <h2>Update message in Network</h2>
    <form action="/onhello" method="post">
      <p>
        <label>New message</label>
        <input name="message" type="text"/>
      </p>
      <button>update message</button>
    </form>
  </section>
</main>
</body>
</html>