<template>
    <div>
    <LoadingSpinner v-if="spinnerFlag"/>
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
                                v-model="checkFlag"
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
</div>
</template>

<script>
import btof from './base64ToFile'
import axios from 'axios'
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner.vue'
export default {
    name: "MissionConfirm",
    data(){
        return{
            dataImg : Object,
            previewImg : "",
            roomName : "",
            missionInfo : "",
            spinnerFlag : false,
            checkFlag : false,
            answer : Object,
        }
    },
    components:{
        LoadingSpinner,
    },
    methods:{
        closeImage : function(){
            this.$emit("closeModal")
        },
        axiosAi : function(){
            this.spinnerFlag = true
            let formData = new FormData()
            formData.append('image',this.dataImg)
            axios.post(`http://localhost:8080/article/analyze?condition=${"와인"}`,formData,{
            headers:{"Content-Type": `multipart/form-data`}
            }).then(res=>{
                this.answer = res.data.choices[0].message.content
                this.spinnerFlag = false
                this.checkFlag = false
                
                if(this.answer.includes("True")){
                    this.$emit("missionTrue")
                } else{
                    this.$emit("missionFalse")
                }
            })
            .catch(err=>{
                this.spinnerFlag = false
                this.checkFlag = false
                alert("서버에 오류가 발생했습니다.")
                this.$emit("closeModal")
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

<style scoped src="./css/MissionConfirm.css">

</style>