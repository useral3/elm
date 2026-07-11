<template>
  <div class="page-body">
    <NavBar title="我的订单" />
    <div v-if="loading" class="empty-state"><p>加载中...</p></div>
    <div v-else-if="orders.length === 0" class="empty-state">
      <div class="icon">📋</div><p>还没有订单</p>
      <button class="btn btn-primary" @click="$router.push('/index')">去下单</button>
    </div>
    <div v-for="o in orders" :key="o.orderId" class="card" style="cursor:pointer;">
      <div style="display:flex;justify-content:space-between;align-items:center;margin-bottom:8px;">
        <span style="font-size:15px;font-weight:600;">{{ o.business?.businessName || '商家#'+o.businessId }}</span>
        <span :style="{color:o.orderState===1?'#38ca73':'#f90',fontSize:'13px'}">{{ o.orderState === 1 ? '已支付' : '待支付' }}</span>
      </div>
      <div style="font-size:13px;color:var(--text-secondary);">订单号：{{ o.orderId }} | {{ o.orderDate }}</div>
      <div style="font-size:16px;font-weight:600;margin-top:4px;">¥{{ o.orderTotal }}</div>
      <button v-if="o.orderState===0" class="btn btn-primary" style="margin-top:8px;padding:6px 16px;font-size:13px;" @click="goPay(o)">去支付</button>
    </div>
    <Footer />
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

const router = useRouter();
const user = JSON.parse(sessionStorage.getItem('user') || 'null');
const orders = ref([]);
const loading = ref(true);

onMounted(async () => {
  if (!user) return router.push('/login');
  try {
    const res = await post('/orders/listOrdersByUserId', { userId: user.userId });
    orders.value = res.data || [];
  } catch (e) { console.error(e); }
  loading.value = false;
});

const goPay = (o) => o.orderState === 0 && router.push({ path: '/payment', query: { orderId: o.orderId } });
</script>
