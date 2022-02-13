import Vue from 'vue';
import router from './router';
import store from './store';

import App from './views/App/App.vue';
import ErrorMessage from './components/ErrorMessage.vue';
import PageTitle from './components/PageTitle.vue';
import RedirectButton from './components/RedirectButton.vue';
import SelectList from './components/SelectList.vue';


Vue.config.productionTip = false;
Vue.config.devtools = false;

Vue.component('error-message', ErrorMessage);
Vue.component('page-title', PageTitle);
Vue.component('redirect-button', RedirectButton);
Vue.component('select-list', SelectList);

new Vue({
    router,
    store,
    render: function (h) { return h(App); }
}).$mount('#app');
