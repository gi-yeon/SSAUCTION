import axios from "axios";
axios.defaults.headers.post["Content-Type"] = "application/json";
axios.defaults.headers.put["Content-Type"] = "application/json";
axios.defaults.baseURL = 'https://i7a806.p.ssafy.io/ssauction';
export default axios;

