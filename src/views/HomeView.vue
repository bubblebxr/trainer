<template>
  <el-container class="home-container">
    <el-header
      style="display: flex; align-items: center; justify-content: space-between"
    >
      <div style="display: flex; align-items: center">
        <img
          src="../assets/logo2.png"
          style="width: 100px; height: 55px; margin-top: 3%"
        />
        <el-menu
          :default-active="activeIndex"
          class="menu"
          mode="horizontal"
          :ellipsis="false"
          @select="selectMenu"
          style="color: black; margin-top: 2%"
        >
          <el-menu-item
            index="1"
            style="color: black; font-weight: bold; font-size: 17px"
            >火车订票</el-menu-item
          >
          <el-menu-item
            index="2"
            style="color: black; font-weight: bold; font-size: 17px"
            >酒店预订</el-menu-item
          >
          <el-menu-item
            index="3"
            style="color: black; font-weight: bold; font-size: 17px"
            >火车订餐</el-menu-item
          >
          <el-menu-item
            index="4"
            style="color: black; font-weight: bold; font-size: 17px"
            >我的</el-menu-item
          >
        </el-menu>
      </div>
      <div style="display: flex; align-items: center">
        <el-button
          type="primary"
          round
          plain
          size="large"
          style="margin-top: 12%; margin-right: 3%"
          >登录/注册</el-button
        >
        <el-badge
          :value="unReadNum"
          :show-zero="false"
          :max="10"
          style="margin-top: 8%; margin-right: 1em"
        >
          <img
            src="../assets/message.png"
            style="width: 50px; height: 50px"
            @click="drawer = true"
          />
        </el-badge>
      </div>
    </el-header>
    <div class="main">
      <el-main width="80%" style="height: 90vh">
        <router-view />
      </el-main>
    </div>

    <!-- 弹出消息栏 -->
    <el-drawer v-model="drawer" title="消息" direction="rtl" size="35%">
      <div v-for="(item, index) in message" :key="index">
        <el-card
          style="border-radius: 15px; margin-bottom: 5%"
          @click="
            item.haveRead = true;
            jumpToOrder(item.orderType, item.orderId);
          "
        >
          <template #header>
            <div style="display: flex; justify-content: space-between">
              <el-badge is-dot :hidden="item.haveRead">
                <span style="margin-top: 0.3em; margin-right: 0.3em">
                  {{ item.title }}
                </span>
              </el-badge>
              <span style="color: #dedfe0">{{ item.messageTime }}</span>
            </div>
          </template>
          <text>{{ item.content }}</text>
          <span style="vertical-align: middle; text-align: center">
            <text style="color: #a0cfff">查看详细信息</text>
            <el-icon style="color: #a0cfff"><DArrowRight /></el-icon>
          </span>
        </el-card>
      </div>
    </el-drawer>
  </el-container>
</template>

<script setup>
import { ref, onMounted, computed } from "vue";
import { useRouter } from "vue-router";
import eventBus from "@/eventBus.js";
import { getMessage } from "@/api/api.js";
const router = useRouter();
const activeIndex = ref("1");
const drawer = ref(false);
const selectMenu = (key) => {
  console.log("当前选中为" + key);
  activeIndex.value = key;
  eventBus.MyOrdersActiveKey = "1";
  if (activeIndex.value === "1") {
    router.push("/home/ticket");
  } else if (activeIndex.value === "2") {
    router.push("/home/hotel");
  } else if (activeIndex.value === "3") {
    router.push("/home/dining");
  } else if (activeIndex.value === "4") {
    router.push("/home/orders");
  }
};
// const message = ref([
//   {
//     title: "车票提醒",
//     messageTime: "2020-03-10",
//     content:
//       "您已购买2024-05-09 G5车次 杭州--->北京车次，发车时间05月09日08:00。请合理安排出行时间。",
//     haveRead: true,
//     orderType:"3",
//     orderId: "string3",
//   },
//   {
//     title: "订单取消",
//     messageTime: "2024-09-12",
//     content: "您已下单2024-10-20 G81车次 午餐餐品。将由列车员送到座位上。",
//     haveRead: false,
//     orderType:"5",
//     orderId: "string4",
//   },
// ]);
const message = ref([]);
const userID = "0000";
const unReadNum = computed(() => {
  return message.value.filter((msg) => !msg.haveRead).length;
});
onMounted(() => {
  router.push("/home/ticket");
  getAllMessage();
});
const getAllMessage = async () => {
  //TODO 判断是否登录
  try {
    const responce = await getMessage(userID);
    message.value = responce.data.result;
  } catch (error) {
    console.log("获取消息失败", error);
  }
};
const jumpToOrder = (orderType, orderId) => {
  eventBus.HeaderKey = "4";
  activeIndex.value = "4";
  //TODO 依据不同情况修改
  eventBus.MyOrdersActiveKey = orderType;
  if (orderType === "3") {
    router.push("/home/orders/ticketOrders");
    router.push({
      path: "/home/orders/ticketOrders",
      query: { orderId: orderId },
    });
  } else if (orderType === "4") {
    router.push({
      path: "/home/orders/hotelOrders",
      query: { orderId: orderId },
    });
  } else if (orderType === "5") {
    router.push({
      path: "/home/orders/foodOrders",
      query: { orderId: orderId },
    });
  }
};
setInterval(getAllMessage,1000);
</script>

<style>
.home-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-header {
  height: 60px;
}

* {
  margin: 0;
  padding: 0;
}

.main {
  flex: 1;
  display: flex;
  align-items: stretch;
}

.header-content {
  display: flex;
  align-items: center;
  padding-top: 8px;
}

.title {
  align-self: center;
  margin-right: auto;
}

.menu {
  margin-left: 10%;
}

.menu-change {
  height: 100%;
}
</style>
