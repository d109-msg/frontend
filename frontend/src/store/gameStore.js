import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"
import { useAuthStore } from "./authStore"
import servers from "@/server"


// const server =  'https://i10d109.p.ssafy.io/api'
// const server2 = 'http://localhost:8080/api'
const server = servers
const server2 = 'https://i10d109.p.ssafy.io/api'

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

        },
        getParticipant : async function(roomId){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                // "Content-Type" : "application/json"
            }
            return axios.get(`${server}/game/participant?roomId=${roomId}`,{ headers })               
        },
        getMission : async function(id){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                // "Content-Type" : "application/json"
            }
            return axios.get(`${server}/game/room/mission?participantId=${id}`,{ headers })               
        },
        getAbility : async function(id){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                // "Content-Type" : "application/json"
            }
            return axios.get(`${server}/game/ability?participantId=${id}`,{ headers })               
        },
        getMemberList : async function(roomId){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                // "Content-Type" : "application/json"
            }
            return axios.get(`${server}/game/room/participant?roomId=${roomId}`,{ headers })               
          },
          postVote : async function(id,jobId, targetId){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                "Content-Type" : "application/json"
            }
            const data = {
                "participantId": id,
                "jobId": jobId,
                "targetId": targetId
            }
            return axios.patch(`${server}/game/vote`,JSON.stringify(data),{ headers })

        },
        getMyPick : async function(id){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}/game/vote/pick?participantId=${id}`,{ headers })               
        },
        getvoteRoom : async function(roomId){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}/game/vote/room?roomId=${roomId}`,{ headers })               
        },
        checkNight : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}/scheduler/night`,{ headers })               

        },
        getAliveMember : async function(roomId){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}/game/room/num?roomId=${roomId}`,{ headers })   
        },
        nightFlag : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}/scheduler/8pm`,{ headers })               

        },
        dayFlag : async function(){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}/scheduler/8am`,{ headers })               

        },
        useAbility : async function(id,targetId){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                // "Content-Type" : "application/json"
            }

            return axios.patch(`${server}/game/ability?participantId=${id}&targetId=${targetId}`,null,{ headers })

        },
        readFeed : async function(nextUrl){
            const auth = useAuthStore()
            await auth.useRefresh()
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}/article/feed/room${nextUrl}`,{headers})
        }



    },

})