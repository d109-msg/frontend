<template>
  <div class="myfeed-box">
    <p class="myfeed-title"> 
      <img src="./Img/icon_myfeed.png" alt="">
      My Feed
    </p>
    <div class="myfeed-content" >
      <div class="content" v-for="(item,key) in myFeed" :key="key">
        <img :src="item.url" class="img" @click="onDetail(item)">
      </div>
    </div>
    <DetailPage v-if="detailFlag"
        @close-detail="offDetail"
        :idx="detail.articleId"
        />
  </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
import { useFeedStore } from '@/store/feedStore'
import DetailPage from '../DetailPage/DetailPage.vue'
export default {
    name: 'UserFeed',
    data(){
      return{
        myFeed : [],
        myInfo : {},
        detailFlag : false,
        detail : {}
      }
    },
    props:{
      id : Number,
      size : String,
    },
    methods:{
      getFeed : async function(){
      const feed = useFeedStore()
      try{
        
        let value = await feed.getUserProfile(this.$route.params.id)
        this.myFeed = value.data
      } catch(error) {
      }
    },
      offDetail : function(){
        this.detailFlag = false
      },
      onDetail : function(item){
        this.detail = item
        this.detailFlag = true
      }
    },
    components:{
      DetailPage
    },
    mounted(){
      const auth = useAuthStore()
      this.myInfo = auth.getUserInfo
      this.getFeed()
    },
    
    
}
</script>

<style scoped src="./css/UserFeed.css">

</style>