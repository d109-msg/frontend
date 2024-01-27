import { createStore} from 'vuex'
import createPersistedState from 'vuex-persistedstate'
import loginStore from './modules/loginStore'

export default createStore({
    state:{
        authenticated: false,
        userId: null,
        token: null
    },
    getters:{},
    mutations:{
        login(state, resData){
            state.userId = resData.userId
            state.token = resData.token
            state.authenticated = true
        }
    },
    actions:{},
    modules:{ loginStore},
    plugins: [
        createPersistedState({
            paths: ['loginStore']
        })
    ]

})