<template>
    <div>
        <span>Show closed: </span>
        <input type="checkbox" v-model="internalShowClosedAccounts" />
    </div>
</template>

<script>
import { mapMutations } from 'vuex';

export default {
    name: 'ShowClosedAccountsCheckbox',

    data() {
        return {
            internalShowClosedAccounts: this.$store.state.selections.showClosedAccounts,
        };
    },

    watch: {
        internalShowClosedAccounts(newValue) {
            this.setShowClosedAccounts(newValue);
        },
    },

    methods: {
        ...mapMutations(['setShowClosedAccounts']),
    },
}
</script>
