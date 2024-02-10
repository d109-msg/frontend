<template>
    <div class="feed-container">
        <div class="first-col" v-if="size!='xs'">
            <div class="feed-create">
                <div class="create-container">
                    <img class="create-img" :src="userImage" v-if="isLogin">
                    <img class="create-img" src="./Icon/default.png" v-if="!isLogin">
                    <div class="create-comment">What are you thinking?</div>
                </div>
                <div class="create-btn-box">
                    <div class="create-btn"
                    @click="create=true"
                    v-if="isLogin"
                    >Create</div>
                    <div class="create-btn"
                    @click="goLogin"
                    v-if="!isLogin"
                    >Login</div>
                </div>
            </div>
            <template  v-for="(feed,idx) in feedList" :key="idx">
                <div v-if="idx%2===1">
                    <Feed :id="idx" v-if="idx%2===1" :item="feed"
                    ></Feed>
                </div>
            </template>
        </div>
        <div class="second-col" v-if="size != 'xs'">
            <template  v-for="(feed,idx) in feedList" :key="idx" >
                <div v-if="idx%2===0">
                    <Feed :id="idx" v-if="idx%2===0" :item="feed"
                    ></Feed>                    
                </div>
            </template>
            <FeedCreate v-if="create" @close="complete"/>
        </div>
        <div class="first-col" v-else>
            <div class="feed-create">
                <div class="create-container">
                    <img class="create-img" :src="userImage" v-if="isLogin">
                    <img class="create-img" src="./Icon/default.png" v-if="!isLogin">
                    <div class="create-comment">What are you thinking?</div>
                </div>
                <div class="create-btn-box">
                    <div class="create-btn"
                    @click="create=true"
                    v-if="isLogin"
                    >Create</div>
                    <div class="create-btn"
                    @click="goLogin"
                    v-if="!isLogin"
                    >Login</div>
                </div>
            </div>
            <template  v-for="(feed,idx) in feedList" :key="idx">
                <div>
                    <Feed :id="idx" :item="feed"
                    ></Feed>
                </div>
            </template>
            <FeedCreate v-if="create" @close="complete"/>
        </div>
    </div>
</template>

<script>
    import Feed from './Feed.vue';
    import FeedCreate from './FeedCreate.vue';
    import { useFeedStore } from '@/store/feedStore';
    import { useAuthStore } from '@/store/authStore';
import router from '@/router';
// const server =  'https://i10d109.p.ssafy.io/api'
// const server2 = 'http://localhost:8080/api'
import servers from '@/server';
const server2 = 'https://i10d109.p.ssafy.io/api'

    export default {
        name: "FeedPage",

        data(){
            return{
                col : 2,
                colSapce : 16,
                arr : [1,2,3],
                create : false,
                baseUrl : `${servers}/article/feed`,
                feedList : [],
                detailFlag : false,
                last : {},
                io : {},
                item: {},
                userImage: "",
                isLogin : false,
                userInfo : {},
                nextUrl : "",
                guestio : {},
                guestUrl : `${servers}/article/guest`,
                nextGuestUrl : "",
            }
        },  

        methods: {
            getUser : async function(){
                const auth = useAuthStore()
                try{
                    await auth.useRefresh()
                    let value = await auth.getUser()
                    this.userInfo = value.data
                    auth.setUserInfo(this.userInfo)
                    this.userImage = value.data.imageUrl
                }catch(err){
                    // await auth.logout()
                }
            },
            readFeed : async function(){
                
            },
            axiosRead : async function(){
                if(this.baseUrl != null){
                    const feed = useFeedStore()
                    let value = await feed.readFeed(this.baseUrl+this.nextUrl)
                    if(value.data != ""){
                        value.data.articleDetailDtos.forEach(item=>{
                            this.feedList.push(item)
                        })
                        this.nextUrl = value.data.nextUrl
                        this.$nextTick(()=>{
                            if(this.nextUrl !== null && this.feedList.length != 0){
                                const last = document.getElementById(`${this.feedList.length-1}`)
                                this.io.observe(last)
                            }
                        })
                    }   else{
                                this.axiosGuest()
                            }
                } else{
                    this.axiosGuest()
                }
                },

            callBack : function(items,io){
               items.forEach(async item=>{
                if(item.isIntersecting){
                    this.io.unobserve(item.target)
                    await this.axiosRead()
                }
               })
            },
            complete : function(value){
                this.create = false
                if(value == 1){
                    window.location.reload()
                    //emit으로 글 작성 완료되었다는 이벤트 왔을 시 현 화면 새로고침
                }
            },
            axiosGuest : async function(){
                if(this.guestUrl != null){
                    const feed = useFeedStore()
                    let value = await feed.guestFeed(this.guestUrl+this.nextGuestUrl)
                    if(value.data != ""){
                        value.data['articles'].forEach(article=>{
                            this.feedList.push(article)
                        })
                    
                    this.nextGuestUrl = value.data.nextUrl
                    this.$nextTick(()=>{
                        const last = document.getElementById(`${this.feedList.length-1}`)
                        if(this.guestUrl !== null){
                            this.guestio.observe(last)
                        }
                    })
                }
                    // const last = document.getElementById(`${this.feedList.length-1}`)
                    // console.log(last)
                    // this.guestio.observe(last)
                } else{
                    console.log('피드 끝!!!')
                }
            },
            guestCall : function(items,io){
                items.forEach(async(item)=>{
                    if(item.isIntersecting){
                        // console.log(this.guestUrl)
                        this.guestio.unobserve(item.target)
                        await this.axiosGuest()
                    }
                })

            },
            goLogin : function(){
                router.push('/login')
            },
            startPage : async function(){
                const auth = useAuthStore()
                    if(auth.getAccess == ""){
                        this.isLogin = false
                        auth.reset()
                        await this.axiosGuest()
                        //Access 토큰이 비어있음 -> 유저 처리 자체가 비로그인 상태 게스트피드 호출시킴
                    } else{
                        await this.getUser()
                        this.isLogin = true
                        await this.axiosRead()
                        //로그인 상태 시 유저 피드 호출
                    }
            }
            
        },
        props:{
            size : String,
        },
        components: {
            Feed,
            FeedCreate,
        },
        watch:{
            userInfo(nv,ov){
                this.$emit('userInfo',nv)
            }
        },
        mounted(){
            const auth = useAuthStore()
            this.io = new IntersectionObserver(this.callBack,{ threshold : 0.7})
            this.guestio = new IntersectionObserver(this.guestCall,{threshold : 0.7})
            //요소의 가시성을 0.7로 설정, 요소가 70% 뷰포트에 가시 될 시 지정한 callBack 함수 실행
            this.startPage()
        },
        created(){
        },
    }
</script>

<style scoped src="./css/FeedPage.css">
.container{
    width: 1000px;
}
</style>