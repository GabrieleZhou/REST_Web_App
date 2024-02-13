<template>
  <div class="c-4" @click.self="$emit('toogleCaro')">
    <div class="carousel">
      <div class="c-top">
        <i class="fa-solid fa-arrow-left fa-2xl" style="color: #ff5757;" @click="prev"></i>
        <img :src="require(`../assets/pagine/${arrPagine[currentIndex]}.jpg`)" :alt="arrPagine[currentIndex]">
        <i class="fa-solid fa-arrow-right fa-2xl" style="color: #ff5757;" @click="next"></i>
      </div>
      <span>{{currentIndex + 1}}/{{arrPagine.length}}</span>
    </div>
  </div>
</template>

<script>
export default {
  props : ["arrPagine"],
  emits : ["toogleCaro"],
  data() {
    return {
      currentIndex : 0
    }
  },
  methods : {
    prev() {
      if(this.currentIndex > 0) {
        this.currentIndex--
      }
    },
    next() {
      if(this.currentIndex < this.arrPagine.length - 1) {
        this.currentIndex++
      }
    }
  }
}
</script>

<style>
  .c-4 {
    position: fixed;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    display: flex;
    justify-content: center;
    background-color: rgba(41, 38, 38, 0.9);
    z-index: 3;
  }
  .carousel {
    display: flex;
    flex-direction: column;
    align-items: center;
    justify-content: center;
    gap: 1rem;
    color: white;
    font-size: 1.2rem;
  }
  .c-top {
    display: flex;
    gap: 3rem;
    align-items: center;
  }
  .c-top i {
    padding: 2rem;
  }
  .c-top img{
    width: 28rem;
    height: 42rem;
    object-fit: contain;
  }
</style>