<template>
  <div class="page-body">
    <NavBar title="地址管理" />
    <div v-if="list.length === 0" class="empty-state"><p>暂无地址</p></div>
    <div v-for="a in list" :key="a.daId" class="card" style="display:flex;align-items:center;gap:10px;">
      <div style="flex:1;">
        <div style="font-size:15px;font-weight:500;">{{ a.contactName }} {{ a.contactSex===1?'先生':'女士' }} {{ a.contactTel }}</div>
        <div style="font-size:13px;color:var(--text-secondary);">{{ a.address }}</div>
      </div>
      <button class="btn" style="padding:6px 12px;font-size:12px;background:var(--bg);" @click="edit(a)">编辑</button>
      <button class="btn" style="padding:6px 12px;font-size:12px;color:#f40;background:var(--bg);" @click="del(a.daId)">删除</button>
    </div>
    <div style="padding:12px;">
      <button class="btn btn-primary btn-block" @click="$router.push('/addUserAddress')">+ 新增地址</button>
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
const list = ref([]);

onMounted(async () => {
  if (!user) return router.push('/login');
  try {
    const res = await post('/deliveryAddress/listDeliveryAddressByUserId', { userId: user.userId });
    list.value = res.data || [];
  } catch (e) { console.error(e); }
});

const edit = (a) => router.push({ path: '/editUserAddress', query: { daId: a.daId } });
const del = async (daId) => {
  if (!confirm('确认删除？')) return;
  try { await post('/deliveryAddress/removeDeliveryAddress', { daId }); list.value = list.value.filter(a => a.daId !== daId); } catch (e) {}
};
</script>
