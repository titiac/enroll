import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store/index'

//导入element-ui
import './plugins/element.js'

// 全局样式表
import "./assets/css/base.css"

// 导入axios
import axios from 'axios'
Vue.prototype.$axios = axios

// 配置请求的跟路径 由后端提供 这样我们只要输入根路径后面的地址就OK （例如/login /home

//本地服务器 好处是不用跨域
axios.defaults.baseURL = 'http://localhost:9999/'

axios.withCredentials = true

Vue.config.productionTip = false

//


new Vue({
  router,
  store,
  render: h => h(App)
}).$mount('#app')
