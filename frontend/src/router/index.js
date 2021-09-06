import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Watch from "@/views/Watch";
import Authentication from "@/views/Authentication";

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
  }

]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
