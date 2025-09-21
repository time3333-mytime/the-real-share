<template>
  <div>
    <!-- 顶部背景 -->
    <van-image :src="bgImgUrl">
      <template v-slot:error>加载失败</template>
      <template v-slot:loading>
        <van-loading type="spinner" size="20" />
      </template>
    </van-image>
  </div>
  <PullDownRefreshContainer
    :request="getMessageListHandler"
    ref="pullDownRefreshContainerRef"
    class="min-h-[70vh]"
  >
    <template v-if="MessageList?.length">
      <WorryCard v-for="item in MessageList" :key="item.id" :data="item">
      </WorryCard>
    </template>
  </PullDownRefreshContainer>
</template>

<script setup lang="ts" name="MyRoom">
import { ref } from "vue";
import bgImgUrl from "@/assets/my_room_bg.png";
import { useRouter } from "vue-router";
import PullDownRefreshContainer from "@/components/PullDownRefreshContainer/PullDownRefreshContainer.vue";
import MessageCard from "@/components/MessageCard/MessageCard.vue";
import type { MessageInterface } from "@/api/message/types";
import type { ReqPage } from "@/api/types";
import { getMessageList, getWorryList } from "@/api/message";
import WorryCard from "@/components/MessageCard/WorryCard.vue";

const router = useRouter();

const pullDownRefreshContainerRef =
  ref<InstanceType<typeof PullDownRefreshContainer>>();
const MessageList = ref<MessageInterface[]>([]);
async function getMessageListHandler(pageInfo: ReqPage) {
  // 调用接口
  let { data } = await getWorryList(pageInfo);
  let targetRecords = data.records.map(item => {
    return {
      id: item.id,
      message: item.message,
      userid: item.userid,
      graphVoList: item.graphVoList,
      subject: item.subject,
      userInfo: {
        nickname: item.userInfo.nickname,
        avatarUrl: item.userInfo.avatarUrl || ""
      },
      //  浏览时间
      sendTime: item.sendTime || ""
    };
  }) as unknown as MessageInterface[];
  if (pageInfo.current === 1) {
    MessageList.value = targetRecords;
  } else {
    MessageList.value = [...MessageList.value, ...targetRecords];
  }
  pullDownRefreshContainerRef.value?.setFinished(
    MessageList.value.length >= data.total
  );
}
</script>
