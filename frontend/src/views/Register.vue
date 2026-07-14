<template>
  <div class="page-body">
    <NavBar title="注册" showBack />
    <div style="padding: 40px 24px;">
      <h2 style="font-size:28px;margin-bottom:32px;">创建账号</h2>
      <div style="margin-bottom:14px;"><input class="input-field" v-model="form.userId" placeholder="账号" /></div>
      <div style="margin-bottom:14px;"><input class="input-field" v-model="form.userName" placeholder="昵称" /></div>
      <div style="margin-bottom:14px;"><input class="input-field" type="password" v-model="form.password" placeholder="密码" /></div>
      <div style="margin-bottom:14px;"><input class="input-field" type="password" v-model="confirmPwd" placeholder="确认密码" /></div>
      <div style="margin-bottom:4px;display:flex;gap:8px;">
        <input class="input-field" v-model="code" placeholder="验证码" style="flex:1;" />
        <button class="btn btn-primary" @click="sendCode" :disabled="cd > 0" style="width:110px;font-size:13px;">
          {{ cd > 0 ? cd + 's' : '获取验证码' }}
        </button>
      </div>
      <div style="margin-bottom:14px;font-size:12px;color:var(--text-secondary);">验证码会打印在后端控制台</div>
      <div style="margin-bottom:14px;">
        <label style="font-size:14px;margin-right:16px;">性别：</label>
        <label style="margin-right:12px;"><input type="radio" v-model="form.userSex" :value="1" /> 男</label>
        <label><input type="radio" v-model="form.userSex" :value="0" /> 女</label>
      </div>
      <button class="btn btn-primary btn-block" @click="register">注 册</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, inject } from 'vue';
import { useRouter } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';

const router = useRouter();
const form = reactive({ userId: '', userName: '', password: '', userSex: 1 });
const confirmPwd = ref('');
const code = ref('');
const cd = ref(0);

const sendCode = async () => {
  if (!form.userId) return alert('请先输入账号');
  try {
    const res = await post('/user/sendCode', { phone: form.userId });
    if (res.data.success) {
      alert('验证码已发送（查看后端控制台）');
      cd.value = 60;
      const t = setInterval(() => { cd.value--; if (cd.value <= 0) clearInterval(t); }, 1000);
    } else {
      alert(res.data.message || '发送失败');
    }
  } catch (e) { alert('发送失败，请检查网络'); }
};

const register = async () => {
  if (!form.userId || !form.password || !form.userName) return alert('请填写完整信息');
  if (form.password !== confirmPwd.value) return alert('两次密码不一致');
  if (!code.value) return alert('请输入验证码');
  try {
    const res = await post('/user/saveUser', {
      userId: form.userId,
      password: form.password,
      userName: form.userName,
      userSex: form.userSex,
      code: code.value
    });
    if (res.data.success) {
      alert('注册成功！');
      router.push('/login');
    } else {
      alert(res.data.message || '注册失败，账号可能已存在');
    }
  } catch (e) { alert('注册失败，请检查网络'); }
};
</script>
