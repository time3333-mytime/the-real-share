<script setup lang="ts">
import { useRoute, useRouter } from "vue-router";
import { onMounted, type PropType, ref } from "vue";
import type {
  MessageDetailInterface,
  MessageInterface
} from "@/api/message/types";
import { getMessageDetailById } from "@/api/message";
import Message from "@/views/message/message.vue";
import { string } from "postcss-selector-parser";

const router = useRouter();
const route = useRoute();

const sendpinglun = () => {
  router.push({
    path: "/sendpinglun",
    query: {
      id: route.query.id
    }
  });
};
const chachengfen = () => {
  router.push({
    path: "/chachengfen",
    query: { id: messageDetailInfo.value.userid }
  });
};
const chachengfen1 = (sendid: number | string) => {
  router.push({
    path: "/chachengfen",
    query: { id: sendid }
  });
};
const messageDetailInfo = ref<MessageDetailInterface>(
  {} as MessageDetailInterface
);
const getMessageDetailHandle = async () => {
  const { data } = await getMessageDetailById(route.query.id as string);
  messageDetailInfo.value = data;
};
onMounted(async () => {
  await getMessageDetailHandle();
});
</script>

<template>
  <router-view :key="$route.fullPath"></router-view>
  <!DOCTYPE html>
  <html lang="zh-CN">
    <head>
      <meta charset="UTF-8" />
      <meta name="viewport" content="width=device-width, initial-scale=1.0" />
      <title>帖子详情页</title>
    </head>
    <body>
      <header>
        <h1>圈子</h1>
      </header>
      <main>
        <div class="post-title">{{ messageDetailInfo.subject }}</div>
        <van-image
          round
          width="12vw"
          height="12vw"
          :src="messageDetailInfo.userInfo?.avatarUrl || `失败`"
        >
          <template v-slot:error>加载失败</template>
          <template v-slot:loading>
            <van-loading type="spinner" size="20" />
          </template>
        </van-image>
        <el-button @click="chachengfen">点我查他成分</el-button>
        <div class="post-info">
          发帖人：{{ messageDetailInfo.userInfo?.nickname }} | 发帖时间：{{
            messageDetailInfo.sendTime?.slice(0, 10)
          }}
        </div>
        <div class="post-content">
          <p>{{ messageDetailInfo.message }}</p>
          <van-row>
            <van-col
              :span="8"
              v-for="(item, index) in messageDetailInfo.graphVoList"
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
        </div>
        <hr />
        <h1>评论区由此开始</h1>
        <el-button @click="sendpinglun">点我来当键盘侠！键来</el-button>
        <div class="reply">
          <div
            v-for="(talk, index) in messageDetailInfo.talkList"
            :key="index"
            class="talk-item"
          >
            <div class="talk-author">{{ talk.userInfo?.nickname }}</div>
            <van-image
              round
              width="12vw"
              height="12vw"
              :src="talk.userInfo?.avatarUrl || `失败`"
            >
              <template v-slot:error>加载失败</template>
              <template v-slot:loading>
                <van-loading type="spinner" size="20" />
              </template>
            </van-image>
            <el-button @click="chachengfen1(talk.senduserid)"
              >点我查他成分</el-button
            >
            <div class="talk-content">{{ talk.talk }}</div>
            <van-row>
              <van-col
                :span="8"
                v-for="(item, index) in talk.graphVoList"
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
            <!-- 可根据需要添加时间戳等其他信息 -->
            <div class="talk-timestamp">{{ talk.sendTime?.slice(0, 10) }}</div>
          </div>
        </div>
      </main>
      <footer>
        <el-button @click="sendpinglun">点我来当键盘侠！键来</el-button>
        <div>总得有个结尾吧</div>
        <p>版权所有 &copy; 2024 超绝vue</p>
      </footer>
    </body>
  </html>
</template>

<style>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 0;
}
header,
footer {
  background-color: #f8f8f8;
  padding: 10px;
  text-align: center;
}
main {
  padding: 20px;
}
.post-title {
  font-size: 24px;
  font-weight: bold;
}
.post-info {
  margin-top: 10px;
  color: #888;
}
.post-content {
  margin-top: 20px;
}
.reply {
  margin-top: 20px;
  border-left: 2px solid #ddd;
  padding-left: 10px;
}
.reply-header {
  font-weight: bold;
}
.reply-content {
  margin-top: 5px;
}
.toolbar {
  margin-top: 20px;
  text-align: right;
}
.talk-item {
  margin-bottom: 10px;
  padding: 10px;
  border: 1px solid #ccc;
  border-radius: 5px;
}
.talk-author {
  font-weight: bold;
}
.talk-content {
  margin-top: 5px;
}
.talk-timestamp {
  color: gray;
  font-size: 0.9em;
  margin-top: 5px;
}
</style>
