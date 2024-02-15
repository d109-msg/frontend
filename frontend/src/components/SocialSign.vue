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
import { toast} from 'vue3-toastify'
import "vue3-toastify/dist/index.css"

export default {
    name : "SocialSign",
    data(){
        return{
            code : this.$route.query.code,
            provider : this.$route.params.id,
        }
    },
    components: {
        LoadingSpinner
    },
    mounted(){
        // console.log(this.code)
        axios.get(`https://i10d109.p.ssafy.io/api/user/sign-in/oauth2/${this.provider}?code=${this.code}`,{
            headers:{"Content-Type": `application/json`}
        })
        .then(res=>{
            const auth = useAuthStore()
            auth.setAccess(res.data.accessToken)
            auth.setRefresh(res.data.refreshToken)
            router.push('/')          
        })
        .catch(err => {
            toast('에러가 발생했습니다. 이미 존재하는 이메일 혹은 계정입니다.',{
                        theme : "auto",
                        "type": "error",
                        "pauseOnHover": false,
                        "position": "top-center",
                        "transition": "slide",
                        "autoClose": 1000,
                    })
            router.push('/login')
        })
    }
}
</script>

<style>

</style>