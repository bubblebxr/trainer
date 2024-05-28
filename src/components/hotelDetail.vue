<template>
  <div class="home-container" style="overflow-y:scroll;overflow-x:hidden;">
    <el-scrollbar style="height: 100%">
        <div class="top">
             <div style="display: flex;border-color:#a3a3a3;border-style: solid;margin: 0 auto;" class="top-info">
                <div style="height:25px;width:160px;margin-left:10px">
                  <div style="margin-top:5px;padding-left:10px;color:#969696;height:18px;font-size:15px;">入住时间</div>
                  <el-date-picker class="date_picker" v-model="check_in" type="date"  size="large" style="height:25px;width:160px;margin-top:0px;border:none" placeholder="请选择" bordered=0 suffixIcon=" " @change="recalculateDateDiff"/>
                </div>
                <div style="height:25px;width:80px;margin-top:15px;margin-left:10px">
                  <div style="background-color:#ededed;margin-top:5px;width:55px;padding-left:15px;color:black">{{ daysDiff }}晚</div>
                </div>
                <div style="height:25px;width:160px;">
                  <div style="margin-top:5px;padding-left:10px;color:#969696;height:18px;font-size:15px;">退房时间</div>
                  <el-date-picker class="date_picker" v-model="check_out" type="date" size="large" style="height:25px;width:160px;margin-top:0px;border:none" placeholder="请选择" bordered=0 suffixIcon=" " @change="recalculateDateDiff"/>
                </div>  
             </div>
        </div>

        <div style="border-color:#a3a3a3;margin: 0 auto;" class="information">
          <div style="display:flex;margin-left:10px;margin-top:10px;height:70px;">
            <p style="font-size:40px;font-weight: bold;">{{hotelData.name}}</p>
            <div style="margin-top:5px;margin-left:20px">
              <a-rate :value="hotelData.rank" style="font-size:30px;" disabled />
            </div>
            <div style="width:50px;margin-top:10px;margin-left:600px;">
              <button :class="{ 'before': !isPressed,'after': isPressed,'circle': true}" @click="toggleButton">
               <BookOutlined  :style="{color: isPressed ? 'white' : 'black', fontSize: '23px'}"/>
              </button>
            </div>
          </div>
          
          <div style="display:flex;margin-left:10px;color:grey">
            <EnvironmentOutlined />
            <p style="margin-left:3px;">{{hotelData.position}}</p>
          </div>
        
        </div>

        <div style="display:grid; grid-template-columns: 650px 245px 245px; grid-template-rows: 220px; grid-gap: 10px; border-color:#a3a3a3;border-style: solid;margin: 0 auto;" class="photo">
    <a-image-preview-group>
       <div class="big" style="grid-column-start: 1;">
        <a-image :width="650" height="440" style="object-fit:cover;height:450px;width:650px;" src="https://pavo.elongstatic.com/i/Mobile720_720/nw_1qOfnXM7v7G.jpg" />
       </div>
       <div class="small" style="grid-column-start: 2;">
        <a-image :width="245" height="220" style="height:220px;width:245px; object-fit:cover; " src="https://pavo.elongstatic.com/i/h5hotel350_350/1kCaNKrs6U8.jpg" />
        </div>
        <div class="small" style="grid-column-start: 3;">
        <a-image :width="245" height="220" style="height:220px;width:245px; object-fit:cover; " src="https://pavo.elongstatic.com/i/h5hotel350_350/1qOgNMpMF0c.jpg" />
        </div>
        <div class="small" style="grid-column-start: 2;">
        <a-image :width="245" height="220" style="height:220px;width:245px; object-fit:cover; " src="https://pavo.elongstatic.com/i/h5hotel350_350/1qOgNVQNZhm.jpg" />
        </div>
        <div class="small" style="grid-column-start: 3;">
        <a-image :width="245" height="220" style="height:220px;width:245px; object-fit:cover;" src="https://pavo.elongstatic.com/i/h5hotel350_350/1qOgNic4kJG.jpg" />
        </div>
    </a-image-preview-group>
