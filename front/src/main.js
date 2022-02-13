import Vue from 'vue';
import router from './router';
import store from './store';

import App from './views/App/App.vue';

import DatePicker from './components/DatePicker.vue';
import ErrorMessage from './components/ErrorMessage.vue';
import PageTitle from './components/PageTitle.vue';
import RedirectButton from './components/RedirectButton.vue';
import SelectList from './components/SelectList.vue';

import AccountSelector from './components/AccountSelector.vue';
import CurrencySelector from './components/CurrencySelector.vue';
import OperationTypeSelector from './components/OperationTypeSelector.vue';


Vue.config.productionTip = false;
Vue.config.devtools = false;

Vue.component('date-picker', DatePicker);
Vue.component('error-message', ErrorMessage);
Vue.component('page-title', PageTitle);
Vue.component('redirect-button', RedirectButton);
Vue.component('select-list', SelectList);

Vue.component('account-selector', AccountSelector);
Vue.component('currency-selector', CurrencySelector);
Vue.component('operation-type-selector', OperationTypeSelector);

OperationTypeSelector
new Vue({
    router,
    store,
    render: function (h) { return h(App); }
}).$mount('#app');
