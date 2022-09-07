const utils = {
    getTodayDateString() {
        return new Date().toISOString().slice(0, 10);
    },

    monthsNames: ['jan', 'feb', 'mar', 'apr', 'may', 'jun', 'jul', 'aug', 'sep', 'oct', 'nov', 'dec'],

    getMonthTokenByIsoDate(date) {
        const monthIndex = parseInt(date.substring(5,7)) - 1;
        return this.monthsNames[monthIndex] + date.substring(2,4);
    },

    formatMoneyToString(amount) {
        if (!amount || Math.abs(amount) < 0.0000001) {
            return '-';
        }
        return amount
            .toLocaleString('ru-RU', {style: 'decimal', minimumFractionDigits: 2, maximumFractionDigits: 2})
            .replace(',', '.');
    },

    shortenExpenseType(type) {
        return type === 'EXPENSE' ?
            type.substring(0, 3) :
            type;
    },

    removeEmptyFieldsFromObject(obj) {
        const toClean = { ...obj };
        Object.keys(toClean).forEach((k) => toClean[k] == null && delete toClean[k]);
        return toClean;
    },

    removeUnderscores(str) {
        return str.replace(/_/g, '');
    },
};

export default utils;
