<template>
  <div id="hero-section">
    <img src="../assets/hero.jpg" alt="hero">
  </div>
  <div v-if="showLibroInfo">
    <LibroInfoVue :info="libroInfObj" :logged="logged" home="true" @toogle="toogleInfo" />
  </div>
  <div class="c-1">
    <h2>Novità da non perdere</h2>
    <div class="evidenza">
      <div class="libro" v-for="libro in libri.slice().filter(lib => lib.evidenza)" :key="libro.id">
        <img :src="require(`../assets/copertina/${libro.id}.jpg`)" :alt="libro.titolo">
        <span class="libro-titolo">{{libro.titolo}}</span>
        <span class="libro-autore">di {{libro.autore}}</span>
        <span class="libro-rating">
          <i class="fa-solid fa-star" style="color: #FFD43B;" v-for="s in libro.rating" :key="s"></i>
          <i class="fa-solid fa-star" style="color: #b3b2ad" v-for="s in (5 - libro.rating)" :key="s"></i>
        </span>
        <div class="libro-btn">
          <button class="info" @click="mostraInfo(libro.id)">Info</button>
          <button class="aggiungi" @click="aggiungiLibro(libro)" v-if="logged && !libro.data_aggiunta">Aggiungi</button>
          <button class="aggiungi" @click="ricordaLogin" v-if="!logged">Aggiungi</button>
          <button class="aggiunto" v-if="libro.data_aggiunta">Aggiunto</button>
        </div>
      </div>
    </div>
  </div>
  <div class="c-5">
    <h2>Tutti i libri</h2>
    <div class="filtri">
      Filtra per:
      <select v-model="param_1">
        <option value="def">Default</option>
        <option value="rating">Rating</option>
        <option value="data_p" >Data pubblicazione</option>
        <option value="n_pagine">Pagine</option>
      </select>
      <div v-if="param_1 != 'def'">
        <div class="up-down" v-if="param_2">
          <button @click="toogleParam2"><i class="fa-solid fa-arrow-up-wide-short fa-lg"></i></button>
          Crescente
        </div>
        <div class="up-down" v-else>
          <button @click="toogleParam2"><i class="fa-solid fa-arrow-down-wide-short fa-lg"></i></button>
          Decrescente
        </div>
      </div>
    </div>
    <div class="tutti-libri">
      <div class="libro-2" v-for="libro in sortedLibri" :key="libro.id">
        <img :src="require(`../assets/copertina/${libro.id}.jpg`)" :alt="libro.titolo">
        <span class="libro-titolo">{{libro.titolo}}</span>
        <span class="libro-autore">di {{libro.autore}}</span>
        <span class="libro-rating">
          <i class="fa-solid fa-star" style="color: #FFD43B;" v-for="s in libro.rating" :key="s"></i>
          <i class="fa-solid fa-star" style="color: #b3b2ad" v-for="s in (5 - libro.rating)" :key="s"></i>
        </span>
        <div class="libro-btn">
          <button class="info" @click="mostraInfo(libro.id)">Info</button>
          <button class="aggiungi" @click="aggiungiLibro(libro)" v-if="logged && !libro.data_aggiunta">Aggiungi</button>
          <button class="aggiungi" @click="ricordaLogin" v-if="!logged">Aggiungi</button>
          <button class="aggiunto" v-if="libro.data_aggiunta">Aggiunto</button>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import LibroInfoVue from '@/components/LibroInfo.vue'

