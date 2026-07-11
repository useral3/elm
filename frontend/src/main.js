import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import api from './api'
import './assets/theme.css'

const app = createApp(App)
app.use(router)
app.provide('api', api)
app.mount('#app')
