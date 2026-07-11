<template>
  <div class="nav-bar">
    <button v-if="showBack" class="back-btn" @click="goBack">←</button>
    <span>{{ title }}</span>
    <slot name="right"></slot>
  </div>
</template>

<script setup>
import { useRouter } from 'vue-router';
defineProps({ title: String, showBack: { type: Boolean, default: false } });
const router = useRouter();
const goBack = () => router.back();
</script>
