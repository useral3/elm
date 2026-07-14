<template>
  <div class="page-body">
    <NavBar title="⚡ 限时秒杀" showBack />
    <div v-if="loading" class="empty-state"><p>加载中...</p></div>
    <div v-else-if="list.length === 0" class="empty-state">
      <div class="icon">⚡</div><p>暂时没有秒杀活动</p>
    </div>
    <div v-for="item in list" :key="item.foodId" class="card"
         style="display:flex;gap:12px;align-items:center;">
      <div style="width:72px;height:72px;border-radius:12px;background:linear-gradient(135deg,#f40,#f60);
                  display:flex;flex-direction:column;align-items:center;justify-content:center;color:#fff;">
        <span style="font-size:20px;font-weight:700;">⚡</span>
        <span style="font-size:10px;">秒杀</span>
      </div>
      <div style="flex:1;">
        <div style="font-size:15px;font-weight:600;">{{ item.foodName }}</div>
        <div style="font-size:12px;color:var(--text-muted);margin-top:2px;">
          {{ item.business?.businessName || '商家#' + item.businessId }}
        </div>
        <div style="margin-top:4px;display:flex;align-items:baseline;gap:8px;">
          <span style="font-size:20px;font-weight:700;color:#f40;">¥{{ item.flashPrice }}</span>
          <span style="font-size:12px;color:var(--text-muted);text-decoration:line-through;">¥{{ item.originalPrice }}</span>
        </div>
        <div style="margin-top:4px;">
          <div style="height:6px;background:#eee;border-radius:3px;overflow:hidden;width:120px;">
            <div :style="{width:Math.max(item.stock/100*120,4)+'px',height:'6px',background:item.stock<20?'#f40':'#f60',borderRadius:'3px'}" />
          </div>
          <span style="font-size:10px;color:var(--text-muted);">剩余 {{ item.stock }} 件</span>
        </div>
      </div>
      <button class="btn" @click="buy(item)"
              :disabled="item.stock <= 0"
              :style="{background:item.stock>0?'#f40':'#ccc',color:'#fff',border:'none',borderRadius:'20px',padding:'8px 20px',fontSize:'14px'}">
        {{ item.stock > 0 ? '¥' + item.flashPrice + ' 抢' : '已抢完' }}
      </button>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted, inject } from 'vue';
import { useRouter } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

const router = useRouter();
const user = JSON.parse(sessionStorage.getItem('user') || 'null');
const list = ref([]);
const loading = ref(true);
const buying = ref(false);

onMounted(async () => {
  try {
    const res = await post('/flashSale/list');
    list.value = res.data || [];
  } catch (e) { console.error(e); }
  loading.value = false;
});

const buy = async (item) => {
  if (!user) return router.push('/login');
  if (buying.value) return;
  buying.value = true;
  try {
    const res = await post('/flashSale/buy', { userId: user.userId, foodId: item.foodId });
    if (res.data.success) {
      alert('秒杀成功！');
      item.stock--;
    } else {
      alert(res.data.message || '秒杀失败');
    }
  } catch (e) { alert('秒杀失败'); }
  buying.value = false;
};
</script>
