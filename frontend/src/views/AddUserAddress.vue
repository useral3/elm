<template>
  <div class="page-body">
    <NavBar title="新增地址" showBack />
    <div style="padding:16px;">
      <div style="margin-bottom:12px;"><input class="input-field" v-model="form.contactName" placeholder="联系人" /></div>
      <div style="margin-bottom:12px;">
        <label style="margin-right:16px;"><input type="radio" v-model="form.contactSex" :value="1" /> 先生</label>
        <label><input type="radio" v-model="form.contactSex" :value="0" /> 女士</label>
      </div>
      <div style="margin-bottom:12px;"><input class="input-field" v-model="form.contactTel" placeholder="电话" /></div>
      <div style="margin-bottom:16px;"><input class="input-field" v-model="form.address" placeholder="详细地址" /></div>
      <button class="btn btn-primary btn-block" @click="save">保存</button>
    </div>
  </div>
</template>

<script setup>
import { reactive } from 'vue';
import { useRouter } from 'vue-router';
import { post } from '../api';
import NavBar from '../components/NavBar.vue';

const router = useRouter();
const user = JSON.parse(sessionStorage.getItem('user') || 'null');
const form = reactive({ contactName: '', contactSex: 1, contactTel: '', address: '' });

const save = async () => {
  if (!form.contactName || !form.contactTel || !form.address) return alert('请填写完整');
  try {
    await post('/deliveryAddress/saveDeliveryAddress', { ...form, userId: user.userId });
    router.back();
  } catch (e) { alert('保存失败'); }
};
</script>
