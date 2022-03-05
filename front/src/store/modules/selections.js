export default {
    state: {
        showExpense: true,
        showIncome: true,
        showTrans: false,
        showEmptyAccounts: true,
    },

    mutations: {
        setShowExpense(state, value) {
            state.showExpense = !!value;
        },

        setShowIncome(state, value) {
            state.showIncome = !!value;
        },    

        setShowTrans(state, value) {
            state.showTrans = !!value;
        },

        setShowEmptyAccounts(state, value) {
            console.log(value)
            state.showEmptyAccounts = !!value;
        },
    },
};
