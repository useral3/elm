import { createRouter, createWebHashHistory } from 'vue-router'

const routes = [
  { path: '/',           name: 'Home',           component: () => import('../views/Index.vue') },
  { path: '/index',      name: 'Index',          component: () => import('../views/Index.vue') },
  { path: '/login',      name: 'Login',          component: () => import('../views/Login.vue') },
  { path: '/register',   name: 'Register',       component: () => import('../views/Register.vue') },
  { path: '/businessList', name: 'BusinessList', component: () => import('../views/BusinessList.vue') },
  { path: '/businessInfo', name: 'BusinessInfo', component: () => import('../views/BusinessInfo.vue') },
  { path: '/cart',       name: 'Cart',           component: () => import('../views/Cart.vue'),       meta: { auth: true } },
  { path: '/orders',     name: 'Orders',         component: () => import('../views/Orders.vue'),      meta: { auth: true } },
  { path: '/payment',    name: 'Payment',        component: () => import('../views/Payment.vue'),     meta: { auth: true } },
  { path: '/orderList',  name: 'OrderList',      component: () => import('../views/OrderList.vue'),   meta: { auth: true } },
  { path: '/userAddress',name: 'UserAddress',    component: () => import('../views/UserAddress.vue'), meta: { auth: true } },
  { path: '/addUserAddress', name: 'AddUserAddress', component: () => import('../views/AddUserAddress.vue'), meta: { auth: true } },
  { path: '/editUserAddress',name: 'EditUserAddress',component: () => import('../views/EditUserAddress.vue'), meta: { auth: true } },
  { path: '/myPage',     name: 'myPage',          component: () => import('../views/MyPage.vue') },
  { path: '/userInfo',   name: 'userInfo',        component: () => import('../views/UserInfo.vue'),   meta: { auth: true } },
  { path: '/chatbot',    name: 'ChatBot',         component: () => import('../views/ChatBot.vue') },
  { path: '/favoriteList', name: 'FavoriteList',  component: () => import('../views/FavoriteList.vue'), meta: { auth: true } },
  { path: '/browseHistory', name: 'BrowseHistory', component: () => import('../views/BrowseHistory.vue'), meta: { auth: true } },
  { path: '/flashSale', name: 'FlashSale', component: () => import('../views/FlashSale.vue') },
];

const router = createRouter({ history: createWebHashHistory(), routes });

// 统一路由守卫：需要登录的页面检查 token
router.beforeEach((to, from, next) => {
  const token = sessionStorage.getItem('token');
  if (to.meta.auth && !token) {
    next('/login');
  } else {
    next();
  }
});

export default router;
