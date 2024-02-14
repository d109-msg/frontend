<template>
    <div>
    <LoadingSpinner v-if="spinnerFlag" style="z-index: 9999999;"/>
        <div class="mission-gray">
        
        <div class="mission-container">
            <div class="cancel-box">
                <span class="cancel" @click="closeImage"></span>
            </div>
            <div class="confirm-box">
                <img class="img-box" :src="previewImg">
                <div class="content-box">
                    <div class="confirm">
                        <p class="check-style">선택한 미션과 본인 미션을 확인해주세요</p>
                        <p class="check-style-room">방 : {{ roomName }}</p>
                        <p class="check-style-mission">미션 : {{ missionInfo }}</p>
                    </div>
                    <div class="call-confirm">
                        <p class="check-title">해당 사진으로 등록하시겠습니까?</p>
                        <div class="is-picture">
                            <input type="checkbox" name="" id="" class="check"
                            v-model="checkFlag"
                            @click="axiosAi"
                            >
                            <span class="check-title" >미션 성공여부 확인하기</span>
                        </div>
                    </div>
                    
                </div>
            </div>
        </div>
    </div>  
</div>
</template>

<script>
import btof from './base64ToFile'
import axios from 'axios'
import LoadingSpinner from '../LoadingSpinner/LoadingSpinner.vue'
import { useFeedStore } from '@/store/feedStore'
import { useAuthStore } from '@/store/authStore'
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
            feedStore : useFeedStore(),
            authStore : useAuthStore(),
        }
    },
    components:{
        LoadingSpinner,
    },
    methods:{
        closeImage : function(){
            this.$emit("closeModal")
        },

        processResponse: function(value) {
        this.answer = value.data.result
        if (this.answer == true) {
            this.$emit("missionTrue");
        } else {
            this.$emit("missionFalse");
        }},

        axiosAi : async function(){
            try{
                this.spinnerFlag = true
                const value = await this.feedStore.missionConfirm(this.dataImg,this.missionInfo)
                this.processResponse(value)
            } catch(err){
                await this.authStore.useRefresh()
                try{
                    let value = await this.feedStore.missionConfirm(this.dataImg,this.missionInfo)
                    this.processResponse(value)
                } catch(err){
                    alert("서버에 오류가 발생했습니다.")
                    this.$emit("closeModal")
                } finally {
                    this.spinnerFlag = false
                    this.checkFlag = false
                }
            }
        }
    },
    props:{
        confirmInfo : Array,
        mission : String,
        isDarkMode : Boolean
    },
    mounted(){
        this.missionInfo = useFeedStore().getMission
        this.previewImg=this.$props.confirmInfo[1]
        this.roomName = this.$props.confirmInfo[2]
        this.dataImg = btof(this.previewImg,'temp.png')
    }
}
</script>

<style scoped src="./css/MissionConfirm.css">

</style>