<template>
  <van-card @click="goTalking" class="rounded-xl shadow">
    <template #title>
      <slot name="title">
        <div class="flex">
          <van-image
            round
            width="16vw"
            height="16vw"
            :src="data.avatarUrl || `失败`"
          >
            <template v-slot:error>加载失败</template>
            <template v-slot:loading>
              <van-loading type="spinner" size="20" />
            </template>
          </van-image>
          <div class="flex flex-col justify-center ml-[10px] text-[12px]">
            <div class="name">{{ data.nickname }}</div>
            <div class="float-right" v-if="data.count > 0">
              你的好友给你发了{{ data.count }}条消息
            </div>
          </div>
        </div>
      </slot>
    </template>
  </van-card>
</template>
<style></style>
<script setup lang="ts">
import type { PropType } from "vue";
import { useRouter } from "vue-router";
import type { ContentInterface, MessageInterface } from "@/api/message/types";
import type { FriendsInterface } from "@/api/information/types";
const router = useRouter();
const props = defineProps({
  data: {
    type: Object as PropType<FriendsInterface>,
    default: () => ({}),
    readOnly: true
  }
});

const goTalking = () => {
  router.push({ path: "/talking", query: { id: props.data.id } });
};
</script>

<style scoped>
.float-right {
  float: right;
  font-size: 18px;
  color: brown;
}
.name {
  font-style: italic;
  font-family: Arial, sans-serif;
  color: #333;
  font-size: 16px;
  line-height: 1.5;
}
</style>
