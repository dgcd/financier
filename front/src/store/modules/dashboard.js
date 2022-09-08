import utils from '@/service/utils.js';

export default {
    state: {
    },

    getters: {
        getPreparedOperations(state, getters, rootState) {
            let basePerCurrencyOps = {};
            let perMonthPerCurrencyOps = {};

            for (let op of rootState.operations) {
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
                basePerCurrencyOps,
                perMonthPerCurrencyOps,
            };
        },


        makeMonthsSpace(state, getters, rootState) {
            let minDate = utils.getTodayDateString();
            let maxDate = utils.getTodayDateString();

            for (let op of rootState.operations) {
                if (minDate.localeCompare(op.date) > 0) {
                    minDate = op.date;
                }
                if (maxDate.localeCompare(op.date) < 0) {
                    maxDate = op.date;
                }
            }

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

    },
};
