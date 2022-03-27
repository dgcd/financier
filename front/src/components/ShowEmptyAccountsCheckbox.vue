<template>
    <div>
        <span>Show empty: </span>
        <input type="checkbox" v-model="internalShowEmptyAccounts" />
    </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
    name: 'ShowEmptyAccountsCheckbox',

    data() {
        return {
            internalShowEmptyAccounts: this.$store.state.selections.showEmptyAccounts,
        };
    },

    watch: {
        internalShowEmptyAccounts(newValue) {
            this.setShowEmptyAccounts(newValue);
            if (!newValue) {
                this.setShowClosedAccounts(false);
            }
        },
    },

    methods: {
        ...mapMutations(['setShowEmptyAccounts', 'setShowClosedAccounts']),
    },
}
</script>
