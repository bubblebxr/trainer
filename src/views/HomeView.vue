<template>
  <el-container class="home-container">
    <el-header style="display: flex; align-items: center; justify-content: space-between;">
      <div style="display: flex; align-items: center;">
        <img src="../assets/logo2.png" style="width:100px;height:55px;margin-top:3%;" />
        <el-menu :default-active="activeIndex" class="menu" mode="horizontal" :ellipsis="false" @select="selectMenu"
          style="color:black;margin-top:2%;">
          <el-menu-item index="1" style="color: black;font-weight: bold;font-size:17px;">火车订票</el-menu-item>
          <el-menu-item index="2" style="color: black;font-weight: bold;font-size:17px;">酒店预订</el-menu-item>
          <el-menu-item index="3" style="color: black;font-weight: bold;font-size:17px;">火车订餐</el-menu-item>
          <el-menu-item index="4" style="color: black;font-weight: bold;font-size:17px;">我的</el-menu-item>
        </el-menu>
      </div>
      <div style="display: flex; align-items: center;">
        <div style="display: flex; align-items: center;">
        <el-popover
          placement="bottom"
          :width="200"
          trigger="click"
          content="this is content, this is content, this is content"
        >
          <template #reference >
            <el-button id="loginButton" type="primary" round plain size="large" style="margin-top:12%;margin-right:3%;" class="log">登录/注册</el-button>
            
          </template>
          <div>
            <a-button @click="login" style="width:170px;">
              登录
            </a-button>
            <a-modal v-model:open="openLogin" title="登陆" @ok="handleLoginOk">
              <div style="margin-top:20px;">
                <el-input
                  v-model="my_id"
                  style="max-width: 600px"
                  placeholder="输入身份证号"
                >
                  <template #prepend>ID号</template>
                </el-input>
              </div>
              <div style="margin-top:20px;">
                <el-input
                  v-model="my_password"
                  style="max-width: 600px"
                  placeholder="输入密码"
                >
                  <template #prepend>密码</template>
                </el-input>
              </div>
            </a-modal>

          </div>
          <div>
            <a-button @click="register" style="width:170px;margin-top:10px;">
              注册
            </a-button>
            <a-modal v-model:open="openRegister" title="注册" @ok="handleRegisterOk">
              <div style="margin-top:20px;">
                <el-input
                  v-model="my_id"
                  style="max-width: 600px"
                  placeholder="输入身份证号"
                >
                  <template #prepend>ID号</template>
                </el-input>
              </div>
              <div style="margin-top:20px;">
                <el-input
                  v-model="my_name"
                  style="max-width: 600px"
                  placeholder="输入姓名"
                >
                  <template #prepend>姓名</template>
                </el-input>
              </div>
              <div style="margin-top:20px;">
                <el-input
                  v-model="my_password"
                  style="max-width: 600px"
                  placeholder="输入密码"
                >
                  <template #prepend>密码</template>
                </el-input>
              </div>
              <div style="margin-top:20px;">
                <el-input
                  v-model="my_email"
                  style="max-width: 600px"
                  placeholder="输入邮箱"
                >
                  <template #prepend>邮箱</template>
                </el-input>
              </div>
            </a-modal>
          </div>
        </el-popover>
        <div id="loggedInMessage" style="display:none">
              <div style="marigin-top:30px;">
              
                <a-avatar style="margin-top:10px;margin-right:20px;" size="large" :src="'https://m.elongstatic.com/hotel_pc_i18n/product/_nuxt/userHead.0-0-3-213881db..svg'" />

              </div>
        </div>
        <img src="../assets/message.png" style="width: 55px; height: 55px; margin-top: 8%;" />
      </div>
      </div>
    </el-header>
    <div class="main">
      <el-main width="80%" style="height:97vh;">
        <router-view />
      </el-main>
    </div>
  </el-container>
</template>

<script setup>
import { ref, onMounted,inject } from 'vue'
import { useRouter } from "vue-router";
const router = useRouter();
const activeIndex = ref('1')
const selectMenu = (key) => {
  console.log('当前选中为' + key);
  activeIndex.value = key;
  if(activeIndex.value==="1"){
    router.push('/home/ticket');
  }else if (activeIndex.value === "2") {
    router.push('/home/hotel');
  } else if (activeIndex.value === "3") {
    router.push('/home/dining');
  } else if (activeIndex.value === "4") {
    router.push('/home/orders');
  }
}

//登陆
//登陆
import { postLogin } from "@/api/api"
const openLogin = ref(false);
const my_id = ref('');
const my_password = ref('');

const login = () => {
  openLogin.value = true;
};

const handleLoginOk = (e) => {
  console.log(e);
  if (my_id.value === "" || my_password.value === "") {
    ElMessage({
      message: "还有没填写的信息",
      type: "error",
      plain: true,
    });
  } else {
    postmyLogin();
  }
  openLogin.value = false;
};
const postmyLogin = async () =>{
  try{
    const response = await postLogin(my_id,my_password);
    if(response.data.result){
      ElMessage({
        message: "登陆成功",
        type: "success",
      });
      localstorage.setItem('user_id',my_id);
      localStorage.setItem('isLoggedIn',true);
      localstorage.setItem('email',response.data.email);
      localStorage.setItem('password',my_password);
      localStorage.setItem('name',response.data.name);
      updateUI();
      //重新加载页面
      window.location.href = window.location.href;
    }else{
      ElMessage.error("登陆失败:用户ID或密码错误");
    }
  }catch (error) {
    console.error("登陆失败:", error);
  }
}



//注册
const my_email = ref('');
const my_name = ref('');

import { postRegister } from "@/api/api"
const openRegister = ref(false);

const register = () => {
  openRegister.value = true;
};

const postmyRegister = async () =>{
  try{
    const response = await postRegister(my_id, my_name, my_password,my_email);
    if(response.data.result){
      ElMessage({
        message: "注册成功",
        type: "success",
      });
      localStorage.setItem('user_id',my_id);
      localStorage.setItem('isLoggedIn',true);
      localStorage.setItem('email',my_email);
      localStorage.setItem('password',my_password);
      localStorage.setItem('name',my_name);
      updateUI();
      
      //重新加载页面
      window.location.href = window.location.href;
    }else{
      ElMessage.error("注册失败:"+response.data.reason);
    }
  }catch (error) {
    console.error("注册失败:", error);
  }
}

const handleRegisterOk = (e) => {
  console.log(e);
  if (my_id.value === "" || my_password.value === "" || my_email.value === "" || my_name === "") {
    ElMessage({
      message: "还有没填写的信息",
      type: "error",
      plain: true,
    });
  } else {
    postmyRegister();
  }
  openRegister.value = false;
};


// 页面加载完成时执行
document.addEventListener('DOMContentLoaded', function() {
  // 更新页面元素的显示状态
  updateUI();
});

// 更新页面元素显示状态的函数
function updateUI() {
  var isLoggedIn = localStorage.getItem('isLoggedIn');
  
  
  // 根据 isLoggedIn 的值更新页面元素显示状态
  if (isLoggedIn === 'true') {
    document.getElementById('loginButton').style.display = 'none'; // 隐藏登录按钮
    document.getElementById('loggedInMessage').style.display = 'block'; // 显示已登录的消息
  } else {
    document.getElementById('loginButton').style.display = 'block'; // 显示登录按钮
    document.getElementById('loggedInMessage').style.display = 'none'; // 隐藏已登录的消息
  }
}

onMounted(() => {
  router.push('/home/ticket');
});

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