</div>

        <div style="display: flex;border-color:#a3a3a3;margin: 0 auto;margin-top:10px" class="other">
            <el-tabs v-model="activeName" class="demo-tabs" @tab-click="handleClick" size="large">
                <el-tab-pane label="房型" name="rooms">
                    <div style="height:50px;display:flex;margin-left:2px;">
                        <el-space :size="size" :spacer="spacer">
                          <el-check-tag :checked="checked1" type="primary" @change="onChange1" >
                            标准双人间
                          </el-check-tag>
                          <el-check-tag :checked="checked2" type="primary" @change="onChange2">
                            大床房
                          </el-check-tag>
                          <el-check-tag :checked="checked3" type="primary" @change="onChange3">
                            家庭房
                          </el-check-tag>
                        </el-space>
                    </div>
                    <div style="width:1155px;display:flex;margin-left:2px;">
                      <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="hotelData.rooms">
                        <template #renderItem="{ item }">
                          <a-list-item key="item.name" style="width:1155px">
                            <template #actions>
                                <ReconciliationOutlined />
                                <span>
                                  
                                    {{ item.size }}
                                </span>
                                <span>
                                  <ReconciliationOutlined />
                                    {{ item.bed_size }}
                                </span>
                                
                                <div style="margin-left: 200px">
                                  <a-typography-title :level="2">¥ {{item.price}}</a-typography-title>         
                                </div>
                                <a-button :size="large" style="color:black" @click="showDrawer(item)">点击预订</a-button>
                                <a-drawer
                                  v-model:open="open"
                                  class="custom-class"
                                  root-class-name="root-class-name"
                                  :root-style="{ color: 'blue' }"
                                  style="color: black"
                                  title="预订信息"
                                  placement="right"
                                  @after-open-change="afterOpenChange"
                                > 
                                  <div style="display: flex;border-color:#a3a3a3;margin: 0 auto;" class="top-info">
                                    <div style="height:25px;width:120px;">
                                      <div style="margin-top:5px;padding-left:10px;color:#969696;height:18px;font-size:15px;">入住时间</div>
                                      <el-date-picker disabled="true"; v-model="check_in" size="large" style="height:25px;width:120px;border-style:none;margin-top:10px" placeholder="hihi" suffixIcon=" " />
                                    </div>
                                    <div style="height:25px;width:80px;margin-top:15px;margin-left:20px">
                                      <div style="background-color:#ededed;margin-top:5px;width:55px;padding-left:15px;color:black">{{daysDiff}}晚</div>
                                    </div>
                                    <div style="height:25px;width:130px;">
                                      <div style="margin-top:5px;padding-left:10px;color:#969696;height:18px;font-size:15px;">退房时间</div>
                                      <el-date-picker disabled="true"; v-model="check_out" size="large" style="height:25px;width:130px;border-style:none;margin-top:10px" placeholder="hihi" suffixIcon=" " />
                                    </div>  
                                  </div>
                                  <a-divider />
                                  <p style="font-size:17px;">住客资料</p>
                                  
                                  <p>房间数</p>
                                  <el-select
                                    v-model="roomCount"
                                    placeholder="Select"
                                    size="large"
                                    style="width: 300px;margin-bottom:10px;"
                                    @change="updateMoney"
                                   >
                                    <el-scrollbar style="height:100px;">
                                      <el-option
                                      v-for="index in selectedRoom.num"
                                      :key="index"
                                      :label="index+'间'"
                                      :value="index"
                                      />
                                    </el-scrollbar>
                                  </el-select>
                                  <div style="height:50px;width:300px;background-color:#f8e8e2;border-type:solid;border-radius:2px;color:#eb775c;padding-top:4px;padding-left:6px;padding-right:3px;margin-bottom:15px;">
                                    请输入住客姓名以及身份证号，每间只需填1人，姓名不可重复。
                                  </div>
                                  <div style="height:300px;">
                                    <el-scrollbar style="height:300px;">
                                    <template v-for="i in roomCount">
                                      <div style="margin-top:1px;display:flex;">
                                        <div style="margin-top:8px;margin-right:10px;">
                                          <p>住客{{i}}</p>
                                        </div>
                                        <el-input v-model="input_name[i]" style="width: 70px;height:35px;margin-right:5px;" placeholder="姓名" />
                                        <el-input v-model="input_id[i]" style="width: 180px;height:35px;" placeholder="身份证号" />
                                      </div>
                                    
                                    </template>
                                    </el-scrollbar>
                                  </div>
                                  <div style="height:50px;margin-top:50px;display:flex;">
                                    <div>
                                     <p style="font-size:20px;margin-top:10px;">{{"需支付: ¥"+money}}</p>
                                    </div>
                                    <a-button style="margin-left:70px;margin-top:5px;height:40px;width:110px;background-color:#0077ff;border-radius:5px;color:white;font-size:17px;font-weight:bold" @click="pay">
                                      点击支付
                                    </a-button>
                                  </div>
                                  
                                </a-drawer>
                            </template>

                            <template #extra >

                              <img :src="item.photo" width="360"  height="200" style="float:right"
                              />
                            </template>

                            <a-list-item-meta :description="'余' + item.num + '间'">
                              <template #title>
                               <a-typography-title :level="3">{{ item.name }}</a-typography-title>
                              </template>
                            </a-list-item-meta>
                              <a-typography-paragraph><blockquote>{{ item.others }}</blockquote></a-typography-paragraph>
                          </a-list-item>
                        </template>
                      </a-list>
                    </div>

                </el-tab-pane>
                <el-tab-pane label="评论" name="comments">
                  <div style="width:1155px;display:flex;margin-left:2px;">
                      <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="hotelData.comments">
                        <template #renderItem="{ item }">
                          <a-list-item key="item.name" style="width:1155px">
                            <template #actions>
                                <ReconciliationOutlined />
                                <span>
                                    {{ item.time }}                             
                                </span>
                                <span>
                                    {{ "id:"+item.place }}                             
                                </span>
                               
                                
                            </template>

                            <template #extra >
                              <div style="margin-top:20px;font-size:17px;color:white;background-color:black;width:40px;text-align:center;font-weight: bold;">
                                {{item.rank}}
                              </div> 
                            </template>

                            <a-list-item-meta :description="item.room">
                              <template #title>
                               <a-typography-title :level="3">{{ item.name }}</a-typography-title>
                              </template>
                              <template #avatar><a-avatar :src="item.photo" /></template>
                            </a-list-item-meta>
                              <a-typography-paragraph style="font-size:18px"><blockquote>{{ item.content }}</blockquote></a-typography-paragraph>
                          </a-list-item>
                        </template>
                      </a-list>
                    </div>
                </el-tab-pane>
                <el-tab-pane label="政策" name="policy">
                
                  <div style="margin-bottom:20px;">
                    <a-typography-title :level="3">{{"酒店政策"}}</a-typography-title>
                  </div>
                  <div style="display:grid;grid-template-columns: 280px 880px;grid-row-gap: 50px;grid-column-gap: 0;">
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;font-size:18px;font-weight:bold">
                      
                      {{"酒店简介"}}
                      
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;color:#4a4a4a;">
                      <div style="margin-bottom:40px;">
                      {{hotelData.others.description}}
                      </div>
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;font-size:18px;font-weight:bold">
                      {{"成立时间"}}
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;color:#4a4a4a;">
                      <div style="margin-bottom:40px;">
                      {{hotelData.others.set_time}}
                      </div>
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;font-size:18px;font-weight:bold">
                      {{"联系电话"}}
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;color:#4a4a4a;">
                      <div style="margin-bottom:40px;">
                      {{hotelData.others.phone}}
                      </div>
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;font-size:18px;font-weight:bold">
                      {{"关于早餐"}}
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;color:#4a4a4a;">
                      <div style="margin-bottom:40px;">
                      {{hotelData.others.breakfast_description}}
                      </div>
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;font-size:18px;font-weight:bold">
                      {{"年龄限制"}}
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;color:#4a4a4a;">
                      <div style="margin-bottom:40px;">
                      {{hotelData.others.age_notion}}
                      </div>
                    </div>
                    <div style="border-bottom:solid;border-bottom-color:#f0f0f0;font-size:18px;font-weight:bold">
                      {{"入离时间"}}
                    </div>
                     
                    <div>
                     <div style="margin-bottom:40px;">
                      <div style="color:#4a4a4a;">
                        {{"入住时间:"+hotelData.others.checkin_time}}
                      </div>
                      <div style="color:#4a4a4a;">
                        {{"离开时间:"+hotelData.others.checkout_time}}
                      </div>
                      </div>
                    </div>
                  </div>

               
                </el-tab-pane>
            </el-tabs>
        </div>


   </el-scrollbar>

  </div>