export default {
 components : {LibroInfoVue},
 props : ["logged", "user"],
 emits : ["msg", "accesso", "cancellaUtente"],
  data() {
    return {
      libri : [],
      libroInfObj : {},
      showLibroInfo : false,
      param_1 : "def",
      param_2 : true
    }
  },
  methods : {
    mostraInfo(id) {
      this.libroInfObj = this.libri.filter(lib => lib.id == id)[0]
      this.toogleInfo()
    },
    toogleInfo() {
      this.showLibroInfo = !this.showLibroInfo
    },
    toogleParam2() {
      this.param_2 = !this.param_2
    },
    aggiungiLibro(libro) {
      const url = `http://localhost:9010/api/utentiLibri/${this.user.id}`
      fetch(url)
      .then(res => {
        return res.json()
      })
      .then(data => {
        const time = new Date()
        let dataAggiunta = `${time.getFullYear()}-${time.getMonth() + 1}-${time.getDate()}`
        libro.data_aggiunta = dataAggiunta
        let parsedListaLibri = JSON.parse(data.listaLibri)
        parsedListaLibri.push(libro)
        data.listaLibri = JSON.stringify(parsedListaLibri)
        let notFound = true
        let index = null
        let parsedListaLibriEliminati = JSON.parse(data.listaLibriEliminati)
        for(let i = 0; i < parsedListaLibriEliminati.length && notFound; i++) {
          if(parsedListaLibriEliminati[i].id == libro.id) {
            index = i
            notFound = false
          }
        }
        if(index != null) {
          parsedListaLibriEliminati.splice(index, 1)
        }
        data.listaLibriEliminati = JSON.stringify(parsedListaLibriEliminati)
        fetch(url, {
          method: "PUT",
          headers: {
              'Content-type': 'application/json'
          },
          body: JSON.stringify(data)
        })
        .then(res => { return res.json() })
        .then(this.$emit("msg", "Aggiunto alla libreria", false))
      })
      .catch(e => {
        console.log(e)
      })
    },
    ricordaLogin() {
      this.$emit("msg", "Devi prima fare il login", true)
    },
    refreshDispLibri() {
      if(this.logged) {
        const url = `http://localhost:9010/api/utentiLibri/${this.user.id}`
        fetch(url)
        .then(res => {
          return res.json()
        })
        .then(data => {
          let userListaLibri = JSON.parse(data.listaLibri)
          let notFound = true
          for(let i = 0; i < userListaLibri.length; i++) {
            for(let j = 0; j < this.libri.length && notFound; j++) {
              if(this.libri[j].id == userListaLibri[i].id) {
                this.libri[j].data_aggiunta = userListaLibri[i].data_aggiunta
                notFound = false
              }
            }
            notFound = true
          }
        })
        .catch(e => {
          console.log(e)
        })
      }
    }
  },
  computed : {
    sortedLibri() {
      let s_arr = this.libri.slice()
      if(this.param_1 == "rating") {
        s_arr.sort((a, b) => a.rating - b.rating);
        if(!this.param_2) {
          s_arr.reverse()
        }
      }
      else if(this.param_1 == "data_p") {
        s_arr.sort((a, b) => new Date(a.data_pubblicazione) - new Date(b.data_pubblicazione));
        if(!this.param_2) {
          s_arr.reverse()
        }
      }
      else if(this.param_1 == "n_pagine") {
        s_arr.sort((a, b) => a.numero_pagine - b.numero_pagine);
        if(!this.param_2) {
          s_arr.reverse()
        }
      }
      return s_arr
    }
  },
  created() {
    const url = "http://localhost:9010/api/libri"
    fetch(url)
    .then(res => {
      return res.json()
    })
    .then(data => {
      let randomLibro = []
      while(randomLibro.length < 4) {
        let r = Math.floor(Math.random() * data.length)
        if(randomLibro.indexOf(r) == -1) {
          randomLibro.push(r)
        }
      }
      randomLibro.forEach(libro => {
        data[libro].evidenza = true
      });
      this.libri = data
      this.libri.forEach(lib => {
        lib.pagine = JSON.parse(lib.pagine)
      })

      this.refreshDispLibri()
    })
    .catch(e => {
      console.log(e)
    })
  }
}
</script>

<style>
  #hero-section {
    height: 100vh;
  }
  #hero-section img {
    height: 100%;
    width: 100%;
    object-fit: cover;
  }
  .c-1 {
    padding-top: 3rem;
  }
  h2 {
    color: #ff5757;
    font-size: 2rem;
    margin: 0;
    text-align: center;
  }
  .evidenza, .tutti-libri {
    display: flex;
    flex-wrap: wrap;
    justify-content: center;
    gap: 5rem;
    padding: 3rem;
  }
  .libro, .libro-2 {
    display: flex;
    flex-direction: column;
    align-items: center;
    width: 18rem;
  }
  .libro img {
    width: 16rem;
    height: 22rem;
    object-fit: contain;
  }
  .libro-titolo {
    height: 2.5rem;
    display: flex;
    justify-content: center;
    align-items: center;
    text-align: center;
    align-items: center;
    overflow: hidden;
    padding: 0.5rem 0;
    font-weight: 700;
  }
  .libro-autore {
    font-size: 0.8rem;
    max-height: 1rem;
    overflow: hidden;
  }
  .libro-rating {
    margin-top: 0.5rem;
  }
  .libro-btn {
    margin-top: 1rem;
    display: flex;
    gap: 2rem;
  }
  .libro-btn button {
    width: 5.5rem;
    color: white;
    padding: 0.5rem;
    font-size: 1rem;
  }
  .info {
    background-color: rgb(86, 86, 231);
  }
  .aggiungi, .aggiunto {
    background: rgb(21, 185, 21);
  }
  .aggiunto {
    filter: brightness(0.7);
    cursor: not-allowed;
  }
  .c-5 {
    margin-top: 1rem;
  }
  .filtri {
    margin-top: 2rem;
    background-color: #e83e3e;
    padding: 1rem;
    display: flex;
    align-items: center;
    gap: 2rem;
    color: #eee;
    font-weight: 700;
  }
  .filtri button {
    padding: 0.5rem 1rem;
    background-color: #eee;
  }
  .up-down {
    color: #eee;
    display: flex;
    gap: 2rem;
    align-items: center;
  }
  select, option {
    font-size: 1rem;
    font-family:Arial, Helvetica, sans-serif;
    padding: 0.5rem 1rem;
    border: none;
    border-radius: 5px;
    background-color: #eee;
  }
  select:focus, input:focus {
    outline: none;
  }
  .tutti-libri img {
    width: 14rem;
    height: 20rem;
    object-fit: contain;
  }
  .libro-2 {
    width: 16rem;
  }
</style>