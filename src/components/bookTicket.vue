<template>
    <div style="width: 100%; overflow: hidden;">
        <v-card hover style="background-color: #e4f5ff; border-radius: 25px; width:100%; height:10%;display: block;">
            <div class="select">
                <el-autocomplete v-model="startStation" :fetch-suggestions="queryStartStation" placeholder="出发地"
                    class="item" @select="handleStart" clearable :popper-class="popperClass" />
                <el-icon :size="20">
                    <Switch @click="switchStation" />
                </el-icon>
                <el-autocomplete v-model="destinationStation" :fetch-suggestions="queryStartStation" placeholder="到达地"
                    class="item" @select="handleDestination" clearable :popper-class="popperClass" />
                <el-date-picker class="item" v-model="date" type="date" placeholder="出发日期" :shortcuts="shortcuts"
                    :size="20" :disabled-date="disabledDate" />
                <el-icon style="width:30px;">
                    <Search @click="search"
                        style="background-color:orange;width:30px;;height:200%;border-radius: 5px;" />
                </el-icon>
            </div>
            <div
                style="margin-top: 0.1%; width: 100%; display: flex; justify-content: space-between; padding-left: 1%;">
                <div style="flex: 1; display: flex; justify-content: center;">
                    <el-text v-if="startInvalid" type="info" style="text-align: center;">没有查询到车站</el-text>
                </div>
                <div style="flex: 1; display: flex; justify-content: center;">
                    <el-text v-if="destinationInvalid" type="info" style="text-align: center;">没有查询到车站</el-text>
                </div>
                <div style="flex: 1;"></div>
            </div>
        </v-card>
        <div style="margin-top:0.9%;  width:100%; height:13%;display: block;padding-left:1%;">
            <el-row>
                <el-col :span="1.5">
                    <el-text tag="b" size="large">车次类型：</el-text>
                </el-col>
                <el-col :span="2">
                    <el-button type="success" plain size="small" @click="selectAllStyle">全部</el-button>
                </el-col>
                <el-col :span="7" style="margin-top:-0.7%;">
                    <el-checkbox v-model="train" label="动车" size="large" />
                    <el-checkbox v-model="normalTrain" label="普通火车" size="large" />
                </el-col>
            </el-row>
            <el-row style="margin-top:0.5%;">
                <el-col :span="1.5">
                    <el-text tag="b" size="large">车次席别：</el-text>
                </el-col>
                <el-col :span="2">
                    <el-button type="success" plain size="small" @click="selectAllSeat">全部</el-button>
                </el-col>
                <el-col :span="20.5" style="margin-top:-0.7%;">
                    <el-checkbox v-model="business" label="商务座" size="large" />
                    <el-checkbox v-model="one" label="一等座" size="large" />
                    <el-checkbox v-model="two" label="二等座" size="large" />
                </el-col>
            </el-row>
            <el-row style="margin-top:0.5%;">
                <el-col :span="1.5">
                    <el-text tag="b" size="large">排序方式：</el-text>
                </el-col>
                <el-col :span="10" style="margin-top:-0.7%;">
                    <el-radio-group v-model="sortType" class="ml-4">
                        <el-radio value="1" size="large">出发时间升序</el-radio>
                        <el-radio value="2" size="large">出发时间降序</el-radio>
                        <el-radio value="3" size="large">旅途总时长升序</el-radio>
                    </el-radio-group>
                </el-col>
            </el-row>
        </div>
        <div style="margin-top:1%;  width:100%; height:3%;display: block;padding-left:1%;">
            <el-row>
                <el-col :span="8">
                    <el-text tag="i" style="color:gray;">{{ startStation }}到{{ destinationStation }}共{{ }}个车次</el-text>
                </el-col>
                <el-col :span="3" style="margin-top:-0.7%;" :offset="13">
                    <el-checkbox v-model="isHide" label="隐藏冲突列车信息" size="large" />
                </el-col>
            </el-row>
        </div>
        <div style="margin-top:0.2%;  width:100%;display: block;padding-left:1%;margin-bottom:3%;">
            <el-table :data="searchResult" height="560" 
                :header-cell-style="{ background: '#8abbe7', color: 'white',}"  empty-text="没有列车信息">
                <el-table-column prop="tid" label="车次" width="100" />
                <el-table-column prop="station" :label="'出发站\n到达站'" width="220" />
                <el-table-column prop="startEnd" :label="'出发时间\n到达时间'" width="220" />
                <el-table-column prop="time" label="历时" width="125" />
                <el-table-column prop="business" label="商务座" width="125" />
                <el-table-column prop="one" label="一等座" width="125" />
                <el-table-column prop="two" label="二等座" width="125" />
                <el-table-column prop="beizhu" label="备注" />
            </el-table>
        </div>
    </div>

