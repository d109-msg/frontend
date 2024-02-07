import { defineStore } from "pinia"
import { useAuthStore } from "./authStore"
import axios from "axios"
import SockJS from "sockjs-client"
import Stomp from 'webstomp-client'
import { createPersistedState } from "pinia-plugin-persistedstate"
import servers from "@/server"

// const server =  'https://i10d109.p.ssafy.io/api'
// const server2 = 'http://localhost:8080/api'
const server = servers
const server2 = 'https://i10d109.p.ssafy.io/api'


export const useChatStore = defineStore('chat',{
    state:()=>({
        stompClient : {},
        message : {},
    }),
    getters:{
        getStomp : (state)=>{
            return state.stompClient
        },
        getMessage : (state)=>{
            return state.message
        }
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
            console.log('아이디 : ',idx)
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
        },
        userRoom : async function(){
            const auth = useAuthStore()
            const headers = {
                'Authorization' : `Bearer ${auth.getAccess}`,
            }
            return axios.get(`${server}/user/room`,{headers})
        },
        addRoom : function(room){
            this.messsageRoom.push(room)
        },
        setStomp : function(roomId,obj){
            this.stompClient[roomId] = obj
        },
        setMessage : function(roomId,value){
            if( roomId in this.message){
                this.message[roomId].push(value)
            }else{
                this.message[roomId] = [value]
            }
        },
        subscribe : async function(roomId){
            let socket = new SockJS(`${server}/ws-stomp`)
            this.setStomp(roomId,Stomp.over(socket))
            const headers = {"Authorization": useAuthStore().getAccess}
            this.stompClient[roomId].connect(headers,()=>{
                this.stompClient[roomId].subscribe('/sub/'+roomId,(e)=>{
                    this.setMessage(roomId,JSON.parse(e.body))
                })
            }, function(e){
                console.log('연결 끊어짐')
            }
            )
        }
    },

})