import { createRouter, createWebHistory } from 'vue-router'
import OptimizationDashboard from './components/OptimizationDashboard.vue'
import MaterialList from './components/MaterialList.vue'
import ProductList from './components/ProductList.vue'

const routes = [
  { path: '/', component: OptimizationDashboard },
  { path: '/materials', component: MaterialList },
  { path: '/products', component: ProductList },
]

export const router = createRouter({
  history: createWebHistory(),
  routes,
})