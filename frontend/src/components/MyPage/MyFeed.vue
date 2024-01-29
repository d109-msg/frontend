<template>
  <div class="myfeed-box">
    <p class="myfeed-title"> 
      <img src="./Img/icon_myfeed.png" alt="">
      My Feed
    </p>
    <div class="myfeed-content" >
      <div class="content" v-for="i in [1,2,3,4,5,6,7,8,9,10]" :key="i">
        <div class="img"></div>
      </div>
    </div>
  </div>
</template>

<script>
export default {
    name: 'MyFeed'
}
</script>

<style scoped src="./css/MyFeed.css">

</style>