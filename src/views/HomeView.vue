<template>
  <el-container class="home-container">
    <el-header>
      <el-row :gutter="30" class="header-content">
        <el-col :span="1">
          <img src="../assets/logo.png" style="width:100%;height:100%;margin-top:20%;"/>
        </el-col>
        <el-col :span="3">
          <h1>汪汪旅途</h1>
        </el-col>
        <el-col :span="7.5">
          <el-menu :default-active="activeIndex" class="menu" mode="horizontal" :ellipsis="false" @select="selectMenu"
            style="color:black">
            <el-menu-item index="1" style="color: black;font-weight: bold;font-size:17px;">火车订票</el-menu-item>
            <el-menu-item index="2" style="color: black;font-weight: bold;font-size:17px;">酒店预订</el-menu-item>
            <el-menu-item index="3" style="color: black;font-weight: bold;font-size:17px;">火车订餐</el-menu-item>
            <el-menu-item index="4" style="color: black;font-weight: bold;font-size:17px;">我的订单</el-menu-item>
          </el-menu>
        </el-col>
        <el-col :span="2" :offset="10">
          <el-button type="primary" round plain size="large">登录/注册</el-button>
        </el-col>
      </el-row>
    </el-header>
    <div class="main">
      <el-main width="80%">
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
      <el-aside width="18%">
        <v-card hover style="background-color: #f8fafb;border-radius: 25px;width:100%;height:95%;margin-top:6%;">
          <h1>消息通知（占地）</h1>
        </v-card>
      </el-aside>
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
const selectMenu = (key) => {
  console.log('当前选中为' + key)
  activeIndex.value = key
}
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

.el-aside {
  margin-right: 1%;
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
.menu-change{
  height:100%;
}
</style>
