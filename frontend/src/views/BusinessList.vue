<template>
  <div class="page-body">
    <NavBar title="商家列表" showBack />
    <div v-if="loading" class="empty-state"><p>加载中...</p></div>
    <div v-else-if="businessArr.length === 0" class="empty-state">
      <p>暂无商家</p>
      <button class="btn btn-primary" @click="$router.back()">返回</button>
    </div>
    <div v-for="b in businessArr" :key="b.businessId" class="card"
         @click="toInfo(b.businessId)" style="display:flex;gap:12px;cursor:pointer;">
      <img :src="imgUrl(b.businessImg, 'img1.png')" style="width:72px;height:72px;border-radius:8px;object-fit:cover;" />
      <div style="flex:1;">
        <div style="font-size:15px;font-weight:600;">{{ b.businessName }}</div>
        <div style="font-size:12px;color:var(--text-secondary);margin:4px 0;">⭐ 4.5 | 月售200+</div>
        <div style="font-size:12px;color:var(--text-secondary);">¥{{ b.starPrice }}起送 | ¥{{ b.deliveryPrice }}配送</div>
        <div style="font-size:12px;color:var(--text-muted);margin-top:2px;">{{ b.businessExplain }}</div>
        <span v-if="b.quantity > 0" style="background:var(--primary);color:#fff;font-size:10px;padding:1px 6px;border-radius:10px;">{{ b.quantity }}</span>
      </div>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { post } from '../api';
import { imgUrl } from '../utils/image';
import NavBar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

const router = useRouter();
const route = useRoute();
const businessArr = ref([]);
const loading = ref(true);

onMounted(async () => {
  const orderTypeId = route.query.orderTypeId || 1;
  try {
    const res = await post('/business/listBusinessByOrderTypeId', { orderTypeId });
    businessArr.value = res.data || [];
  } catch (e) { console.error(e); }
  loading.value = false;
});

const toInfo = (id) => router.push({ path: '/businessInfo', query: { businessId: id } });
</script>
