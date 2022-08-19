<template>
  <div class="login-wrapper border border-light">
    <form class="form-signin" @submit.prevent="sendForm">
      <h2 class="form-signin-heading" v-if="loginMode">로그인</h2>
      <h2 class="form-signin-heading" v-else>등록하기</h2>

      <label for="inputEmail" class="sr-only">이메일 주소</label>
      <input v-model="username" type="text" id="inputEmail" class="form-control" placeholder="사용자 이름" required
        autofocus>

      <label for="inputPassword" class="sr-only">비밀번호</label>
      <input v-model="password" type="password" id="inputPassword" class="form-control" placeholder="비밀번호" required>

      <div v-if="!loginMode">
        <label for="inputPasswordRetype" class="sr-only">비밀번호 확인</label>
        <input v-model="passwordRetype" type="password" id="inputPasswordRetype" class="form-control"
          placeholder="비밀번호 확인" required>
      </div>

      <br>
      <button class="btn btn-lg btn-primary btn-block" type="submit" v-if="loginMode">로그인</button>
      <button class="btn btn-lg btn-primary btn-block" type="submit" v-else>등록하기</button>
      <br>
      <label @click="toggleMode()" v-if="loginMode">등록하기</label>
      <label @click="toggleMode()" v-else>로그인으로 돌아가기</label>
      <hr />
      빌드: {{buildVersion}}
    </form>
  </div>
</template>

<script>
  export default {
    name: "Login",
    mounted(){
      const cookieToken = this.$cookies.get("token");
      if(cookieToken){
        this.$store.commit('user/SET_USER', cookieToken);
        this.$store.dispatch('user/LOAD_USER_STATUS');
        this.$router.push({name: "chat"});
      }
    },
    data() {
      return {
        buildVersion: process.env.VUE_APP_BUILD || '',
        loadingScreen: null,
        loginMode: true,
        username: "",
        password: "",
        passwordRetype: "",
      };
    },
    methods: {
      showDialog(mode, message) {
        this.$notify({
          group: "all",
          type: mode,
          text: message
        });
      },
      showLoading() {
        this.loadingScreen = this.$loading.show({
          canCancel: false,
          onCancel: null
        });
      },
      toggleMode() {
        this.loginMode = !this.loginMode;
      },
      sendForm() {
        this.loginMode
          ? this.login()
          : this.register()
      },
      login() {
        this.showLoading();
        this.$store
          .dispatch("user/LOGIN_USER", {
            username: this.username,
            password: this.password
          })
          .then(res => {
            const token = res.headers.authorization;
            this.showDialog("success", "로그인 성공");
            this.loadingScreen.hide();
            this.$cookies.set("token", token, '1h');
            this.$router.push({name: "chat"});
          })
          .catch(err => {
            // show error
            this.loadingScreen.hide();
            this.showDialog("error", "로그인 오류");
          });
      },
      register() {
        this.showLoading();
          // register user
          if (this.password === this.passwordRetype) {
            this.$store
              .dispatch("user/REGISTER_USER", {
                username: this.username,
                password: this.password
              })
              .then(res => {
                this.loadingScreen.hide();
                this.showDialog("success", "등록 성공");
                this.loginMode = true;
              })
              .catch(err => {
                console.log("err", err);
                this.loadingScreen.hide();
                this.showDialog("error", "등록 중 오류");
              });
          } else {
            this.loadingScreen.hide();
            this.showDialog("error", "비밀번호가 일치해야합니다.");
          }
      }
    }
  };
</script>

<style lang="css">
    body {
        background: #605b56;
    }

    .login-wrapper {
        background: #fff;
        width: 40%;
        margin: 12% auto;
    }

    .form-signin {
        max-width: 330px;
        padding: 10% 15px;
        margin: 0 auto;
    }

    .form-signin .form-signin-heading,
    .form-signin .checkbox {
        margin-bottom: 10px;
    }

    .form-signin .checkbox {
        font-weight: normal;
    }

    .form-signin .form-control {
        position: relative;
        height: auto;
        -webkit-box-sizing: border-box;
        box-sizing: border-box;
        padding: 10px;
        font-size: 16px;
    }

    .form-signin .form-control:focus {
        z-index: 2;
    }

    .form-signin input[type="text"] {
        margin-bottom: -1px;
        border-bottom-right-radius: 0;
        border-bottom-left-radius: 0;
    }

    .form-signin input[type="password"] {
        margin-bottom: -1px;
        border-top-left-radius: 0;
        border-top-right-radius: 0;
    }
</style>
