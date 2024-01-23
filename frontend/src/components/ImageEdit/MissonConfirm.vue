<template>
    <div class="mission-gray">
        <div class="mission-container">
            
                <div class="confirm-box">
                    <img class="img-box" :src="previewImg">
                    <div class="content-box">
                        <div class="confirm">
                            <p class="check-style">선택한 미션과 본인 미션을 확인해주세요</p>
                            <p class="check-style-room">방 : {{ roomName }}</p>
                            <p class="check-style-mission">미션 : {{ missionInfo }}</p>
                        </div>
                        <div class="call-confirm">
                            <p class="">해당 사진으로 등록하시겠습니까?</p>
                            <div class="is-picture">
                                <input type="checkbox" name="" id="" class="check"
                                @click="axiosAi"
                                >
                                <span>미션 성공여부 확인하기</span>
                            </div>
                        </div>
                        
                    </div>
                </div>
                <span class="cancel" @click="closeImage">X</span>
        </div>
    </div>  
</template>

<script>
import btof from './base64ToFile'
import axios from 'axios'
export default {
    name: "MissionConfirm",
    data(){
        return{
            dataImg : Object,
            previewImg : "",
            roomName : "",
            missionInfo : "",
        }
    },
    methods:{
        closeImage : function(){
            this.$emit("closeModal")
        },
        axiosAi : function(){
            let formData = new FormData()
            formData.append('image',this.dataImg)
            axios.post(`http://localhost:8080/article/analyze?condition=${"강아지"}`,formData,{
            headers:{"Content-Type": `multipart/form-data`}
            }).then(res=>{
                console.log(res)
            })
            .catch(err=>{
                console.log(err)
            })
        }
    },
    props:{
        confirmInfo : Array,
    },
    mounted(){
        this.previewImg=this.$props.confirmInfo[1]
        this.roomName = this.$props.confirmInfo[2]
        this.dataImg = btof(this.previewImg,'temp.png')
    }
}
</script>

<style scoped src="./MissionConfirm.css">

</style>