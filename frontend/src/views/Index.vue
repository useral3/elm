<template>
  <div class="page-body">
    <!-- 搜索栏 -->
    <div style="padding:12px 16px;background:var(--card-bg);border-bottom:1px solid var(--border);">
      <div style="background:var(--bg);border-radius:20px;padding:8px 16px;display:flex;align-items:center;gap:8px;color:var(--text-muted);font-size:14px;">
        🔍 搜索商家、商品
      </div>
    </div>

    <!-- 分类入口 -->
    <div style="display:flex;flex-wrap:wrap;padding:12px 8px;background:var(--card-bg);margin-bottom:8px;">
      <div v-for="cat in categories" :key="cat.id"
           @click="toBusinessList(cat.id)"
           style="width:20%;display:flex;flex-direction:column;align-items:center;padding:8px 0;cursor:pointer;">
        <img :src="imgUrl(cat.icon)" style="width:40px;height:40px;object-fit:contain;" />
        <span style="font-size:12px;color:var(--text);margin-top:4px;">{{ cat.name }}</span>
      </div>
    </div>

    <!-- 秒杀入口 -->
    <div @click="$router.push('/flashSale')"
         style="margin:8px 12px;padding:12px 16px;border-radius:12px;
                background:linear-gradient(135deg,#f40,#f60);color:#fff;
                display:flex;align-items:center;justify-content:space-between;cursor:pointer;">
      <div>
        <div style="font-size:16px;font-weight:700;">⚡ 限时秒杀</div>
        <div style="font-size:11px;opacity:0.85;">超低价抢购，手慢无！</div>
      </div>
      <span style="font-size:13px;">立即抢购 ›</span>
    </div>

    <!-- Redis 销量排行榜 -->
    <div class="card">
      <div style="font-size:16px;font-weight:600;margin-bottom:12px;">🏆 热销排行</div>
      <div v-if="rankList.length === 0" style="text-align:center;color:var(--text-muted);padding:20px 0;">暂无排行数据，快去下单吧！</div>
      <div v-for="(item, i) in rankList" :key="i"
           @click="toBusinessInfo(item.businessId)"
           style="display:flex;align-items:center;gap:10px;padding:10px 0;border-bottom:1px solid var(--border);cursor:pointer;">
        <span style="font-size:20px;font-weight:700;width:24px;text-align:center;"
              :style="{ color: i < 3 ? ['#f40','#f90','#fc0'][i] : '#ccc' }">{{ i + 1 }}</span>
        <span style="flex:1;font-size:15px;">{{ item.business?.businessName || '商家#' + item.businessId }}</span>
        <span style="font-size:13px;color:var(--text-secondary);">已售 {{ item.score }} 单</span>
      </div>
    </div>

    <!-- 推荐商家 -->
    <div style="padding:12px 16px 0;font-size:16px;font-weight:600;">推荐商家</div>
    <div v-if="businesses.length === 0" style="padding:40px;text-align:center;color:var(--text-muted);">加载中...</div>
    <div v-for="b in businesses" :key="b.businessId" class="card"
         @click="toBusinessInfo(b.businessId)" style="display:flex;gap:12px;cursor:pointer;">
      <img :src="imgUrl(b.businessImg, 'img1.png')" style="width:72px;height:72px;border-radius:8px;object-fit:cover;" />
      <div style="flex:1;">
        <div style="font-size:15px;font-weight:600;">{{ b.businessName }}</div>
        <div style="font-size:12px;color:var(--text-secondary);margin:4px 0;">⭐ 4.5 | 月售200+</div>
        <div style="font-size:12px;color:var(--text-secondary);">¥{{ b.starPrice }}起送 | ¥{{ b.deliveryPrice }}配送</div>
        <div style="font-size:12px;color:var(--text-muted);margin-top:2px;">{{ b.businessExplain }}</div>
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
import Footer from '../components/Footer.vue';

const router = useRouter();
const businesses = ref([]);
const rankList = ref([]);

const categories = [
  { id:1, name:'美食', icon:'dcfl01.png' }, { id:2, name:'早餐', icon:'dcfl02.png' },
  { id:3, name:'跑腿代购', icon:'dcfl03.png' }, { id:4, name:'汉堡披萨', icon:'dcfl04.png' },
  { id:5, name:'甜品饮品', icon:'dcfl05.png' }, { id:6, name:'速食简餐', icon:'dcfl06.png' },
  { id:7, name:'地方小吃', icon:'dcfl07.png' }, { id:8, name:'米粉面馆', icon:'dcfl08.png' },
  { id:9, name:'包子粥铺', icon:'dcfl09.png' }, { id:10, name:'炸鸡炸串', icon:'dcfl10.png' },
];

onMounted(async () => {
  try {
    const res = await post('/business/listBusinessByOrderTypeId', { orderTypeId: 1 });
    businesses.value = res.data || [];
  } catch (e) { console.error('加载商家失败:', e); }
  try {
    const res = await post('/leaderboard/topSales', { top: 5 });
    rankList.value = res.data || [];
  } catch (e) { console.error('加载排行榜失败:', e); }
});

const toBusinessList = (id) => router.push({ path: '/businessList', query: { orderTypeId: id } });
const toBusinessInfo = (id) => router.push({ path: '/businessInfo', query: { businessId: id } });
</script>
