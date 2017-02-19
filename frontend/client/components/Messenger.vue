<template>
  <div>
    <h3><i class="fa fa-comments-o" aria-hidden="true"></i> <strong>{{ currentUser.name }}</strong></h3>
    <transition-group name="list" id="messages-list" tag="ul">
      <div class="clearfix list-item" v-for="message in messages" v-bind:key="message">
        <li :class="message.mine ? 'sent' : 'received'">{{ message.message }}</li>
      </div>
    </transition-group>
    <form @submit.prevent="sendMessage">
      <input id="new-message" v-model="message" placeholder="Pisz wiadomość...">
      <button type="submit" id="send-message"><i class="fa fa-paper-plane" aria-hidden="true"></i></button>
    </form>
  </div>
</template>
<script>
    export default{
        data() {
            return{
                message: ""
            }
        },

        computed: {
          currentUser() {
            return this.$store.state.currentUser;
          },

          messages() {
            return this.$store.state.messages
              .filter(m => (m.to == this.currentUser.id && m.from == this.$store.state.userId)
                || (m.from == this.currentUser.id && m.to == this.$store.state.userId));
          }
        },

        methods: {
          sendMessage() {
            let apiToken = this.$store.state.apiToken;
            let formData = new FormData();
            formData.append('message', this.message);
            formData.append('to', this.currentUser.id);
            formData.append('apiToken', apiToken);
            this.$http.post('http://127.0.0.1:8080/message', formData)
              .then((response) =>
                this.$store.commit('addNewMessage', {
                  id: response.data.id,
                  message: response.data.message,
                  from: response.data.from,
                  date: response.data.date,
                  to: response.data.to,
                  mine: true
                })
              );
            this.message = "";
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

  #messages-list {
    padding-left: 0;
    margin-top: 20px;
    height: 330px;
    overflow: scroll-y;
  }

  li {
    list-style-type: none;
    padding: 10px;
    font-size: 15px;
    color: #fff;
    box-sizing: border-box;
    margin-bottom: 5px;
    width: auto;
    border-radius: 15px;
  }

  .sent {
    background-color: #7f8c8d;
    float: right;
  }

  .received {
    background-color: #1abc9c;
    float: left;
  }

  .clearfix {
    clear: both;
    float: none;
    display: block;
    margin: 5px 0;
  }

  #new-message {
    text-align: center;
    padding: 10px;
    font-size: 14px;
    border: 1px solid #2c3e50;
    border-radius: 3px;
    width: 380px;
    box-sizing: border-box;
    text-align: left;
  }

  #send-message {
    border: 0;
    font-size: 16px;
    padding: 10px;
    background-color: #1abc9c;
    color: #fff;
    font-weight: bold;
    cursor: pointer;
    transition: all 200ms;
    border-radius: 3px;
    width: 60px;
    box-sizing: border-box;
    margin: 0;
  }

  #send-message:hover {
    background-color: #16a085;
  }

  .list-enter-active, .list-leave-active {
    transition: all 1s;
  }
  .list-enter, .list-leave-to /* .list-leave-active for <2.1.8 */ {
    opacity: 0;
    transform: translateY(30px);
  }
</style>
