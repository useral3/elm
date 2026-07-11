<template>
  <div class="page-body">
    <NavBar title="购物车" />
    <div v-if="groups.length === 0" class="empty-state">
      <div class="icon">🛒</div><p>购物车空空如也</p>
      <button class="btn btn-primary" @click="$router.push('/index')">去逛逛</button>
    </div>
    <div v-for="g in groups" :key="g.businessId" class="card" style="padding:0;">
      <div @click="g.show=!g.show" style="display:flex;align-items:center;padding:12px 16px;border-bottom:1px solid var(--border);cursor:pointer;font-size:15px;font-weight:500;">
        <span style="margin-right:8px;">{{ g.show ? '▼' : '▶' }}</span> {{ g.businessName }}
        <span style="margin-left:auto;color:#f60;">¥{{ g.total.toFixed(2) }}</span>
      </div>
      <div v-show="g.show">
        <div v-for="item in g.items" :key="item.foodId" style="display:flex;align-items:center;padding:10px 16px;border-bottom:1px solid #f5f5f5;">
          <img :src="imgUrl(item.food?.foodImg, 'img2.png')" style="width:48px;height:48px;border-radius:6px;object-fit:cover;margin-right:10px;" />
          <div style="flex:1;font-size:14px;">{{ item.food?.foodName }}<br><span style="color:var(--text-secondary);font-size:12px;">¥{{ item.food?.foodPrice }} × {{ item.quantity }}</span></div>
          <div style="font-size:15px;color:#f60;">¥{{ ((item.food?.foodPrice||0) * item.quantity).toFixed(2) }}</div>
        </div>
        <div style="display:flex;justify-content:space-between;align-items:center;padding:10px 16px;">
          <span style="font-size:13px;color:var(--text-secondary);">配送费 ¥{{ g.business?.deliveryPrice||0 }}</span>
          <button class="btn btn-primary" @click="checkout(g)" :disabled="g.total < (g.business?.starPrice||0)" style="padding:8px 20px;font-size:13px;">
            {{ g.total < (g.business?.starPrice||0) ? '还差¥'+(g.business.starPrice-g.total).toFixed(2) : '去结算' }}
          </button>
        </div>
      </div>
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
const user = JSON.parse(sessionStorage.getItem('user') || 'null');
const groups = ref([]);

onMounted(async () => {
  if (!user) return router.push('/login');
  try {
    const res = await post('/cart/listCart', { userId: user.userId });
    const data = res.data || [];
    const map = {};
    for (const item of data) {
      const bid = item.businessId;
      if (!map[bid]) {
        let biz = item.business || {};
        try { const r = await post('/business/getBusinessById', { businessId: bid }); biz = r.data; } catch (e) {}
        map[bid] = { businessId: bid, businessName: biz.businessName || '商家#'+bid, business: biz, items: [], total: 0, show: true };
      }
      map[bid].items.push(item);
      map[bid].total += (item.food?.foodPrice || 0) * item.quantity;
    }
    groups.value = Object.values(map);
  } catch (e) { console.error(e); }
});

const checkout = (g) => router.push({ path: '/orders', query: { businessId: g.businessId } });
</script>
