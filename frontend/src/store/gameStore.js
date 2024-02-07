import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"
import { useAuthStore } from "./authStore"


const server =  'https://i10d109.p.ssafy.io/api'
const server2 = 'http://localhost:8080/api'
// const server = 'http://localhost:8080/api'
// const server2 = 'https://i10d109.p.ssafy.io/api'

export const useGameStore = defineStore('game',{
    state: ()=>({

    }),
    getters: {

    },
    actions: {

        createRoom : async function(){
            const headers= {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                "Content-Type" : "application/json"
            }

            return axios.post(`${server}/game/group`,null,{ headers })
        },
        getRoomList : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.get(`${server}/game/room/list`,{headers})
        },
        enterInviteRoom : async function(roomId){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                "Content-Type" : "application/json"
            }
            const data = {
                "roomId": roomId,
            }
            return axios.post(`${server}/game/group/join`,JSON.stringify(data),{ headers })

        },
        randomFlag : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.get(`${server}/game/random`,{headers})
        },
        enterRandomRoom : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                // "Content-Type" : "application/json"
            }
            return axios.post(`${server}/game/random`,null,{ headers })

        },
        exitRandomRoom : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                // "Content-Type" : "application/json"
            }
            return axios.delete(`${server}/game/random`,{ headers })

        }






    },

})