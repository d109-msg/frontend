<template>
    <!-- 웹 버전 -->
    <div class="nav-container" >
        <div   v-if="size == 'lg'" class="lg-style" >
            <div class="nav-logo"></div>
            <div class="right-bar" >
                <div class="tag"  @click="$router.push('/')" id="/">HOME</div>
                <div class="tag"  @click="$router.push('/game')"  id="/game">GAME</div>
                <div class="tag" @click="$router.push('/message')"  id="/message">MESSAGE</div>
                <div class="tag"  @click="$router.push('/mypage')"  id="/mypage">MYPAGE</div>
                <div class="search-container">
                    <input type="text" class="search-bar" placeholder="검색" maxlength="30" v-model="keyword">
                    <div class="search-icon"></div>
                    <div class="search-result" v-if="searchFlag">

                    </div>
                </div>
            </div>
        </div>

        <!-- 태블릿 버전 -->
        <div v-if="size == 'md'"  class="md-style">
            <div class="nav-logo" ></div>

            <div class="right-bar">
                <div class="tag"  @click="$router.push('/')" id="/">HOME</div>
                <div class="tag"  @click="$router.push('/game')"  id="/game">GAME</div>
                <div class="tag" @click="$router.push('/message')"  id="/message">MESSAGE</div>
                <div class="tag"  @click="$router.push('/mypage')"  id="/mypage">MYPAGE</div>
                <div class="search-container">
                    <input type="text" class="search-bar" placeholder="검색" maxlength="30" >
                    <div class="search-icon"></div>
                </div>
            </div>
        </div>

        <div  v-if="size == 'xs'" class="xs-style">
            <div class="right-bar">
                <img class="tag" src="./Img/icon_home.png" alt=""  @click="$router.push('/')" id="/" style="height: 40px; width: 40px;">
                <!-- <div class="tag" >HOME</div> -->
                <img class="tag" src="./Img/icon_game.png" alt=""  @click="$router.push('/game')" id="/game" style="height: 35px; width: 35px;">
                <img class="tag" src="./Img/icon_message.png" alt=""  @click="$router.push('/message')" id="/message" style="height: 45px; width: 45px;">
                <img class="tag" src="./Img/icon_mypage.png" alt=""  @click="$router.push('/mypage')" id="/mypage" style="height: 50px; width: 50px;">
            </div>
        </div>
    </div>



</template>

<script>
import router from '@/router'
import { useAuthStore } from '@/store/authStore';

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
            io : {},
            searchFlag : false,
        }
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
        tagClick : function(){
            const list = document.querySelectorAll('.tag')
            this.emitter.on('pageChange',(value)=>{
            if(this.step == 0){
                list[value].classList.add('tag-click')
                this.prev = value
                this.step+=1
            } else{
                list[this.prev].classList.remove('tag-click')
                list[value].classList.add('tag-click')
                this.prev = value
            }
            })
        },
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
                alert('로그인이 필요한 기능입니다.')
                this.keyword = ""
            } else{
                try{
                    let value = await auth.searchUser(this.baseUrl,this.keyword,this.offset)
                    if(value.data.searchResult.length > 0){
                        value.data.searchResult.forEach(item=>{
                            this.userInfo.push(item)
                        })
                        if(this.userInfo.length != 0 && this.userInfo.length%10 ==0){
                            const target = document.getElementById(`${this.userInfo.length-1}`)
                            this.io.observe(target)
                            this.baseUrl = value.data.nextUrl
                        }
                    }
                }catch(err){
                }
            }
        },
        ioLogic : function(items,i0){
            items.forEach(async (item)=>{
                await this.searchUser()
                this.io.unobserve(item.target)
            })
        },
        refereshTest : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
        }

    },
    beforeDestroy() {
        // console.log("beforeDestroy...");
        window.removeEventListener('resize', this.handleResize);
    },

    mounted() {
        this.io = new IntersectionObserver(this.ioLogic,{threshold:0.8})
        this.reactiveSize()
        this.tagClick()
        this.manageScroll()
    },
    watch:{
        width(){
            if (this.width<860) {
                this.size =  "xs"
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
                this.io = new IntersectionObserver(this.ioLogic,{threshold:0.8})
            }
        }


    }
}


</script>



<style scoped src="./NavBar.css">

</style>