import Vue from 'vue';
import Vuex from 'vuex';

import selections from "./modules/selections.js";


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

        updateAccounts(state, updatedAccounts) {
            const updIds = new Set(updatedAccounts.map(a => a.id));
            const restAccounts = state.accounts.filter(a => !updIds.has(a.id));
            state.accounts = [
                ...updatedAccounts,
                ...restAccounts,
            ];
        },

        addCategory(state, newCategory) {
            state.categories = [newCategory, ...state.categories];
        },

        addOperations(state, operations) {
            state.operations = [
                ...operations.map(op => { op.thisSession = true; return op; }),
                ...state.operations,
            ];
        },

        removeOperationsByIds(state, operationsIds) {
            const ids = new Set(operationsIds);
            console.log(ids)
            state.operations = state.operations.filter(op => !ids.has(op.id));
        },
    },

    modules: {
        selections,
    },
});
