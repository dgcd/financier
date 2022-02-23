import Vue from 'vue';
import VueRouter from 'vue-router';
// import Dashboard from '../views/Dashboard/Dashboard.vue';
import Operations from '../views/Operations/Operations.vue';
import OperationCreate from '../views/OperationCreate/OperationCreate.vue';
import Accounts from '../views/Accounts/Accounts.vue';
import AccountCreate from '../views/AccountCreate/AccountCreate.vue';
import Categories from '../views/Categories/Categories.vue';
import CategoryCreate from '../views/CategoryCreate/CategoryCreate.vue';

Vue.use(VueRouter);

const routes = [
    // {
    //     path: '/dashboard',
    //     name: 'Dashboard',
    //     component: Dashboard,
    //     meta: {
    //         title: 'Dashboard',
    //     },
    // },
    {
        path: '/operations',
        alias: '/',
        name: 'Operations',
        component: Operations,
        meta: {
            title: 'Operations',
        },
    },
    {
        path: '/operations/create',
        name: 'OperationCreate',
        component: OperationCreate,
        meta: {
            title: 'Create operation',
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
];

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes
});

export default router;
