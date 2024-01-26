import { createStore } from 'vuex'

const store = createStore({
    //데이터 저장하는 곳
    state(){
        return {
            accessToken: "",
            refreshToken: "",

        }
    },
    // state 변경시키는 곳
    mutations :{
        getAccessToken(state){
            return state.accessToken
        },
        getRefreshToken(state){
            return state.refreshToken
        },
        setAccessToekn(state, access){
            state.accessToken = access
        },
        setRefreshToken(state,refresh){
            state.refreshToken = refresh
        }

    }
})

export default store;
//  $store.state.name : 데이터 값 꺼내쓸때, 
//  $store.commit('name') : 값 변경해야될 때(mutations 실행시킬 때)
//  형식으로 꺼내쓰면 됨