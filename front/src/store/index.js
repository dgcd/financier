import Vue from 'vue'
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        accounts: null,
        categories: null,
        isAppReady: false,
    },

    mutations: {
        setInitData(state, initData) {
            console.log('init data: ', initData);
            state.accounts = initData.accounts || [];
            state.categories = initData.categories || [];
            state.isAppReady = true;
        },

        addAccount(state, newAccount) {
            state.accounts = [newAccount, ...state.accounts];
        },

        addCategory(state, newCategory) {
            state.categories = [newCategory, ...state.categories];
        },
    },
});
