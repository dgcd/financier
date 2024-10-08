<template>
    <div>
        <table class="tbl">
            <tr>
                <!-- <th>Id</th> -->
                <th>Title</th>
                <th>Currency</th>
                <th>Balance</th>
                <th v-if="showChecked">Check !!!</th>
                <th v-for="c in getHistoryColumns" :key="c">{{c}}</th>
            </tr>
            <tr v-for="a in sortedAccounts" :key="a.id">
                <!-- <td>{{ a.id }}</td> -->
                <td align="left" style="min-width:250px">
                    {{ a.title }}
                    <button class="btn btn-link btn-sm py-0" v-if="a.id && !a.balance && !a.closed" @click="closeAccountHandler(a)">X</button>
                </td>
                <td>{{ a.currency }}</td>
                <td align="right" style="font-weight: bold">{{ a.balance | formatMoneyToString }}</td>
                <td align="right" v-if="showChecked">{{ a.checkedBalance | formatMoneyToString }}{{ a.checkFailed ? " !" : ""}}</td>
                <td align="right" v-for="c in getHistoryColumns" :key="c">{{getHistoryValue(a, c) | formatMoneyToString }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import dicts from '@/config/dicts.js';
import utils from '@/service/utils.js';

export default {
    name: 'Accounts-AccountsTable',

    props: {
        closeAccountHandler: {
            type: Function,
            required: true,
        },
    },

    computed: {
        ...mapState(['accounts', 'operations', 'rates', 'selections']),

        showChecked() {
            for (let acc of this.sortedAccounts) {
                if (acc.checkFailed) {
                    return true;
                }
            }
            return false;
        },

        sortedAccounts() {
            const checkedAccounts = this.accounts
                .sort((a, b) => a.currency === b.currency ?
                    a.title.localeCompare(b.title) :
                    dicts.currencies.indexOf(a.currency) - dicts.currencies.indexOf(b.currency)
                )
                .map(acc => {
                    const checkedBalanceSrc = this.checkedBalance[acc.id] || 0;
                    const checkFailed = Math.abs(acc.balance - checkedBalanceSrc) > 0.0001;
                    const checkedBalance = checkFailed ? checkedBalanceSrc : null;
                    return {
                        ...acc,
                        checkedBalance,
                        checkFailed,
                    }
                });

            const result = checkedAccounts
                    .filter(acc => !acc.closed)
                    .filter(acc => this.selections.showEmptyAccounts || !!acc.balance || !!acc.checkedBalance);

            if (this.selections.showEmptyAccounts && this.selections.showClosedAccounts) {
                result.push({ title: "---", currency: "---"});
                const closed = checkedAccounts.filter(acc => acc.closed);
                for (let clsd of closed) {
                    result.push(clsd);
                }
            }

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

        getHistoryColumns() {
            const colsArray = [];
            for (let year of this.makeMonthsSpace()) {
                for (let month of year.monthsTokens) {
                    colsArray.push(month);
                }
            }
            colsArray.reverse();
            colsArray.push('BASE');
            return colsArray;
        },
    },

    methods: {
        ...mapGetters(['makeMonthsSpace']),

        getHistoryValue(account, column) {
            if (!account.id) {
                return "";
            }
            return this.operations
                .filter(op => op.accountId === account.id)
                .filter(op => {
                    if (column === 'BASE') {
                        return column === op.type;
                    }
                    return column === utils.getMonthTokenByIsoDate(op.date);
                })
                .map(op => op.amount)
                .reduce((amount, accumulator) => amount + accumulator, 0);
        },
    },
}
</script>
