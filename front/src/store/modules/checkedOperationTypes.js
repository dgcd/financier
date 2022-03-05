export default {
    state: {
        showExpense: true,
        showIncome: true,
        showTrans: false,
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
    },
};
