<template>
  <body>
    <div :class="{'body-light':!auth.getMode, 'body-dark' : auth.getMode}">
      <router-view :is-dark-mode="auth.getMode"></router-view>
      <div class="theme-switch-wrapper">
        <label class="theme-switch" for="checkbox">
          <input type="checkbox" id="checkbox" @change="toggleTheme" :checked="auth.getMode"/>
          <div class="slider round"></div>
        </label>
      </div>
    </div>
  </body>

</template>

<script>
import { useMeta } from 'vue-meta'
import SockJS from "sockjs-client"
import Stomp from 'webstomp-client'
import { useChatStore } from './store/chatStore'
import { useAuthStore } from './store/authStore'

export default {
  name: 'App',
  data(){
    return{
      stompClient : {},
      auth : useAuthStore()
    }
  },
  computed:{
    isDarkMode(){
      return useAuthStore().getMode
    }
  },
  methods:{
    toggleTheme(target) {
            
            // 토글 상태 변경
            this.auth.turnMode(!this.auth.getMode)
            // this.emitter.emit('isMode', this.isDarkMode)
    },
  },
  watch:{
    isDarkMode(nv,ov){
      if (nv) {
                document.body.classList.add('body-dark');
            } else {
                document.body.classList.remove('body-dark');
            }
    }
  },
  mounted() {
    this.emitter.on('nightChange',(e)=>{
      this.auth.turnMode(e)
    })
    const mode = this.auth.getMode
    if(mode){
      document.body.classList.add('body-dark')
    }else{
      document.body.classList.remove('body-dark')
    }
  },
  setup(){

// 메타데이터 설정(open graph)
useMeta({
  title: "마피아 SNS 게임 - MSG",
  meta: [
  {
    vmid: 'description',
    name: 'description',
    content:
      'Mafia sns game',
  },
  {
    vmid: 'keywords',
    name: 'keywords',
    content: 'MSG',
  },
  {
    vmid: 'author',
    name: 'author',
    content: '',
  },
  ]}) // end useMeta



}
}
</script>

<style>
@import './assets/css/reset.css';
body{
  background-color: #FFF8ED;
  scroll-snap-type: y mandatory;
}

.body-dark{
  background-color: #212020;
}

@media  screen and (max-width:860px) {
  .theme-switch-wrapper {
        position: absolute;
        z-index: 990;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 20px;
        top: 0;
    }
    
    .theme-switch {
        display: inline-block;
        height: 34px;
        position: relative;
        width: 60px;
    }
    
    .theme-switch input {
        display:none;
    }
    
    .slider {
        /* background-color: #F0C401; */
        /* background: url('./Img/background_toggle_day.png'); */
        background: url('./assets/css/Icon/background_toggle_day.png');
        background-size: contain;
        width: 100%;
        bottom: 0;
        cursor: pointer;
        left: 0;
        position: absolute;
        right: 0;
        top: 0;
        transition: .4s;
    }
    
    .slider:before {
        background-color: #fff;
        bottom: 4px;
        content: "";
        height: 26px;
        left: 4px;
        position: absolute;
        transition: .4s;
        width: 26px;
    }
    
    input:checked + .slider {
        background-color: #2C2836;
        background: url('./assets/css/Icon/background_toggle_night.png');
        background-size: contain;
    
    }
    
    input:checked + .slider:before {
        transform: translateX(26px);
    }
    
    .slider.round {
        border-radius: 34px;
    
    }
    
    .slider.round:before {
        border-radius: 50%;
    }
}
@media  screen and (min-width:861px) {
  .theme-switch-wrapper {
        position: absolute;
        z-index: 990;
        display: flex;
        align-items: center;
        justify-content: center;
        margin: 20px;
        top: 80px;
    }
    
    .theme-switch {
        display: inline-block;
        height: 34px;
        position: relative;
        width: 60px;
    }
    
    .theme-switch input {
        display:none;
    }
    
    .slider {
        /* background-color: #F0C401; */
        /* background: url('./Img/background_toggle_day.png'); */
        background: url('./assets/css/Icon/background_toggle_day.png');
        background-size: contain;
        width: 100%;
        bottom: 0;
        cursor: pointer;
        left: 0;
        position: absolute;
        right: 0;
        top: 0;
        transition: .4s;
    }
    
    .slider:before {
        background-color: #fff;
        bottom: 4px;
        content: "";
        height: 26px;
        left: 4px;
        position: absolute;
        transition: .4s;
        width: 26px;
    }
    
    input:checked + .slider {
        background-color: #2C2836;
        background: url('./assets/css/Icon/background_toggle_night.png');
        background-size: contain;
    
    }
    
    input:checked + .slider:before {
        transform: translateX(26px);
    }
    
    .slider.round {
        border-radius: 34px;
    
    }
    
    .slider.round:before {
        border-radius: 50%;
    }
}

</style>
