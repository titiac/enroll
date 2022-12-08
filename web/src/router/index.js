import Vue from 'vue'
import VueRouter from 'vue-router'
import vuex from '../store/index'
import Login from '../views/Login.vue'
import Admin from '../views/Admin.vue'
import Student from '../views/Student.vue'
import Teacher from '../views/Teacher.vue'
import Index from '../views/Index.vue'

Vue.use(VueRouter)

const routes = [
  {
    //路径为空时跳到index(默认)
    path: '/',
    redirect: '/login',
  },
  {
    path: '/login',
    component: Login,
  },
  {
    path: '/admin',
    component: Admin
  },
  {
    path: '/student',
    component: Student
  },
  {
    path: '/teacher',
    component: Teacher
  }

]

const router = new VueRouter({
  routes,
  mode: 'history',
})

// 挂载路由导航守卫
// router.beforeEach((to, from, next) => {
//   // to 将要访问的路径
//   // from 代表从哪个路径跳转而来
//   // next 是一个函数，表示放行
//   //     next()  放行    next('/login')  强制跳转
//   if (to.path === '/login'||to.path === '/student') return next()
//   if ((to.path === '/admin' && vuex.state.userInfo.userType !== "superAdmin") || (to.path === '/teacher' && vuex.state.userInfo.userType === "student")) {
//     alert('没有该权限！')
//     console.log(vuex.state.userInfo.userType)
//     return next('/login')//这里是如果未登录 强制跳转到主页
//   }
//   // 获取登录状态
//   if (!vuex.state.isLogin) {
//     alert('需要登录！')
//     return next('/login')//这里是如果未登录 强制跳转到主页
//   }
//   next()
// })

export default router
