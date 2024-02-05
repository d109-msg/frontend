<template>
  <div class="card-body">
      <!-- <img  class="card-img" :src="slide" alt=""> -->
      <div class="card-content"></div>
      <div class="card-title-box">
          <div class="card-title"></div>
      </div>
      <div class="card-job-icon"></div>
  </div>
</template>

<script>
export default {
    name: 'RoomCard',
    props:{
        slide : String,
    }
}
</script>

<style src="./css/RoomCard.css">

</style>