<template>
  <div>
    <!-- 顶部背景 -->
    <van-image :src="bgImgUrl">
      <template v-slot:error>加载失败</template>
      <template v-slot:loading>
        <van-loading type="spinner" size="20" />
      </template>
    </van-image>

    <div class="main-container flex justify-around mt-[15px]">
      <div
        v-for="item in navList"
        :key="item.path"
        class="flex flex-col justify-center items-center"
        @click="handleNavClick(item)"
      >
        <SvgIcon :name="item.icon" size="45" />
        <span>{{ item.name }}</span>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts" name="MyRoom">
import { ref } from "vue";
import bgImgUrl from "@/assets/my_room_bg.png";
import { useRouter } from "vue-router";

const router = useRouter();

const navList = ref([
  {
    icon: "物业费用出账",
    name: "交房租",
    path: "/myAgreement",
    isExternal: false
  },
  {
    icon: "电费",
    name: "交电费",
    path: "https://www.95598.cn/osgweb/index",
    isExternal: true
  },
  {
    icon: "物业报修",
    name: "报修",
    path: "/worry",
    isExternal: false
  }
]);

const handleNavClick = (item: any) => {
  if (item.isExternal) {
    // 外部链接使用新标签页打开
    window.open(item.path, "_blank");
  } else if (item.path) {
    // 内部路由使用 Vue Router
    router.push(item.path);
  } else {
    console.warn("未配置有效路径");
  }
};
</script>
