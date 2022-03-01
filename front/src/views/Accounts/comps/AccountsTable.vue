<template>
    <div>
        <table class="tbl">
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Currency</th>
                <th>Balance</th>
                <th>Check</th>
            </tr>
            <tr v-for="a in sortedAccounts" :key="a.id">
                <td>{{ a.id }}</td>
                <td align="left">{{ a.title }}</td>
                <td>{{ a.currency }}</td>
                <td align="right">{{ a.balance | formatMoneyToString }}</td>
                <td align="right">{{ a.checkedBalance | formatMoneyToString }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import dicts from '@/config/dicts.js';

export default {
    name: 'Accounts-AccountsTable',

    computed: {
        ...mapState(['accounts', 'operations', 'rates']),

        sortedAccounts() {
            const result = this.accounts
                .sort((a, b) => a.currency === b.currency ?
                    a.title.localeCompare(b.title) :
                    dicts.currencies.indexOf(a.currency) - dicts.currencies.indexOf(b.currency))
                .map(acc => {
                    return {
                        ...acc,
                        checkedBalance: this.checkedBalance[acc.id],
                    }
                });

            const accRub = result.filter(acc => acc.currency === "RUB").map(a => a.balance).reduce((bal, acc) => bal + acc, 0);
            const accUsd = result.filter(acc => acc.currency === "USD").map(a => a.balance).reduce((bal, acc) => bal + acc, 0);
            const accEur = result.filter(acc => acc.currency === "EUR").map(a => a.balance).reduce((bal, acc) => bal + acc, 0);

            result.push({ title: "---", currency: "---"});
            result.push({ title: "Total RUB", currency: "RUB", balance: accRub });
            result.push({ title: "Total USD", currency: "USD", balance: accUsd });
            result.push({ title: "Total EUR", currency: "EUR", balance: accEur });
            
            result.push({ title: "---", currency: "---"});
            const totalRub = accRub + accUsd * this.rates.USD + accEur * this.rates.EUR;
            result.push({ title: "Total in RUB", balance: totalRub });

            return result;
        },

        checkedBalance() {
            const map = {};
            for (let op of this.operations) {
                if (!map[op.accountId]) {
                    map[op.accountId] = 0;
                }
                map[op.accountId] += op.amount;
            }
            return map;
        },
    },
}
</script>
