<template>
    <div style="width: 100%; overflow: hidden;">
        <v-card hover style="background-color: #e4f5ff; border-radius: 25px; width:100%; height:10%;display: block;">
            <div class="select">
                <el-autocomplete v-model="startStation" :fetch-suggestions="queryStartStation" placeholder="出发地"
                    class="item" @select="handleStart" clearable :popper-class="popperClass" id="startStation" />
                <el-icon :size="20">
                    <Switch @click="switchStation" />
                </el-icon>
                <el-autocomplete v-model="destinationStation" :fetch-suggestions="queryStartStation" placeholder="到达地"
                    class="item" @select="handleDestination" clearable :popper-class="popperClass"
                    id="destinationStation" />
                <el-date-picker class="item" v-model="date" type="date" placeholder="出发日期" :shortcuts="shortcuts"
                    :size="20" />
                <el-icon style="width:30px;">
                    <Search @click="search"
                        style="background-color:orange;width:30px;;height:200%;border-radius: 5px;" />
                </el-icon>
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
                    <el-checkbox v-model="soft_sleeper" label="软卧" size="large" />
                    <el-checkbox v-model="hard_sleeper" label="硬卧" size="large" />
                    <el-checkbox v-model="hard_seat" label="硬座" size="large" />
                </el-col>
            </el-row>
            <el-row style="margin-top:0.5%;">
                <el-col :span="1.5">
                    <el-text tag="b" size="large">排序方式：</el-text>
                </el-col>
                <el-col :span="20.5" style="margin-top:-0.7%;">
                    <el-radio-group v-model="sortType" class="ml-4">
                        <el-radio value="1" size="large">出发时间升序</el-radio>
                        <el-radio value="2" size="large">出发时间降序</el-radio>
                        <el-radio value="3" size="large">旅途总时长升序</el-radio>
                    </el-radio-group>
                </el-col>
            </el-row>
        </div>
        <div style="margin-top:0.2%;  width:100%; height:3%;display: block;padding-left:1%;">
            <el-text tag="i" style="color:gray;">{{ startStation }}到{{ destinationStation }}共{{ }}个车次</el-text>
        </div>
        <div style="margin-top:0.2%;  width:100%; height:15%;display: block;padding-left:1%;">
            <el-table :data="tableData" height="610" style="width: 100%"
                :header-cell-style="{ background: '#8abbe7', color: 'white' }">
                <el-table-column prop="date" label="车次" width="100" />
                <el-table-column prop="name" :label="'出发站\n到达站'" width="180" />
                <el-table-column prop="address" :label="'出发时间\n到达时间'" width="180" />
                <el-table-column prop="name" label="历时" width="100" />
                <el-table-column prop="name" label="商务座" width="100" />
                <el-table-column prop="name" label="一等座" width="100" />
                <el-table-column prop="name" label="二等座" width="100" />
                <el-table-column prop="name" label="软卧" width="100" />
                <el-table-column prop="name" label="硬卧" width="100" />
                <el-table-column prop="name" label="硬座" width="100" />
                <el-table-column prop="name" label="其他" width="100" />
                <el-table-column prop="name" label="备注" />
            </el-table>
        </div>
    </div>

</template>

<script setup>
import { ref, onMounted, watch } from 'vue'
import { getStation } from '../api/api';
const date = ref('');
const train = ref(true);
const normalTrain = ref(true);
const business = ref(true);
const one = ref(true);
const two = ref(true);
const soft_sleeper = ref(true);
const hard_sleeper = ref(true);
const hard_seat = ref(true);
const sortType = ref('1');
const startStation = ref('');
const destinationStation = ref('');
const startStationOptions = ref([]);
const stations = ref([]);/*车站信息*/
const tableData = [
    {
        date: '2016-05-03',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-02',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-04',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-01',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-08',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-06',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
    {
        date: '2016-05-07',
        name: 'Tom',
        address: 'No. 189, Grove St, Los Angeles',
    },
]
const queryStartStation = (queryString, cb) => {
    const filteredOptions = queryString
        ? startStationOptions.value.filter(option =>
            option.value.toLowerCase().includes(queryString.toLowerCase())
        )
        : startStationOptions.value;
    cb(filteredOptions);
};
const handleStart = item => {
    console.log('Selected start station:', item);
};
const handleDestination = item => {
    console.log('Selected end station:', item);
};
const loadStartStationOptions = () => {
    return [
        { value: '北京' },
        { value: '上海' },
        { value: '广州' },
        { value: '深圳' },
        { value: '杭州' },
    ];
};
const search = () => {
    //TODO
}
watch([startStation], (newValue, oldValue) => {
    // if (newValue[0] != '' && !stations.value.includes(newValue[0])){
    //     var start = document.getElementById('startStation');
    //     start.setAttribute('id', 'startNo');
    // }else{
    //     var start = document.getElementById('startStation');
    //     if(start===null){
    //         start = document.getElementById('startNo');
    //     }
    //     start.setAttribute('id', 'startStation');
    // }
    // if (newValue[1] != '' && !stations.value.includes(newValue[1])) {
    //     var start = document.getElementById('destinationStation');
    //     start.setAttribute('id', 'endNo');
    // } else {
    //     var end = document.getElementById('destinationStation');
    //     if (end === null) {
    //         end = document.getElementById('endNo');
    //     }
    //     end.setAttribute('id', 'destinationStation');
    // }
}, { immediate: true });
watch([train, normalTrain, business, one, two, soft_sleeper, hard_sleeper, hard_seat, sortType], (newValue, oldValue) => {
    console.log('筛选方式改变', newValue, oldValue);
    search();
}, { immediate: true });
const fetchData = async () => {
    console.log("check");
    try {
        const data = await getStation();
        stations.value = data.data.station;
        console.log("获取车站数组成功", stations.value);
    } catch (error) {
        console.error('获取车站数组失败：', error);
    }
};
onMounted(() => {
    startStationOptions.value = loadStartStationOptions();
    fetchData();

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
        white-space: pre-wrap;
    }
}

#startNo {
    border: 1px solid red;
}

#endNo {
    border: 1px solid red;
}
</style>
