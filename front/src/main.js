import Vue from 'vue';
import App from './App.vue';
import router from './router';
import store from './store';
import Title from './components/Title.vue';

Vue.config.productionTip = false;
Vue.config.devtools = false;

Vue.component('title-component', Title);

new Vue({
    router,
    store,
    render: function (h) { return h(App); }
}).$mount('#app');
