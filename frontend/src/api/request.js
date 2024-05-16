import axios from "axios";
const api = axios.create(
  {
    baseURL: "http://127.0.0.1:4523/m1/4458630-0-default", //这里配置的是后端服务提供的接口
    timeout: 1000
  }
);
export default api;
