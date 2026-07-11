<template>
  <div class="page-body">
    <NavBar title="个人信息" showBack />
    <div style="padding:16px;">
      <div style="text-align:center;margin-bottom:20px;">
        <img :src="avatar" style="width:80px;height:80px;border-radius:50%;object-fit:cover;background:#eee;" />
        <div style="margin-top:8px;">
          <input type="file" accept="image/*" @change="uploadAvatar" style="font-size:12px;" />
        </div>
      </div>
      <div style="margin-bottom:12px;"><label style="font-size:13px;color:var(--text-secondary);">昵称</label>
        <input class="input-field" v-model="form.userName" /></div>
      <div style="margin-bottom:16px;"><label style="font-size:13px;color:var(--text-secondary);">性别</label>
        <label style="margin-left:12px;"><input type="radio" v-model="form.userSex" :value="1" /> 男</label>
        <label style="margin-left:12px;"><input type="radio" v-model="form.userSex" :value="0" /> 女</label>
      </div>
      <button class="btn btn-primary btn-block" @click="save">保存</button>
    </div>
  </div>
</template>

<script setup>
import { reactive, ref, onMounted } from 'vue';
import { useRouter } from 'vue-router';
import { post, api } from '../api';
import { imgUrl } from '../utils/image';
import NavBar from '../components/NavBar.vue';

const router = useRouter();
const user = JSON.parse(sessionStorage.getItem('user') || 'null');
const form = reactive({ userId: '', userName: '', userSex: 1 });
const avatar = ref('');

onMounted(async () => {
  if (!user) return router.push('/login');
  form.userId = user.userId;
  form.userName = user.userName;
  form.userSex = user.userSex;
  if (user.userImg) {
    avatar.value = imgUrl(user.userImg, '');
  }
  try {
    const res = await post('/user/getUserById', { userId: user.userId });
    Object.assign(form, res.data);
    if (res.data.userImg) avatar.value = imgUrl(res.data.userImg, '');
    sessionStorage.setItem('user', JSON.stringify(res.data));
  } catch (e) {}
});

const save = async () => {
  try {
    await post('/user/updateUser', { userId: form.userId, userName: form.userName, userSex: form.userSex });
    alert('保存成功');
    const u = { ...JSON.parse(sessionStorage.getItem('user')), ...form };
    sessionStorage.setItem('user', JSON.stringify(u));
  } catch (e) { alert('保存失败'); }
};

const uploadAvatar = async (e) => {
  const file = e.target.files[0];
  if (!file) return;
  const fd = new FormData();
  fd.append('file', file);
  fd.append('userId', user.userId);
  try {
    const res = await api.post('/user/uploadAvatar', fd);
    if (res.data.success) {
      avatar.value = imgUrl(res.data.imageUrl, '');
      alert('头像上传成功');
    }
  } catch (e) { alert('上传失败'); }
};
</script>
