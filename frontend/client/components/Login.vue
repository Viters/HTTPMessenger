<template>
  <div class="login-wrapper">
    <form @submit.prevent="createNewUser">
      <input id="user-name" v-model="userName" placeholder="Twój pseudonim" autocomplete="off">
      <p id="confirm" v-if="userName">Cześć <strong>{{ userName }}</strong>! Czy to na pewno Ty?</p>
      <input id="login-button" type="submit" value="Zaloguj">
    </form>
  </div>
</template>

<script>
export default {
  name: 'Login',

  data() {
    return {
      userName: "",
    }
  },

  methods: {
    createNewUser() {
      let formData = new FormData();
      formData.append('name', this.userName);
      this.$http.post('http://127.0.0.1:8080/user', formData)
        .then((response) => {
          this.$store.commit('setUserId', response.data.id);
          this.$store.commit('setUserName', response.data.name);
          this.$store.commit('setApiToken', response.data.token);
        });
    }
  },
}
</script>

<style scoped>
  .login-wrapper {
    padding-top: 100px;
    width: 300px;
    margin: 0 auto;
  }

  input {
    display: block;
    width: 100%;
    box-sizing: border-box;
    border-radius: 3px;
  }

  #user-name {
    text-align: center;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #2c3e50;
    margin-bottom: 10px;
  }

  #confirm {
    font-size: 12px;
    color: #34495e;
    margin: 10px 0;
  }

  #login-button {
    margin-top: 10px;
    border: 0;
    font-size: 16px;
    padding: 10px;
    background-color: #1abc9c;
    color: #fff;
    font-weight: bold;
    cursor: pointer;
    transition: all 200ms;
  }

  #login-button:hover {
    background-color: #16a085;
  }
</style>
