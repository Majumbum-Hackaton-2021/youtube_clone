import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Watch from "@/views/Watch";
import Authentication from "@/views/Authentication";
import UserPage from "@/views/UserPage";
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/watch',
    name: 'Watch',
    component: Watch
  },
  {
    path: '/authentication',
    name: 'Authentication',
    component: Authentication
  },
  {
    path: '/userpage',
    name: 'UserPage',
    component: UserPage
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
