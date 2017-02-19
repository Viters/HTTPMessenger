import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

const state = {
  userId: null,
  apiToken: "",
  userName: "",
  messages: [],
  users: [],
  currentUser: { id: 0, name: '' },
}

const mutations = {
  setUserId(state, id) {
    state.userId = id;
  },

  setApiToken(state, token) {
    state.apiToken = token;
  },

  setUserName(state, name) {
    state.userName = name;
  },

  addNewMessage(state, data) {
    let newMessage = {
      id: data.id, message: data.message, from: data.from, date: data.date, to: data.to, mine: data.mine
    };
    state.users.forEach(u => {
      if (u.id == newMessage.from)
        u.newMessages++;
    })
    state.messages.push(newMessage);
  },

  addNewUser(state, data) {
    let newUser = {
      id: data.id, name: data.name, newMessages: 0
    };
    state.users.push(newUser);
  },

  changeCurrentUser(state, id) {
    let foundUser = { id: 0, name: '' };
    for (let i = 0; i < state.users.length; i++) {
      if (state.users[i].id == id) {
        foundUser = state.users[i];
        foundUser.newMessages = 0;
        break;
      }
    }
    state.currentUser = foundUser;
  }
}

const actions = {

}

const store = new Vuex.Store({
  state,
  mutations,
  actions
})

export default store
