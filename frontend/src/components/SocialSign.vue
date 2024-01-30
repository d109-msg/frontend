<template>
  <div>
    {{ $route }}
  </div>
</template>

<script>
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
    mounted(){
        // console.log(this.code)
        axios.get(`http://localhost:8080/user/sign-in/oauth2/${this.provider}?code=${this.code}`,{
            headers:{"Content-Type": `application/json`}
        })
        .then(res=>{    
            console.log(res,"성공")
        })
        .catch(err => {
            console.log(err,"에러")
        })
    }
}
</script>

<style>

</style>