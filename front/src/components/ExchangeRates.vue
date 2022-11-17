<template>
    <span>Exchange rates: USD: {{rates.USD | formatMoneyToString}}, EUR: {{rates.EUR | formatMoneyToString}}</span>
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'ExchangeRates',

    computed: {
        ...mapState(['rates']),
    },
}
</script>
