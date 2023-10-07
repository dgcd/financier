<template>
    <span>Exchange rates: USD: <b>{{rates.USD | formatMoneyToString}}</b>, EUR: <b>{{rates.EUR | formatMoneyToString}}</b></span>
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
