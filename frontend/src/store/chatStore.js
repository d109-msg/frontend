import { defineStore } from "pinia"
import { useAuthStore } from "./authStore"
import axios from "axios"


const server =  'https://i10d109.p.ssafy.io/api'
const server2 = 'http://localhost:8080/api'
// const server = 'http://localhost:8080/api'
// const server2 = 'https://i10d109.p.ssafy.io/api'


export const useChatStore = defineStore('chat',{
    state:()=>({

    }),
    getters:{

    },
    actions:{
        makeChat : async function(idx){
            const auth = useAuthStore()
            await auth.useRefresh()
            const data = {
                "id" : idx
            }
            const headers = {
                'Authorization' : `Bearer ${auth.getAccess}`,
                'Content-Type' : 'application/json'
            }
            return axios.post(`${server}/chat/personal`,JSON.stringify(data),{headers})
        },
        chatList : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                'Authorization' : `Bearer ${auth.getAccess}`,
                'Content-Type' : 'application/json'
            }
            return axios.get(`${server}/chat/personal`,{ headers })
        }
    }
})