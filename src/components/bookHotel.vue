<template>

  <div style="display: flex; flex-direction: column; align-items: center; width: 100%;height: 90%;">

    <v-card hover style="background-color: #e4f5ff; border-radius: 25px; width: 100%; height: 10%; display: block;">
      <div class="select">
        <el-autocomplete v-model="startStation" :fetch-suggestions="queryStartStation" placeholder="出发地"
          class="item" @select="handleStart" clearable :popper-class="popperClass" />
        <el-icon :size="20">
          <Switch @click="switchStation" />
        </el-icon>
        <el-autocomplete v-model="destinationStation" :fetch-suggestions="queryStartStation" placeholder="到达地"
          class="item" @select="handleDestination" clearable :popper-class="popperClass" />
        <el-date-picker class="item" v-model="date" type="date" placeholder="出发日期" :shortcuts="shortcuts"
          :size="20" />
        <el-icon style="width: 30px;">
          <Search @click="search"
            style="background-color: orange; width: 30px; height: 200%; border-radius: 5px;" />
        </el-icon>
      </div>
    </v-card>
    <div style="margin-top:0.9%;  width:100%; height:5%;display: block;padding-left:1%;">
      <el-row style="margin-top:0.5%;">
                <el-col :span="1.5">
                    <el-text tag="b" size="large">排序方式：</el-text>
                </el-col>
                <el-col :span="20.5" style="margin-top:-0.7%;">
                    <el-radio-group v-model="sortType" class="ml-4">
                        <el-radio value="1" size="large">高价优先</el-radio>
                        <el-radio value="2" size="large">低价优先</el-radio>
                        <el-radio value="3" size="large">热度优先</el-radio>
                        <el-radio value="3" size="large">距离优先</el-radio>
                    </el-radio-group>
                </el-col>
            </el-row>
    </div>
    <a-divider />
    <div style="display: flex; flex-direction: column; align-items: center; width: 100%;height: 80%;">

        <a-list item-layout="vertical" size="large" :pagination="pagination" :data-source="listData">
            <template #renderItem="{ item }">
                <a-list-item key="item.title">

                    <template #actions>
                      <a-rate :value="item.rank" disabled />
                      <span>
                        <component :is="actions.icon1" style="margin-right: 8px" />
                        {{ item.stars }}
                      </span>
                      <span>
                      <component :is="actions.icon2" style="margin-right: 8px" />
                        {{ item.likes }}
                      </span>
                      <span>
                      <component :is="actions.icon3" style="margin-right: 8px" />
                        {{ item.messages }}
                      </span>
                      <div style="margin-left: 170px">
                      <a-typography-title :level="4">¥ {{item.miniprice}} 起</a-typography-title>
                      <a-button>点击查看</a-button>
                      </div>
                    </template>

                    <template #extra >

                        <img
                            width="350"
                            alt="logo"
                            src="https://gw.alipayobjects.com/zos/rmsportal/mqaQswcyDLcXyDKnZfES.png"
                        />
                    </template>

                    <a-list-item-meta :description="item.description">
                        <template #title>
                            <a-typography-title :level="3">{{ item.title }}</a-typography-title>
                        </template>
                    </a-list-item-meta>
                    {{ item.content }}

                </a-list-item>
            </template>
        </a-list>
    </div>
  </div>
</template>

<script setup>
import { StarOutlined, LikeOutlined, MessageOutlined } from '@ant-design/icons-vue'
const listData = []
for (let i = 0; i < 23; i++) {
  listData.push({
    // 评分
    rank: 2,
    // 收藏量
    stars: 2,
    // 喜欢
    likes: 2,
    // 评论数
    messages: 2,
    // 最低价
    miniprice: 199,
    href: 'https://www.antdv.com/',
    title: `Hotel ${i}`,
    avatar: 'https://www.antdv.com/',
    description:
        `Description ${i}`,
    content:
        'We supply a series of design principles, practical patterns and high quality design resources (Sketch and Axure), to help people create their product prototypes beautifully and efficiently.'
  })
}
const pagination = {
  onChange: page => {
    console.log(page)
  },
  pageSize: 3
}
const actions =
    {
      icon1: StarOutlined,
      icon2: LikeOutlined,
      icon3: MessageOutlined

    }
</script>
