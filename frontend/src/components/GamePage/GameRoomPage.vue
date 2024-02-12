<template>
  <div :class="{'game-mid':!isDarkMode, 'game-mid-dark':isDarkMode}">
    <div class="game-room-page-title">
      My Room
    </div>
    <div class="room-box" >
      <!-- <Carousel :items-to-show="3.0"> -->
        <!-- <Slide v-for="(slide,idx) in items" :key="idx"> -->
            <RoomCard 
            v-for="(room,idx) in roomList" :key="idx"
            @click="enterRoom(room)" :room="room"/>
        <!-- </Slide> -->
      <!-- </Carousel> -->
    </div>
  </div>
</template>

<script>
import router from '@/router';
import RoomCard from './RoomCard.vue';
import { Carousel, Slide } from "vue3-carousel";


import 'vue3-carousel/dist/carousel.css'
import { useGameStore } from '@/store/gameStore';

export default {
    name: 'GameRoomPage',
    data(){
      return{
        roomId : "1",
        width: 0,
        height: 0,
        size : 'lg',
      }
    },
    components:{
      RoomCard,
      Carousel,
      Slide,
    },
    props:{
      roomList : Object,
      isDarkMode : Boolean
    },
    methods:{
      enterRoom : function(room){
        router.push({
                name:'room',
                params: {
                  data: JSON.stringify(room),
                  
                }
                
              }
              )
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

<style scoped  src="./css/GameRoomPage.css">

</style>