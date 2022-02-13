<template>
    <select-list v-model="internalTitle" :valuesList="accountTitles" />
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'AccountSelector',

    props: {
        value: {                // account ID
            type: Number,
            required: false,
        },
        currency: {
            type: String,
            required: false,
        },
    },

    data() {
        return {
            internalTitle: null,
            filteredAccounts: null,
        };
    },

    created() {
        this.setInternalValues();
    },

    computed: {
        ...mapState(['accounts']),

        accountTitles() {
            return this.filteredAccounts.map(a => a.title);
        },
    },

    watch: {
        internalTitle(newInternalTitle) {
            if (!newInternalTitle) {
                return null;
            }
            const account = this.accounts.find(a => a.title === newInternalTitle);
            this.$emit('input', account.id);
        },
        currency() {
            this.setInternalValues();
        },
    },

    methods: {
        setInternalValues() {
            this.internalTitle = null;
            this.filteredAccounts = this.currency ?
                this.accounts.filter(a => a.currency === this.currency) :
                this.accounts;
        },
    },
}
</script>
