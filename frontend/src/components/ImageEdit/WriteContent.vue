<template>
  <div class="write-container">
    <div class="img-wrapper">
        
        <img class="write-img" :src="imgSrc[step]">
        <div class="left-arrow" @click.prevent="prevImg" v-if="imgSrc.length>1"></div>
        <div class="right-arrow" @click.prevent="nextImg" v-if="imgSrc.length>1"></div>
    </div>
    <div class="text-wrapper">
        <p>게시물 게시 범위 : {{ selectRoom }}</p>
        <p class="description">설명</p>
        <div class="description-text">
            <textarea type="text" placeholder="설명을 입력해주세요.(300자)" maxlength="300" v-model="content"></textarea>
        </div>
        <div class="final-control">
            <div class="back-control" @click.prevent="$emit('closeWrite')">뒤로가기</div>
            <div class="submit-control" @click.prevent="addFeed">제출하기</div>
        </div>
    </div>
  </div>

</template>

<script>
import axios from 'axios'
import { useAuthStore } from '@/store/authStore'
import router from '@/router'
import { useCookies } from 'vue3-cookies'
import { useFeedStore } from '@/store/feedStore'

export default {
    name : "WriteContent",
    data(){
        return{
            roomId : "",
            imgData : Array,
            imgSrc : Array,
            selectRoom : String,
            step : 0,
            content : "",
            feedStore : useFeedStore(),
            authStore : useAuthStore()
        }
    },
    props:{
        dataInfo : Object,
    },
    methods:{
        prevImg : function(){
            this.step--
            if(this.step<0) this.step = (this.imgSrc.length - 1)
        },
        nextImg : function(){
            this.step++
            if(this.step==this.imgSrc.length) this.step = 0
        },
        addFeed : async function(){
            try{
                await this.feedStore.axiosFeed(this.content,this.roomId,this.imgData)
            } catch {
                await this.authStore.useRefresh()
                await this.feedStore.axiosFeed(this.content,this.roomId,this.imgData)
                }
            this.$emit('createFeed')
        },
            },
    mounted(){
        this.imgData = this.$props.dataInfo['imgData']
        this.imgSrc = this.$props.dataInfo['imgSrc']
        this.selectRoom= this.$props.dataInfo['selectRoom']
    }
}
</script>

<style scoped src="./css/WriteContent.css">

</style>