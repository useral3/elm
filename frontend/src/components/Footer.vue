<template>
  <div class="bottom-nav">
    <div v-for="item in tabs" :key="item.path"
         class="nav-item" :class="{ active: current === item.path }"
         @click="go(item.path)">
      <span class="icon">{{ item.icon }}</span>
      <span>{{ item.label }}</span>
    </div>
  </div>
</template>

<script setup>
import { computed } from 'vue';
import { useRouter, useRoute } from 'vue-router';

const router = useRouter();
const route = useRoute();
const current = computed(() => route.path);

const tabs = [
  { path: '/index',  icon: '🏠', label: '首页' },
  { path: '/chatbot', icon: '💬', label: '客服' },
  { path: '/orderList', icon: '📋', label: '订单' },
  { path: '/cart',    icon: '🛒', label: '购物车' },
  { path: '/myPage',  icon: '👤', label: '我的' },
];

const go = (path) => {
  const token = sessionStorage.getItem('token');
  const authPaths = ['/orderList', '/cart', '/myPage'];
  if (authPaths.includes(path) && !token) {
    router.push('/login');
  } else {
    router.push(path);
  }
};
</script>
