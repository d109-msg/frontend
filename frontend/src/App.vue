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
  title: "SNS 음식 공유 플랫폼",
  meta: [
  {
    vmid: 'description',
    name: 'description',
    content:
      'HONEYCOMB로 음식 조합을 공유하고 자랑해보세요',
  },
  {
    vmid: 'keywords',
    name: 'keywords',
    content: 'Eat, Share, HONEYCOMB',
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

</style>
