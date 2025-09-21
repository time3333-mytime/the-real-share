<template>
  <input v-model="search" placeholder="你想搜索什么" />
  <el-button @click="doSearch">点击搜索</el-button>
  <hr />
  <router-view :key="$route.fullPath"></router-view>
  <div class="bg-[--color-block-background]">
    <PullDownRefreshContainer
      :request="getMessageListHandler"
      ref="pullDownRefreshContainerRef"
      class="min-h-[70vh]"
    >
      <van-image :src="bgImgUrl">
        <template v-slot:error>加载失败</template>
        <template v-slot:loading>
          <van-loading type="spinner" size="20" />
        </template>
        <el-button @click="sendtiezi">点我发帖子</el-button>
      </van-image>
      <template v-if="MessageList?.length">
        <MessageCard v-for="item in MessageList" :key="item.id" :data="item">
          <template #tags>
            <div>
              <span class="text-[12px] --van-gray-6">{{ item.sendTime }}</span>
            </div>
          </template>
        </MessageCard>
      </template>
    </PullDownRefreshContainer>
  </div>
</template>

<script setup lang="ts" name="Group">
import { onMounted, ref } from "vue";
import bgImgUrl from "@/assets/group_bg.png";
import PullDownRefreshContainer from "@/components/PullDownRefreshContainer/PullDownRefreshContainer.vue";
import { useRoute, useRouter } from "vue-router";
import type { MessageInterface } from "@/api/message/types";
import type { ReqPage } from "@/api/types";
import { getMessageList } from "@/api/message";
import MessageCard from "@/components/MessageCard/MessageCard.vue";
import type { UserInfoInterface } from "@/api/user/types";
import { changeName } from "@/api/user";

const pullDownRefreshContainerRef =
  ref<InstanceType<typeof PullDownRefreshContainer>>();
const router = useRouter();
const MessageList = ref<MessageInterface[]>([]);
let search = ref<string>("");
const route = useRoute();
const doSearch = () => {
  if (!search.value.trim()) return;
  router.push({ path: "/searchResult", query: { keyword: search.value } });
};
const sendtiezi = () => {
  router.push({ path: "/fatiezi" });
};

async function getMessageListHandler(pageInfo: ReqPage) {
  // 调用接口
  let { data } = await getMessageList(pageInfo);
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
<style lang="less" scoped></style>
