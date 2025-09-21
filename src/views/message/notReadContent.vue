<script setup lang="ts">
import { onDeactivated, ref } from "vue";
import type { ContentInterface } from "@/api/message/types";
import bgImgUrl from "@/assets/group_bg.png";
import PullDownRefreshContainer from "@/components/PullDownRefreshContainer/PullDownRefreshContainer.vue";
import type { ReqPage } from "@/api/types";
import { getMessageList, getNotReadList } from "@/api/message";
import { useRouter } from "vue-router";
import MessageCard from "@/components/MessageCard/MessageCard.vue";
import ContentCard from "@/views/message/ContentCard.vue";
const pullDownRefreshContainerRef =
  ref<InstanceType<typeof PullDownRefreshContainer>>();
const router = useRouter();
const ContentList = ref<ContentInterface[]>([]);
async function getContentListHandler(pageInfo: ReqPage) {
  // 调用接口
  let { data } = await getNotReadList(pageInfo);
  let targetRecords = data.records.map(item => {
    return {
      id: item.id,
      message: item.message,
      userid: item.userid,
      graphVoList: item.graphVoList,
      talk: item.talk,
      mid: item.mid,
      userInfo: {
        nickname: item.userInfo.nickname,
        avatarUrl: item.userInfo.avatarUrl || ""
      },
      //  浏览时间
      sendTime: item.sendTime || ""
    };
  }) as unknown as ContentInterface[];
  if (pageInfo.current === 1) {
    ContentList.value = targetRecords;
  } else {
    ContentList.value = [...ContentList.value, ...targetRecords];
  }
  pullDownRefreshContainerRef.value?.setFinished(
    ContentList.value.length >= data.total
  );
}
</script>

<template>
  <div class="bg-[--color-block-background]">
    <PullDownRefreshContainer
      :request="getContentListHandler"
      ref="pullDownRefreshContainerRef"
      class="min-h-[70vh]"
    >
      <van-image :src="bgImgUrl">
        <template v-slot:error>加载失败</template>
        <template v-slot:loading>
          <van-loading type="spinner" size="20" />
        </template>
      </van-image>
      <template v-if="ContentList?.length">
        <ContentCard v-for="item in ContentList" :key="item.id" :data="item">
          <template #tags>
            <div>
              <span class="text-[12px] --van-gray-6">{{ item.sendTime }}</span>
            </div>
          </template>
        </ContentCard>
      </template>
    </PullDownRefreshContainer>
  </div>
</template>

<style scoped lang="less"></style>
