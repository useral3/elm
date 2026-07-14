<template>
  <div class="page-body">
    <NavBar title="我的" />
    <!-- 用户卡片 -->
    <div class="card" style="display:flex;align-items:center;gap:14px;" v-if="user">
      <img :src="avatar" style="width:56px;height:56px;border-radius:50%;object-fit:cover;background:#eee;" />
      <div>
        <div style="font-size:18px;font-weight:600;">{{ user.userName }}</div>
        <div style="font-size:13px;color:var(--text-secondary);">ID: {{ user.userId }}</div>
      </div>
    </div>
    <div class="card" v-else>
      <p style="text-align:center;color:var(--text-secondary);">
        暂未登录，<router-link to="/login" style="color:var(--primary);">去登录</router-link>
      </p>
    </div>
    <!-- 菜单 -->
    <div class="card" style="padding:0;">
      <div @click="go('/orderList')" style="display:flex;align-items:center;padding:15px 16px;border-bottom:1px solid var(--border);cursor:pointer;font-size:15px;"><span style="margin-right:12px;">📋</span><span style="flex:1;">我的订单</span><span style="color:#ccc;">›</span></div>
      <div @click="go('/userAddress')" style="display:flex;align-items:center;padding:15px 16px;border-bottom:1px solid var(--border);cursor:pointer;font-size:15px;"><span style="margin-right:12px;">📍</span><span style="flex:1;">收货地址</span><span style="color:#ccc;">›</span></div>
      <div @click="go('/userInfo')" style="display:flex;align-items:center;padding:15px 16px;border-bottom:1px solid var(--border);cursor:pointer;font-size:15px;"><span style="margin-right:12px;">👤</span><span style="flex:1;">个人信息</span><span style="color:#ccc;">›</span></div>
      <div @click="go('/favoriteList')" style="display:flex;align-items:center;padding:15px 16px;border-bottom:1px solid var(--border);cursor:pointer;font-size:15px;"><span style="margin-right:12px;">❤️</span><span style="flex:1;">我的收藏</span><span style="color:#ccc;">›</span></div>
      <div @click="go('/browseHistory')" style="display:flex;align-items:center;padding:15px 16px;cursor:pointer;font-size:15px;"><span style="margin-right:12px;">🕐</span><span style="flex:1;">浏览记录</span><span style="color:#ccc;">›</span></div>
    </div>
    <div class="card" style="padding:0;" v-if="user">
      <div @click="logout" style="display:flex;align-items:center;padding:15px 16px;cursor:pointer;font-size:15px;color:#f40;"><span style="margin-right:12px;">🚪</span><span style="flex:1;">退出登录</span><span style="color:#ccc;">›</span></div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { post } from '../api';
import { imgUrl } from '../utils/image';
import NavBar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

const router = useRouter();
const user = ref(null);
const avatar = ref('');

onMounted(async () => {
  const stored = sessionStorage.getItem('user');
  if (stored) user.value = JSON.parse(stored);
  if (user.value?.userImg) {
    avatar.value = imgUrl(user.value.userImg, '');
  }
});

const go = (path) => {
  if (!user.value && path !== '/login') return router.push('/login');
  router.push(path);
};

const logout = () => {
  post('/user/logout').catch(() => {});
  sessionStorage.clear();
  user.value = null;
  router.push('/index');
};
</script>
