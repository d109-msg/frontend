import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"
import { useAuthStore } from "./authStore"
import servers from "@/server"

const cookies = useCookies().cookies
// const server =  'https://i10d109.p.ssafy.io/api'
// const server2 = 'http://localhost:8080/api'
const server = servers
const server2 = 'https://i10d109.p.ssafy.io/api'


export const useFeedStore = defineStore('feed',{
    state: ()=>({
        mission : "",
    }),
    getters: {
        getMission :(state)=>{
            return state.mission
        }
    },
    actions: {
        setMission : function(value){
            this.mission = value
        },
        axiosFeed : async function(content,roomId,imgData){
            const authStore = useAuthStore()
            const data = new FormData()
            data.append('content',content)
            data.append('roomId',roomId) //차후에 추가
            let accessToken = authStore.getAccess
            imgData.forEach(img=>{
                data.append('articleImageList',img)
            })
            const headers = {
                'Content-Type' : 'multipart/form-data',
                Authorization : `Bearer ${accessToken}`
            }
            return axios.post(`${server}/article/create`,data,{ headers })
        },
        
        missionConfirm : async function(dataImg,item){
            const formData = new FormData()
            formData.append('image',dataImg)
            let accessToken = useAuthStore().getAccess
            const headers = {
                "Content-Type": `multipart/form-data`,
                Authorization : `Bearer ${accessToken}`
            }
            return axios.post(`${server}/game/analyze?condition=${item}`,formData,{ headers })
        },

        getUserProfile : async function(id){
            let accessToken = useAuthStore().getAccess
            const headers = {
                Authorization : `Bearer ${accessToken}`
            }
            return axios.get(`${server}/article/profile?userId=${id}`,{headers})
        },

        readFeed : async function(url){
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.get(url,{headers})
        },
        getDetail : async function(idx){
            const access = useAuthStore().getAccess
            const headers = {
                Authorization : `Bearer ${access}`
            }
            return axios.get(`${server}/article/detail?articleId=${idx}`,{headers})
        
        },
        writeComment : async function(id,content,commentId){
            const token = useAuthStore().getAccess
            const headers= {
                Authorization : `Bearer ${token}`,
                "Content-Type" : "application/json"
            }
            const data = {
                articleId : id,
                content : content,
                commentId : commentId
            }
            return axios.post(`${server}/article/comment`,JSON.stringify(data),{ headers })
        },
        likeArticle : async function(idx){
            const headers= {
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            console.log(idx)
            return axios.post(`${server}/article/like?articleId=${idx}`,null,{ headers })
        },
        likeComment : async function(idx){
            const headers={
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.post(`${server}/article/comment-like?commentId=${idx}`,null,{ headers })
        },
        readRecomment : async function(idx,article){
            const headers={
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.get(`${server}/article/child-comment?commentId=${idx}&articleId=${article}`,{headers})
        },
        editFeed : async function(userId,articleId,content){
            const headers={
                Authorization : `Bearer ${useAuthStore().getAccess}`,
                "Content-Type": 'application/json',
            }
            const data = {
                userId, articleId, content
            }
            axios.patch(`${server}/article`,JSON.stringify(data),{headers})
        },
        deleteFeed : async function(articleId){
            const headers={
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            axios.delete(`${server}/article?articleId=${articleId}`,{headers})
        },
        async guestFeed(server){
            return axios.get(`${server}`)
        },
        async popularFeed(server){
            const headers={
                Authorization : `Bearer ${useAuthStore().getAccess}`,
            }
            return axios.get(`${server}`,{headers})
        }
        
    },
    persist: [
        {
            
        },
 
    ]
})