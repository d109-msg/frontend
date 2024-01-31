<template>
  <div>
    <div>
      My Room
    </div>
    <div class="room-box">
      <RoomCard></RoomCard>
      <RoomCard></RoomCard>
      <RoomCard></RoomCard>
    </div>
  </div>
</template>

<script>
import RoomCard from './RoomCard.vue';
export default {
    name: 'GameRoomPage',
    components:{
      RoomCard,
    }
}
</script>

<style src="./css/GameRoomPage.css">

</style>