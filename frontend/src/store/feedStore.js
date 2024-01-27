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
        createFeed : function(content,roomId,imgData){
            const authStore = useAuthStore()
            const data = new FormData()
            data.append('content',content)
            data.append('roomId',roomId) //차후에 추가
            let accessToken = authStore.getAccess
            imgData.forEach(img=>{
                data.append('articleImageList',img)
            })
            axios.post('http://localhost:8080/article/create',data,{
                headers: {
                    'Content-Type' : 'multipart/form-data',
                    Authorization : `Bearer ${accessToken}`
                }
            }).catch(err=>{
                const cookies = useCookies().cookies
                const refresh = cookies.get('msgRefresh')
                axios.get('http://localhost:8080/user/token',{
                    headers: {Authorization: `Bearer ${refresh}`}
                    //header에 refresh 담아서 access 요청
                }).then(res=>{
                    accessToken = res.data.accessToken
                    authStore.setAccess(accessToken)
                    axios.post('http://localhost:8080/article/create',data,{
                        headers: {
                            'Content-Type' : 'multipart/form-data',
                            Authorization : `Bearer ${accessToken}`
                        }
                    })
                    .catch((err)=>{
                        console.log(err)
                    })
                }).catch(()=>{
                    router.push('/login')
                })
            })
        },
        missionConfirm : function(){
            
        }
    },
    persist: [
        {
            
        },
 
    ]
})