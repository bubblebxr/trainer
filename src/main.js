import { createApp } from 'vue'
import App from './App.vue'
import Antd from 'ant-design-vue'
import router from './router'
import store from './store'

import ElementPlus from 'element-plus'
import 'ant-design-vue/dist/reset.css'
import 'element-plus/dist/index.css'
import 'vuetify/styles'
import { createVuetify } from 'vuetify'
import * as components from 'vuetify/components'
import * as directives from 'vuetify/directives'
import * as ElementPlusIconsVue from '@element-plus/icons-vue'
import axios from "./api/request.js";

const vuetify = createVuetify({
  components,
  directives
})
const app = createApp(App)
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}
app.use(store).use(router)
app.provide("$axios", axios)
app.use(ElementPlus)
app.use(vuetify)
app.use(Antd)
app.mount('#app')
