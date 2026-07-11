<template>
  <div class="page-body">
    <NavBar title="在线支付" showBack />
    <div class="card" style="text-align:center;padding:40px 20px;">
      <div style="font-size:48px;margin-bottom:16px;">💳</div>
      <div style="font-size:20px;font-weight:600;">¥{{ order?.orderTotal }}</div>
      <div style="font-size:14px;color:var(--text-secondary);margin:16px 0;">订单号：{{ orderId }}</div>
      <button class="btn btn-primary btn-block" @click="pay" :disabled="paying" style="margin-top:20px;">
        {{ paying ? '处理中...' : '确认支付' }}
      </button>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';

const router = useRouter();
const route = useRoute();
const orderId = route.query.orderId;
const order = ref({});
const paying = ref(false);

onMounted(async () => {
  try {
    const res = await post('/orders/getOrdersById', { orderId });
    order.value = res.data || {};
  } catch (e) { console.error(e); }
});

const pay = async () => {
  paying.value = true;
  try {
    await post('/orders/updateOrderPayStatus', { orderId });
    alert('支付成功！');
    router.replace('/orderList');
  } catch (e) { alert('支付失败'); }
  paying.value = false;
};
</script>
