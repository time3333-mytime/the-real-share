<script setup lang="ts">
import { onMounted } from "vue";
import { useRouter } from "vue-router";
const router = useRouter();

onMounted(async () => {
  router.push({ path: "/userCenter" });
});
</script>

<template>

</template>

<style scoped lang="less"></style>
