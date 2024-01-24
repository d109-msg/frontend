import { createStore } from 'vuex'

const store = createStore({
    //데이터 저장하는 곳
    state(){
        return {
            name : 'Kim',

        }
    },
    // state 수정방법 정의하는 곳
    mutations :{

    }
})

export default store;
//  꺼내쓸때, 
//  $store.state.name
//  형식으로 꺼내쓰면 돼