<template>
    <el-container width="100%;" style="display: flex;align-items: center;justify-content: center;">
        <el-main width="75%" style="height:88vh;margin-top:-1%;">
            <el-card style="width:100%;justify-content: center;">
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
            <el-card style="width:100%;margin-top:2%;">
                <h1 style="margin-top:-1%;margin-bottom: 1%;">剩余座席</h1>
                <div style="display:flex;text-align:center;">
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="business != '无票'">
                        <el-text tag="b" style="font-size:16px;margin-top:0%;">商务座</el-text><br />
                        <el-text size="large" style="color: #ffa31a;">￥{{ business }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="business != '无票'">
                        <el-text tag="b" style="font-size:16px;">商务座</el-text>
                        <el-text size="large" style="color: #ffa31a;">￥{{ business }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="business != '无票'">
                        <el-text tag="b" style="font-size:16px;">商务座</el-text>
                        <el-text size="large" style="color: #ffa31a;">￥{{ business }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="business != '无票'">
                        <el-text tag="b" style="font-size:16px;">商务座</el-text>
                        <el-text size="large" style="color: #ffa31a;">￥{{ business }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="business != '无票'">
                        <el-text tag="b" style="font-size:16px;">商务座</el-text>
                        <el-text size="large" style="color: #ffa31a;">￥{{ business }}</el-text>
                    </el-card>
                    <el-card style="flex:1;margin:0% 1%;" shadow="never" v-if="business != '无票'">
                        <el-text tag="b" style="font-size:16px;">商务座</el-text>
                        <el-text size="large" style="color: #ffa31a;">￥{{ business }}</el-text>
                    </el-card>
                </div>
            </el-card>
            <el-card style="width:100%;margin-top:2%;height:43%;">
                <h1>乘客信息</h1>
                
            </el-card>
            <div style="display: flex;justify-content: center;margin-top:1%;">
                <el-button type="info" plain size="large" style="width:10%;" @click="goBack()">返回</el-button>
                <el-button type="success" plain size="large" style="width:10%;">提交</el-button>
            </div>
        </el-main>
        <el-aside width="25%" style="height:89vh;display: block;">
            <div style="display: flex;justify-content: center;">
                <el-card style="width:90%;height:30%;margin-top:3.5%;">
                    <template #header>
                        <div class="card-header">
                            <h1 style="font-size: 25px;">票价信息</h1>
                        </div>
                    </template>
                    <h1 style="font-size: 25px;">订单总额：</h1>
                    <div style="display: flex;justify-content: center;">
                        <h1 style="font-size: 25px;margin-top:6%;color:#ffa31a;">￥255</h1>
                    </div>
                </el-card>
            </div>
            <div style="display: flex;justify-content: center;height:65%;">
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
</template>
<script setup>
import { useRouter } from "vue-router";
import { ref, onMounted } from 'vue'
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

const goBack = () => {
    router.back();
};
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
});
</script>
<style></style>