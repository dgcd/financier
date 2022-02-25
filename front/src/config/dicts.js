const currencies = [
    'RUB',
    'USD',
    'EUR',
];

const OPERATION_TYPE_EXPENSE = 'EXPENSE';
const OPERATION_TYPE_INCOME = 'INCOME';
const OPERATION_TYPE_TRANS = 'TRANS';
const OPERATION_TYPE_BASE = 'BASE';

const operationTypesSet = new Set([
    OPERATION_TYPE_EXPENSE,
    OPERATION_TYPE_INCOME,
    OPERATION_TYPE_TRANS,
    OPERATION_TYPE_BASE,
]);

export default {
    currencies,
    operationTypesSet,
    OPERATION_TYPE_EXPENSE,
    OPERATION_TYPE_INCOME,
    OPERATION_TYPE_TRANS,
    OPERATION_TYPE_BASE,
};
