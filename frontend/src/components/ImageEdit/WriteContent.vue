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
            <div class="submit-control" @click.prevent="createFeed">제출하기</div>
        </div>
    </div>
  </div>

</template>

<script>
import axios from 'axios'
import store from '@/store/modules/loginStore'
export default {
    name : "WriteContent",
    data(){
        return{
            imgData : Array,
            imgSrc : Array,
            selectRoom : String,
            step : 0,
            content : "",
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
        createFeed : function(){
            const data = new FormData()
            data.append('content',this.content)
            data.append('roomId',"") //차후에 추가
            let accessToken = store.getters.accessToken
            console.log(accessToken)
            //토큰도 차후 vuex에서 다룰 것
            this.imgData.forEach(img=>{
                data.append('articleImageList',img)
            })
            axios.post('http://localhost:8080/article/create',data,{
                headers: {
                    'Content-Type' : 'multipart/form-data',
                    Authorization : `Bearer ${accessToken}`
                }
            }).then(res=>{
                this.$emit('createFeed')
            }).catch(err=>{
                console.log('실패',err)
            })
        }
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