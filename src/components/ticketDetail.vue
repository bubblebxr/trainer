<template>
    <el-container width="100%;" style="display: flex;align-items: center;justify-content: center;">
        <el-main width="75%" style="height:88vh;margin-top:-1%;">
            <el-card style="width:100%;justify-content: center;height:15%;">
                <el-row>
                    <el-col :span="6" style="display: flex;flex-direction: column;justify-content: center;">
                        <el-text size="large" style="width">{{ start_station }}</el-text>
                        <el-text tag="b" style="font-size: 25px;">{{ start_time }}</el-text>
                        <el-text size="large">{{ start_date }}</el-text>
                    </el-col>
                    <el-col :span="6" style="display: flex;flex-direction: column;justify-content: center;">
                        <el-divider content-position="center"><el-text tag="b" size="large"
                                style="font-size:20px;padding:5px 35px;border:1.5px solid #DDDCDC;border-radius: 10px;">{{
                                tid }}</el-text></el-divider>
                    </el-col>
                    <el-col :span="6"
                        style="display: flex;flex-direction: column;justify-content: center;border-right:1.5px solid #DDDCDC;">
                        <el-text size="large" style="width">{{ arrive_station }}</el-text>
                        <el-text tag="b" style="font-size: 25px;">{{ arrive_time }}</el-text>
                        <el-text size="large">{{ arrive_date }}</el-text>
                    </el-col>
                    <el-col :span="6" style="display: flex;flex-direction: column;justify-content: center;">
                        <el-text tag="b" style="font-size: 25px;">{{ Time }}</el-text>
                    </el-col>
                </el-row>
            </el-card>
            <el-card style="width:100%;margin-top:1%;height:27%;padding-top: 1%;">
                <h1 style="margin-top:-1%;margin-bottom: 1%;">剩余座席</h1>
                <div style="display:flex;text-align:center;">
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="business != '无票'">
                        <el-text tag="b" style="font-size:16px;margin-top:0%;">商务座</el-text><br /><br />
                        <el-text size="large" style="color: #ffa31a;">￥{{ business }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="one != '无票'">
                        <el-text tag="b" style="font-size:16px;">一等座</el-text><br /><br />
                        <el-text size="large" style="color: #ffa31a;">￥{{ one }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="two != '无票'">
                        <el-text tag="b" style="font-size:16px;">二等座</el-text><br /><br />
                        <el-text size="large" style="color: #ffa31a;">￥{{ two }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="soft_sleeper != '无票'">
                        <el-text tag="b" style="font-size:16px;">软卧</el-text><br /><br />
                        <el-text size="large" style="color: #ffa31a;">￥{{ soft_sleeper }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="hard_sleeper != '无票'">
                        <el-text tag="b" style="font-size:16px;">硬卧</el-text><br /><br />
                        <el-text size="large" style="color: #ffa31a;">￥{{ hard_sleeper }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="hard_seat != '无票'">
                        <el-text tag="b" style="font-size:16px;">硬座</el-text><br /><br />
                        <el-text size="large" style="color: #ffa31a;">￥{{ hard_seat }}</el-text>
                    </el-card>
                </div>
            </el-card>
            <el-card style="width:100%;margin-top:1%;height:49%;">
                <h1>乘客信息</h1>
                <div style="display: flex;justify-content: center;">
                    <el-checkbox-group v-model="selectedPerson" @change="handleChecked" :min="1">
                        <el-checkbox v-for="p in person" :key="p" :label="p" :value="p">
                            {{ p }}
                        </el-checkbox>
                    </el-checkbox-group>
                </div>
                <el-table :data="passengersTable" height="250" style="width: 100%">
                    <el-table-column label="席别" width="300">
                        <template #default="scope">
                            <el-select v-model="value[scope.$index]" placeholder="Select" size="large"
                                style="width: 260px">
                                <el-option v-for="item in options" :key="item" :label="item" :value="item" />
                            </el-select>
                        </template>
                    </el-table-column>
                    <el-table-column prop="name" label="姓名" width="200" />
                    <el-table-column label="证件类型" width="180">
                        <template #default="scope">
                            <el-text>中国居民身份证</el-text>
                        </template>
                    </el-table-column>
                    <el-table-column prop="identification" label="身份证号" width="300" />
                    <el-table-column label="操作">
                        <template #default="scope">
                            <div v-if="scope.$index != '0'">
                                <el-button type="danger" :icon="Delete" circle @click="singleDelete(scope.$index)"
                                    color="#FF8486" />
                            </div>
                        </template>
                    </el-table-column>
                </el-table>
            </el-card>
            <div style="display: flex;justify-content: center;margin-top:1%;">
                <el-button type="info" plain size="large" style="width:10%;" @click="goBack()">返回</el-button>
                <el-button type="success" plain size="large" style="width:10%;"
                    @click="billVisible = true; buttonVisible = true">提交</el-button>
            </div>
        </el-main>
        <el-aside width="25%" style="height:88vh;display: block;">
            <div style="display: flex;justify-content: center;">
                <el-card style="width:90%;height:30%;margin-top:3.5%;">
                    <template #header>
                        <div class="card-header">
                            <h1 style="font-size: 25px;">票价信息</h1>
                        </div>
                    </template>
                    <h1 style="font-size: 25px;">订单总额：</h1>
                    <div style="display: flex;justify-content: center;">
                        <h1 style="font-size: 25px;margin-top:6%;color:#ffa31a;">￥{{ total }}</h1>
                    </div>
                </el-card>
            </div>
            <div style="display: flex;justify-content: center;height:63%;">
                <el-card style="width:90%;height:100%;margin-top:6%;">
                    <template #header>
                        <div class="card-header">
                            <h1 style="font-size: 25px;">温馨提示</h1>
                        </div>
                        <br />
                    </template>
                    <br />
                    <el-text>购票结果会及时短信通知您;您也可以到订单中心查看。</el-text><br />
                    <el-text>乘客姓名与证件号码必须与乘车时所使用证件上的名字和号码一致。</el-text><br />
                    <el-text>身高超过1.5米的孩子需要购买成人票。</el-text><br />
                    <el-text>送票到家购票成功后，会有短信通知您快递方式及单号。</el-text><br />
                    <el-text>如未能出票，将第一时间为您退款(1~15工作日到账，以具体支付方式为准)</el-text><br />
                    <el-text>选择送票上门后，车票仅能通过快递送达，无法至取票机取票。</el-text>
                </el-card>
            </div>
        </el-aside>
    </el-container>
    <el-dialog v-model="billVisible" title="确认订单" width="20em" align-center>
        <div style="
            display: flex;
            justify-content: center;
            align-items: center;
          ">
            <div>
                <text>总金额：</text>
                <text style="font-weight: bold; color: #ffa31a">￥{{ total }}</text>
            </div>
        </div>
        <div>
            <img :src="payPicture" alt="2DPayPicture"
                style="width: 100%; height: 100%; object-fit: cover;margin-top:3%;" id />
            <div style="margin-top:3%;display:flex;justify-content: center;">
                <el-button type="primary" @click="changePay">换一种支付方式</el-button>
                <el-button type="success" @click="ordersCommit">已完成支付</el-button>
            </div>
        </div>
    </el-dialog>
</template>
<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted, watch } from 'vue';
import { getPassengers, postTicketBill } from '../api/api';
import { ElNotification, ElMessage } from 'element-plus';
const router = useRouter();
const line = ref(null);
const start_station = ref('');
const arrive_station = ref('');
const start_date = ref('');
const start_time = ref('');
const arrive_date = ref('');
const arrive_time = ref('');
const Time = ref('');
const tid = ref('');
const business = ref('');
const one = ref("");
const two = ref("");
const soft_sleeper = ref("");
const hard_sleeper = ref("");
const hard_seat = ref("");
const selectedPerson = ref([]);
const person = ref([]);
const passengers = ref([]);
const id = ref('1');//暂时代替用户id
const passengersTable = ref([]);
const options = ref([]);
const value = ref([]);//多选框选中的
const total = ref(0);
const billVisible = ref(false);
const payPicture = ref(require("../assets/vxPay.jpg"));
const ordersCommit=async()=>{
    billVisible.value=false;
    var info=[];
    const regex = /^(.*?)（/;
    for (var i = 0; i < passengersTable.value.length;i++){
        var temp = value.value[i].match(regex)[1];
        info.push({ name: passengersTable.value[i]['name'], identification: passengersTable.value[i]['identification'],seat_type:temp });
    }
    try{
        const responce = await postTicketBill(
            info,
            id.value,
            tid.value,
            start_date.value,
            total.value,
        );
        var result = responce.data.result;
        if(result){
            ElMessage({
                message: "下单成功",
                type: "success",
            });
            //todo:页面跳转
        }else{
            ElMessage({
                message: "下单成功",
                type: "error",
            });
        }
    }catch(error){
        console.log('提交火车票订单失败',error);
    }
}
const changePay = () => {
    if (payPicture.value === require("../assets/vxPay.jpg"))
        payPicture.value = require("../assets/zfbPay.jpg");
    else payPicture.value = require("../assets/vxPay.jpg");
};
import {
    Delete,
} from '@element-plus/icons-vue'
const goBack = () => {
    router.back();
};
const getInfo = async () => {
    try {
        const data = await getPassengers(id.value);
        passengers.value = data.data.passenger;
        console.log("获取乘车人数组成功", passengers.value);
        person.value = passengers.value.map(passenger => passenger.name);
        console.log("person" + person.value);
        selectedPerson.value.push(passengers.value[0]['name']);
        passengersTable.value.push(passengers.value[0]);
    } catch (error) {
        console.error('获取乘车人数组失败：', error);
    }
};
const handleChecked = (e) => {
    // console.log("handle!" + e);
    const temp = person.value.filter(name => !e.includes(name));
    for (var i = 0; i < temp.length; i++) {
        value.value.pop(i);
        passengersTable.value.pop(passengers.value.find(p => p.name === temp[i]));
    }
    for (var i = 0; i < e.length; i++) {
        if (!passengersTable.value.find(passenger => passenger.name === e[i])) {
            passengersTable.value.push(passengers.value.find(p => p.name === e[i]));
            value.value[passengersTable.value.length - 1] = options.value[0];
        }
    }
};
const singleDelete = async (index) => {
    var name = passengersTable.value[index]['name'];
    selectedPerson.value.pop(name);
    passengersTable.value.splice(index, 1);
    value.value.pop(index);
};
watch(value, () => {
    total.value = 0;
    for (var i = 0; i < value.value.length; i++) {
        const regex = /(\d+)/;
        total.value += parseInt(value.value[i].match(regex), 10);;
    }
}, {
    deep: true
});
onMounted(async () => {
    line.value = JSON.parse(router.currentRoute.value.query.line);
    console.log(line.value);
    const stations = line.value.station.split(/\n/);
    start_station.value = stations[0];
    arrive_station.value = stations[1];
    const time = line.value.startEnd.split(/\n/);
    var time1 = time[0];
    var start = time1.split(' ');
    start_date.value = start[0];
    start_time.value = start[1];
    var time2 = time[1];
    var arrive = time2.split(' ');
    arrive_date.value = arrive[0];
    arrive_time.value = arrive[1];
    Time.value = line.value.time;
    tid.value = line.value.tid;
    business.value = line.value.business;
    one.value = line.value.one;
    two.value = line.value.two;
    soft_sleeper.value = line.value.soft_sleeper;
    hard_sleeper.value = line.value.hard_sleeper;
    hard_seat.value = line.value.hard_seat;
    getInfo();
    if (two.value != '无票') {
        options.value.push('二等座（￥' + two.value + "元）");
    }
    if (one.value != '无票') {
        options.value.push('一等座（￥' + one.value + "元）");
    }
    if (business.value != '无票') {
        options.value.push('商务座（￥' + business.value + "元）");
    }
    if (soft_sleeper.value != '无票') {
        options.value.push('软卧（￥' + soft_sleeper.value + "元）");
    }
    if (hard_sleeper.value != '无票') {
        options.value.push('硬卧（￥' + hard_sleeper.value + "元）");
    }
    if (hard_seat.value != '无票') {
        options.value.push('硬座（￥' + hard_seat.value + "元）");
    }
    value.value[0] = options.value[0];
    const regex = /(\d+)/;
    total.value = parseInt(options.value[0].match(regex), 10);
});
</script>
<style></style>