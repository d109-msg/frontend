<template>
    <!-- 웹 버전 -->
    <div class="nav-container" :class="{'nav-container-light': !isDarkMode, 'nav-container-dark': isDarkMode}" >
        <div   v-if="size == 'lg'" class="lg-style" >
            <div :class="{'nav-logo-light':!isDarkMode, 'nav-logo-dark': isDarkMode}"  @click="$router.push('/')" id="/" style="cursor: pointer;"></div>
            <div class="right-bar" >
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"  @click="$router.push('/')" id="/">HOME</div>
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"   @click="$router.push('/game')"  id="/game">GAME</div>
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"  @click="$router.push('/message')"  id="/message">MESSAGE</div>
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"   @click="$router.push('/mypage')"  id="/mypage">MYPAGE</div>
                <div class="search-container">
                    <input type="text" class="search-bar" placeholder="검색" maxlength="30" v-model="keyword" @focus="searchFlag=true">
                    <div class="search-icon"></div>
                    <div class="search-result" v-if="searchFlag">
                        <div class="result-info" v-for="(item,key) in userInfo" :key="key"
                        @click.self="onFollowFlag(key)"
                        >
                            <img class="comment-img" :src="item.imageUrl" @click.self="onFollowFlag(key)">
                            <div class="info-container" :id="key">
                                <p class="comment-nick" @click.self="onFollowFlag(key)">{{ item.nickname }}</p>
                                <p @click.self="onFollowFlag(key)"> {{ item.content }}</p>
                            </div>
                            <div class="plus-box" v-if="followFlag[key]">
                                <div @click="followUser(item.userId,key)" v-if="isFollow[key]==0">
                                    <img src="./Icon/add.png" alt="" >
                                    팔로우 하기
                                </div>
                                <div @click="followUser(item.userId,key)" v-if="isFollow[key]==1">
                                    <img src="./Icon/remove.png" alt="" >
                                    팔로우 취소
                                </div>
                                <div @click="userProfile(item.userId,key)">
                                    <img src="./Icon/share.png" alt="" style="height: 18px;">
                                    페이지 방문하기
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </div>

        <!-- 태블릿 버전 -->
        <div v-else-if="size == 'md'"  class="md-style" style="cursor: pointer;">
            <div :class="{'nav-logo':!isDarkMode, 'nav-logo-dark':isDarkMode}" @click="$router.push('/')" id="/" ></div>

            <div class="right-bar">
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"  @click="$router.push('/')" id="/">HOME</div>
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"   @click="$router.push('/game')"  id="/game">GAME</div>
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"  @click="$router.push('/message')"  id="/message">MESSAGE</div>
                <div :class="{'tag': !isDarkMode, 'tag-dark':isDarkMode}"   @click="$router.push('/mypage')"  id="/mypage">MYPAGE</div>
                <div class="search-container">
                    <input type="text" class="search-bar" placeholder="검색" maxlength="30" >
                    <div class="search-icon"></div>
                </div>
            </div>
        </div>

        <!-- 모바일 버전 -->
        <div  v-else-if="size == 'xs'" class="xs-style">
            <div class="right-bar">
                <img v-if="!isDarkMode" class="tag" src="./Img/icon_home.png" alt=""  @click="$router.push('/')" id="/" style="height: 40px; width: 40px;">
                <img v-else class="tag" src="./Img/icon_home._darkpng.png" alt=""  @click="$router.push('/')" id="/" style="height: 40px; width: 40px;">

                <!-- <div class="tag" >HOME</div> -->
                <img v-if="!isDarkMode" class="tag" src="./Img/icon_game.png" alt=""  @click="$router.push('/game')" id="/game" style="height: 35px; width: 35px;">
                <img v-else class="tag" src="./Img/icon_game_dark.png" alt=""  @click="$router.push('/game')" id="/" style="height: 35px; width: 35px;">

                <img v-if="!isDarkMode" class="tag" src="./Img/icon_message.png" alt=""  @click="$router.push('/message')" id="/message" style="height: 45px; width: 45px;">
                <img v-else class="tag" src="./Img/icon_message_dark.png" alt=""  @click="$router.push('/message')" id="/" style="height: 45px; width: 45px;">

                <img v-if="!isDarkMode" class="tag" src="./Img/icon_mypage.png" alt=""  @click="$router.push('/mypage')" id="/mypage" style="height: 50px; width: 50px;">
                <img v-else class="tag" src="./Img/icon_mypage_dark.png" alt=""  @click="$router.push('/mypage')" id="/" style="height: 50px; width: 50px;">

            </div>
        </div>
    </div>



