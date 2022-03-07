import Vue from 'vue';
import VueRouter from 'vue-router';
// import DashboardView from '../views/Dashboard/DashboardView.vue';
import OperationsView from '../views/Operations/OperationsView.vue';
import OperationCreateView from '../views/OperationCreate/OperationCreateView.vue';
import AccountsView from '../views/Accounts/AccountsView.vue';
import AccountCreateView from '../views/AccountCreate/AccountCreateView.vue';
import CategoriesView from '../views/Categories/CategoriesView.vue';
import CategoryCreateView from '../views/CategoryCreate/CategoryCreateView.vue';

Vue.use(VueRouter);

const routes = [
    // {
    //     path: '/dashboard',
    //     name: 'DashboardView',
    //     component: DashboardView,
    //     meta: {
    //         title: 'Dashboard',
    //     },
    // },
    {
        path: '/operations',
        alias: '/',
        name: 'OperationsView',
        component: OperationsView,
        meta: {
            title: 'Operations',
        },
    },
    {
        path: '/operations/create',
        name: 'OperationCreateView',
        component: OperationCreateView,
        meta: {
            title: 'Create operation',
        },
    },
    {
        path: '/accounts',
        name: 'AccountsView',
        component: AccountsView,
        meta: {
            title: 'Accounts',
        },
    },
    {
        path: '/accounts/create',
        name: 'AccountCreateView',
        component: AccountCreateView,
        meta: {
            title: 'Create account',
        },
    },
    {
        path: '/categories',
        name: 'CategoriesView',
        component: CategoriesView,
        meta: {
            title: 'Categories',
        },
    },
    {
        path: '/categories/create',
        name: 'CategoryCreateView',
        component: CategoryCreateView,
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
