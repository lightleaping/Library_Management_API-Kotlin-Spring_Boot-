import axios from "axios";

/**
 * CRA는 package.json 의 "proxy": "http://localhost:8080" 를 이용해서
 * /api/** 를 백엔드로 프록시합니다. baseURL 은 비우고 경로만 /api 로 시작.
 */
const api = axios.create({
    // baseURL: "", // 굳이 지정안함. 절대/상대 모두 동작
    headers: { "Content-Type": "application/json" },
    withCredentials: false,
});

export default api;