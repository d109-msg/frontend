<template>
  <div class="myfeed-box"> 
    <div style="display: flex; justify-content: space-between;">
      <div >
        <p class="myfeed-title" @click="goGameRecord">
          <img src="./Img/icon_myfeed.png" alt="">
          My Feed
        </p>
      </div>
    </div>

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
    name: 'MyFeed',
    data(){
      return{
        myFeed : [],
        myInfo : {},
        detailFlag : false,
        detail : {}
      }
    },
    props:{
      pageNum : String,
      size : String,
    },
    watch: {
      size(){
        if(this.size=="xs"){

        }
      }
    },
    methods:{
      getFeed : async function(){
      const feed = useFeedStore()
      try{
        let value = await feed.getUserProfile(this.myInfo.id)
        this.myFeed = value.data
        console.log(this.myFeed)
      } catch(error) {
        console.log(error)
      }
    },
      offDetail : function(){
        this.detailFlag = false
      },
      onDetail : function(item){
        this.detail = item
        this.detailFlag = true
      },
      goGameRecord(){
        this.$emit('changePage', '2')

      },
      goMyfeed(){
        this.$emit('changePage', '1')
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

<style scoped src="./css/MyFeed.css">

</style>