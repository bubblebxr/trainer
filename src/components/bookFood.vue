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
          <el-select
            v-model="tid"
            placeholder="车次"
            class="item"
            :popper-class="popperClass"
          >
            <el-option
              v-for="item in Paidticket"
              :key="item"
              :label="item"
              :value="item"
            >
            </el-option>
          </el-select>
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
    <foodCart v-model="order_foods" @getPaid="submitBill"/>
  </div>
</template>

<script setup>
import { ref, onMounted, watch } from "vue";
import foodCart from "./foodCart.vue";
import { getFoods, getThisTicket } from "@/api/api";
import { ElMessage } from "element-plus";
const userID = 0; //当前用户ID
const tid = ref("");
const date = ref("");
const time = ref("lunch");
const foodList = ref([]);

const order_foods = ref([]); //已点的食物
const Paidticket = ref([]);

const search = () => {
  if (tid.value === "" || time.value === "" || date.value === "") {
    ElMessage({
      message: "还有没填写的信息",
      type: "error",
      plain: true,
    });
  } else {
    fetchFoods();
    ElMessage({
      message: "查询成功",
      type: "success",
    });
  }
};
const fetchFoods = async () => {
  try {
    const response = await getFoods(tid, date, time);
    foodList.value = response.data.result;
  } catch (error) {
    console.error("获取食物数组失败:", error);
  }
};

const fetchTids = async () => {
  try {
    const response = await getThisTicket(userID, "paid");
    var ticket = response.data.result;
    var tempList = [];
    for (var item of ticket) {
      tempList.push(item.tid);
    }
    Paidticket.value = tempList;
  } catch (error) {
    console.error("获取车站数组失败：", error);
  }
};
const submitBill=()=>{
  //todo 向后端提交表单

  ElMessage({
      message: "下单成功",
      type: "success",
    });
};

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
onMounted(() => {
  fetchTids();
});
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
  position: absolute;
  z-index: 0;
}
</style>