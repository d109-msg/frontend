import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"
import servers from "@/server"

// const cookies = useCookies().cookies
// const server =  'https://i10d109.p.ssafy.io/api'
// const server2 = 'http://localhost:8080/api'
const server = servers
const server2 = 'https://i10d109.p.ssafy.io/api'

export const useAuthStore = defineStore('auth',{
    state: ()=>({
        access: "",
        userInfo : {},
        refresh : "",
    }),
    getters: {
        getAccess: (state)=>{
            return state.access
        },
        getUserInfo: (state)=>{
            return state.userInfo
        }
    },
    actions: {
        reset(){
            this.access = ""
            this.userInfo = {}
            this.refresh = ""
        },
        async logout(){
            const headers = {Authorization: `Bearer ${this.refresh}`}
            try{
                axios.get(`${server}/user/sign-out`,{headers})
            } catch(err){
                console.log(err)
            }
            finally{
                this.reset()
            }
            // cookies.set('msgRefresh',"")
        },
        setAccess(token){
            this.access = token
        },
        setUserInfo(value){
            this.userInfo = value
        },
        setRefresh(token){
            this.refresh = token
            // cookies.set("msgRefresh",token, (60*60*24))
            //refresh 토큰의 갱신기간 하루(24시간)로 설정
        },
        async useRefresh(){
            // const refresh = cookies.get('msgRefresh')
            try{
            let value = await axios.get(`${server}/user/refresh`,{
                headers: {Authorization: `Bearer ${this.refresh}`}
                //header에 refresh 담아서 access 요청 후에 catch를 통한 에러처리 필요
            })
            this.setAccess(value.data.accessToken)
            } catch(err){
                this.reset()
                window.location.reload()
            }
        },
        async signUp(name,email,password){
            const data = {
                nickname : name,
                emailId : email,
                emailPassword : password,
            }
            const headers = {
                "Content-Type": `application/json`
            }
            axios.post(`${server}/user/sign-up`,JSON.stringify(data),{ headers })
        },
        async login(email,password){
            const data = {
                emailId : email,
                emailPassword : password
            }
            const headers = {
                "Content-Type": `application/json`
            }
            return axios.post(`${server}/user/sign-in`,JSON.stringify(data),{ headers })
        },
        async resetPassword(email){
            const data = {
                emailId : email
            }
            const headers = {
                "Content-Type": `application/json`
            }
            return axios.post(`${server}/user/password/reset`,JSON.stringify(data),{ headers })
        },
        async changePassword(temp,password){
                const data = {
                    "oldEmailPassword": temp,
                    "newEmailPassword": password 
                }
                const token = this.getAccess
                const headers = {
                    "Content-Type": `application/json`,
                    Authorization : `Bearer ${token}`
                }
                return axios.post(`${server}/user/password`,JSON.stringify(data),{ headers })
        },
        async getUser(){
            const token = this.getAccess
            const headers = {
                Authorization : `Bearer ${token}`
            }
            return axios.get(`${server}/user/info`,{ headers })
        },
       
        async editNickname(value){
            const token = this.getAccess
            const headers = {
                Authorization : `Bearer ${token}`,
                "Content-Type" : "application/json"
            }
            const data = {
                "nickname": value
              }
            return axios.patch(`${server}/user/nickname`,JSON.stringify(data),{ headers})
        },
        async editIntro(value){
            const token = this.getAccess
            const headers = {
                Authorization : `Bearer ${token}`,
                "Content-Type" : "application/json"
            }
            const data = {
                "userId": 0,
                "bio": value
              }
            return axios.patch(`${server}/user/bio`,JSON.stringify(data),{ headers})

        },
        async editPhoto(value){
            const token = this.getAccess
            const headers = {
                Authorization : `Bearer ${token}`,
                "Content-Type" : "multipart/form-data"
            }
            const data = new FormData()
            data.append('image',value)
            return axios.patch(`${server}/user/image` , data, { headers} )
        },
        async searchUser(url, keyword,offset){
            const headers = {
                Authorization : `Bearer ${this.getAccess}`
            }
            console.log('키워드',keyword)
            console.log(offset)
            if(url == ""){
                return axios.get(`${server}/user/list?keyword=${keyword}&offset=${offset}&limit=10`,{ headers })
            } else{
                return axios.get(url,{headers})
            }
        },
        async follow(idx){
            const data = {
                id : idx
            }
            const headers = {
                Authorization : `Bearer ${this.getAccess}`,
                'Content-Type' : "application/json"
            }
            return axios.post(`${server}/user/follow`,JSON.stringify(data),{headers})
        },
        async getOtherUser(idx){
            const headers = {
                Authorization : `Bearer ${this.getAccess}`
            }
            return axios.get(`${server}/user/info/${idx}`,{headers})
        },
        async searchFollowing(ser){
            const headers = {
                Authorization : `Bearer ${this.getAccess}`
            }
            return axios.get(ser,{headers})
        }
    },
    persist: [
        {
            paths: ['access'],
            storage : localStorage,
            //해당 accessToken local로 저장, refresh는 cookie에서 따로 관리
        },
        {
            paths: ['userInfo','refresh'],
            storage : sessionStorage,
        }
 
    ]
})