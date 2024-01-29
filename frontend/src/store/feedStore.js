import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"
import { useAuthStore } from "./authStore"

const cookies = useCookies().cookies

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
            return axios.post('http://localhost:8080/article/create',data,{ headers })
        },
        missionConfirm : async function(dataImg,item){
            const formData = new FormData()
            formData.append('image',dataImg)
            let accessToken = useAuthStore().getAccess
            const headers = {
                "Content-Type": `multipart/form-data`,
                Authorization : `Bearer ${accessToken}`
            }
            return axios.post(`http://localhost:8080/article/analyze?condition=${item}`,formData,{ headers })
        }
    },
    persist: [
        {
            
        },
 
    ]
})