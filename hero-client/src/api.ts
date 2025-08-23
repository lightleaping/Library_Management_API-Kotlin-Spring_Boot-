import axios from 'axios'

// .env.local에 VITE_API_BASE가 없으면 8081 기본 사용
const baseURL = import.meta.env.VITE_API_BASE || 'http://localhost:8081/api'

export const api = axios.create({ baseURL })