import type { RouteRecordRaw } from "vue-router";
/**
 * @description 📚 路由参数配置简介
 * @param path ==> 菜单路径
 * @param name ==> 菜单别名
 * @param redirect ==> 重定向地址
 * @param component ==> 视图文件路径
 * @param meta ==> 菜单信息
 * @param meta.icon ==> 菜单图标，只有tabBar菜单才需要配置
 * @param meta.title ==> 菜单标题
 * @param meta.noCache ==> 是否不缓存，默认开启组件缓存，需要vue组件中配置name属性和路由菜单别名一致
 * @param meta.isShowNavBar ==> 是否展示顶部导航栏，默认不展示
 * */

const routes: Array<RouteRecordRaw> = [
  {
    name: "messageDetail",
    path: "/messageDetail",
    component: () => import("@/views/group/messageDetail.vue"),
    meta: {
      title: "帖子详情",
      noCache: true
    }
  },
  {
    name: "sendpinglun",
    path: "/sendpinglun",
    component: () => import("@/views/group/sendpinglun.vue"),
    meta: {
      title: "发送评论",
      noCache: true
    }
  },
  {
    name: "sendpinglun2",
    path: "/sendpinglun2",
    component: () => import("@/views/myRoom/sendpinglun2.vue"),
    meta: {
      title: "房间发送评论",
      noCache: true
    }
  },
  {
    name: "search",
    path: "/searchResult",
    component: () => import("@/views/group/searchResult.vue"),
    meta: {
      title: "关键词查找",
      noCache: true
    }
  },
  {
    name: "jiahaoyou",
    path: "/jiahaoyou",
    component: () => import("@/views/message/jiahaoyou.vue"),
    meta: {
      title: "加好友",
      noCache: true
    }
  },
  {
    name: "notice",
    path: "/notice",
    component: () => import("@/views/message/notice.vue"),
    meta: {
      title: "好友验证",
      noCache: true
    }
  },
  {
    name: "change",
    path: "/change",
    component: () => import("@/views/userCenter/change.vue"),
    meta: {
      title: "换头像",
      noCache: true
    }
  },
  {
    name: "talking",
    path: "/talking",
    component: () => import("@/views/message/talking.vue"),
    meta: {
      title: "开始聊天吧",
      noCache: true
    }
  },
  {
    name: "notReadContent",
    path: "/notReadContent",
    component: () => import("@/views/message/notReadContent.vue"),
    meta: {
      title: "查看未读评论",
      noCache: true
    }
  },
  {
    name: "payment",
    path: "/payment",
    component: () => import("@/views/agreement/payment.vue"),
    meta: {
      title: "支付宝支付界面",
      noCache: true
    }
  },
  {
    name: "payByWechat",
    path: "/payByWechat",
    component: () => import("@/views/agreement/payByWechat.vue"),
    meta: {
      title: "微信支付界面",
      noCache: true
    }
  },
  {
    name: "worry",
    path: "/worry",
    component: () => import("@/views/myRoom/worry.vue"),
    meta: {
      title: "微信支付界面",
      noCache: true
    }
  },
  {
    name: "chachengfen",
    path: "/chachengfen",
    component: () => import("@/views/group/chachengfen.vue"),
    meta: {
      title: "狠狠视奸",
      noCache: true
    }
  },
  {
    name: "fatiezi",
    path: "/fatiezi",
    component: () => import("@/views/group/fatiezi.vue"),
    meta: {
      title: "发帖子",
      noCache: true
    }
  },
  {
    name: "RoomDetail",
    path: "/roomDetail",
    component: () => import("@/views/roomDetail/roomDetail.vue"),
    meta: {
      title: "房间详情",
      noCache: true
    }
  },
  {
    name: "ApartmentDetail",
    path: "/apartmentDetail",
    component: () => import("@/views/apartmentDetail/apartmentDetail.vue"),
    meta: {
      title: "公寓详情",
      noCache: true
    }
  },
  {
    name: "Appointment",
    path: "/appointment",
    component: () => import("@/views/appointment/appointment.vue"),
    meta: {
      title: "预约看房",
      noCache: true
    }
  },
  {
    name: "MyAppointment",
    path: "/myAppointment",
    component: () => import("@/views/myAppointment/myAppointment.vue"),
    meta: {
      title: "我的预约",
      noCache: true
    }
  },
  {
    name: "MyAgreement",
    path: "/myAgreement",
    component: () => import("@/views/myAgreement/myAgreement.vue"),
    meta: {
      title: "我的租约",
      noCache: true
    }
  },
  {
    name: "Agreement",
    path: "/agreement",
    component: () => import("@/views/agreement/agreement.vue"),
    meta: {
      title: "租约详情",
      noCache: true
    }
  },
  {
    name: "BrowsingHistory",
    path: "/browsingHistory",
    component: () => import("@/views/browsingHistory/browsingHistory.vue"),
    meta: {
      title: "浏览历史",
      noCache: true
    }
  },
  {
    name: "Login",
    path: "/login",
    component: () => import("@/views/login/login.vue"),
    meta: {
      title: "登录",
      noCache: true
    }
  }
];

export default routes;
