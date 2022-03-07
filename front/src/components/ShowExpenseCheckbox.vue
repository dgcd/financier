<template>
    <div>
        <span>Expense: </span>
        <input type="checkbox" v-model="internalShowExpense" />
    </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
    name: 'ShowExpenseCheckbox',

    data() {
        return {
            internalShowExpense: this.$store.state.selections.showExpense,
        };
    },

    watch: {
        internalShowExpense(newValue) {
            this.setShowExpense(newValue);
        },
    },

    methods: {
        ...mapMutations(['setShowExpense']),
    },
}
</script>
