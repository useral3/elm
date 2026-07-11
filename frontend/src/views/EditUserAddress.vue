<template>
  <div class="page-body">
    <NavBar title="编辑地址" showBack />
    <div style="padding:16px;">
      <div style="margin-bottom:12px;"><input class="input-field" v-model="form.contactName" placeholder="联系人" /></div>
      <div style="margin-bottom:12px;">
        <label style="margin-right:16px;"><input type="radio" v-model="form.contactSex" :value="1" /> 先生</label>
        <label><input type="radio" v-model="form.contactSex" :value="0" /> 女士</label>
      </div>
      <div style="margin-bottom:12px;"><input class="input-field" v-model="form.contactTel" placeholder="电话" /></div>
      <div style="margin-bottom:16px;"><input class="input-field" v-model="form.address" placeholder="详细地址" /></div>
      <button class="btn btn-primary btn-block" @click="save">更新</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, onMounted } from 'vue';
import { useRouter, useRoute } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';

const router = useRouter();
const route = useRoute();
const daId = route.query.daId;
const form = reactive({ daId: 0, contactName: '', contactSex: 1, contactTel: '', address: '' });

onMounted(async () => {
  try {
    const res = await post('/deliveryAddress/getDeliveryAddressById', { daId });
    Object.assign(form, res.data);
  } catch (e) { console.error(e); }
});

const save = async () => {
  try {
    await post('/deliveryAddress/updateDeliveryAddress', { ...form });
    router.back();
  } catch (e) { alert('更新失败'); }
};
</script>
