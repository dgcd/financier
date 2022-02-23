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
                <td>{{ a.title }}</td>
                <td>{{ a.currency }}</td>
                <td align="right">{{ a.balance | formatMoneyToString }}</td>
                <td align="right">{{ a.checkedBalance | formatMoneyToString }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { mapState } from 'vuex';

export default {
    name: 'Accounts-AccountsTable',

    computed: {
        ...mapState(['accounts', 'operations']),

        sortedAccounts() {
            return this.accounts
                .sort((a, b) => b.id - a.id)
                .map(acc => {
                    return {
                        ...acc,
                        checkedBalance: this.checkedBalance[acc.id],
                    }
                });
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
