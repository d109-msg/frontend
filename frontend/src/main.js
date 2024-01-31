import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { createMetaManager } from 'vue-meta'
import { createPinia } from 'pinia'
import piniaPluginPersistedState from 'pinia-plugin-persistedstate'
import mitt from 'mitt'

// import socket from 'vue3-websocket'
let emitter = mitt();
const pinia = createPinia()
pinia.use(piniaPluginPersistedState)

const app = createApp(App)
app.use(router)
app.use(pinia)
app.use(createMetaManager())
app.config.globalProperties.emitter = emitter
    // .use(socket, 'ws-stomp')
app.mount('#app')
