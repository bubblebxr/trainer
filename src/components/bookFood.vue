<template>
  <div style="width: 100%; height: 100%">
    <div style="width: 100%; margin-bottom: 5%; height: 10%">
      <v-card
        hover
        style="
          background-color: #e4f5ff;
          border-radius: 25px;
          width: 100%;
          height: 100%;
          display: block;
        "
      >
        <div class="select">
          <el-autocomplete
            v-model="tid"
            :fetch-suggestions="queryTid"
            placeholder="车次"
            class="item"
            @select="handleStart"
            clearable
            :popper-class="popperClass"
          />
          <el-date-picker
            class="item"
            v-model="date"
            type="date"
            placeholder="出发日期"
            :shortcuts="shortcuts"
            :size="20"
          />
          <el-radio-group
            v-model="time"
            style="
              margin-right: 10px;
              margin-left: 10px;
              flex: 0.3;
              justify-content: center;
            "
          >
            <el-radio-button label="午餐" value="lunch" />
            <el-radio-button label="晚餐" value="dinner" />
          </el-radio-group>
          <el-icon style="flex: 0.1">
            <Search
              @click="search"
              style="
                background-color: orange;
                width: 30px;
                height: 200%;
                border-radius: 5px;
              "
            />
          </el-icon>
        </div>
      </v-card>
    </div>

    <div class="container">
      <div v-for="(item, index) in foodList" :key="index">
        <div style="display: flex; flex-wrap: wrap; margin: 2.5%">
          <el-card shadow="hover">
            <template #header>
              <div style="display: flex; justify-content: space-between">
                <div>{{ item.name }}</div>
                <div style="font-weight: bold; color: #ffa31a">
                  ￥{{ item.price }}
                </div>
              </div>
            </template>
            <img
              loading="lazy"
              referrerpolicy="no-referrer"
              object-fit="contain"
              :src="item.photo"
              style="
                width: 15em;
                height: 15em;
                display: block;
                margin-left: auto;
                margin-right: auto;
              "
            />
            <template #footer>
              <div style="display: flex; justify-content: space-between">
                <text>数量</text>
                <el-input-number v-model="item.num" size="small" :min="0" />
              </div>
            </template>
          </el-card>
        </div>
      </div>
    </div>
  </div>
  <div style="position: fixed; bottom: 2%; right: 1%">
    <foodCart v-model="order_foods" />
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import foodCart from "./foodCart.vue";
const tid = ref("");
const date = ref("");
const time = ref("");

const foodList = ref([
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202206/13/20220613194502_b7bab.jpeg",
    name: "冰淇淋",
    price: "18",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122044_7f0a8.jpeg",
    name: "煎蛋",
    price: "30",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122042_a68d8.jpeg",
    name: "水果坚果",
    price: "180.00",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202206/13/20220613194502_b7bab.jpeg",
    name: "冰淇淋",
    price: "18",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122044_7f0a8.jpeg",
    name: "煎蛋",
    price: "30",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122042_a68d8.jpeg",
    name: "水果坚果",
    price: "180",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202206/13/20220613194502_b7bab.jpeg",
    name: "冰淇淋",
    price: "18",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122044_7f0a8.jpeg",
    name: "煎蛋",
    price: "30",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122042_a68d8.jpeg",
    name: "水果坚果",
    price: "180",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122044_7f0a8.jpeg",
    name: "煎蛋",
    price: "30",
    num: 0,
  },
  {
    photo:
      "https://c-ssl.duitang.com/uploads/blog/202203/17/20220317122042_a68d8.jpeg",
    name: "水果坚果",
    price: "180",
    num: 0,
  },
]);
const order_foods = ref([]); //已点的食物

watch(
  foodList,
  (newFoodList) => {
    // 当 num 大于 0 时，将该项加入 order_foods
    order_foods.value = newFoodList.filter((item) => item.num > 0);
    console.log("修改了购物车内容");
  },
  { deep: true },
  { immediate: true }
);
</script>

<style scoped>
.item {
  flex: 1;
  margin-right: 10px;
  margin-left: 10px;
  margin: auto;
}
.container {
  display: flex;
  flex-wrap: wrap; /* 设置子元素自动换行 */
  justify-content: center;
}
</style>