import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"


const cookies = useCookies().cookies
const server = 'http://commitTest'
const server1 = 'http://localhost:8080'
const server2 = 'http://i10d109.p.ssafy.io/api'

export const useAuthStore = defineStore('auth',{
    state: ()=>({
        access: "",
    }),
    getters: {
        getAccess: (state)=>{
            return state.access
        },
        
    },
    actions: {
        setAccess(token){
            this.access = token
        },
        setRefresh(token){
            cookies.set("msgRefresh",token, (60*60*24))
            //refresh 토큰의 갱신기간 하루(24시간)로 설정
        },
        async useRefresh(){
            const refresh = cookies.get('msgRefresh')
            try{
            let value = await axios.get(`${server}/user/token`,{
                headers: {Authorization: `Bearer ${refresh}`}
                //header에 refresh 담아서 access 요청 후에 catch를 통한 에러처리 필요
            })
            this.setAccess(value.data.accessToken)
            } catch(err){
                if(err.response && err.response.status == 401){
                    //401 에러 발생시 로그인 관련 오류 뜨게함
                    alert('로그인 세션이 만료되었습니다.')
                } else{
                    //미발견된 에러시(서버 및 네트워크 오류) 토큰 갱신 실패
                    alert('토큰 갱신에 실패하였습니다. 다시 로그인해주세요.')
                }
                router.push('/login')
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
    },
    persist: [
        {
            paths: ['access'],
            storage : localStorage,
            //해당 accessToken local로 저장, refresh는 cookie에서 따로 관리
        },
 
    ]
})