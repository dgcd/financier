<template>
    <div>
        <h2>Overall</h2>

        <table class="tbl">
            <tr>
                <th colspan="1" scope="colgroup"></th>
                <th colspan="6" scope="colgroup">Movement</th>
                <th colspan="5" scope="colgroup">Balance</th>
            </tr>
            <tr>
                <th>Month</th>

                <th>Income</th>
                <th>Expense</th>
                <th>Trans</th>
                <th>Result</th>
                <th>Avg inc</th>
                <th>Avg exp</th>

                <th>Balance</th>
                <th>Deposit</th>
                <th>Invest</th>
                <th>Summ</th>
                <th>Delta</th>
            </tr>

            <tr v-for="po in preparedOverall" :key="po.month" :class="po.isMonthRow ? '' : 'boldRow'">
                <td>{{ po.month }}</td>

                <td>{{ po.income | formatMoneyToString }}</td>
                <td>{{ po.expense | formatMoneyToString }}</td>
                <td>{{ po.trans | formatMoneyToString }}</td>
                <td>{{ po.result | formatMoneyToString }}</td>
                <td>{{ po.avgIncome | formatMoneyToString }}</td>
                <td>{{ po.avgExpense | formatMoneyToString }}</td>

                <td>{{ po.balance | formatMoneyToString }}</td>
                <td>{{ po.deposit | formatMoneyToString }}</td>
                <td>{{ po.invest | formatMoneyToString }}</td>
                <td>{{ po.summ | formatMoneyToString }}</td>
                <td>{{ po.delta | formatMoneyToString }}</td>
            </tr>

        </table>
    </div>
</template>

<script>
import { mapState } from 'vuex';
import utils from '@/service/utils.js';

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
        ...mapState(['operations']),

        preparedOverall() {
            const preparedOps = this.prepareOperations(this.operations);
            // console.log("preparedOps: ", preparedOps);
            const monthsSpace = this.makeMonthsSpace(preparedOps.minDate, preparedOps.maxDate);
            // console.log("monthsSpace: ", monthsSpace);
            const tableData = this.makeTableData(monthsSpace, preparedOps.opsHolder, this.currency);
            // console.log("tableData: ", tableData);
            if (this.showOnlyYears) {
                return tableData.filter(row => !row.isMonthRow);
            }
            return tableData;
        },
    },


    methods: {
        prepareOperations(operations) {
            let minDate = utils.getTodayDateString();
            let maxDate = utils.getTodayDateString();
            let basePerCurrencyOps = {};
            let perMonthPerCurrencyOps = {};

            for (let op of operations) {
                if (minDate.localeCompare(op.date) > 0) {
                    minDate = op.date;
                }
                if (maxDate.localeCompare(op.date) < 0) {
                    maxDate = op.date;
                }

                if (op.type == 'BASE') {
                    if (!basePerCurrencyOps[op.currency]) {
                        basePerCurrencyOps[op.currency] = [];
                    }
                    basePerCurrencyOps[op.currency].push(op);
                } else {
                    const monthToken = utils.getMonthTokenByIsoDate(op.date);
                    if (!perMonthPerCurrencyOps[monthToken]) {
                        perMonthPerCurrencyOps[monthToken] = {};
                    }
                    if (!perMonthPerCurrencyOps[monthToken][op.currency]) {
                        perMonthPerCurrencyOps[monthToken][op.currency] = [];
                    }
                    perMonthPerCurrencyOps[monthToken][op.currency].push(op);
                }
            }
            return { 
                minDate, maxDate, 
                opsHolder: { basePerCurrencyOps, perMonthPerCurrencyOps },
            };
        },


        makeMonthsSpace(minDate, maxDate) {
            const minMonth = parseInt(minDate.substring(5,7));
            const minYear = parseInt(minDate.substring(0,4));
            const maxMonth = parseInt(maxDate.substring(5,7));
            const maxYear = parseInt(maxDate.substring(0,4));

            const result = [];
            let year = minYear;
            let month = minMonth;
            let subresult = {};
            while(true) {
                if (!subresult.yearToken) {
                    subresult.yearToken = year;
                    subresult.monthsTokens = [];
                    result.push(subresult);
                }
                const monthToken = utils.monthsNames[month - 1] + year.toString().substring(2);
                subresult.monthsTokens.push(monthToken);

                if (month === maxMonth && year === maxYear) {
                    break;
                }

                if (++month > 12) {
                    year++;
                    month = 1;
                    subresult = {};
                } 
            }

            return result;
        },


        makeTableData(monthsSpace, opsHolder, currency) {
            const result = [];
            let lastMonthRow = this.makeMonthlyRow("base", opsHolder.basePerCurrencyOps[currency]);
            let lastYearRow = lastMonthRow;
            result.push(lastMonthRow);

            let monthBalanceAcc = lastMonthRow.balance;
            let monthDepositAcc = lastMonthRow.deposit;
            let monthInvestAcc = lastMonthRow.invest;

            for (let year of monthsSpace) {
                const monthRows = [];
                for (let monthToken of year.monthsTokens) {
                    const perCurrencyOps = opsHolder.perMonthPerCurrencyOps[monthToken] || { [currency]: [] }
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

            for (let op of monthlyOps) {
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
                result: income + expense,
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
                result: income + expense,
                avgIncome: income / months,
                avgExpense: expense / months,
                balance, deposit, invest,
                summ: balance + deposit + invest,
            }; 
        }

    },
}
</script>
