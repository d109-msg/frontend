<template>
  <div>
    <LoadingSpinner/>
  </div>
</template>

<script>
import router from '@/router'
import LoadingSpinner from './LoadingSpinner/LoadingSpinner.vue'
import { useAuthStore } from '@/store/authStore'
import axios from 'axios'
export default {
    name : "SocialSign",
    data(){
        return{
            code : this.$route.query.code,
            provider : this.$route.params.id,
            state : "http://localhost:80/"
        }
    },
    components: {
        LoadingSpinner
    },
    mounted(){
        // console.log(this.code)
        axios.get(`http://localhost:8080/user/sign-in/oauth2/${this.provider}?code=${this.code}`,{
            headers:{"Content-Type": `application/json`}
        })
        .then(res=>{
            const auth = useAuthStore()
            auth.setAccess(res.data.accessToken)
            auth.setRefresh(res.data.refreshToken)
            router.push('/')          
        })
        .catch(err => {
            alert('에러가 발생했습니다. 이미 존재하는 이메일 혹은 계정입니다.')
            router.push('/login')
        })
    }
}
</script>

<style>

</style>