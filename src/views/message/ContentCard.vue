<template>
  <van-card @click="goMessageDetail" class="rounded-xl shadow">
    <template #title>
      <slot name="title">
        <div class="flex">
          <van-image
            round
            width="12vw"
            height="12vw"
            :src="data.userInfo.avatarUrl || `失败`"
          >
            <template v-slot:error>加载失败</template>
            <template v-slot:loading>
              <van-loading type="spinner" size="20" />
            </template>
          </van-image>
          <div class="flex flex-col justify-center ml-[10px] text-[12px]">
            <div class="font-bold">{{ data.userInfo.nickname }}</div>
            <div class="--van-gray-6">{{ data.sendTime.slice(0, 10) }}</div>
          </div>
        </div>
      </slot>
    </template>
    <template #price>
      <slot name="price">
        <div class="mt-[10px] flex flex-col">
          <div class="font-bold">{{ data.talk }}</div>
          <div
            class="van-multi-ellipsis--l3"
            v-on-click-remove-class="['van-multi-ellipsis--l3']"
          >
            具体帖子：{{ data.message }}
          </div>
          <!--            图片-->
          <van-row>
            <van-col
              :span="8"
              v-for="(item, index) in data.graphVoList"
              :key="index"
            >
              <van-image width="25vw" height="25vw" :src="item.url">
                <template v-slot:error>加载失败</template>
                <template v-slot:loading>
                  <van-loading type="spinner" size="20" />
                </template>
              </van-image>
            </van-col>
          </van-row>
          <!--            点赞、评论-->
          <div class="flex mt-[10px]">
            <div class="flex ml-[10px]">
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
const router = useRouter();
const props = defineProps({
  data: {
    type: Object as PropType<ContentInterface>,
    default: () => ({}),
    readOnly: true
  }
});
// 跳转到房间的详情页面
const goMessageDetail = () => {
  router.push({ path: "/messageDetail", query: { id: props.data.mid } });
};
</script>

<style scoped></style>
