import { defineStore } from "pinia"
import { createPersistedState } from "pinia-plugin-persistedstate"

export const usePageStore = defineStore('page', {
    state: ()=>({
        page : "",
    }),
    getters:{
        getPage: (state)=>{
            return state.page
        }
    },
    actions:{
        setPage: function(value){
            this.page = value
        }
    },
    persist:[{
        paths:['page'],
        storage: localStorage

    }
    ]
})