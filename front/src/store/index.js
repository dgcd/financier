import Vue from 'vue'
import Vuex from 'vuex';

Vue.use(Vuex);

export default new Vuex.Store({
    state: {
        accounts: null,
        isAppReady: false,
    },

    mutations: {
        setInitData(state, initData) {
            console.log('init data: ', initData);
            state.accounts = initData.accounts || [];
            state.isAppReady = true;
        },
    },
});
