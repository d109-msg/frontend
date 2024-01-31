<template>
  <div>
    <div>
      My Room
    </div>
    <div class="room-box">
      <RoomCard
      @click="enterRoom"
      />
      <RoomCard></RoomCard>
      <RoomCard></RoomCard>
      <RoomCard></RoomCard>
      <RoomCard></RoomCard>
      <RoomCard></RoomCard>
    </div>
  </div>
</template>

<script>
import router from '@/router';
import RoomCard from './RoomCard.vue';
export default {
    name: 'GameRoomPage',
    data(){
     return{
      roomId : "1"
     }
    },
    components:{
      RoomCard,
    },
    methods:{
      enterRoom : function(){
        router.push(`/game/${this.roomId}`)
      }
    }
}
</script>

<style src="./css/GameRoomPage.css">

</style>