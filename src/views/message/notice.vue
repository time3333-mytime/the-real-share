<script setup lang="ts">
import FriendCard from "@/views/message/FriendCard.vue";
import PullDownRefreshContainer from "@/components/PullDownRefreshContainer/PullDownRefreshContainer.vue";
import type { ReqPage } from "@/api/types";
import { getFriendList, getfulFriendList } from "@/api/information";
import type { FriendsInterface, nwFriendsInterface } from "@/api/information/types";
import { ref } from "vue";
import NoticeCard from "@/views/message/noticeCard.vue";
const FriendList = ref<nwFriendsInterface[]>([]);
const pullDownRefreshContainerRef =
  ref<InstanceType<typeof PullDownRefreshContainer>>();
async function getFriendHandler(pageInfo: ReqPage) {
  // 调用接口
  let { data } = await getfulFriendList(pageInfo);
  console.log(111111, data);
  let targetRecords = data.records.map(item => {
    return {
      id: item.id,
      nickname: item.nickname,
      avatarUrl: item.avatarUrl || ""
    };
  }) as unknown as nwFriendsInterface[];
  if (pageInfo.current === 1) {
    FriendList.value = targetRecords;
  } else {
    FriendList.value = [...FriendList.value, ...targetRecords];
  }
  pullDownRefreshContainerRef.value?.setFinished(
    FriendList.value.length >= data.total
  );
}
</script>

<template>
  <router-view :key="$route.fullPath"></router-view>
  <div>
    <!--    消息列表-->
    <PullDownRefreshContainer
      :request="getFriendHandler"
      ref="pullDownRefreshContainerRef"
      class="min-h-[70vh]"
    >
      <div class="flex flex-col main-container">
        <!--      单条消息-->
        <template v-if="FriendList?.length">
          <noticeCard v-for="item in FriendList" :key="item.id" :data="item">
            <template #tags>
              <div>
                <span class="text-[12px] --van-gray-6">{{ 11111 }}</span>
              </div>
            </template>
          </noticeCard>
        </template>
      </div>
    </PullDownRefreshContainer>
  </div>
</template>

<style scoped lang="less"></style>
