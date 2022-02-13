<template>
    <select-list v-model="internalCurrency" :valuesList="filteredCurrencies" />
</template>

<script>
import dicts from '@/config/dicts.js';
import { mapState } from 'vuex';

export default {
    name: 'CurrencySelector',

    props: {
        value: {                        // currency,
            type: String,
            required: false,
        },
        filterExistentAccounts: {       // if 'true' show only for existent accounts
            type: Boolean,
            required: false,
        },
    },

    data() {
        return {
            internalCurrency: this.value,
            currencies: dicts.currencies,
        };
    },

    computed: {
        ...mapState(['accounts']),

        filteredCurrencies() {
            if (!this.filterExistentAccounts)
                return this.currencies;

            return this.currencies
                .filter(c => {
                    const acc = this.accounts.find(a => a.currency === c);
                    return (!!acc);
                });
        },
    },

    watch: {
        internalCurrency(newInternalCurrency) {
            this.$emit('input', newInternalCurrency);
        },
    },
}
</script>
