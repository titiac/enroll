import Vue from 'vue'
import Vuex from 'vuex'

Vue.use(Vuex)

export default new Vuex.Store({
  state: {
    isLogin: false,
    userInfo: {},
    token: '',
    
  },
  mutations: {
    // 更新用户信息
    updataUserInfo(state, data) {
      state.userInfo = data;
    },
    // 更新登录状态
    updataIsLogin(state, data) {
      state.isLogin = data;
    },
    // 更新登录状态
    updataToken(state, data) {
      state.token = data;
  }
  },
  actions: {
  },
  modules: {
  }
})
