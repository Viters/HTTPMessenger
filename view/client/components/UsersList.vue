<template>
  <div>
    <h3><strong>Czesć {{ userName }}!</strong></h3>
    <h3><i class="fa fa-users"></i> Podłączeni użytkownicy</h3>
    <transition-group name="list" tag="ul">
      <li v-for="user in users" v-bind:key="user" class="list-item" @click="switchCurrentUser(user.id)">
        <span class="new-message" v-if="user.newMessages != 0">{{ user.newMessages }}</span>
        {{ user.name }} <i class="fa fa-comments-o" aria-hidden="true"></i>
      </li>
    </transition-group>
  </div>
</template>
<script>
  export default{
    computed: {
      users() {
        return this.$store.state.users
          .filter(u => u.id != this.$store.state.userId);
      },

      userName() {
        return this.$store.state.userName;
      }
    },

    methods: {
      switchCurrentUser(id) {
        this.$store.commit('changeCurrentUser', id);
      }
    }
  }
</script>
<style scoped>
  h3 {
    color: #2c3e50;
    font-size: 18px;
    font-weight: 300;
    margin-bottom: 0;
  }

  ul {
    padding-left: 0;
    margin-top: 20px;
  }

  li {
    list-style-type: none;
    padding: 10px;
    font-size: 15px;
    background-color: #34495e;
    color: #fff;
    box-sizing: border-box;
    cursor: pointer;
    margin-bottom: 5px;
  }

  li > .new-message {
    background-color: #8e44ad;
    border-radius: 3px;
    padding: 2px 5px;
    font-size: 12px;
    vertical-align: 2px;
    margin-right: 5px;
  }

  li > i {
    float: right;
  }

  .list-enter-active, .list-leave-active {
    transition: all 1s;
  }
  .list-enter, .list-leave-to /* .list-leave-active for <2.1.8 */ {
    opacity: 0;
    transform: translateY(30px);
  }
</style>
