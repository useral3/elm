<template>
  <div class="page-body">
    <NavBar title="登录" />
    <div style="padding: 40px 24px;">
      <h2 style="font-size:28px;margin-bottom:32px;">欢迎回来</h2>
      <div style="margin-bottom:16px;">
        <input class="input-field" v-model="userId" placeholder="请输入账号" />
      </div>
      <div style="margin-bottom:24px;">
        <input class="input-field" type="password" v-model="password" placeholder="请输入密码" @keyup.enter="login" />
      </div>
      <button class="btn btn-primary btn-block" @click="login" :disabled="loading">
        {{ loading ? '登录中...' : '登 录' }}
      </button>
      <div style="text-align:center;margin-top:20px;color:var(--text-secondary);font-size:14px;">
        还没有账号？<router-link to="/register" style="color:var(--primary);">立即注册</router-link>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';

const router = useRouter();
const userId = ref('');
const password = ref('');
const loading = ref(false);

const login = async () => {
  if (!userId.value) return alert('请输入账号');
  if (!password.value) return alert('请输入密码');
  loading.value = true;
  try {
    const res = await post('/user/login', { userId: userId.value, password: password.value });
    if (res.data.success) {
      sessionStorage.setItem('token', res.data.token);
      sessionStorage.setItem('user', JSON.stringify(res.data.user));
      router.replace('/index');
    } else {
      alert(res.data.message || '登录失败');
    }
  } catch (e) {
    alert('登录失败，请检查网络');
  } finally {
    loading.value = false;
  }
};
</script>
