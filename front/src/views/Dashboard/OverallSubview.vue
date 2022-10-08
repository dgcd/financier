<template>
    <div>
        <h2>Overall</h2>

        <table class="tbl">
            <tr>
                <th colspan="1" scope="colgroup"></th>
                <th colspan="1" scope="colgroup"></th>
                <th colspan="6" scope="colgroup">Movement</th>
                <th colspan="1" scope="colgroup"></th>
                <th colspan="5" scope="colgroup">Balance</th>
            </tr>
            <tr>
                <th>Month</th>

                <th></th>

                <th>Income</th>
                <th>Expense</th>
                <th>Trans</th>
                <th>Avg inc</th>
                <th>Avg exp</th>
                <th>Result</th>

                <th></th>

                <th>Balance</th>
                <th>Deposit</th>
                <th>Invest</th>
                <th>Summ</th>
                <th>Delta</th>
            </tr>

            <tr v-for="row in preparedTableData" :key="row.month" :class="!row.isMonthRow && !showOnlyYears ? 'boldRow' : ''">
                <td>{{ row.month }}</td>

                <td></td>

                <td>{{ row.income | formatMoneyToString }}</td>
                <td>{{ row.expense | formatMoneyToString }}</td>
                <td>{{ row.trans | formatMoneyToString }}</td>
                <td>{{ row.avgIncome | formatMoneyToString }}</td>
                <td>{{ row.avgExpense | formatMoneyToString }}</td>
                <td :class="'boldRow'">{{ row.result | formatMoneyToString }}</td>

                <td></td>

                <td>{{ row.balance | formatMoneyToString }}</td>
                <td>{{ row.deposit | formatMoneyToString }}</td>
                <td>{{ row.invest | formatMoneyToString }}</td>
                <td :class="'boldRow'">{{ row.summ | formatMoneyToString }}</td>
                <td>{{ row.delta | formatMoneyToString }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

export default {
    name: 'OverallSubview',

    props: {
        currency: {
            type: String,
            required: true,
        },
        showOnlyYears: {
            type: Boolean,
            required: true,
        },
    },

    computed: {
        preparedTableData() {
            const preparedOps = this.groupOpsAndBaseByMonthByCurrency();
            const monthsSpace = this.makeMonthsSpace();
            const tableData = this.makeTableData(monthsSpace, preparedOps, this.currency);
            if (this.showOnlyYears) {
                return tableData.filter(row => !row.isMonthRow);
            }
            return tableData;
        },
    },

    methods: {
        ...mapGetters(['groupOpsAndBaseByMonthByCurrency', 'makeMonthsSpace']),

        makeTableData(monthsSpace, preparedOps, currency) {
            const result = [];
            let lastMonthRow = this.makeMonthlyRow("base", preparedOps.basePerCurrencyOps[currency]);
            let lastYearRow = lastMonthRow;
            result.push(lastMonthRow);

            let monthBalanceAcc = lastMonthRow.balance;
            let monthDepositAcc = lastMonthRow.deposit;
            let monthInvestAcc = lastMonthRow.invest;

            for (let year of monthsSpace) {
                const monthRows = [];
                for (let monthToken of year.monthsTokens) {
                    const perCurrencyOps = preparedOps.perMonthPerCurrencyOps[monthToken] || { [currency]: [] };
                    const monthRow = this.makeMonthlyRow(monthToken, perCurrencyOps[currency] || []);
                    monthRows.push(monthRow);
                    result.push(monthRow);

                    monthRow.isMonthRow = true;
                    monthBalanceAcc += monthRow.balance;
                    monthRow.balance = monthBalanceAcc;
                    monthDepositAcc += monthRow.deposit;
                    monthRow.deposit = monthDepositAcc;
                    monthInvestAcc += monthRow.invest;
                    monthRow.invest = monthInvestAcc;
                    monthRow.summ = monthRow.balance + monthRow.deposit + monthRow.invest;
                    monthRow.delta = monthRow.summ - lastMonthRow.summ;
                    lastMonthRow = monthRow;
                }

                const yearRow = this.makeYearRow(year.yearToken, monthRows);
                result.push(yearRow);

                yearRow.delta = yearRow.summ - lastYearRow.summ;
                lastYearRow = yearRow;
            }
            return result.reverse();
        },

        makeMonthlyRow(monthToken, monthlyOps) {
            let income = 0;
            let expense = 0;
            let trans = 0;

            let balance = 0;
            let deposit = 0;
            let invest = 0;

            for (let op of monthlyOps || []) {
                switch (op.type) {
                    case 'INCOME':
                    case 'BASE':
                        income += op.amount;
                        break;
                    case 'EXPENSE':
                        expense += op.amount;
                        break;
                    case 'TRANS':
                    case 'EXCHANGE':
                        trans += op.amount;
                        break;
                    default:
                        throw new Error("Unknown type");
                }
                if (op.accountTitle.includes('deposit')) {
                    deposit += op.amount;
                } else if (op.accountTitle.includes('invest')) {
                    invest += op.amount;
                } else  {
                    balance += op.amount;
                }
            }
            return {
                month: monthToken,
                income, expense, trans,
                result: income + expense + trans,
                balance, deposit, invest,
                summ: balance + deposit + invest,
            };
        },

        makeYearRow(yearToken, monthRows) {
            let income = 0;
            let expense = 0;
            let trans = 0;

            let balance = 0;
            let deposit = 0;
            let invest = 0;

            let months = 0;

            for (let mon of monthRows) {
                months++;
                income += mon.income;
                expense += mon.expense;
                trans += mon.trans;
                balance = mon.balance;
                deposit = mon.deposit;
                invest = mon.invest;
            }

            return {
                month: yearToken,
                income, expense, trans,
                result: income + expense + trans,
                avgIncome: income / months,
                avgExpense: expense / months,
                balance, deposit, invest,
                summ: balance + deposit + invest,
            }; 
        },
    },
}
</script>
