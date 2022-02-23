const utils = {
    getTodayDateString() {
        return new Date().toISOString().slice(0, 10);
    },

    formatMoneyToString(amount) {
        if (!amount)
            return '-';
        return amount
            .toLocaleString('ru-RU', {style: 'decimal', minimumFractionDigits: 2})
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
};

export default utils;
