import Vue from 'vue'
import VueRouter from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import Operations from '../views/Operations.vue'
import Accounts from '../views/Accounts.vue'
import Categories from '../views/Categories.vue'

Vue.use(VueRouter)

const routes = [
    {
        path: '/',
        name: 'Dashboard',
        component: Dashboard,
        meta: {
            title: 'Dashboard',
        },
    },
    {
        path: '/operations',
        name: 'Operations',
        component: Operations,
        meta: {
            title: 'Operations',
        },
    },
    {
        path: '/accounts',
        name: 'Accounts',
        component: Accounts,
        meta: {
            title: 'Accounts',
        },
    },
    {
        path: '/categories',
        name: 'Categories',
        component: Categories,
        meta: {
            title: 'Categories',
        },
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