</template>

<script setup>
import { getHotelDetail,postHotelBill } from "../api/api.js";
import { useRoute } from 'vue-router';
import { ref,onMounted,watch } from 'vue';
import { BookOutlined,HeartOutlined,StarOutlined, LikeOutlined, MessageOutlined,EnvironmentOutlined } from '@ant-design/icons-vue'
//标签的选择的
const activeName = ref('rooms');

//Hotel
var hotelData= ref();
//comments
var commentData= ref([]);

const hotelid = ref('');

var check_in = ref('');
var check_out = ref('');

//页面加载预处理

const route = useRoute();

const fetchData = async () => {
    try {
            const response = await getHotelDetail(hotelid.value,checked1.value,checked2.value,checked3.value,check_in.value,check_out.value);
            hotelData.value = response.data;
            console.log("获取酒店详细信息成功");
        } catch (error) {
            console.error('获取酒店详细信息失败：', error);
        }
};

onMounted(() => {
      hotelid.value = route.query.id;
      check_in.value = route.query.checkin;
      check_out.value = route.query.checkout;
      const NstartDate = new Date(check_in.value);
      const NendDate = new Date(check_out.value);
      const timeDiff = Math.abs(NendDate - NstartDate);
      daysDiff.value = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
      fetchData(); 
     
});




