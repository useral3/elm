<template>
  <div class="page-body">
    <NavBar title="商家信息" showBack />
    <!-- 商家信息 -->
    <div class="card" style="display:flex;gap:12px;align-items:center;">
      <img :src="imgUrl(business.businessImg, 'img1.png')" style="width:64px;height:64px;border-radius:8px;object-fit:cover;" />
      <div style="flex:1;">
        <div style="display:flex;align-items:center;gap:8px;">
          <span style="font-size:17px;font-weight:600;">{{ business.businessName }}</span>
          <span @click="toggleFav" style="cursor:pointer;font-size:18px;">{{ faved ? '❤️' : '🤍' }}</span>
        </div>
        <div style="font-size:12px;color:var(--text-secondary);">¥{{ business.starPrice }}起送 | ¥{{ business.deliveryPrice }}配送</div>
        <div style="font-size:12px;color:var(--text-muted);">{{ business.businessExplain }}</div>
      </div>
    </div>

    <!-- 食品列表 -->
    <div v-if="foodArr.length === 0" class="empty-state"><p>加载中...</p></div>
    <div v-for="(item, i) in foodArr" :key="item.foodId" class="card"
         style="display:flex;align-items:center;gap:10px;">
      <img :src="imgUrl(item.foodImg, 'img2.png')" style="width:56px;height:56px;border-radius:8px;object-fit:cover;" />
      <div style="flex:1;">
        <div style="font-size:14px;font-weight:500;">{{ item.foodName }}</div>
        <div style="font-size:12px;color:var(--text-muted);">{{ item.foodExplain }}</div>
        <div style="font-size:14px;color:#f60;font-weight:500;">¥{{ item.foodPrice }}</div>
      </div>
      <div style="display:flex;align-items:center;gap:8px;">
        <span @click="minus(i)" v-show="item.quantity>0" style="font-size:22px;color:var(--primary);cursor:pointer;">⊖</span>
        <span v-show="item.quantity>0" style="min-width:20px;text-align:center;">{{ item.quantity }}</span>
        <span @click="add(i)" style="font-size:22px;color:var(--primary);cursor:pointer;">⊕</span>
      </div>
    </div>

    <!-- 底部购物车栏 -->
    <div style="position:fixed;bottom:0;left:0;width:100%;
                background:var(--card-bg);border-top:1px solid var(--border);
                display:flex;align-items:center;padding:8px 12px;z-index:999;gap:10px;">
      <div style="position:relative;font-size:28px;">
        🛒<span v-show="totalQty>0" style="position:absolute;top:-6px;right:-8px;background:#f40;color:#fff;
          font-size:10px;width:18px;height:18px;border-radius:50%;display:flex;align-items:center;justify-content:center;">{{ totalQty }}</span>
      </div>
      <div style="flex:1;">
        <div style="font-size:15px;font-weight:600;">¥{{ totalPrice }}</div>
        <div style="font-size:11px;color:var(--text-muted);">配送费 ¥{{ business.deliveryPrice || 0 }}</div>
      </div>
      <button class="btn btn-primary" @click="toOrder" :disabled="totalPrice < (business.starPrice||0)"
              style="padding:10px 24px;font-size:14px;"
              :style="{ opacity: totalPrice >= (business.starPrice||0) ? 1 : 0.5 }">
        {{ totalPrice >= (business.starPrice||0) ? '去结算' : '¥' + business.starPrice + '起送' }}
      </button>
    </div>
    <div style="height:64px;"></div>
  </div>
</template>

<script setup>
import { ref, computed, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { post } from '../api';
import { imgUrl } from '../utils/image';
import NavBar from '../components/NavBar.vue';

const router = useRouter();
const route = useRoute();
const businessId = route.query.businessId;
const user = JSON.parse(sessionStorage.getItem('user') || 'null');

const business = ref({});
const foodArr = ref([]);
const faved = ref(false);

const totalQty = computed(() => foodArr.value.reduce((s, f) => s + f.quantity, 0));
const totalPrice = computed(() => foodArr.value.reduce((s, f) => s + f.quantity * f.foodPrice, 0).toFixed(2));

onMounted(async () => {
  try {
    const bizParams = { businessId };
    if (user) bizParams.userId = user.userId;  // 记录浏览历史
    const [bizRes, foodRes] = await Promise.all([
      post('/business/getBusinessById', bizParams),
      post('/food/listFoodByBusinessId', { businessId })
    ]);
    business.value = bizRes.data;
    foodArr.value = (foodRes.data || []).map(f => ({ ...f, quantity: 0 }));
    if (user) {
      try {
        const cartRes = await post('/cart/listCart', { userId: user.userId, businessId });
        const cartData = cartRes.data || [];
        for (const f of foodArr.value)
          for (const c of cartData)
            if (c.foodId === f.foodId) f.quantity = c.quantity;
      } catch (e) {}
      // 检查收藏状态
      try {
        const favRes = await post('/favorite/isBusinessFavorited', { userId: user.userId, businessId });
        faved.value = favRes.data?.favorited || false;
      } catch (e) {}
    }
  } catch (e) { console.error(e); }
});

const add = async (i) => {
  if (!user) return router.push('/login');
  foodArr.value[i].quantity++;
  try { await post('/cart/saveCart', { userId: user.userId, businessId, foodId: foodArr.value[i].foodId }); } catch (e) {}
};
const minus = async (i) => {
  if (foodArr.value[i].quantity <= 0) return;
  foodArr.value[i].quantity--;
  const f = foodArr.value[i];
  try {
    if (f.quantity === 0) await post('/cart/removeCart', { userId: user.userId, businessId, foodId: f.foodId });
    else await post('/cart/updateCart', { userId: user.userId, businessId, foodId: f.foodId, quantity: f.quantity });
  } catch (e) {}
};
const toOrder = () => {
  if (!user) return router.push('/login');
  router.push({ path: '/orders', query: { businessId } });
};
const toggleFav = async () => {
  if (!user) return router.push('/login');
  try {
    await post('/favorite/toggleBusiness', { userId: user.userId, businessId });
    faved.value = !faved.value;
  } catch (e) {}
};
</script>
