<template>
  <div>
    <div>
      My Room
    </div>
    <div class="room-box" v-for="(slide,idx) in items" :key="idx">
      <!-- <Carousel :items-to-show="3.0"> -->
        <!-- <Slide v-for="(slide,idx) in items" :key="idx"> -->
            <RoomCard/>
        <!-- </Slide> -->
      <!-- </Carousel> -->
    </div>
  </div>
</template>

<script>
import router from '@/router';
import RoomCard from './RoomCard.vue';
import { Carousel, Slide } from "vue3-carousel";
import room_1 from "./Img/Room_image/room_img1.png"
import room_2 from "./Img/Room_image/room_img2.png";
import room_3 from "./Img/Room_image/room_img3.png";

import 'vue3-carousel/dist/carousel.css'

export default {
    name: 'GameRoomPage',
    data(){
     return{
      roomId : "1",
      width: 0,
      height: 0,
      size : 'lg',
      items: [room_1, room_2, room_3]
    }
    },
    components:{
      RoomCard,
      Carousel,
      Slide,
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