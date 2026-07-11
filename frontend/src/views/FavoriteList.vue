<template>
  <div class="page-body">
    <NavBar title="我的收藏" showBack />
    <div v-if="loading" class="empty-state"><p>加载中...</p></div>
    <div v-else-if="list.length === 0" class="empty-state">
      <div class="icon">❤️</div><p>还没有收藏商家</p>
      <button class="btn btn-primary" @click="$router.push('/index')">去逛逛</button>
    </div>
    <div v-for="item in list" :key="item.businessId" class="card"
         @click="$router.push({path:'/businessInfo',query:{businessId:item.businessId}})"
         style="display:flex;gap:12px;cursor:pointer;">
      <img :src="imgUrl(item.business?.businessImg, 'img1.png')" style="width:64px;height:64px;border-radius:8px;object-fit:cover;" />
      <div style="flex:1;">
        <div style="font-size:15px;font-weight:600;">{{ item.business?.businessName || '商家#'+item.businessId }}</div>
        <div style="font-size:12px;color:var(--text-secondary);">❤️ {{ item.favoriteCount || 1 }}人收藏</div>
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
const list = ref([]);
const loading = ref(true);

onMounted(async () => {
  if (!user) return router.push('/login');
  try {
    const res = await post('/favorite/listFavoriteBusinesses', { userId: user.userId });
    list.value = res.data || [];
  } catch (e) { console.error(e); }
  loading.value = false;
});
</script>
