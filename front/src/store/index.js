import Vue from 'vue';
import Vuex from 'vuex';

import selections from "./modules/selections.js";
import dashboard from "./modules/dashboard.js";


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
            state.operations = initData.operations
                .map(op => {
                    if (!op.categoryTitle) op.categoryTitle = 'n/a';
                    if (!op.subcategoryTitle) op.subcategoryTitle = 'n/a';
                    return op;
                });
            state.isAppReady = true;
        },

        addAccount(state, newAccount) {
            state.accounts = [newAccount, ...state.accounts];
        },

        updateAccounts(state, updatedAccounts) {
            const updIds = new Set(updatedAccounts.map(a => a.id));
            const restAccounts = state.accounts.filter(a => !updIds.has(a.id));
            state.accounts = [...updatedAccounts, ...restAccounts];
        },

        addCategory(state, newCategory) {
            state.categories = [newCategory, ...state.categories];
        },

        addOperations(state, operations) {
            operations.forEach(op => {
                op.thisSession = true;
                if (!op.categoryTitle) op.categoryTitle = 'n/a';
                if (!op.subcategoryTitle) op.subcategoryTitle = 'n/a';
            });
            state.operations = [...operations, ...state.operations];
        },

        removeOperationsByIds(state, operationsIds) {
            const ids = new Set(operationsIds);
            state.operations = state.operations.filter(op => !ids.has(op.id));
        },
    },

    modules: {
        selections,
        dashboard,
    },
});
