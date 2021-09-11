import Vue from 'vue'
import VueRouter from 'vue-router'

import Header from "../components/Header";
const CustomerListPage = () => import('@/pages/CustomerListPage');
const CustomerPage = () => import('@/pages/CustomerCreateOrUpdatePage');
const NotFoundPage = () => import('@/pages/NotFoundPage');

Vue.use(VueRouter)
const routes = [
    {
        path: '/',
        name: 'DefaultPage',
        component: Header,
        redirect: '/customers',
        children: [
            {
                path: 'notfound',
                component: NotFoundPage,
                name: 'notfound',
            },
            {
                path: '/customers',
                component: CustomerListPage,
                name: 'customers',
            },
            {
                path: '/customer/:id?',
                component: CustomerPage,
                name: 'customer',
            },
        ]
    }, {
        path: '*',
        component: Header,
        children: [
            {
                path: '*',
                component: NotFoundPage,
                name: 'Sayfa bulunamadı',
            },
        ]
    }
]

const router = new VueRouter({
    mode: 'history',
    base: process.env.BASE_URL,
    routes,
})

export default router