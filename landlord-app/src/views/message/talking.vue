<template>
  <div>温馨提示：每三天自动清理消息记录</div>
  <div class="chat-container">
    <div class="chat-header">Chat With {{ userInfo.nickname }}</div>
    <div class="chat-body" ref="chatBodyContainer" v-html="content"></div>
    <div class="chat-footer">
      <textarea v-model="text" placeholder="Type a message..." />
      <button @click="send">Send</button>
    </div>
  </div>
</template>

<script setup lang="ts">
import { ref, onMounted, reactive, nextTick } from "vue";
import type { MyUserInfoInterface } from "@/api/user/types";
import {
  getDetailById,
  getme,
  getmeReadById,
  getNoReadById
} from "@/api/information";
import { useRoute, useRouter } from "vue-router";
import type { InformationInterface } from "@/api/information/types";

const myuserInfo = ref<MyUserInfoInterface>({} as MyUserInfoInterface);
const isCollapse = ref(false);
const userInfo = ref<MyUserInfoInterface>({} as MyUserInfoInterface);
const text = ref("");
const messages = ref([]);
const meRead = ref<InformationInterface[]>({} as InformationInterface[]);
const content = ref("");
let socket;
const route = useRoute();
const chatBodyContainer = ref<HTMLDivElement | null>(null);

// ... 其他代码 ...

const scrollToBottom = () => {
  if (chatBodyContainer.value) {
    chatBodyContainer.value.scrollTop = chatBodyContainer.value.scrollHeight;
  }
};
const send = () => {
  console.log("您的浏览器支持WebSocket");
  const message = {
    from: myuserInfo.value.id,
    to: userInfo.value.id,
    text: text.value
  };
  socket.send(JSON.stringify(message));
  messages.value.push({ sid: myuserInfo.value.id, text: text.value });
  createContent(null, myuserInfo.value.id, text.value);
  text.value = "";
  nextTick(() => {
    scrollToBottom();
  });
};

const getDetailHandle = async () => {
  const { data } = await getDetailById(route.query.id as string);
  // console.log(12312,data)
  userInfo.value = data;
};

const getmeReadHandle = async () => {
  const { data } = await getmeReadById(route.query.id as string);
  meRead.value = data;
};
const getMYHandle = async () => {
  const { data } = await getme();
  // console.log(22222222,data)
  myuserInfo.value = data;
};
const createContent = (remoteUser, nowUser, text) => {
  let html;
  if (nowUser) {
    html = `
          <div class="el-row" style="padding: 5px 0">
            <div class="el-col el-col-22" style="text-align: right; padding-right: 10px">
              <div class="tip left">${text}</div>
            </div>
            <div class="el-col el-col-2">
              <span class="el-avatar el-avatar--circle" style="height: 30px; width: 30px; line-height: 30px;">
                <img src="${myuserInfo.value.avatarUrl}" style="object-fit: cover;">
              </span>
            </div>
          </div>
        `;
  } else if (remoteUser) {
    html = `
          <div class="el-row" style="padding: 5px 0">
            <div class="el-col el-col-2" style="text-align: right">
              <span class="el-avatar el-avatar--circle" style="height: 30px; width: 30px; line-height: 30px;">
                <img src="${userInfo.value.avatarUrl}" style="object-fit: cover;">
              </span>
            </div>
            <div class="el-col el-col-22" style="text-align: left; padding-left: 10px">
              <div class="tip right">${text}</div>
            </div>
          </div>
        `;
  }
  content.value += html;
};

const init = () => {
  const sid = myuserInfo.value.id;
  if (typeof WebSocket === "undefined") {
    console.log("您的浏览器不支持WebSocket");
  } else {
    console.log("您的浏览器支持WebSocket");
    const socketUrl = `ws://localhost:8081/talkingserver/${sid}`;
    if (socket) {
      socket.close();
      socket = null;
    }
    socket = new WebSocket(socketUrl);
    socket.onopen = () => {
      console.log("websocket已打开");
      for (let meread of meRead.value) {
        if (meread.toId == myuserInfo.value.id)
          createContent(meread.fromId, null, meread.text);
        else {
          createContent(null, meread.toId, meread.text);
        }
      }
    };
    socket.onmessage = msg => {
      console.log("收到数据====" + msg.data);
      const data = JSON.parse(msg.data);
      if (data.from == userInfo.value.id) {
        messages.value.push(data);
        createContent(data.from, null, data.text);
      }
      nextTick(() => {
        scrollToBottom();
      });
    };
    socket.onclose = () => {
      console.log("websocket已关闭");
    };
    socket.onerror = () => {
      console.log("websocket发生了错误");
    };
  }
};

onMounted(async () => {
  await getDetailHandle();
  await getMYHandle();
  await getmeReadHandle();
  init();
  nextTick(() => {
    scrollToBottom();
  });
});
</script>

<style scoped>
.chat-container {
  width: 350px;
  margin: 10px auto;
  background-color: #fff;
  border-radius: 8px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
}
.chat-header {
  background-color: #007bff;
  color: #fff;
  padding: 15px;
  text-align: center;
  border-top-left-radius: 8px;
  border-top-right-radius: 8px;
}

.chat-body {
  padding: 15px;
  overflow-y: auto;
  height: 600px;
  position: relative;
}
.tip {
  color: white;
  text-align: center;
  border-radius: 10px;
  font-family: sans-serif;
  padding: 10px;
  width: auto;
  display: inline-block !important;
  display: inline;
}
.right {
  background-color: deepskyblue;
}
.left {
  background-color: forestgreen;
}
.chat-message {
  margin: 10px 0;
  padding: 10px;
  border-radius: 5px;
  max-width: 80%;
}

.chat-message.sent {
  background-color: #dcf8c6;
  align-self: flex-end; /* 注意：为了让这条样式生效，父容器需要设置为 display: flex，但在这个简单示例中我们没有这么做 */
  float: right; /* 使用 float 作为替代方案 */
  clear: both; /* 清除浮动 */
}

.sent-by-me,
.sent-by-other {
  font-weight: bold;
  margin-right: 5px; /* 给文本和消息内容之间留点空间 */
}

.sent-by-other {
  color: #007bff; /* 其他人的消息用蓝色表示 */
}

.chat-footer {
  display: flex;
  padding: 10px;
  background-color: #f8f9fa;
  border-bottom-left-radius: 8px;
  border-bottom-right-radius: 8px;
}

.chat-footer input {
  flex: 1;
  padding: 10px;
  border: 1px solid #ddd;
  border-radius: 5px;
  outline: none;
}

.chat-footer button {
  padding: 10px 20px;
  margin-left: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}

.chat-footer button:hover {
  background-color: #0056b3;
}
</style>
