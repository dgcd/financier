import Vue from 'vue'
import VueRouter from 'vue-router'
import Dashboard from '../views/Dashboard.vue'
import Operations from '../views/Operations.vue'
import Accounts from '../views/Accounts.vue'
import AccountCreate from '../views/AccountCreate.vue'
import Categories from '../views/Categories.vue'
import CategoryCreate from '../views/CategoryCreate.vue'

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
        path: '/accounts/create',
        name: 'AccountCreate',
        component: AccountCreate,
        meta: {
            title: 'Create account',
        },
    },
    {
        path: '/categories',
        name: 'Categories',
        component: Categories,
        meta: {
            title: 'Categories',
        },
    },
    {
        path: '/categories/create',
        name: 'CategoryCreate',
        component: CategoryCreate,
        meta: {
            title: 'Create category',
        },
    },
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
})

export default router
