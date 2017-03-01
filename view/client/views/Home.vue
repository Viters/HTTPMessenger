<template>
  <div class="page">
    <login v-if="!isLogged"></login>
    <div id="main" v-else>
      <users-list id="users-list"></users-list>
      <messenger v-if="currentUser.id != 0" id="messenger"></messenger>
    </div>
  </div>
</template>

<script>
import Login from 'components/Login'
import UsersList from 'components/UsersList'
import Messenger from 'components/Messenger'

export default {
  components: {
    Login,
    UsersList,
    Messenger
  },

  computed: {
    isLogged() {
      return this.$store.state.userId !== null;
    },

    userName() {
      return this.$store.state.userName;
    },

    currentUser() {
      return this.$store.state.currentUser;
    },

    lastId() {
      var lastId = 0;
      if (this.$store.state.messages.length > 0) {
        let lastMessage = this.$store.state.messages[this.$store.state.messages.length - 1];
        lastId = lastMessage.id;
      }
      return lastId;
    }
  },

  watch: {
    isLogged(val) {
      if (val === true) {
        this.fetchNewMessages();
        this.fetchNewUsers();
      }
    },
  },

  methods: {
    fetchNewMessages() {
      let apiToken = this.$store.state.apiToken;
      this.$http.get('http://127.0.0.1:8080/messages&apiToken=' + apiToken + '&id=' + this.lastId)
        .then((response) => {
          response.data.forEach((val) =>
              this.$store.commit('addNewMessage', {
                id: val.id,
                message: val.message,
                from: val.from,
                date: val.date,
                to: val.to,
                mine: false
              })
            );
        });
      setTimeout(() => this.fetchNewMessages(), 200);
    },

    fetchNewUsers() {
      let apiToken = this.$store.state.apiToken;
      this.$http.get('http://127.0.0.1:8080/users&apiToken=' + apiToken)
        .then((response) => {
            response.data.forEach((val) => {
              let exists = false;
              this.$store.state.users.forEach(u => {
                if (u.id == val.id)
                  exists = true;
              });
              if (!exists)
                this.$store.commit('addNewUser', { id: val.id, name: val.name })
            });
        });
      setTimeout(() => this.fetchNewUsers(), 200);
    },
  }
}
</script>

<style scoped>
  #main {
    width: 800px;
    border-radius: 3px;
    height: 500px;
    margin: 100px auto;
  }

  #main > div {
    height: 100%;
    text-align: left;
    padding: 10px 20px;
    box-sizing: border-box;
  }

  #users-list {
    width: 300px;
    float: left;
  }

  #messenger {
    width: 500px;
    float: right;
  }
</style>
