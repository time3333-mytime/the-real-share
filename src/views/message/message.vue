<template>
  <router-view :key="$route.fullPath"></router-view>
  <div>
    <!--    消息列表-->
    <PullDownRefreshContainer
      :request="getFriendHandler"
      ref="pullDownRefreshContainerRef"
      class="min-h-[70vh]"
    >
      <!--    顶部-->
      <van-sticky offset-top="0">
        <div
          class="main-container flex justify-around items-center bg-[--van-primary-background-color] h-[20vh] backdrop-filter backdrop-blur-xl backdrop-saturate-150"
        >
          <div
            v-for="item in navList"
            :key="item.path"
            class="flex flex-col justify-center items-center"
            @click="router.push(item.path)"
          >
            <div
              :style="{ background: item.color }"
              class="flex flex-col justify-center items-center h-[12vw] w-[12vw] rounded-xl py-[3px]"
            >
              <van-icon
                :name="item.icon"
                :badge="item.badge > 0 ? item.badge : null"
                size="25"
              />
            </div>
            <span class="mt-[2px]">{{ item.name }}</span>
          </div>
        </div>
      </van-sticky>
      <div class="flex flex-col main-container">
        <!--      单条消息-->
        <template v-if="FriendList?.length">
          <FriendCard v-for="item in FriendList" :key="item.id" :data="item">
            <template #tags>
              <div>
                <span class="text-[12px] --van-gray-6">{{ 11111 }}</span>
              </div>
            </template>
          </FriendCard>
        </template>
      </div>
    </PullDownRefreshContainer>
  </div>
</template>

<script setup lang="ts" name="Message">
import { onDeactivated, onMounted, onUnmounted, ref } from "vue";
import { useRouter } from "vue-router";
import PullDownRefreshContainer from "@/components/PullDownRefreshContainer/PullDownRefreshContainer.vue";
import { getMessageList, getMyFriendNumber, getMyNotReadNumber } from "@/api/message";
import { getFriendList } from "@/api/information";
import type { ReqPage } from "@/api/types";
import type { ContentInterface, MessageInterface } from "@/api/message/types";
import type { FriendsInterface } from "@/api/information/types";
import ContentCard from "@/views/message/ContentCard.vue";
import FriendCard from "@/views/message/FriendCard.vue";
const router = useRouter();
const notNumber = ref<any>();
const FNumber = ref<any>();

const notNumberHandle = async () => {
  const { data } = await getMyNotReadNumber();
  notNumber.value = data;
};
const notHandle = async () => {
  const { data } = await getMyFriendNumber();
  FNumber.value = data;
};
const FriendList = ref<FriendsInterface[]>([]);
const pullDownRefreshContainerRef =
  ref<InstanceType<typeof PullDownRefreshContainer>>();
async function getFriendHandler(pageInfo: ReqPage) {
  // 调用接口
  let { data } = await getFriendList(pageInfo);
  let targetRecords = data.records.map(item => {
    return {
      id: item.id,
      nickname: item.nickname,
      avatarUrl: item.avatarUrl || "",
      count: item.count
    };
  }) as unknown as FriendsInterface[];
  if (pageInfo.current === 1) {
    FriendList.value = targetRecords;
  } else {
    FriendList.value = [...FriendList.value, ...targetRecords];
  }
  pullDownRefreshContainerRef.value?.setFinished(
    FriendList.value.length >= data.total
  );
}
onMounted(async () => {
  await notNumberHandle();
  await notHandle();
});
const navList = ref([
  {
    icon: "volume-o",
    name: "好友申请",
    path: "/notice",
    color: "#f1be4a",
    badge: FNumber
  },
  {
    icon: "chat-o",
    name: "收到的评论",
    path: "/notReadContent",
    color: "#51b290",
    badge: notNumber
  }
]);
</script>
<style lang="less" scoped></style>
