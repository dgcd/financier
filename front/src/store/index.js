import Vue from 'vue';
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        accounts: null,
        categories: null,
        operations: null,
        isAppReady: false,
    },

    mutations: {
        setInitData(state, initData) {
            state.accounts = initData.accounts;
            state.categories = initData.categories;
            state.operations = initData.operations;
            state.isAppReady = true;
        },

        addAccount(state, newAccount) {
            state.accounts = [newAccount, ...state.accounts];
        },

        updateAccount(state, account) {
            var accs = state.accounts.filter(a => a.id !== account.id);
            state.accounts = [account, ...accs];
        },

        addCategory(state, newCategory) {
            state.categories = [newCategory, ...state.categories];
        },

        addOperation(state, newOperation) {
            state.operations = [newOperation, ...state.operations];
        },
    },
});
