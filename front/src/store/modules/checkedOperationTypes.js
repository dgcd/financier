export default {
    state: {
        showExpense: true,
        showIncome: false,
        showTrans: false,
    },

    mutations: {
        setShowExpense(state, value) {
            console.log("setShowExpense:", value)
            state.showExpense = !!value;
        },

        setShowIncome(state, value) {
            console.log("setShowIncome:", value)
            state.showIncome = !!value;
        },    

        setShowTrans(state, value) {
            console.log("setShowTrans:", value)
            state.showTrans = !!value;
        },
    },
};
