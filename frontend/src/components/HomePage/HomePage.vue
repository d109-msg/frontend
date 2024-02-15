<template>
  <body :class="{'body-light': !isDarkMode, 'body-dark': isDarkMode}">
    <div :class="{'container-light':!isDarkMode, 'container-dark':isDarkMode}">
      <NavBarVue :page="page" :is-dark-mode="isDarkMode"/>
      <router-view @page-change="change" :is-dark-mode="isDarkMode" ></router-view>

    </div>
  </body>
</template>

<script>
import NavBarVue from '@/components/NavBar/NavBar.vue'
import MainPage from '../MainPage/MainPage.vue';
import { useChatStore } from '@/store/chatStore';
import { useAuthStore } from '@/store/authStore';
import { initializeApp } from "firebase/app"
import {getMessaging, getToken } from 'firebase/messaging';

    const firebaseConfig = {
        apiKey: "AIzaSyA93yQXg5CPPHWPwLRPwAcE7IgtUMb4Uww",
        authDomain: "msg-project-e8fba.firebaseapp.com",
        projectId: "msg-project-e8fba",
        storageBucket: "msg-project-e8fba.appspot.com",
        messagingSenderId: "930302126573",
        appId: "1:930302126573:web:4ae373f9480381c3429fe4",
        measurementId: "G-6KZ18EXPFY"
    };

    const app = initializeApp(firebaseConfig);
    let messaging = null
    if (typeof window !== "undefined" && typeof window.navigator !== "undefined") {
    messaging = getMessaging(app);

}


export default {
  name:"HomePage",
  data(){
    return{
      page : '',
      data : [],

    }
  },
  props:{
    isDarkMode : Boolean
  },
  computed:{
      isConnect(){
        const chat = useChatStore()
        return chat.getConnect
      },

    },
    watch:{
      isConnect(nv,ov){
        const chat = useChatStore()
          if(nv == true){
            try{
              chat.sub(this.data)
              chat.notifyConnect()
              console.log('완료')
            }catch(err){
              console.log('조짐')
            }
          }else{
            console.log('연결이 왜 해제되었나요...')
          }
      }
    },
  methods:{

    change : function(value){
      this.page = value
    },
    startPage : async function(){
      const auth = useAuthStore()
      const chat = useChatStore()
      if(auth.getAccess === ""){
        return
      }else{
        try{
          await auth.useRefresh()
          let value = await chat.userRoom()
          this.data = value.data
          this.requestPermission();
          await chat.makeConnect()

        }catch(err){
          console.log('에러내용',err)
        }
      }
    },
    requestPermission : async function(){
        Notification.requestPermission().then(permission => {
            if (permission == "granted") {
                console.log("알림 권한이 허용되었습니다.");
                this.registerServiceWorker();
            }else {
                console.error("알림 권한이 거부되었습니다.")
            }
        })
      },
      registerServiceWorker(){
        if ('serviceWorker' in navigator) {
            navigator.serviceWorker.register('/firebase-messaging-sw.js').then(registration => {
              console.log('Service Worker 등록 성공:', registration.scope);
              this.initFirebase();
            }).catch(err => {
              console.error('Service Worker 등록 실패:', err);
        });
        } else {
            console.error("이 브라우저는 Service Worker를 지원하지 않습니다.");
        }
      },
      initFirebase(){
        getToken(messaging, {
            vapidKey: "BHgNzGf0e7bm-3Ier0lO7tHGhTStQ2mCihIo4rARSWLQuCT_S44FLwYztTsINJMK3u9sKMYHM3XUSXEzRI_EGCE",
        }).then( (currentToken) => {
            if(currentToken) {
                console.log(currentToken)
                // 서버로 token 값 전송
                const auth = useAuthStore()
                auth.registerFCMToken(currentToken);

            }else {
                console.log("No registration token available. Request permission to generate one.");
            }
        })
        .catch((err) => {
            console.log("An error occurred while retrieving token. ", err);
        });
      },
  },
  components:{
      NavBarVue,
      MainPage
  },
  mounted(){
    this.startPage()
  }
 
}
</script>


<style scoped src="./Homepage.css">
  
</style>
