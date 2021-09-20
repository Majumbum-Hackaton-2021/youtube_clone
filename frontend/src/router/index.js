import { createRouter, createWebHistory } from 'vue-router'
import Home from '../views/Home.vue'
import Watch from "@/views/Watch";
import Authentication from "@/views/Authentication";
import UserPage from "@/views/UserPage";
import Upload from "@/views/Upload";
import UserAdmin from "@/views/UserAdmin";
import SavedVideo from "@/views/SavedVideo";
const routes = [
  {
    path: '/',
    name: 'Home',
    component: Home
  },
  {
    path: '/watch:id',
    name: 'Watch',
    component: Watch,
    props: true
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
  {
    path: '/upload',
    name: 'Upload',
    component: Upload
  },
  {
    path: '/useradmin',
    name: 'UserAdmin',
    component: UserAdmin
  },
  {
    path: '/savedVideo',
    name: 'SavedVideo',
    component: SavedVideo
  },
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
