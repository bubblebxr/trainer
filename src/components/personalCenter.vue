<template>
    <div style="display:flex;align-content: center;justify-content: center;width:100%;height:100%;">
        <el-row>
            <el-col :span="5">
                <el-text size="large">姓名：</el-text>
            </el-col>
            <el-col :span="2">
                <el-input v-model="input" style="width: 300px" placeholder="Please input" clearable />
            </el-col>
        </el-row>
    </div>
</template>
<script setup>

</script>
