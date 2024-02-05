<template>
  <div class="messagelist-container">
    <div class="messagelist-title-box">
        <p class="messagelist-title">Message List</p>
        <img class="messagelist-title-icon" src="./Img/icon_plus.png" alt="" @click="showList">
        <div class="list-back" v-if="listFlag" @click.self="listFlag=false">
          <div class="list-modal">
            <div class="search-container">
                    <input type="text" class="search-bar" placeholder="search" maxlength="30" >
                    <div class="search-icon"></div>
            </div>
            <div class="search-result">
              <div class="result" v-for="(item,key) in userList" :key="key" >
                  <img :src="item.imageUrl" class="profile-img" :id="key">
                  <div class="info">
                    <span>
                      {{ item.nickname}}
                    </span>
                    <p>
                      plz add bio
                    </p>
                  </div>
                  <div class="button-box">
                    <img src="./Img/icon_plus.png" class="make-message">
                  </div>
              </div>
            </div>
          </div>
        </div>
    </div>
    <div class="messagelist-content">

            
        </div>
  </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
import { nextTick } from 'vue'
export default {
    name: 'MessageList',
    data(){
      return{
        listFlag : false,
        userList : [],
        baseUrl : "http://localhost:8080/api/user/follow?type=from",
        io : {},
      }
    },
    methods:{
      showList : function(){
        this.listFlag = true
        this.getFollowing()
      },
      getFollowing : async function(){
        const auth = useAuthStore()
        await auth.useRefresh()
        if(this.baseUrl != null){
          try{
            let value = await auth.searchFollowing(this.baseUrl)
            if(value.data != ""){
              value.data.followUserList.forEach(item=>{
                this.userList.push(item)
              })
            }
            this.baseUrl = value.data.nextUrl
            this.$nextTick(()=>{
              if(this.baseUrl != null){
                const last = document.getElementById(`${this.userList.length-1}`)
                console.log(this.baseUrl)
                this.io.observe(last)
              } 
            })
          }catch(err){
            console.log('조짐')
          }
        }
      },
      ioCall : function(items,io){
        items.forEach(async item=>{
          if(item.isIntersecting){
            this.io.unobserve(item.target)
            await this.getFollowing()
          }
        })
      }
    },
    mounted(){
      this.io = new IntersectionObserver(this.ioCall,{threshold:0.7})
    }
}
</script>

<style  scoped src="./css/MessageList.css">

</style>