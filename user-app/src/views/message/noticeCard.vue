<template>
  <van-card class="rounded-xl shadow">
    <template #title>
      <slot name="title">
        <div class="flex">
          <van-image
            round
            width="14vw"
            height="14vw"
            :src="data.avatarUrl || `失败`"
          >
            <template v-slot:error>加载失败</template>
            <template v-slot:loading>
              <van-loading type="spinner" size="20" />
            </template>
          </van-image>
          <div class="flex flex-col justify-center ml-[10px] text-[12px]">
            <div class="name">{{ data.nickname }}</div>
          </div>
        </div>
        <el-button @click="tongyi">同意申请</el-button>
        <el-button @click="jujue">拒绝申请</el-button>
      </slot>
    </template>
  </van-card>
</template>
<style></style>
<script setup lang="ts">
import { type PropType, ref } from "vue";
import { useRoute, useRouter } from "vue-router";
import type { ContentInterface, MessageInterface } from "@/api/message/types";
import type {
  FriendsInfoInterface, Mynumber,
  nwFriendsInterface
} from "@/api/information/types";
import { pass, reject, saveOrUpdate } from "@/api/information";
const router = useRouter();
const route = useRoute();
const props = defineProps({
  data: {
    type: Object as PropType<nwFriendsInterface>,
    default: () => ({}),
    readOnly: true
  }
});
function tongyi() {
  alert("同意成功");
  const form = ref<FriendsInfoInterface>({
    firstId: "",
    secondId: "",
    status: ""
  });
  form.value.firstId = props.data.id;
  console.log(form.value);
  pass(form.value);
}
function jujue() {
  alert("拒绝成功");
  const form = ref<FriendsInfoInterface>({
    firstId: "",
    secondId: "",
    status: ""
  });
  form.value.firstId = props.data.id;
  reject(form.value);
}
const goTalking = () => {
  router.push({ path: "/chachengfen", query: { id: props.data.id } });
};
</script>

<style scoped>
.float-right {
  float: right;
  font-size: 18px;
  color: brown;
}
.name {
  font-style: italic;
  font-family: Arial, sans-serif;
  color: #333;
  font-size: 16px;
  line-height: 1.5;
}
</style>
