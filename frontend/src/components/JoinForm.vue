<template>
  <h1>JoinForm</h1>
  <hr/>
  <form action="/join" method="POST">
    <input type="text" name="username" placeholder="username"><br/>
    <input type="email" name="email" placeholder="email"><br/>
    <input type="password" name="password" placeholder="password"> <br/>
    <button>회원가입</button>
  </form>
</template>

<script>

export default {
  name: 'JoinForm',
}

</script>