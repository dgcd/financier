export default {
    state: {
        showExpense: true,
        showIncome: true,
        showTrans: true,

        showEmptyAccounts: false,
        showClosedAccounts: false,

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

        setShowClosedAccounts(state, value) {
            state.showClosedAccounts = !!value;
            if (value) {
                state.showEmptyAccounts = true;
            }
        },

        setSelectedCategoryId(state, value) {
            state.selectedCategoryId = !!value;
        },

        setSelectedSubcategoryId(state, value) {
            state.selectedSubcategoryId = !!value;
        },
    },
};
