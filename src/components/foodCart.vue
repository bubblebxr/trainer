<template>
  <div style="background-color: white; width: 21em">
    <el-card v-show="isShow">
      <template #header>
        <div style="display: flex">
          <div style="margin-right: 1em">购物车</div>
          <el-button
            :icon="Delete"
            circle
            style="height: 1.6em; width: 1.6em"
            @click="clear"
          ></el-button>
        </div>
      </template>
      <div v-for="(item, index) in model" :key="index">
        <text style="display: inline-block; width: 6em">{{ item.name }}</text>
        <span
          style="
            display: inline-block;
            width: 6em;
            font-weight: bold;
            color: #ffa31a;
          "
          >￥{{ item.price * item.num }}</span
        >
        <el-input-number
          v-model="item.num"
          size="small"
          :min="0"
          style="width: 6em"
        />
      </div>
      <template #footer>
        <div
          style="
            display: flex;
            justify-content: space-between;
            align-items: center;
          "
        >
          <div>
            <text>总金额：</text>
            <text style="font-weight: bold; color: #ffa31a"
              >￥{{ sum_price }}</text
            >
          </div>
          <div>
            <el-button color="#ffa31a" :dark="isDark" plain @click="bill"
              >结算</el-button
            >
          </div>
        </div>
      </template>
    </el-card>
  </div>
  <div @click="openCart" style="display: block; text-align: right">
    <img
      src="../assets/shopingCart.png"
      alt="shoppingCart"
      style="width: 3em"
    />
  </div>
</template>
<script setup>
import { ref, onMounted, watch } from "vue";
import {
  Delete,
} from "@element-plus/icons-vue";
const model = defineModel();
var sum_price = ref(0);
var isShow = ref(true);
onMounted(() => {});
watch(model, (newmodel) => {
  const total = newmodel.reduce((acc, item) => {
    return acc + item.price * item.num;
  }, 0);

  // 将总价存储在 sum_price 中
  sum_price = total;
});
const openCart = () => {
  console.log("点击了购物车,isShow=" + isShow.value);
  isShow.value = !isShow.value;
};
const clear = () => {
  for (const item of model.value) {
    item.num = 0;
  }
};
const bill = () => {
  //TODO
};
</script>
<style scoped>
</style>