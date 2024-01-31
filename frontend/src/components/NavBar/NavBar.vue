<template>
    <div class="nav-container" >
        <div class="nav-logo"></div>
        <div class="right-bar">
            <div class="tag"  @click="$router.push('/')" id="/">HOME</div>
            <div class="tag"  @click="$router.push('/game')"  id="/game">GAME</div>
            <div class="tag" @click="$router.push('/message')"  id="/message">MESSAGE</div>
            <div class="tag"  @click="$router.push('/mypage')"  id="/mypage">MYPAGE</div>
            <div class="search-container">
                <input type="text" class="search-bar" placeholder="검색" maxlength="30">
                <div class="search-icon"></div>
            </div>
        </div>
    </div>
</template>

<script>
import router from '@/router'
import { usePageStore } from '@/store/pageStore'

export default {    
    name : 'NavBar',
    data(){
        return{
            selectedTag : Object,
            prevScrollY : '',
            getId : '/',
            basicUrl : usePageStore().getPage,
            prev : null,
            step : 0,
            prev : -1,
        }
    },
    methods : {
        // tagClick : function(place){
        //     this.selectedTag.classList.toggle('tag-click')
        //     const current = document.getElementById(place)
        //     current.classList.add('tag-click')
        //     this.selectedTag = current
        //     //라우터 추가 함수 여기에 넣으면 됩니다.
        //     router.push({path:place})
        // },

    },

    mounted() {
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
        

        // const basicUrl = window.location.pathname
        // for(let i=1; i<basicUrl.length;i++){
        //     this.getId += basicUrl.charAt(i)
        // }
        // this.selectedTag = document.getElementById(this.getId)
        // if(this.selectedTag==null){ //Home에서 잘못된 URI로 접근시, ex. router-view가 적용 되지 않은 local.../main 의 주소
        //     router.push({name:'main'})
        //     setTimeout(()=>{
        //         window.location.reload() // router push 이후 새로고침이 안되서 강제로 해줌
        //     },500)
        // }
        // this.selectedTag.classList.add('tag-click')
        let navContainer = document.querySelector('.nav-container')
        this.prevScrollY = window.scrollY
        window.addEventListener('scroll',()=>{
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
}


</script>



<style scoped src="./NavBar.css">

</style>