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
            <textarea type="text" placeholder="설명을 입력해주세요.(300자)" maxlength="300"></textarea>
        </div>
        <div class="final-control">
            <div class="back-control" @click.prevent="$emit('closeWrite')">뒤로가기</div>
            <div class="submit-control" @click.prevent="">제출하기</div>
        </div>
    </div>
  </div>

</template>

<script>
export default {
    name : "WriteContent",
    data(){
        return{
            imgData : Array,
            imgSrc : Array,
            selectRoom : String,
            step : 0,
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