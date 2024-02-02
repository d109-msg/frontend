import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"
import { useAuthStore } from "./authStore"

const cookies = useCookies().cookies
const server =  'https://i10d109.p.ssafy.io/api'
const server2 = 'http://localhost:8080/api'

export const useFeedStore = defineStore('feed',{
    state: ()=>({
 
    }),
    getters: {

    },
    actions: {
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
            const headers = {
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.get(`${server}/article?articleId=${idx}`,{headers})
        },
        writeComment : async function(id,content,commentId){
            const headers= {
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.post(`${server}/article/comment?articleId=${id}&content=${content}&parentCommentId=${commentId}`,null,{ headers })
        },
        likeArticle : async function(idx){
            const headers= {
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }

            return axios.post(`${server}/article/like?articleId=${idx}`,null,{ headers })
        },
        likeComment : async function(idx){
            const headers={
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.post(`${server}/article/commentLike?commentId=${idx}`,null,{ headers })
        },
        readRecomment : async function(idx,article){
            const headers={
                Authorization : `Bearer ${useAuthStore().getAccess}`
            }
            return axios.get(`${server}/article/childComment?commentId=${idx}&articleId=${article}`,{headers})
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
        }
        
    },
    persist: [
        {
            
        },
 
    ]
})