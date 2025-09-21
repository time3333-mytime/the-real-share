import { createApp } from "vue";
import { store } from "./store";
// normalize.css
import "normalize.css/normalize.css";
// 引入样式
import "./styles/index.ts";
// svg icon
import "virtual:svg-icons-register";
import ElementPlus from "element-plus";
import "element-plus/dist/index.css";

import App from "./App.vue";
import router from "./router";
// 引入指令
import directives from "@/directives";
import vant from "vant";
const app = createApp(App);
app.use(store);
app.use(router);
app.use(directives);
app.use(vant);
app.use(ElementPlus);
app.mount("#app");
