import router from "@/router"
import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"
import { useCookies } from "vue3-cookies"
import axios from "axios"


const cookies = useCookies().cookies

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
        },
     
    },
    persist: [
        {
            paths: ['access'],
            storage : localStorage,
        },
 
    ]
})