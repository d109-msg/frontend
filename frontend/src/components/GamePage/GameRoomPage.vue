<template>
  <div>
    <div>
      My Room
    </div>
    <div class="room-box">
      <Carousel :autoplay="3000" :wrap-around="true">
			<Slide v-for="slide in slides" :key="slide">
				<div class="carousel__item">
					<img  class="slideImg" :src="slide"  width="300px" height="200px"
          style="border: 1px red solid;"
          />
				</div>
			</Slide>
		</Carousel>



    </div>
  </div>
</template>

<script>
import router from '@/router';
import RoomCard from './RoomCard.vue';
import { Carousel, Slide } from "vue3-carousel";
import room_1 from "./Img/Room_image/room_img1.png";
import room_2 from "./Img/Room_image/room_img2.png";
import room_3 from "./Img/Room_image/room_img3.png";
import room_4 from "./Img/Room_image/room_img4.png";
import room_5 from "./Img/Room_image/room_img5.png";
import room_6 from "./Img/Room_image/room_img6.png";
import room_7 from "./Img/Room_image/room_img7.png";
import room_8 from "./Img/Room_image/room_img8.png";
import room_9 from "./Img/Room_image/room_img9.png";
import room_10 from "./Img/Room_image/room_img10.png";


export default {
    name: 'GameRoomPage',
    data(){
     return{
      roomId : "1",
      width: 0,
      height: 0,
      size : 'lg',
      slides: [room_1,room_2,room_3,room_4,room_5,room_6,room_7,room_8,room_9,room_10],
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