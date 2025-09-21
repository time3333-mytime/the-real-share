<template>
  <div class="container">
    <h2>发评论</h2>
    <form id="postForm" enctype="multipart/form-data">
      <div class="form-group">
        <label for="content">评论内容：</label>
        <textarea
          id="content"
          v-model="form.talk"
          name="content"
          rows="10"
          required
        ></textarea>
      </div>
      <el-upload
        v-bind="$attrs"
        v-model:file-list="form.graphVoList"
        class="upload-demo"
        action="http://localhost:8081/app/file/upload"
        :headers="{ 'access-token': useUserStore()?.token }"
        :on-preview="handlePreview"
        :on-remove="handleRemove"
        :on-success="uploadSuccessHandle"
        list-type="picture"
      >
        <el-button type="primary">点这里上传文件</el-button>
        <template #tip>
          <div class="el-upload__tip">只接受jpg/png文件 小于等于2MB</div>
        </template>
      </el-upload>
      <div class="form-group">
        <button @click="submitHandle">提交评论</button>
      </div>
    </form>
  </div>
</template>

<script lang="ts" setup>
import { onMounted, onUpdated, reactive, ref } from "vue";

import {
  ElMessage,
  type UploadFile,
  type UploadFiles,
  type UploadProps
} from "element-plus";
import { useUserStore } from "@/store/modules/user";
import { saveOrUpdatepinglun, saveOrUpdatepinglun2, saveOrUpdatetiezi } from "@/api/message";
import type { MessageInterface, TalkInterface } from "@/api/message/types";
import { useRoute, useRouter } from "vue-router";
import router from "@/router";

const route = useRoute();

const handleRemove: UploadProps["onRemove"] = (uploadFile, uploadFiles) => {
  console.log(uploadFile, uploadFiles);
};
const form = ref<TalkInterface>({
  id: "",
  mid: "",
  sendTime: "",
  senduserid: "",
  talk: "",
  status: "3",
  userid: "",
  userInfo: { nickname: " ", avatarUrl: " " },
  graphVoList: [
    // {
    //   name: 'food.jpeg',
    //   url: 'https://fuss10.elemecdn.com/3/63/4e7f3a15429bfda99bce42a18cdd1jpeg.jpeg?imageMogr2/thumbnail/360x360/format/webp/quality/100',
    // },
  ] as UploadFile[]
});
const handlePreview: UploadProps["onPreview"] = file => {
  console.log(file);
};
async function submitHandle() {
  const userStore = useUserStore();
  const safeUserInfo = {
    ...userStore.userInfo,
    nickname: userStore.userInfo.nickname ?? "默认昵称", // 使用 null 合并运算符 ?? 提供默认值
    url: userStore.userInfo.avatarUrl ?? "默认URL" // 同样使用 ?? 提供默认值
  };
  form.value.mid = route.query.id;
  form.value.userInfo = safeUserInfo;
  alert("发送成功");
  router.push({
    path: "/roomDetail",
    query: {
      id: route.query.id
    }
  });
  await saveOrUpdateHandle();
}
async function saveOrUpdateHandle() {
  try {
    await saveOrUpdatepinglun2(form.value);
  } catch (error) {
    console.log(error);
  }
}
function uploadSuccessHandle(
  response: any,
  uploadFile: UploadFile,
  uploadFiles: UploadFiles
) {
  form.value.graphVoList = uploadFiles?.map(item => {
    return {
      ...item,
      url: (item?.response as any)?.data || item.url
    };
  });
}
</script>

<style>
body {
  font-family: Arial, sans-serif;
  margin: 0;
  padding: 20px;
  background-color: #f4f4f4;
}
.container {
  max-width: 600px;
  margin: auto;
  background: #fff;
  padding: 20px;
  box-shadow: 0 0 10px rgba(0, 0, 0, 0.1);
  border-radius: 8px;
}
.container h2 {
  text-align: center;
}
.form-group {
  margin-bottom: 15px;
}
.form-group label {
  display: block;
  margin-bottom: 5px;
}
.form-group input[type="text"],
.form-group textarea {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.form-group input[type="file"] {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  border: 1px solid #ccc;
  border-radius: 4px;
}
.form-group button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: #fff;
  border: none;
  border-radius: 4px;
  cursor: pointer;
  font-size: 16px;
}
.form-group button:hover {
  background-color: #0056b3;
}
</style>