</template>

<script>
import router from '@/router'
import { useAuthStore } from '@/store/authStore';
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

export default {    
    name : 'NavBar',
    data(){
        return{
            selectedTag : Object,
            prevScrollY : '',
            getId : '/',
            prev : null,
            step : 0,
            prev : -1,
            width: 0,
            height: 0,
            size : 'lg',
            keyword : "",
            offset : 0,
            baseUrl : "",
            userInfo : [],
            searchFlag : false,
            followFlag : [],
            isFollow : [],
        }
    },
    props:{
        isDarkMode: Boolean
    },
    methods : {
        handleResize(event) {
            this.width = window.innerWidth;
            this.height = window.innerHeight;
        },
        reactiveSize : function(){
            const viewportWidth = window.innerWidth
            if (viewportWidth<860) {
                    this.size =  "xs"
                }
                else if (viewportWidth >= 860 && viewportWidth < 1200) {
                    this.size = "md"}
                else {this.size = "lg"}
            window.addEventListener('resize', this.handleResize);
        },
        // tagClick : function(){
        //     const list = document.querySelectorAll('.tag')
        //     this.emitter.on('pageChange',(value)=>{
        //     if(this.step == 0){
        //         list[value].classList.add('tag-click')
        //         this.prev = value
        //         this.step+=1
        //     } else{
        //         list[this.prev].classList.remove('tag-click')
        //         list[value].classList.add('tag-click')
        //         this.prev = value
        //     }
        //     })
        // },
        manageScroll : function(){
            let navContainer = document.querySelector('.nav-container')
            this.prevScrollY = window.scrollY
            window.addEventListener('scroll',()=>{
            this.searchFlag = false
            let nowScrollY = window.scrollY
            if(this.prevScrollY < nowScrollY){
                navContainer.classList.remove('nav-up-event')
                navContainer.classList.add('nav-down-event')
            }else{
                navContainer.classList.add('nav-up-event')
                navContainer.classList.remove('nav-down-event')
            }
            this.prevScrollY = nowScrollY
            })
        },
        searchUser : async function(){
            const auth  = useAuthStore()
            if(auth.getAccess==""){
                toast('로그인이 필요한 기능입니다.',{
                    theme : "auto",
                    "type": "warning",
                    "pauseOnHover": false,
                    "position": "top-center",
                    "transition": "slide",
                    "autoClose": 1000,
                })
                this.keyword = ""
            } else{
                try{
                    let value = await auth.searchUser(this.baseUrl,this.keyword,this.offset)
                    this.isFollow = []
                    if(value.data.searchResult.length > 0){
                        value.data.searchResult.forEach(item=>{
                            this.userInfo.push(item)
                            this.followFlag.push(false)
                            this.isFollow.push(item.isFollow)
                        })
                    }
                }catch(err){
                }
            }
        },
        followUser : async function(userId,idx){
            const auth = useAuthStore()
            try{
                await auth.useRefresh()
                let value = await auth.follow(userId)
                this.isFollow[idx] = !this.isFollow[idx]
            }catch(err){
                console.log(err)
            }

        },
        refereshTest : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
        },
        userProfile : function(idx,key){
            router.push(`/user/${idx}`)
            this.followFlag[key] = false
            this.searchFlag = false
        },
        onFollowFlag(value){
            this.followFlag = this.followFlag.map(item => item = false)
            //map을 통해 새로운 복사된 배열 제공
            console.log(this.followFlag)
            this.followFlag[value] = !(this.followFlag[value])
        }

    },
    beforeDestroy() {
        // console.log("beforeDestroy...");
        window.removeEventListener('resize', this.handleResize);
    },

    mounted() {
        this.reactiveSize()
        this.manageScroll()
    },
    watch:{
        width(){
            if (this.width<860) {
                this.size =  "xs"
                console.log(this.size)
            }
            else if (this.width >= 860 && this.width < 1200) {
                this.size = "md"}
            else {this.size = "lg"}
        },
        keyword(){
            if(this.keyword != ""){
                this.searchUser()
                this.baseUrl = ""
                this.userInfo = []
                this.searchFlag = true
            }
        }


    }
}


</script>



<style scoped src="./NavBar.css">

</style>