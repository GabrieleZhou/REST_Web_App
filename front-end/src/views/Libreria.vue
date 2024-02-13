<template>
  <div v-if="!logged" class="not-logged">
    Devi effettuare il login per visualizzare la libreria
    <router-link :to="{name : 'Login'}">Vai al login</router-link>
  </div>
  <div class="c-7" v-else>
    <div v-if="showLibroInfo">
    <LibroInfoVue :info="libroInfObj" :logged="logged" @toogle="toogleInfo" @updateLettura="aggiornaLettura" />
    </div>
    <div class="c-7-container">
      <div class="c-7-user">
        <i class="fa-solid fa-user" style="color: #ff5757;"></i>
        <div class="c-7-user-info">
          <span>Nome: {{user.nome}}</span>
          <span>Cognome : {{user.cognome}}</span>
          <span>Email: {{user.email}}</span>
          <span><button @click="logout">Logout</button></span>
        </div>
      </div>
      <h2>I miei libri</h2>
      <div class="filtri">
        <select v-model="param">
          <option value="allLibri">Tutti i libri</option>
          <option value="mark">Bookmark</option>
          <option value="info" >Informazioni aggiuntive</option>
        </select>
      </div>
      <div class="c-7-user-libri" v-if="param == 'allLibri'">
        <div class="libro-2" v-for="libro in userLibri.slice().reverse()" :key="libro.id" style="position: relative">
          <i class="fa-solid fa-bookmark" style="color: gold;" @click="markLibro(libro.id)" v-if="libro.mark"></i>
          <i class="fa-regular fa-bookmark" style="color: gold;" @click="markLibro(libro.id)" v-else></i>
          <img :src="require(`../assets/copertina/${libro.id}.jpg`)" :alt="libro.titolo">
          <span class="libro-titolo">{{libro.titolo}}</span>
          <span class="libro-autore">di {{libro.autore}}</span>
          <span class="libro-rating">
            <i class="fa-solid fa-star" style="color: #FFD43B;" v-for="s in libro.rating" :key="s"></i>
            <i class="fa-solid fa-star" style="color: #b3b2ad" v-for="s in (5 - libro.rating)" :key="s"></i>
          </span>
          <div class="libro-btn">
            <button class="info" @click="mostraInfo(libro.id)">Info</button>
            <button class="elimina" @click="eliminaLibro(libro.id)">Elimina</button>
          </div>
        </div>
      </div>
      <div class="c-7-user-libri" v-if="param == 'mark'">
        <div class="libro-2" v-for="libro in userLibri.slice().filter(lib => lib.mark)" :key="libro.id" style="position: relative">
          <i class="fa-solid fa-bookmark" style="color: gold;" @click="markLibro(libro.id)" v-if="libro.mark"></i>
          <i class="fa-regular fa-bookmark" style="color: gold;" @click="markLibro(libro.id)" v-else></i>
          <img :src="require(`../assets/copertina/${libro.id}.jpg`)" :alt="libro.titolo">
          <span class="libro-titolo">{{libro.titolo}}</span>
          <span class="libro-autore">di {{libro.autore}}</span>
          <span class="libro-rating">
            <i class="fa-solid fa-star" style="color: #FFD43B;" v-for="s in libro.rating" :key="s"></i>
            <i class="fa-solid fa-star" style="color: #b3b2ad" v-for="s in (5 - libro.rating)" :key="s"></i>
          </span>
          <div class="libro-btn">
            <button class="info" @click="mostraInfo(libro.id)">Info</button>
            <button class="elimina" @click="eliminaLibro(libro.id)">Elimina</button>
          </div>
        </div>
      </div>
      <div class="c-7-user-libri" v-if="param == 'info'">
        <div class="libro-2" v-for="libro in userLibri.slice().reverse()" :key="libro.id">
          <img :src="require(`../assets/copertina/${libro.id}.jpg`)" :alt="libro.titolo">
          <span class="libro-titolo">{{libro.titolo}}</span>
          <span class="libro-autore">di {{libro.autore}}</span>
          <span class="info-agg">Numero letture: {{libro.numero_letture}}</span>
          <span class="info-agg">Data aggiunta : {{libro.data_aggiunta}}</span>
          <span class="info-agg">Data rimozione : {{libro.data_eliminazione || "N.A."}}</span>
        </div>
        <div class="libro-2" v-for="libro in userLibriEliminati.slice().reverse()" :key="libro.id">
          <img :src="require(`../assets/copertina/${libro.id}.jpg`)" :alt="libro.titolo" style="filter : grayscale(1)">
          <span class="libro-titolo">{{libro.titolo}}</span>
          <span class="libro-autore">di {{libro.autore}}</span>
          <span class="info-agg">Numero letture: {{libro.numero_letture}}</span>
          <span class="info-agg">Data aggiunta : {{libro.data_aggiunta}}</span>
          <span class="info-agg">Data rimozione : {{libro.data_eliminazione}}</span>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import router from '@/router'
import LibroInfoVue from '@/components/LibroInfo.vue'

