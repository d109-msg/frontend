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
      roomId : "1",
      width: 0,
      height: 0,
      size : 'lg'
     }
    },
    components:{
      RoomCard,
    },
    methods:{
      enterRoom : function(){
        router.push(`/game/${this.roomId}`)
      },
      handleResize(event) {
            this.width = window.innerWidth;
            this.height = window.innerHeight;
      },
      reactiveSize : function(){
            const viewportWidth = window.innerWidth
            if (viewportWidth<860) {
                    this.size =  "xs"
                }
                else if (viewportWidth >= 860 && viewportWidth < 1200) {
                    this.size = "md"}
                else {this.size = "lg"}
            window.addEventListener('resize', this.handleResize);
        },

    },
    beforeDestroy() {
        // console.log("beforeDestroy...");
        window.removeEventListener('resize', this.handleResize);
    },
    mounted(){
      this.reactiveSize()

    },
    watch:{
      width(){
            if (this.width<440) {
                this.size =  "xs"
            }
            else if (this.width >= 440 && this.width < 1100) {
                this.size = "md"}
            else {this.size = "lg"}
        },
    }

}
</script>

<style src="./css/GameRoomPage.css">

</style>