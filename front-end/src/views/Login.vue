<template>
  <div class="c-6">
    <form @submit.prevent="handleSub">
      <label for="nome">Nome</label>
      <input v-model="nome" type="text" id="nome" autocomplete="off" required>
      <label for="cognome">Cognome</label>
      <input  v-model="cognome" type="text" id="cognome" autocomplete="off" required>
      <input type="submit" class="sub">
    </form>
  </div>
</template>

<script>
import router from '@/router'
export default {
  emits : ["accesso", "msg"],
  data() {
    return {
      utenti : [],
      nome : "",
      cognome : "",
      email : "",
      logged : sessionStorage.getItem("logged")
    }
  },
  methods : {
    handleSub() {
      let user
      let notFound = true
      for(let i = 0; i < this.utenti.length && notFound; i++) {
        if(this.nome == this.utenti[i].nome && this.cognome == this.utenti[i].cognome) {
          user = this.utenti[i]
          notFound = false
          sessionStorage.setItem("log", true)
          sessionStorage.setItem("user", JSON.stringify(user))
          this.$emit("accesso")
          router.push({name : "Libreria"})
        }
      }
      if(!user){
        this.$emit("msg", "Utente non trovato", true)
      }
    }
  },
  created() {
    const url = "http://localhost:9010/api/utenti"
    fetch(url)
    .then(res => {
      return res.json()
    })
    .then(data => {
      this.utenti = data
    })
    .catch(e => {
      console.log(e)
    })
  }
}
</script>

<style>
  .c-6 {
    height: 100vh;
    display: flex;
    align-items: center;
    justify-content: center;
    background-color: #eee;
  }
  form {
    display: flex;
    flex-direction: column;
    background-color: #ff5757;
    padding: 2rem;
    border-radius: 5px;
    gap: 0.6rem;
  }
  form input {
    padding: 0.4rem;
    border: none;
    border-radius: 3px;
    width: 14rem;
    font-size: 1rem;
  }
  label {
    color: white;
  }
  .sub {
    margin-top: 1.5rem;
    width: 100%;
    color: #ff5757;
    background-color: white;
    font-weight: 700;
    font-size: 1rem;
    cursor: pointer;
  }
</style>