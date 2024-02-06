<template>
    <div class="feed-title-box">
      <div @click="reload" class="reset-icon"></div>
      <div class="invite-code-title">{{ roomData.title }}</div>
      <div class="create-icon" @click="create=true"></div>
    </div>
    <div class="feed-content">
      <div>

      </div>
    </div>
    <FeedCreate v-if="create" @close="complete"/>
</template>

<script>
import { useAuthStore } from '@/store/authStore';
import FeedCreate from '../FeedPage/FeedCreate.vue';
import { useFeedStore } from '@/store/feedStore';
export default {
    name:'RoomFeedStart',
    data(){
        return{
            create: false,
            feedList:[]
        }
    },
    props:{
        roomData : Object,

    },
    components:{
        FeedCreate
    },
    methods:{
        complete : function(value){
                this.create = false
                if(value == 1){
                    window.location.reload()
                    //emit으로 글 작성 완료되었다는 이벤트 왔을 시 현 화면 새로고침
                }
        },

        
    }
}
</script>

<style scoped src="./css/RoomFeedStart.css">

</style>