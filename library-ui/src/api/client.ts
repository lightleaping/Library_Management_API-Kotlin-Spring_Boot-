// src/api/client.ts
import axios from "axios";

const api = axios.create({
    baseURL: "/api", // CRA dev 서버(3001)의 proxy가 8080으로 전달
    headers: { "Content-Type": "application/json" },
});

export default api;