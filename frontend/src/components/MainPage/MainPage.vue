<template>
    <!-- 메인 페이지 -->
    <div>
        <div class="main-banner-box">
            <div :class="{'main-banner-light': !isDarkMode, 'main-banner-dark': isDarkMode}">
                <!-- <div  :class="{'main-banner-light': isDarkMode, 'main-banner-dark': !isDarkMode}"> -->

            </div>
        </div>
        <div class="main-container">
            <!-- 추후 컴포넌트로 분리 가능(피드) -->
            <FeedPage @user-info="sendUser" :size="size" :is-dark-mode="isDarkMode"/>

            <!-- 추후 컴포넌트로 분리 가능(작은 프로필) -->
            <MiniProfile :userInfo="user" :is-dark-mode="isDarkMode"
            v-if="size == 'lg'"
            />

        </div>
    </div>
</template>

<script>
import FeedPage from '../FeedPage/FeedPage.vue'
import MiniProfile from '../MiniProfile/MiniProfile.vue'

export default {
    name : 'MainPage',
    data(){
        return {
            prevScrollY : '',
            user : {},
            size : "",
            width : 0,
            height : "",
        }
    },
    components :{
        MiniProfile,
        FeedPage,
    },
    props:{
        isDarkMode : Boolean
    },
    methods:{

        sendUser: function(data){
            this.user = data
        },
        handleResize(event) {
            this.width = window.innerWidth;
            this.height = window.innerHeight;
        },
        reactiveSize : function(){
            const viewportWidth = window.innerWidth
            if (viewportWidth<1070) {
                    this.size =  "xs"
                }
                else if (viewportWidth >= 1070 && viewportWidth < 1440
                ) {
                    this.size = "md"}
                else {this.size = "lg"}
            window.addEventListener('resize', this.handleResize);
        },
    },  
    watch:{
        width(nv,ov){
            if(nv<1070){
                this.size = "xs"
            } else if(nv >= 1070 && nv < 1440){
                this.size = "md"

            }else{
                this.size = "lg"
            }
        }
    },
    mounted(){
        this.emitter.emit('pageChange',0)
        this.reactiveSize()




    }
}
</script>

<style scoped src="./MainPage.css">

</style>