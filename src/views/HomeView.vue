<template>
  <el-container class="home-container">
    <el-header style="display: flex; align-items: center; justify-content: space-between;">
      <div style="display: flex; align-items: center;">
        <img src="../assets/logo.png" style="width:55px;height:55px;margin-top:3%;" />
        <el-menu :default-active="activeIndex" class="menu" mode="horizontal" :ellipsis="false" @select="selectMenu"
          style="color:black;margin-top:2%;">
          <el-menu-item index="1" style="color: black;font-weight: bold;font-size:17px;">火车订票</el-menu-item>
          <el-menu-item index="2" style="color: black;font-weight: bold;font-size:17px;">酒店预订</el-menu-item>
          <el-menu-item index="3" style="color: black;font-weight: bold;font-size:17px;">火车订餐</el-menu-item>
          <el-menu-item index="4" style="color: black;font-weight: bold;font-size:17px;">我的订单</el-menu-item>
        </el-menu>
      </div>
      <div style="display: flex; align-items: center;">
        <el-button type="primary" round plain size="large" style="margin-top:12%;margin-right:3%;">登录/注册</el-button>
        <img src="../assets/message.png" style="width: 55px; height: 55px; margin-top: 8%;" />
      </div>
    </el-header>
    <div class="main">
      <el-main width="80%" style="height:95vh;">
        <el-container v-if="activeIndex === '1'" class="menu-change">
          <ticket-component></ticket-component>
        </el-container>
        <el-container v-if="activeIndex === '2'" class="menu-change">
          <hotel-component></hotel-component>
        </el-container>
        <el-container v-if="activeIndex === '3'" class="menu-change">
          <food-component></food-component>
        </el-container>
        <el-container v-if="activeIndex === '4'" class="menu-change">
          <orders-component></orders-component>
        </el-container>
      </el-main>
    </div>
  </el-container>
</template>

<script setup>
import { ref } from 'vue'
import ticketComponent from '../components/bookTicket.vue'
import hotelComponent from '../components/bookHotel.vue'
import foodComponent from '../components/bookFood.vue'
import ordersComponent from '../components/myOrders.vue'
const activeIndex = ref('1')
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
