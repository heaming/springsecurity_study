<script>

export default {
  /* eslint-disable */
  name: 'Login',
  data() {
    return {
      username: '',
      password: '',
      remember_me: false,
    }
  },
  methods: {
    submitForm: function() {
      const url = 'http://localhost:8085/login';
      const form_data = new FormData();
      form_data.append("username", this.username);
      form_data.append("passsword", this.password);
      form_data.append("remember_me", this.remember_me);

      this.axios.post(url, form_data, {
        headers: {
          'Content-Type': 'application/x-www-form-urlencoded',
        }
      })
      .then(res => {
        console.log(res);
        if(res.status == 200) {
          this.$store.commit('loginSuccess');
          this.$router.push('/');
        }

      })
      .catch(err => {
        console.log(err);

        if(err.state == 400) {
          alert("비밀번호 확인");
          //return;
        }

        if(err.state == 404) {
          alert("아이디 확인");
        }
      })
    }
  }
}
</script>


<template>
  <div class="loginForm">
    <h1>Login</h1>

    <form v-on:submit.prevent="submitForm">
      <input v-model="username" type="text" name="username" placeholder="username"><br/>
      <input v-model="password" type="password" name="password" placeholder="password"> <br/>
      <button type="submit">로그인</button>
      <div>
        <input v-model="remember_me" type="checkbox"
               name="remember_me" value="True"
               id="autoLogin">
        <label class="custom-control-label" for="autoLogin">자동 로그인</label>
      </div>
    </form>
    <a href="/oauth2/authorization/google">구글 로그인</a>
    <a href="/oauth2/authorization/naver">네이버 로그인</a>
    <a href="/oauth2/authorization/kakao">카카오 로그인</a>
    <a href="/joinForm">회원가입</a>
  </div>
</template>

