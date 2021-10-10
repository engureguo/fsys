import { createRouter, createWebHistory } from 'vue-router'
import Layout from '@/layout/Layout'

const routes = [
  {
    path: '/',
    name: 'Layout',
    component: Layout,
    redirect: '/index',
    // 管理页的嵌套路由
    children: [
      {
        path: '/me',
        name: 'Me',
        component: () => import('@/views/me/Me')
      },
      {
        path: 'user',
        name: 'User',
        component: () => import('@/views/user/User')
      },
      {
        path: 'avatar',
        name: 'Avatar',
        component: () => import('@/views/avatar/Avatar')
      },
      {
        path: 'word',
        name: 'Word',
        component: () => import('@/views/word/Word')
      },
      {
        path: 'index',
        name: 'Index',
        component: () => import('@/views/Index')
      },
      {
        path: 'financial',
        name: 'Financial',
        component: () => import('@/views/financial/Financial')
      }
    ]
  },
  {
    path: '/login',
    name: 'Login',
    component: () => import('@/views/Login')
  },
  {
    path: '/register',
    name: 'Register',
    component: () => import('@/views/Register')
  }
  // {
  //   path: '/about',
  //   name: 'About',
  //   // route level code-splitting
  //   // this generates a separate chunk (about.[hash].js) for this route
  //   // which is lazy-loaded when the route is visited.
  //   component: () => import(/* webpackChunkName: "about" */ '../views/About.vue')
  // }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
