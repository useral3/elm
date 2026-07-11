<template>
  <div class="page-body">
    <NavBar title="智能客服" />
    <div style="padding:12px;">
      <div v-for="(m, i) in msgs" :key="i" style="margin-bottom:12px;display:flex;"
           :style="{ justifyContent: m.role === 'user' ? 'flex-end' : 'flex-start' }">
        <div :style="m.role==='user'?
          {background: 'var(--primary)',color:'#fff',borderRadius:'12px 12px 0 12px'}:
          {background:'var(--card-bg)',borderRadius:'12px 12px 12px 0'}"
          style="max-width:80%;padding:10px 14px;font-size:14px;line-height:1.5;box-shadow:var(--shadow);">
          {{ m.content }}
        </div>
      </div>
      <div v-if="typing" style="color:var(--text-muted);font-size:13px;padding:4px;">客服正在输入...</div>
    </div>
    <div style="position:fixed;bottom:var(--footer-height);left:0;width:100%;
                background:var(--card-bg);border-top:1px solid var(--border);display:flex;gap:8px;padding:10px 12px;z-index:1001;">
      <input class="input-field" v-model="input" placeholder="输入您的问题..." @keyup.enter="send" style="flex:1;" />
      <button class="btn btn-primary" @click="send" style="padding:0 20px;">发送</button>
    </div>
    <div style="height:100px;"></div>
    <Footer />
  </div>
</template>

<script setup>
import { ref } from 'vue';
import { postJSON } from '../api';
import NavBar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

const input = ref('');
const msgs = ref([]);
const typing = ref(false);
const sessionId = 's' + Date.now();

const send = async () => {
  const text = input.value.trim();
  if (!text) return;
  msgs.value.push({ role: 'user', content: text });
  input.value = '';
  typing.value = true;
  try {
    const res = await postJSON('/chat', { message: text, sessionId });
    msgs.value.push({ role: 'bot', content: res.data?.reply || res.data?.errorMessage || '（未获取到回复）' });
  } catch (e) {
    console.error('Chat error:', e);
    msgs.value.push({ role: 'bot', content: '网络异常：' + (e.response?.status || e.message) });
  }
  typing.value = false;
};
</script>