hotelData = {
  name:"克里斯蒂大酒店",
  rank:4,
  stars:10,
  likes:10,
  messages:10,
  position:"北京市海淀区街道3",
  comments: [
    {
      name:"David",
      time:"2024/1/30",
      content:"really good experience here!",
      place:"北京",
      room:"标准双人间",
      rank:5.0,
      photo:"https://m.elongstatic.com/hotel_pc_i18n/product/_nuxt/userHead.0-0-3-213881db..svg",
    },
    {
      name:"David",
      time:"2024/1/30",
      content:"really good experience here!",
      place:"北京",
      room:"标准双人间",
      rank:4.5,
      photo:"https://m.elongstatic.com/hotel_pc_i18n/product/_nuxt/userHead.0-0-3-213881db..svg",
    },
    {
      name:"David",
      time:"2024/1/30",
      content:"really good experience here!",
      place:"北京",
      room:"标准双人间",
      rank:4.0,
      photo:"https://m.elongstatic.com/hotel_pc_i18n/product/_nuxt/userHead.0-0-3-213881db..svg",
    }
  ],
  rooms: [
    {
      name: `Room ${1}`,
      price: 199,
      photo: 'https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png',
      num: 10,
      size: "30m^2",
      others:"infoinfoaaaaaaaaaaaaaahfshg gfshu gsjgu",
      bed_size:"2张1.5m单人床",
    }
  ],
  others: {
    phone:"15829374891",
    set_time:"2019年3月2日",
    description:"布达佩斯酒店（Budapest Hotel）位于匈牙利首都布达佩斯（Budapest）这个美丽而充满历史底蕴的城市中，是该城市享有盛誉的奢华酒店之一。布达佩斯酒店不仅是一家享誉国际的五星级豪华酒店，更是一座见证了城市风云变幻的历史建筑。这座酒店坐落于多瑙河畔，拥有绝佳的城市景观，为宾客提供了绝佳的欣赏布达佩斯城市全景的机会。建筑外观保留着典雅的欧洲建筑风格，内部装饰豪华精致，融合了现代设计与传统元素，展现出独特的魅力。布达佩斯酒店提供多样化的豪华客房和套房选择，每间客房都配备了舒适的家具和现代化设施，为宾客营造温馨舒适的居住体验。酒店内设施齐全，包括高级餐厅、室内游泳池、健身中心、水疗中心等，让宾客在体验奢华享受的同时也能尽情放松身心。作为一家享有盛誉的国际品牌酒店，布达佩斯酒店以其优质的服务和无与伦比的体验而闻名于世。在这里，宾客可以感受到匈牙利传统与现代化相结合的独特魅力，享受到尊贵的待客之道，留下美好难忘的回忆。无论是商务旅行还是休闲度假，布达佩斯酒店都将为宾客提供一个奢华且难忘的入住体验。",
    breakfast_description:"对于酒店住客每天提供免费早餐，7:00-9:00于二层餐厅供应。",
    age_notion:"办理人要求18岁至90岁，其他不符合的年龄需要看护人办理入住。",
    checkin_time:"每日12:00之后可办理当日入住",
    checkout_time:"每日下午14:00前退房",
  },
}


const actions =
    {
      icon1: StarOutlined,
      icon2: LikeOutlined,
      icon3: MessageOutlined

    }


