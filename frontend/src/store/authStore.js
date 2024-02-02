import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"


const cookies = useCookies().cookies
const server =  'https://i10d109.p.ssafy.io/api'
const server2 = 'http://localhost:8080/api'

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
        logout(){
            this.access = ""
            this.userInfo = {}
            this.refresh = ""
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
            let value = await axios.get(`${server}/user/token`,{
                headers: {Authorization: `Bearer ${this.refresh}`}
                //header에 refresh 담아서 access 요청 후에 catch를 통한 에러처리 필요
            })
            this.setAccess(value.data.accessToken)
            } catch(err){
                this.logout()
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
                console.log(this.getAccess)
                console.log(JSON.stringify(data))
                console.log(headers)
                return axios.post(`${server}/user/password`,JSON.stringify(data),{ headers })
        },
        async getUser(){
            const token = this.getAccess
            const headers = {
                Authorization : `Bearer ${token}`
            }
            return axios.get(`${server}/user/info`,{ headers })
        },
        async guestFeed(){
            return axios.get(`${server}/article/guestFeed`)
        },
        async getFollowing(){
            const token = this.getAccess
            const headers = {
                Authorization : `Bearer ${token}`
            }
            return axios.get(`${server}/user/follow&type=from`)
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