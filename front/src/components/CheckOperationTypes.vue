<template>
    <p>
        <span>Expense: </span>
        <input type="checkbox" v-model="internalShowExpense" />
        &nbsp;&nbsp;&nbsp;
        <span>Income/Base: </span>
        <input type="checkbox" v-model="internalShowIncome" />
        &nbsp;&nbsp;&nbsp;
        <span>Trans: </span>
        <input type="checkbox" v-model="internalShowTrans" />
    </p>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
    name: 'CheckOperationTypes',

    data() {
        return {
            internalShowExpense: this.$store.state.checkedOperationTypes.showExpense,
            internalShowIncome: this.$store.state.checkedOperationTypes.showIncome,
            internalShowTrans: this.$store.state.checkedOperationTypes.showTrans,
        };
    },

    watch: {
        internalShowIncome(newValue) {
            this.setShowIncome(newValue);
        },
        internalShowExpense(newValue) {
            this.setShowExpense(newValue);
        },
        internalShowTrans(newValue) {
            this.setShowTrans(newValue);
        },
    },

    methods: {
        ...mapMutations(['setShowIncome', 'setShowExpense', 'setShowTrans']),
    },
}
</script>
