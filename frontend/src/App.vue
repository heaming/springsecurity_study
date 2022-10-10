<script>
export default {
  name: 'App',
}
</script>


<template>
  <img alt="Vue logo" src="./assets/logo.png">
  <div class="component">
    <h1>Index</h1>
    <div>
      <router-link to="/">Home</router-link>
      <router-link to="/login">Login</router-link>
      <router-link to="/joinForm">Join</router-link>
    </div>
  </div>
  <router-view></router-view>
</template>


<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
  margin-top: 60px;
}
</style>
