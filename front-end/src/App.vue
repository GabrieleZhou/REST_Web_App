<template>
  <nav id="navbar">
    <router-link :to="{name : 'Home'}"><img id="logo" src="./assets/logo.png" alt="logo"></router-link>
    <router-link :to="{name : 'Login'}" id="nav-log" v-if="!logged">
      <i class="fa-solid fa-user fa-lg" style="color: #ffffff; margin-right: 0.3rem;"></i>
      Accedi
    </router-link>
    <router-link :to="{name : 'Libreria'}" v-else>
      <i class="fa-solid fa-user-astronaut fa-lg" style="color: #ff5757; margin-right: 0.3rem;"></i>
      {{user.nome}}
    </router-link>
  </nav>
  <div v-if="showPopMsg">
    <PopMsgVue :msg="msg" :red="msgRed" @tooglePop="tooglePopMsg"/>
  </div>
  <router-view :logged="logged" :user="user" @msg="updateMsg" @accesso="login" @cancellaUtente="deleteUser"/>
</template>
<script>
import PopMsgVue from './components/PopMsg.vue'

export default {
  components : {PopMsgVue},
  data() {
    return {
      logged : sessionStorage.getItem("log") || false,
      showPopMsg : false,
      msg : "",
      msgRed : true,
      user : JSON.parse(sessionStorage.getItem("user")) || {}
    }
  },
  methods : {
    updateMsg(m, r) {
      this.msg = m
      this.msgRed = (r) ? true : false
      this.showPopMsg = true
    },
    tooglePopMsg() {
      this.showPopMsg = !this.showPopMsg;
    },
    login() {
      this.logged = sessionStorage.getItem("log")
      this.user = JSON.parse(sessionStorage.getItem("user"))
    },
    deleteUser() {
      this.logged = false
      this.user = {}
    }
  },
  watch: {
    showPopMsg(newValue) {
      if (newValue) {
        setTimeout(() => {
          this.showPopMsg = false
        }, 3000);
      }
    }
  }
}
</script>
<style>
body {
  padding: 0;
  margin: 0;
}
body button {
  cursor: pointer;
  border: none;
  border-radius: 5px;
}
#app {
  font-family: Helvetica, Arial, sans-serif;
}
a {
  text-decoration: none;
  color: black;
}
#navbar {
  display: flex;
  align-items: center;
  justify-content: space-between;
  height: 5rem;
  width: 100%;
  background-color: white;
  padding: 0 2rem;
  border-bottom: 1px solid rgb(236, 225, 225);
  position: fixed;
  top: 0;
  box-sizing: border-box;
  z-index: 1;
}
#logo {
  width: 4rem;
  margin-right: 5rem;
}
#nav-log {
  padding: 1rem 0.5rem;
  border-radius: 5px;
  background-color: #ff5757;
  color: white;
}
</style>
