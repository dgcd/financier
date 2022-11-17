import Vue from 'vue';
import router from './router';
import store from './store';

import 'bootstrap/dist/css/bootstrap.css';

import App from './views/App/App.vue';

import DatePicker from './components/DatePicker.vue';
import ErrorMessage from './components/ErrorMessage.vue';
import PageTitle from './components/PageTitle.vue';
import RedirectButton from './components/RedirectButton.vue';
import SelectList from './components/SelectList.vue';

import AccountSelector from './components/AccountSelector.vue';
import CurrencySelector from './components/CurrencySelector.vue';
import CategorySelector from './components/CategorySelector.vue';
import OperationTypeSelector from './components/OperationTypeSelector.vue';
import ExchangeRates from './components/ExchangeRates.vue';
import ShowExpenseCheckbox from './components/ShowExpenseCheckbox.vue';
import ShowIncomeCheckbox from './components/ShowIncomeCheckbox.vue';
import ShowTransCheckbox from './components/ShowTransCheckbox.vue';
import ShowEmptyAccountsCheckbox from './components/ShowEmptyAccountsCheckbox.vue';
import ShowClosedAccountsCheckbox from './components/ShowClosedAccountsCheckbox.vue';

import utils from '@/service/utils.js';


Vue.config.productionTip = false;
Vue.config.devtools = false;


Vue.component('date-picker', DatePicker);
Vue.component('error-message', ErrorMessage);
Vue.component('page-title', PageTitle);
Vue.component('redirect-button', RedirectButton);
Vue.component('select-list', SelectList);

Vue.component('account-selector', AccountSelector);
Vue.component('currency-selector', CurrencySelector);
Vue.component('category-selector', CategorySelector);
Vue.component('operation-type-selector', OperationTypeSelector);
Vue.component('exchange-rates', ExchangeRates);
Vue.component('show-expense-checkbox', ShowExpenseCheckbox);
Vue.component('show-income-checkbox', ShowIncomeCheckbox);
Vue.component('show-trans-checkbox', ShowTransCheckbox);
Vue.component('show-empty-accounts-checkbox', ShowEmptyAccountsCheckbox);
Vue.component('show-closed-accounts-checkbox', ShowClosedAccountsCheckbox);


Vue.filter('formatMoneyToString', utils.formatMoneyToString);
Vue.filter('shortenExpenseType', utils.shortenExpenseType);
Vue.filter('removeUnderscores', utils.removeUnderscores);


new Vue({
    router,
    store,
    render: function (h) { return h(App); }
}).$mount('#app');
