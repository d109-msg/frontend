<template>
  <div class="card-body">
      <!-- <img  class="card-img" :src="slide" alt=""> -->
      <!-- <div class="card-content"></div> -->
        <img class="card-content" :src="room.imageUrl" alt="">
      <div v-if="room.dataType == '그룹'"  class="card-title-box friend-group">
          <div class="card-title">{{ room.title }} </div>
         
      </div>
      <div v-else class="card-title-box random-group">
          <div class="card-title">{{ room.title }}</div>
      </div>
      <div class="card-job-icon"></div>
  </div>
</template>

<script>
export default {
    name: 'RoomCard',
    data(){
      
    },
    props:{
        slide : String,
        room : Object,
    },

}
</script>

<style src="./css/RoomCard.css">

</style>