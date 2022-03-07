<template>
    <div>
        <span>Income/Base: </span>
        <input type="checkbox" v-model="internalShowIncome" />
    </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
    name: 'ShowIncomeCheckbox',

    data() {
        return {
            internalShowIncome: this.$store.state.selections.showIncome,
        };
    },

    watch: {
        internalShowIncome(newValue) {
            this.setShowIncome(newValue);
        },
    },

    methods: {
        ...mapMutations(['setShowIncome']),
    },
}
</script>
