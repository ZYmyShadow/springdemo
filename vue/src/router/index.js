import Vue from 'vue'
import VueRouter from 'vue-router'

Vue.use(VueRouter)

// eslint-disable-next-line no-unused-vars
  const routes = [
    {
      path: '/index',
      name: 'index',
      component:() => import("@/components/page_one/index.vue"),
    },
    {
      path: '/',
      name: 'Login',
      // route level code-splitting
      // this generates a separate chunk (about.[hash].js) for this route
      // which is lazy-loaded when the route is visited.
      component: () => import ( /* webpackChunkName: "about" */ '@/views/Login.vue')
    },
    {
      path: '/404',
      component: () => import('@/views/admin/404.vue'),
      hidden: true
    },
    //{ path: '/main', component: Main },
    {
      path: '/admin',
      component: () => import('@/views/admin/home.vue'),
      name: '用户管理',
      children: [
        { path: '/userlist', component:  () => import('@/views/admin/user/userlist.vue'), name: '用户列表' }
      ]
    },
    {
      path: '/admin',
      component:  () => import('@/views/admin/home.vue'),
      name: '权限管理',
      children: [
        { path: '/permislist', component:  () => import('@/views/admin/permission/permislist.vue'), name: '菜单列表' },
        { path: '/rolelist', component:  () => import('@/views/admin/permission/rolelist.vue'), name: '角色权限' }
      ]
    },
    {
      path: '/admin',
      component:  () => import('@/views/admin/home.vue'),
      name: '数据字典',
      children: [
        { path: '/dictionarylist', component:  () => import('@/views/admin/dictionary/dictionarylist.vue'), name: '字典属性' }
      ]
    },
    {
      path: '/admin',
      component:  () => import('@/views/admin/home.vue'),
      name: '系统管理',
      children: [
        { path: '/operatingrecord', component:  () => import('@/views/admin/system/operatingrecord.vue'), name: '操作记录' },
        { path: '/loginlog', component:  () => import('@/views/admin/system/loginlog.vue'), name: '登陆日志' },
        { path: '/statistics', component:  () => import('@/views/admin/system/statistics.vue'), name: '统计图表' }

      ]
    },
    {
      path: '*',
      hidden: true,
      redirect: { path: '/404' }
    }
  ]

const router = new VueRouter({
  mode: 'hash',
  base: process.env.BASE_URL,
  routes
})
export default router
