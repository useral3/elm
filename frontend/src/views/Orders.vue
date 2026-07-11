<template>
  <div class="page-body">
    <NavBar title="确认订单" showBack />
    <div v-if="loading" class="empty-state"><p>加载中...</p></div>
    <template v-else>
      <div class="card" @click="goAddress" style="cursor:pointer;">
        <div v-if="address" style="display:flex;align-items:center;gap:10px;">
          <span style="font-size:22px;">📍</span>
          <div style="flex:1;">
            <div style="font-size:15px;font-weight:500;">{{ address.contactName }} {{ address.contactSex===1?'先生':'女士' }} {{ address.contactTel }}</div>
            <div style="font-size:13px;color:var(--text-secondary);">{{ address.address }}</div>
          </div>
          <span style="color:#ccc;">›</span>
        </div>
        <div v-else style="text-align:center;color:var(--text-secondary);padding:8px;">+ 请选择收货地址</div>
      </div>
      <div class="card">
        <div style="font-size:16px;font-weight:600;margin-bottom:10px;">{{ business.businessName }}</div>
        <div v-for="item in cartItems" :key="item.foodId" style="display:flex;justify-content:space-between;padding:6px 0;font-size:14px;">
          <span>{{ item.food?.foodName || '食品#'+item.foodId }} ×{{ item.quantity }}</span>
          <span>¥{{ ((item.food?.foodPrice||0) * item.quantity).toFixed(2) }}</span>
        </div>
        <div style="border-top:1px solid var(--border);padding-top:10px;margin-top:8px;font-size:13px;color:var(--text-secondary);">
          配送费：¥{{ business.deliveryPrice || 0 }}
          <div style="font-size:16px;color:var(--text);font-weight:600;margin-top:4px;">合计：¥{{ total.toFixed(2) }}</div>
        </div>
      </div>
      <div style="padding:0 12px;">
        <button class="btn btn-primary btn-block" @click="submitOrder" :disabled="submitting">
          {{ submitting ? '提交中...' : '提交订单 ¥' + total.toFixed(2) }}
        </button>
      </div>
    </template>
    <Footer />
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';
import Footer from '../components/Footer.vue';

const router = useRouter();
const route = useRoute();
const user = JSON.parse(sessionStorage.getItem('user') || 'null');
const businessId = route.query.businessId;
const business = ref({});
const cartItems = ref([]);
const address = ref(null);
const loading = ref(true);
const submitting = ref(false);

const total = computed(() => {
  const itemTotal = cartItems.value.reduce((s, i) => s + (i.food?.foodPrice || 0) * i.quantity, 0);
  return itemTotal + (business.value.deliveryPrice || 0);
});

onMounted(async () => {
  if (!user) return router.push('/login');
  try {
    const [bizRes, cartRes, addrRes] = await Promise.all([
      post('/business/getBusinessById', { businessId }),
      post('/cart/listCart', { userId: user.userId, businessId }),
      post('/deliveryAddress/listDeliveryAddressByUserId', { userId: user.userId }),
    ]);
    business.value = bizRes.data;
    cartItems.value = cartRes.data || [];
    if (addrRes.data?.length) address.value = addrRes.data[0];
  } catch (e) { console.error(e); }
  loading.value = false;
});

const goAddress = () => router.push('/userAddress');
const submitOrder = async () => {
  if (!address.value) return alert('请选择收货地址');
  submitting.value = true;
  try {
    const res = await post('/orders/createOrders', {
      userId: user.userId, businessId, daId: address.value.daId, orderTotal: total.value.toFixed(2)
    });
    if (res.data) router.push({ path: '/payment', query: { orderId: res.data } });
  } catch (e) { alert('下单失败'); }
  submitting.value = false;
};
</script>
