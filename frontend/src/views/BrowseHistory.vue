<template>
  <div class="page-body">
    <NavBar title="浏览记录" showBack>
      <template #right>
        <span @click="managing = !managing" style="font-size:14px;cursor:pointer;"
              :style="{ color: managing ? '#f40' : 'var(--primary)' }">
          {{ managing ? '完成' : '管理' }}
        </span>
      </template>
    </NavBar>
    <div v-if="loading" class="empty-state"><p>加载中...</p></div>
    <div v-else-if="list.length === 0" class="empty-state">
      <div class="icon">🕐</div><p>暂无浏览记录</p>
      <button class="btn btn-primary" @click="$router.push('/index')">去逛逛</button>
    </div>
    <template v-else>
      <!-- 管理模式：全删按钮 -->
      <div v-if="managing" style="padding:12px 16px 0;text-align:right;">
        <span @click="clearAll" style="font-size:13px;color:#f40;cursor:pointer;">删除全部</span>
      </div>
      <div v-for="b in list" :key="b.businessId" class="card"
           style="display:flex;gap:12px;align-items:center;"
           :style="{ cursor: managing ? 'default' : 'pointer' }"
           @click="managing ? null : $router.push({path:'/businessInfo',query:{businessId:b.businessId}})">
        <img :src="imgUrl(b.businessImg, 'img1.png')" style="width:56px;height:56px;border-radius:8px;object-fit:cover;" />
        <div style="flex:1;">
          <div style="font-size:15px;font-weight:500;">{{ b.businessName }}</div>
          <div style="font-size:12px;color:var(--text-secondary);">¥{{ b.starPrice }}起送 | ¥{{ b.deliveryPrice }}配送</div>
        </div>
        <span v-if="managing" @click.stop="removeOne(b.businessId)"
              style="font-size:12px;color:#f40;cursor:pointer;border:1px solid #f40;border-radius:12px;padding:3px 12px;">删除</span>
      </div>
    </template>
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
const managing = ref(false);

onMounted(async () => {
  if (!user) return router.push('/login');
  try {
    const res = await post('/business/browseHistory', { userId: user.userId });
    list.value = res.data || [];
  } catch (e) { console.error(e); }
  loading.value = false;
});

const removeOne = async (businessId) => {
  try {
    await post('/business/removeBrowseItem', { userId: user.userId, businessId });
    list.value = list.value.filter(b => b.businessId !== businessId);
  } catch (e) {}
};

const clearAll = async () => {
  try {
    await post('/business/clearBrowseHistory', { userId: user.userId });
    list.value = [];
    managing.value = false;
  } catch (e) {}
};
</script>
