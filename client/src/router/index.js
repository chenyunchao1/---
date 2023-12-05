import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

const constantRoutes = [
  {
    path: '*',
    redirect: '/404'
  },
  {
    path: '/404',
    component: () => import('@/pages/404.vue')
  },
  {
    path: '/',
    name: 'main',
    component: () => import('@/pages/Main'),
    children: [
      {
        path: '/login-in',
        name: 'login-in',
        component: () => import('@/pages/LoginIn')
      },
      {
        path: '/sign-up',
        name: 'sign-up',
        component: () => import('@/pages/SignUp')
      },
      {
        path: '/',
        name: 'home',
        component: () => import('@/pages/Home')
      },
      {
        path: '/product-list',
        name: 'product-list',
        component: () => import('@/pages/ProductList')
      },
      {
        path: '/management',
        name: 'management',
        component: () => import('@/pages/Management'),
        children:[
          {
            path: '',
            redirect:'/studentManage',
          },
          {
            path: '/studentManage',
            name: 'studentManage',
            component: () => import('@/components/StudentManage')
          },
          {
            path: '/productCategoryManage',
            name: 'productCategoryManage',
            component: () => import('@/components/ProductCategoryManage')
          },
          {
            path: '/productionCategoryManage',
              name: 'productionCategoryManage',
            component: () => import('@/components/ProductionCategoryManage')
          },
          {
            path: '/scoreManage',
            name: 'scoreManage',
            component: () => import('@/components/ScoreManage')
          },
          {
            path: '/competitionManage',
            name: 'competitionManage',
            component: () => import('@/components/CompetitionManage')
          },
        ]
      },
      {
        path: '/product-album/:id',
        name: 'product-album',
        component: () => import('@/pages/ProductAlbum')
      },
      {
        path: '/team',
        name: 'team',
        component: () => import('@/pages/Team')
      },
      {
        path: '/singer-album/:id',
        name: 'singer-album',
        component: () => import('@/pages/SingerAlbum')
      },
      {
        path: '/setting',
        name: 'setting',
        component: () => import('@/pages/Setting')
      }
    ]
  }
]

export default new Router({
  mode: 'history', // 去掉url中的#
  scrollBehavior: (o, from, savedPosition) => ({ x: 0, y: 0 }),
  routes: constantRoutes
})
