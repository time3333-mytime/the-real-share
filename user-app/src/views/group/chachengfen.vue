<script setup lang="ts">
import type { ReqPage } from "@/api/types";
import { getMessageList, getMessageListByUserId } from "@/api/message";
import type { MessageInterface } from "@/api/message/types";
import { onMounted, ref } from "vue";
import PullDownRefreshContainer from "@/components/PullDownRefreshContainer/PullDownRefreshContainer.vue";
import { useRoute, useRouter } from "vue-router";
import { string } from "postcss-selector-parser";
import { saveOrUpdate } from "@/api/information";
import type { InformationInterface } from "@/api/information/types";
import { isFriend } from "@/api/user";

const pullDownRefreshContainerRef =
  ref<InstanceType<typeof PullDownRefreshContainer>>();
const router = useRouter();
const route = useRoute();
const MessageList = ref<MessageInterface[]>([]);
let isDone = ref<boolean>(false);
async function getoneMessageListHandler(pageInfo: ReqPage) {
  let mydata = await isFriend({
    id: route.query.id as string
  });
  isDone.value = mydata.data;
  // 调用接口
  let { data } = await getMessageListByUserId({
    id: route.query.id as string,
    ...pageInfo
  });
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
const jiahaoyou = () => {
  alert("已经发送好友申请号等待同意");
  const form = ref<InformationInterface>({
    id: "",
    fromId: "",
    toId: "",
    text: ""
  });
  form.value.toId = route.query.id;
  saveOrUpdate(form.value);
};
</script>

<template>
  <div class="bg-[--color-block-background]">
    <PullDownRefreshContainer
      :request="getoneMessageListHandler"
      ref="pullDownRefreshContainerRef"
      class="min-h-[70vh]"
    >
      <el-button v-if="isDone" @click="jiahaoyou">点我加他好友</el-button>
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

<style scoped lang="less"></style>
