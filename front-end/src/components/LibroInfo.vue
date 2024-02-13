<template>
  <div class="c-2" @click.self="close">
    <div class="libro-info">
      <i class="fa-solid fa-x si-x" style="color: #ff5757;" @click="close"></i>
      <img :src="require(`../assets/copertina/${infoAggiornate.id}.jpg`)" :alt="infoAggiornate.titolo">
      <div class="sezione-info">
        <span class="si-titolo">{{infoAggiornate.titolo}}</span>
        <span class="si-autore">di {{infoAggiornate.autore}}</span>
        <span class="si-rating">
          <i class="fa-solid fa-star" style="color: #FFD43B;" v-for="s in infoAggiornate.rating" :key="s"></i>
          <i class="fa-solid fa-star" style="color: #b3b2ad" v-for="s in (5 - infoAggiornate.rating)" :key="s"></i>
        </span>
        <span class="si-data">Data di pubblicazione: {{infoAggiornate.data_pubblicazione}}</span>
        <span class="si-isbn">Codice ISBN: {{infoAggiornate.codice_ISBN}} | Pagine: {{infoAggiornate.numero_pagine}}</span>
        <span>Trama:</span>
        <textarea v-model="infoAggiornate.trama" readonly></textarea>
        <div class="si-btn">
          <button class="l" @click="toogleCarousel(infoAggiornate.id)" v-if="logged && !home">Leggi</button>
        </div>
      </div>
    </div>
  </div>
  <div v-if="carousel">
    <PageCarouselVue :arrPagine="infoAggiornate.pagine" @toogleCaro="toogleCarousel"/>
  </div>
</template>

<script>
import PageCarouselVue from './PageCarousel.vue'

export default {
  props : ["info", "logged", "home"],
  emits : ["toogle", "updateLettura"],
  components : {PageCarouselVue},
  data() {
    return {
      lib : {
        id : 0,
        titolo : "",
        autore : "",
        codice_ISBN : 0,
        data_aggiunta : "",
        data_eliminazione : "",
        trama : "",
        numero_letture : 0,
        rating : 0,
        pagine : [],
        evidenza : false,
        data_pubblicazione : "",
        numero_pagine : 0
      },
      carousel : false
    }
  },
  methods : {
    close() {
      this.$emit("toogle")
      document.querySelector("body").classList.remove("body-prevent-scroll")
    },
    toogleCarousel(id) {
      if(!this.carousel) {
        this.$emit("updateLettura", id)
      }
      this.carousel = !this.carousel
    }
  },
  computed : {
    infoAggiornate() {
      if(this.info.id) {
        this.lib = this.info
      }
      document.querySelector("body").classList.add("body-prevent-scroll")
      return this.lib
    }
  }
}
</script>

<style>
  .body-prevent-scroll {
    overflow: hidden;
  }
  .c-2 {
    background-color: rgba(0, 0, 0, 0.7);
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    align-items: center;
    justify-content: center;
    z-index: 2;
  }
  .libro-info {
    display: flex;
    background-color: #eee;
    border-radius: 5px;
    padding: 2rem;
    gap: 2rem;
    position: relative;
  }
  .si-x {
    position: absolute;
    right: 0.3rem;
    top: 0.3rem;
    padding: 0.5rem;
  }
  .libro-info img {
    width: 16rem;
    height: 22rem;
    object-fit: contain;
  }
  .sezione-info {
    display: flex;
    flex-direction: column;
    min-width: 18rem;
    gap: 0.2rem;
  }
  .si-titolo {
    font-weight: 700;
    font-size: 1.2rem;
  }
  .si-autore, .si-isbn, .si-data {
    font-size: 0.9rem;
  }
  .sezione-info textarea {
    max-width: 100%;
    height: 100%;
    resize: none;
    font-family: Arial, Helvetica, sans-serif;
    font-size: 1rem;
  }
  .si-btn {
    margin-top: 0.5rem;
  }
  .si-btn .l {
    padding: 0.5rem 1rem;
    color: white;
    font-size: 1rem;
  }
  .si-btn .l {
    background-color: gold;
  }
</style>