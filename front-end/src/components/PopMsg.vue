<template>
  <div class="c-3">
    <div class="pop pop-msg-red" v-if="red">
      <i class="fa-solid fa-x c-3-x" style="color: #c21717" @click="$emit('tooglePop')"></i>
      {{msg}}
    </div>
    <div class="pop pop-msg-green" v-else>
      <i class="fa-solid fa-x c-3-x" style="color: #1c9e41" @click="$emit('tooglePop')"></i>
      {{msg}}
    </div>
  </div>
</template>

<script>
export default {
  props : ["msg","red"]
}
</script>

<style>
  .c-3 {
    position: fixed;
    top: 4rem;
    display: flex;
    justify-content: center;
    align-items: center;
    width: 100%;
    z-index: 10;
    pointer-events: none;
  }
  .c-3-x {
    position: absolute;
    padding: 0.5rem;
    right: 0.3rem;
    top: 0.6rem;
    cursor: pointer;
  }
  .pop-msg-red, .pop-msg-green {
    padding: 1rem 4rem;
    border-radius: 5px;
    position: relative;
    pointer-events: auto;
  }
  .pop-msg-red {
    background-color: rgba(254, 145, 145, 0.9);
    color: red;
  }
  .pop-msg-green {
    background-color: rgba(132, 227, 132, 0.9);
    color: green;
  }
</style>