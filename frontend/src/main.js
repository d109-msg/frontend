import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createMetaManager } from 'vue-meta'
import { createPinia } from 'pinia'
import piniaPluginPersistedState from 'pinia-plugin-persistedstate'
// import socket from 'vue3-websocket'

const pinia = createPinia()
pinia.use(piniaPluginPersistedState)

createApp(App)
    .use(router)
    .use(pinia)
    .use(createMetaManager())
    // .use(socket, 'ws-stomp')
    .mount('#app')
