import Vue from 'vue';
import Vuex from 'vuex';

import checkedOperationTypes from "./modules/checkedOperationTypes.js";


Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        rates: null,
        accounts: null,
        categories: null,
        operations: null,
        isAppReady: false,
    },

    mutations: {
        setInitData(state, initData) {
            state.rates = initData.rates;
            state.accounts = initData.accounts;
            state.categories = initData.categories;
            state.operations = initData.operations;
            state.isAppReady = true;
        },

        addAccount(state, newAccount) {
            state.accounts = [newAccount, ...state.accounts];
        },

        updateAccounts(state, accounts) {
            const newIds = new Set();
            for (let acc of accounts) {
                newIds.add(acc.id);
            }
            var accs = state.accounts.filter(a => !newIds.has(a.id));
            state.accounts = [...accounts, ...accs];
        },

        addCategory(state, newCategory) {
            state.categories = [newCategory, ...state.categories];
        },

        addOperations(state, operations) {
            state.operations = [...operations, ...state.operations];
        },
    },

    modules: {
        checkedOperationTypes,
    },
});
