# 使用Node.js官方镜像作为基础镜像
FROM node:14

# 设置工作目录
WORKDIR /app

# 复制package.json文件并安装依赖
COPY package*.json ./
RUN npm install

# 复制所有代码到容器内
COPY . .

# 构建前端项目
RUN npm run build

# 设置应用启动命令
CMD ["npm", "start"]

# 暴露应用端口
EXPOSE 3000