export default {
  components : {LibroInfoVue},
  props : ["logged", "user"],
  emits : ["cancellaUtente", "msg", "accesso"],
  data() {
    return {
      libroInfObj : {},
      showLibroInfo : false,
      param : "allLibri",
      userLibri : [],
      userLibriEliminati : []
    }
  },
  methods : {
    logout() {
      sessionStorage.clear()
      this.$emit("cancellaUtente")
      router.push({name : "Home"})
    },
    mostraInfo(id) {
      this.libroInfObj = this.userLibri.filter(lib => lib.id == id)[0]
      this.toogleInfo()
    },
    toogleInfo() {
      this.showLibroInfo = !this.showLibroInfo
    },
    eliminaLibro(lib_id) {
      const url = `http://localhost:9010/api/utentiLibri/${this.user.id}`
      fetch(url)
      .then(res => {
        return res.json()
      })
      .then(data => {
        let index
        let notFound = true
        const time = new Date()
        let dataEliminazione = `${time.getFullYear()}-${time.getMonth() + 1}-${time.getDate()}`
        for(let i = 0; i < this.userLibri.length && notFound; i++) {
          if(this.userLibri[i].id == lib_id) {
            index = i
            notFound = false
            this.userLibri[i].data_eliminazione = dataEliminazione
            let parsedListaLibriEliminati = JSON.parse(data.listaLibriEliminati)
            parsedListaLibriEliminati.push(this.userLibri[i])
            data.listaLibriEliminati = JSON.stringify(parsedListaLibriEliminati)
            this.userLibriEliminati.push(this.userLibri[i])
            let parsedListaLibri = JSON.parse(data.listaLibri)
            parsedListaLibri.splice(index, 1)
            data.listaLibri = JSON.stringify(parsedListaLibri)
            fetch(url, {
              method: "PUT",
              headers: {
                  'Content-type': 'application/json'
              },
              body: JSON.stringify(data)
            })
            .then(data => {return data.json()})
            .then(this.$emit("msg", "Libro rimosso", false))
          }
        }
        this.userLibri.splice(index, 1)
      })
      .catch(e => {
        console.log(e)
      })
    },
    aggiornaLettura(id) {
      const url = `http://localhost:9010/api/utentiLibri/${this.user.id}`
      fetch(url)
      .then(res => {
        return res.json()
      })
      .then(data => {
        let index
        let notFound = true
        for(let i = 0; i < this.userLibri.length && notFound; i++) {
          if(this.userLibri[i].id == id) {
            index = i
            notFound = false
            this.userLibri[i].numero_letture += 1
            let parsedListaLibri = JSON.parse(data.listaLibri)
            parsedListaLibri.splice(index, 1, this.userLibri[i])
            data.listaLibri = JSON.stringify(parsedListaLibri)
            fetch(url, {
              method: "PUT",
              headers: {
                  'Content-type': 'application/json'
              },
              body: JSON.stringify(data)
            })
            .then(data => {return data.json()})
          }
        }
      })
      .catch(e => {
        console.log(e)
      })
    },
    markLibro(id) {
      const url = `http://localhost:9010/api/utentiLibri/${this.user.id}`
      fetch(url)
      .then(res => {
        return res.json()
      })
      .then(data => {
        let index
        let notFound = true
        for(let i = 0; i < this.userLibri.length && notFound; i++) {
          if(this.userLibri[i].id == id) {
            index = i
            notFound = false
            this.userLibri[i].mark = !this.userLibri[i].mark
            let parsedListaLibri = JSON.parse(data.listaLibri)
            parsedListaLibri.splice(index, 1, this.userLibri[i])
            data.listaLibri = JSON.stringify(parsedListaLibri)
            fetch(url, {
              method: "PUT",
              headers: {
                  'Content-type': 'application/json'
              },
              body: JSON.stringify(data)
            })
            .then(data => {return data.json()})
          }
        }
      })
      .catch(e => {
        console.log(e)
      })
    }
  },
  created() {
    if(this.logged) {
      const url = `http://localhost:9010/api/utentiLibri/${this.user.id}`
      fetch(url)
      .then(res => {
        return res.json()
      })
      .then(data => {
        this.userLibri = JSON.parse(data.listaLibri)
        this.userLibriEliminati = JSON.parse(data.listaLibriEliminati)
      })
      .catch(e => {
        console.log(e)
      })
    }
  }
}
</script>

<style>
  .not-logged {
    font-size: 1.3rem;
    height: 100vh;
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 1.5rem;
  }
  .not-logged a {
    background-color: rgb(35, 212, 35);
    color: #eee;
    padding: 0.5rem 1rem;
    border-radius: 5px;
  }
  .c-7 {
    margin-top: 7rem;
    display: flex;
    justify-content: center;
  }
  .c-7-container {
    display: flex;
    flex-direction: column;
    padding: 2rem;
    background-color: #eee;
    border-radius: 5px;
    width: 90%;
  }
  .c-7-user {
    display: flex;
    gap: 3rem;
    padding: 1.5rem;
    border: 1px solid;
  }
  .c-7-user-info {
    display: flex;
    flex-direction: column;
    gap: 0.5rem;
    justify-content: center;
  }
  .c-7-user-info button {
    background: #ec3a3a;
    color: white;
    font-size: 1rem;
    padding: 0.5rem 1rem;
  }
  .c-7-user i {
    font-size: 7rem;
  }
  .c-7 h2 {
    margin: 2.5rem 0;
  }
  .c-7-user-libri {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 5rem;
    padding: 3rem;
    min-height: 27rem;
  }
  .c-7-user-libri img {
    width: 14rem;
    height: 20rem;
    object-fit: contain;
  }
  .libro-btn .elimina {
    background-color: #ff5757;
  }
  .info-agg {
    font-weight: 700;
    margin-top: 0.4rem;
  }
  .fa-bookmark {
    position: absolute;
    top: 0;
    left: 1.5rem;
    padding: 0.5rem;
    font-size: 1.3rem;
  }
</style>