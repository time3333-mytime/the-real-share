<template>
  <el-upload
    class="avatar-uploader"
    action="http://localhost:8081/app/file/upload"
    :headers="{ 'access-token': useUserStore()?.token }"
    :show-file-list="false"
    :on-success="handleAvatarSuccess"
    :before-upload="beforeAvatarUpload"
  >
    <img v-if="imageUrl" :src="imageUrl" class="avatar" />
    <el-icon v-else class="avatar-uploader-icon"><Plus /></el-icon>
  </el-upload>
  <el-button @click="submit">提交</el-button>
</template>

<script lang="ts" setup>
import { ref } from "vue";
import { ElMessage, type UploadFile } from "element-plus";
import { Plus } from "@element-plus/icons-vue";

import type { UploadProps } from "element-plus";
import { useUserStore } from "@/store/modules/user";
import router from "@/router";
import { saveOrUpdatetiezi } from "@/api/message";
import { change } from "@/api/information";
import type { MessageInterface } from "@/api/message/types";
import type { UserInfoInterface } from "@/api/user/types";

const imageUrl = ref("");

const form = ref<UserInfoInterface>({
  avatarUrl: "",
  nickname: ""
});

const handleAvatarSuccess: UploadProps["onSuccess"] = response => {
  imageUrl.value = response.data;
};

const beforeAvatarUpload: UploadProps["beforeUpload"] = rawFile => {
  if (rawFile.size / 1024 / 1024 > 2) {
    ElMessage.error("Avatar picture size can not exceed 2MB!");
    return false;
  }
  return true;
};

async function submit() {
  if (imageUrl.value === null) {
    alert("请先选择照片");
  } else {
    alert("换头像成功");
    router.push({ path: "/jiahaoyou" });
    await cahngeHandle();
  }
}
async function cahngeHandle() {
  try {
    form.value.avatarUrl = imageUrl.value;
    await change(form.value);
  } catch (error) {
    console.log(error);
  }
}
</script>

<style scoped>
.avatar-uploader .avatar {
  width: 178px;
  height: 178px;
  display: block;
}
</style>

<style>
.avatar-uploader .el-upload {
  border: 1px dashed var(--el-border-color);
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
  transition: var(--el-transition-duration-fast);
}

.avatar-uploader .el-upload:hover {
  border-color: var(--el-color-primary);
}

.el-icon.avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 178px;
  height: 178px;
  text-align: center;
}
</style>
