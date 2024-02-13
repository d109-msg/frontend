<template>
    <div :class="{'my-mini':!isDarkMode,'my-mini-dark':isDarkMode}"> 
        <div class="notification-back" @click.self="showFlag = false" v-if="showFlag">
            <div class="notification-content">
                <div class="notification-title">
                    <span class="notification-title">My Notification</span>
                </div>
                <div class="notification-body">
                    <div v-for="(item,key) in chat.notify" :key="key">
                        <div class="profile" @click="visit(key,item.id)">
                            <div class="profile-box">
                                <img :src="item.imageUrl" alt="" class="profile-img">
                                <div class="profile-content"><span>{{item.fromNickname}}</span>{{ item.content }}</div>
                            </div>
                            <div class="create-time">{{ item.createTime }}</div>
                            <!-- {{ item }} -->
                            <!-- <span class="create-time">{{ item.createTime }}</span> -->
                            
                        </div>
                    </div>

                </div>
            </div>
        </div>
        <div class="mini-title">
            <div class="mini-title-message">
                <span style="position: relative; cursor: pointer;" v-if="notify>0"
                    @click="showFlag = true"
                    >
                    My Profile 
                    <div class="notify" >{{ notify }}</div>
                </span>
                <span v-else>
                    My Profile 
                </span>
            </div>
            <img class="logout" src="./logout.png" v-if="isLogin" @click="logout" @mouseover="toolFlag=true" @mouseleave="toolFlag=false">
            <div class="tool-tip" v-if="toolFlag"><span>Logout</span></div>
        </div>
        <div class="my-mini-content" v-if="Object.keys(auth.getUserInfo).length > 0">
            <img class="mini-image" :src="auth.getUserInfo.imageUrl">
            <div :class="{'mini-name':!isDarkMode,'mini-name-dark':isDarkMode}">{{ auth.getUserInfo.nickname }}</div>
            <div :class="{'mini-comment':!isDarkMode,'mini-comment-dark':isDarkMode}">{{ auth.getUserInfo.bio }}</div>
            <div class="mini-following-count">
                <span class="following">Following</span>
                <span class="count">{{ auth.getUserInfo.followingCount }}</span>
            </div>
            <div class="mini-following-count">
                <span class="following">Followers</span>
                <span class="count">{{ auth.getUserInfo.followerCount }}</span>
            </div>
        </div>
        <div class="my-mini-content" v-else>
            <img class="mini-image" src="./default.png">
            <div :class="{'mini-name':!isDarkMode,'mini-name-dark':isDarkMode}">Anonymous</div>
            <div :class="{'mini-comment':!isDarkMode,'mini-comment-dark':isDarkMode}">Play the Mafia game!!!</div>
            <div class="mini-following-count">
                <span class="following">Following</span>
                <span class="count">0</span>
            </div>
            <div class="mini-following-count">
                <span class="following">Followers</span>
                <span class="count">0</span>
            </div>
        </div>
        <DetailPage v-if="detailFlag" @close-detail="detailFlag=false" :idx="idx" style="z-index: 999999999;"/>
    </div>
</template>

<script>
import { useAuthStore } from '@/store/authStore'
import { useChatStore } from '@/store/chatStore'
import DetailPage from '../DetailPage/DetailPage.vue'
export default {
    name: 'MiniProfile',
    data(){
        return{
            isLogin : false,
            toolFlag : false,
            user : {},
            userFlag : false,
            chat : useChatStore(),
            notifyLength : "0",
            showFlag : false,
            detailFlag : false,
            idx : 1,
            auth : useAuthStore()
        }
    },
    components:{
        DetailPage
    },
    computed:{
        notify(){
            return this.chat.notify.length
        }
    },
    watch:{
        notify(nv,ov){
        }
    },
    methods:{
        
        logout : function(){
            const auth = useAuthStore()
            auth.logout()
            window.location.reload()
        },
        visit : async function(idx,roomId){
            try{
                this.idx = this.chat.notify[idx].articleId
                if(idx != 0){
                    this.detailFlag = true
                }
                const chat = useChatStore()
                await chat.readNotification(roomId)
                this.chat.notify.splice(idx,1)
                if(idx == 0){
                    router.push({name: 'game'})
                }
            }catch(err){
                console.log(err)
                console.log('읽음처리가 정상적으로 종료되지 않음')
            }
        },
      
    },
    props:{
        userInfo : Object,
        isDarkMode : Boolean
    },

    watch:{
        userInfo(nv,ov){
            Object.keys(nv).forEach(item=>{
                if(item == "imageUrl"){
                    this.userFlag = true
                }
            })
            
        }
    },
    mounted(){
        const auth = useAuthStore()
        if(auth.getAccess == ""){
            this.isLogin = false
            //로그인 여부를 getAccess의 토큰 여부로 검색, isLogin을 일종의 플래그로 둠 이를 통해 위의 :src(이미지)에서 비로그인일 시 undefined 오류 방지
        } else{
            this.isLogin = true
            //로그인 여부를 getAccess의 토큰 여부로 검색, isLogin을 일종의 플래그로 둠 이를 통해 위의 :src(이미지)에서 비로그인일 시 undefined 오류 방지
        }

    }
}
</script>

<style scoped src="./MiniProfile.css">

</style>