//选房型
const checked1 = ref(true);
const checked2 = ref(true);
const checked3 = ref(true);

const onChange1 = (status) => {
  checked1.value = status
};

const onChange2 = (status) => {
  checked2.value = status
};

const onChange3 = (status) => {
  checked3.value = status
};
watch([checked1, checked2, checked3, check_in, check_out], (oldvalue, newvalue) => {
    // 检测到值发生变化，执行fetchData
    fetchData();
});
//按钮
const isPressed = ref(false);

const toggleButton = () => {
  isPressed.value = !isPressed.value;
};

//预订房间
const open = ref(false);
const selectedRoom = ref(null);

const afterOpenChange = (bool) => {
  console.log('open', bool);
};

const showDrawer = (item) => {
  selectedRoom.value = item;
  money.value=item.price;
  open.value = true;
};

const value = ref('')



//选择的房间个数
const roomCount=ref(1);
//填写姓名、身份证
const input_name = ref(['']);
const input_id = ref(['']);

//支付
import { ElMessage, ElMessageBox } from 'element-plus'
const money=ref();
const updateMoney = () => {
  money.value=selectedRoom.value.price*roomCount.value;
};

const pay = async () =>{
    
        const customers = [];
        if (input_name.value.length === input_id.value.length) {
          for (let i = 0; i < input_name.value.length; i++) {
            customers.push({ id: input_id.value[i], name: input_name.value[i] });
          }
        } else {
          console.error('input_name 和 input_id 的长度不一致');
        }
        try{
        const response = await postHotelBill(hotelData.hotel_id,localStorage.getItem("id"),check_in,check_out,roomCount.value,1,customers,money.value);
        var result = response.data.result;
        if(result){
            ElMessage({
                message: "下单成功",
                type: "success",
            });
            //todo:页面跳转
        }else{
            ElMessage({
                message: "下单失败",
                type: "error",
            });
        }
    }catch(error){
        console.log('提交酒店订单失败',error);
    }
};


//计算时间差,防止入住>退房
const daysDiff = ref(0);
const recalculateDateDiff = () => {
  const startDate = new Date(check_in.value);
  const endDate = new Date(check_out.value);
  if (endDate <= startDate) {
    // 退房日期不得早于入住日期，这里可以进行相应处理，比如重置退房日期为入住日期后一天
    const nextDay = new Date(startDate);
    nextDay.setDate(startDate.getDate() + 2);
    check_out.value = nextDay.toISOString().substr(0, 10);
  }
  const NstartDate = new Date(check_in.value);
  const NendDate = new Date(check_out.value);
  const timeDiff = Math.abs(NendDate - NstartDate);
  daysDiff.value = Math.ceil(timeDiff / (1000 * 60 * 60 * 24));
};



</script>


<style scoped>
::v-deep(.el-input__prefix){
  display: none;
}
.home-container {
  width: 100%;
  height: 100vh;
  display: flex;
  flex-direction: column;
}

.el-header {
  height: 60px;
}

.top-info {
    border:1px;
    border-radius:5px;
    height:60px;
    width:440px;
}

.information {
    border:1px;
    border-radius:5px;
    height:108px;
    width:1160px;
}
.photo{
    border:1px;
    border-radius:5px;
    height:450px;
    width:1160px;
}
.other{
    border:1px;
    border-radius:5px;
    width:1160px;
}


.demo-tabs > .el-tabs__content {
  padding: 32px;
  color: #6b778c;
  font-size: 32px;
  font-weight: 600;
}

::v-deep .el-tabs__item {
    font-size:20px;
    padding-right: 40px;
    color:#d6d6d6;
}

::v-deep .el-tabs__item.is-active {
  color: black;
  
}
::v-deep .el-tabs__item:hover {
  color: black;
  cursor: pointer;

}
::v-deep .el-tabs__active-bar {
  background-color: black;
}
.el-tag+.el-tag{
    margin-left:10px;
}
.before {
  background-color: white;
  border-style: solid;
  /* 其他样式 */
}
.after {
  background-color:#0b7def;
}
.circle {
  
  border-radius: 50%; /* 将按钮变成圆形 */
  width: 45px; /* 按钮宽度 */
  height: 45px; /* 按钮高度 */
  border-width:1px;
  border-color:grey;
}

.date_picker .el-date-picker__editor {
  border: none; /* 去掉输入框的边框 */
}


</style>