<template>
  <div>
    <router-view></router-view>
  </div>
</template>

<script>
import { useMeta } from 'vue-meta'



export default {
  name: 'App',
  


  setup(){



// 메타데이터 설정(open graph)
useMeta({
  title: "마피아 SNS 게임 - MSG",
  meta: [
  {
    vmid: 'description',
    name: 'description',
    content:
      'Mafia sns game',
  },
  {
    vmid: 'keywords',
    name: 'keywords',
    content: 'MSG',
  },
  {
    vmid: 'author',
    name: 'author',
    content: '',
  },
  ]}) // end useMeta



}
}
</script>

<style>
@import './assets/css/reset.css';
body{
  background-color: #FFF8ED;
  scroll-snap-type: y mandatory;
}


</style>
