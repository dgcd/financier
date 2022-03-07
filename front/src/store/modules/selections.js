export default {
    state: {
        showExpense: true,
        showIncome: true,
        showTrans: false,
        showEmptyAccounts: false,

        selectedCategoryId: null,
        selectedSubcategoryId: null,
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
            state.showEmptyAccounts = !!value;
        },
        
        setSelectedCategoryId(state, value) {
            console.log(value);
            state.selectedCategoryId = !!value;
        },

        setSelectedSubcategoryId(state, value) {
            console.log(value);
            state.selectedSubcategoryId = !!value;
        },
    },
};