</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { getStation, getSearchResult } from '../api/api';
import { ElMessage } from 'element-plus'
const date = ref('');/**出发时间 */
const train = ref(true);/**高铁 */
const normalTrain = ref(true);/**正常火车 */
const isHide = ref(true);/**是否隐藏冲突列车信息 */
const business = ref(true);/**商务座 */
const one = ref(true);/**一等座*/
const two = ref(true);/**二等座 */
const sortType = ref('1');/**排序方式 */
const startStation = ref('');/**起始站 */
const destinationStation = ref('');/**终点站 */
const startStationOptions = ref([]);/**所有站点信息 */
const stations = ref([]);/*车站信息*/
const searchValid = ref(false);
const searchResult = ref([]);
const handleStart = item => {
    console.log('Selected start station:', item);
};
const handleDestination = item => {
    console.log('Selected end station:', item);
};
const queryStartStation = (queryString, cb) => {
    const filteredOptions = queryString
        ? startStationOptions.value.filter(option =>
            option.value.toLowerCase().includes(queryString.toLowerCase())
        )
        : startStationOptions.value;
    cb(filteredOptions);
};
const first=ref(false);
const fetchSearchResult = async () => {
    if (searchValid.value === false&&first.value) {
        var isGD = 1;
        if (train.value === true && normalTrain.value === true) isGD = 2;
        else if (normalTrain.value === true && train.value === false) isGD = 0;
        var seatType = [business.value, one.value, two.value];
        try {
            const response = await getSearchResult(startStation.value, destinationStation.value, date.value, isGD, sortType.value, seatType, isHide.value);
            var a=response.data.result;
            for (let i=0;i<a.length;i++) {
                a[i].startEnd = `${a[i].start_time}\n${a[i].arrive_time}`;
                delete a[i].start_time;
                delete a[i].arrive_time;
                a[i].station = `${a[i].start_station}\n${a[i].arrive_station}`;
                delete a[i].start_station;
                delete a[i].arrive_station;
                var flag=false;//记录是否还能买票
                if (a[i].business) {
                    if (a[i].business.remain != 0) {
                        var temp = a[i].business.price;
                        delete a[i].business;
                        a[i].business = temp;
                    } else {
                        delete a[i].business;
                        a[i].business = "无票";
                    }
                } else {
                    a[i].business = "无票";
                }
                if (a[i].one) {
                    if (a[i].one.remain != 0) {
                        var temp = a[i].one.price;
                        delete a[i].one;
                        a[i].one = temp;
                    } else {
                        delete a[i].one;
                        a[i].one = "无票";
                    }
                } else {
                    a[i].one = "无票";
                }
                if (a[i].two) {
                    if (a[i].two.remain != 0) {
                        var temp = a[i].two.price;
                        delete a[i].two;
                        a[i].two = temp;
                    } else {
                        delete a[i].two;
                        a[i].two = "无票";
                    }
                } else {
                    a[i].two = "无票";
                }
            }
            searchResult.value = a;
            console.log("获取查询信息成功", a);
        } catch (error) {
            console.error('Error fetching search result:', error);
        }
    }
};
const search = () => {
    if (startStation.value === '' || destinationStation.value === '' || date.value === '') {
        ElMessage({
            message: '还有没填写的信息',
            type: 'error',
            plain: true,
        })
    } else if (searchValid.value === true) {
        ElMessage({
            message: '没有查询到车站',
            type: 'error',
            plain: true,
        })
    } else {
        fetchSearchResult();
        ElMessage({
            message: '查询成功',
            type: 'success',
        })
    }
}
const startInvalid = ref(false);
const destinationInvalid = ref(false);
const disabledDate = (time) => {
    return time.getTime() < Date.now() - 8.64e7
};
watch(startStation, (newValue) => {
    startInvalid.value = newValue != '' && !stations.value.some(station => station.value === newValue);
    if (startInvalid.value === true || destinationInvalid.value === true) searchValid.value = true;
    else searchValid.value = false;
});

watch(destinationStation, (newValue) => {
    destinationInvalid.value = newValue != '' && !stations.value.some(station => station.value === newValue);
    if (startInvalid.value === true || destinationInvalid.value === true) searchValid.value = true;
    else searchValid.value = false;
});
watch([train, normalTrain, business, one, two, sortType, isHide], (newValue, oldValue) => {
    console.log('筛选方式改变', newValue, oldValue);
    fetchSearchResult();
}, { immediate: true });
const flag = ref(1);
const fetchData = async () => {
    if (flag.value === 1) {
        flag.value++;
        try {
            const data = await getStation();
            stations.value = data.data.station;
            console.log("获取车站数组成功", stations.value);
            startStationOptions.value = stations.value;
        } catch (error) {
            console.error('获取车站数组失败：', error);
        }
    }
};
onMounted(() => {
    fetchData();
    first.value=true;
});
const shortcuts = [
    {
        text: '今天',
        value: new Date(),
    },
    {
        text: '明天',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24);
            return date;
        },
    },
    {
        text: '一周以后',
        value: () => {
            const date = new Date();
            date.setTime(date.getTime() + 3600 * 1000 * 24 * 7);
            return date;
        },
    },
];
const switchStation = () => {
    var temp = startStation.value;
    startStation.value = destinationStation.value;
    destinationStation.value = temp;
    search();
}
const selectAllStyle = () => {
    train.value = true;
    normalTrain.value = true;
}
const selectAllSeat = () => {
    business.value = true;
    one.value = true;
    two.value = true;
    soft_sleeper.value = true;
    hard_sleeper.value = true;
    hard_seat.value = true;
}
</script>

<style>
.select {
    margin-top: 1.4%;
    margin-left: 2%;
    display: flex;
    align-items: center;
    margin-right: 2%;
}

.item {
    flex: 1;
    margin-right: 10px;
    margin-left: 10px;
}

.el-table {
    .cell {
        white-space: pre-wrap !important;
        text-align:center !important;
    }
    width:100%;
}
::v-deep .el-table__fixed {
    height: 100% !important;
    z-index: 50;
}
::v-deep .el-table__body-wrapper {
    overflow: scroll !important;
}
::v-deep .el-table__fixed:before {
    width: 0;
    height: 0;
}
</style>
