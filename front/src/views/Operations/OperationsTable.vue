<template>
    <table class="tbl">
        <tr>
            <th>Id</th>
            <th>Date</th>
            <th>Currency</th>
            <th>Account</th>

            <th>Type</th>
            <th>Price</th>
            <th>Quantity</th>
            <th>Amount</th>

            <th>Category</th>
            <th>Subcategory</th>
            <th>Comment</th>
            <th>Counterparty</th>
            <th>Cancel</th>
        </tr>
        <tr>
            <td></td>
            <td></td>
            <td><select-list v-model="currency" :valuesList="currencies"/></td>
            <td><select-list v-model="accountTitle" :valuesList="accountTitles"/></td>

            <td><select-list v-model="operationType" :valuesList="operationTypes"/></td>
            <td></td>
            <td></td>
            <td></td>

            <td><select-list v-model="categoryTitle" :valuesList="categoryTitles"/></td>
            <td><select-list v-model="subcategoryTitle" :valuesList="subcategoryTitles"/></td>
            <td></td>
            <td></td>
            <td></td>
        </tr>
        <tr v-for="o in sortedOperations" :key="o.id">
            <td>{{ o.id }}</td>
            <td style="min-width:100px">{{ o.date }}</td>
            <td>{{ o.currency }}</td>
            <td style="min-width:120px">{{ o.accountTitle | removeUnderscores }}</td>

            <td>{{ o.type | shortenExpenseType }}</td>
            <td align="right">{{ (o.amount / o.quantity) | formatMoneyToString }}</td>
            <td>{{ o.quantity }}</td>
            <td align="right" style="font-weight: bold">{{ o.amount | formatMoneyToString }}</td>

            <td>{{ o.categoryTitle }}</td>
            <td>{{ o.subcategoryTitle }}</td>
            <td>{{ o.comment }}</td>
            <td>{{ o.counterparty }}</td>
            <td><button v-if="o.thisSession" class="btn btn-link btn-sm py-0" @click="cancelOperationHandler(o)">X</button></td>
        </tr>
    </table>
</template>

<script>
import { mapState } from 'vuex';
import dicts from '@/config/dicts.js';

export default {
    name: 'OperationsTable',

    props: {
        operations: {
            type: Array,
            required: true,
        },
        cancelOperationHandler: {
            type: Function,
            required: true,
        },
    },

    data() {
        return {
            currency: dicts.SELECT_ALL,
            currencies: [ dicts.SELECT_ALL, ...dicts.currencies ],
            accountTitle: dicts.SELECT_ALL,
            operationType: dicts.SELECT_ALL,
            operationTypes: [ dicts.SELECT_ALL, ...dicts.operationTypesSet ],
            categoryTitle: dicts.SELECT_ALL,
            subcategoryTitle: dicts.SELECT_ALL,
       };
    },

    computed: {
        ...mapState(['selections', 'accounts', 'categories']),

        accountTitles() {
            const titles = this.accounts
                .filter(acc => {
                    if (this.currency === dicts.SELECT_ALL) {
                        return true;
                    }
                    return acc.currency === this.currency;
                })                
                .map(acc => acc.title)
                .sort();
            return [ dicts.SELECT_ALL, ...titles ];
        },

        categoryTitles() {
            const titles = this.categories
                .filter(cat => !cat.parentId)
                .map(cat => cat.title)
                .sort();
            return [ dicts.SELECT_ALL, dicts.SELECT_EMPTY, ...titles ];
        },

        subcategoryTitles() {
            if (this.categoryTitle === dicts.SELECT_EMPTY) {
                return [ dicts.SELECT_EMPTY ];
            }
            if (this.categoryTitle === dicts.SELECT_ALL) {
                const titles = this.categories
                    .filter(cat => !!cat.parentId)
                    .map(cat => cat.title)
                    .sort();
                const unique = new Set(titles);
                return [ dicts.SELECT_ALL, dicts.SELECT_EMPTY, ...unique ];
            }
            const titles = this.categories
                .filter(cat => !!cat.parentId && cat.parentTitle === this.categoryTitle)
                .map(cat => cat.title)
                .sort();
            return [ dicts.SELECT_ALL, ...titles ];
        },

        sortedOperations() {
            return this.operations
                .filter(op => {
                    if (!this.selections.showExpense && op.type === dicts.OPERATION_TYPE_EXPENSE)
                        return false;
                    if (!this.selections.showIncome && (op.type === dicts.OPERATION_TYPE_INCOME || op.type === dicts.OPERATION_TYPE_BASE))
                        return false;
                    if (!this.selections.showTrans && (op.type === dicts.OPERATION_TYPE_TRANS || op.type === dicts.OPERATION_TYPE_EXCHANGE))
                        return false;
                    return true;
                })
                .filter(op => {
                    if (this.currency === dicts.SELECT_ALL) {
                        return true;
                    }
                    return op.currency === this.currency;
                })
                .filter(op => {
                    if (this.accountTitle === dicts.SELECT_ALL) {
                        return true;
                    }
                    return op.accountTitle === this.accountTitle;
                })
                .filter(op => {
                    if (this.operationType === dicts.SELECT_ALL) {
                        return true;
                    }
                    return op.type === this.operationType;
                })
                .filter(op => {
                    if (this.categoryTitle === dicts.SELECT_ALL) {
                        return true;
                    }
                    if (this.categoryTitle === dicts.SELECT_EMPTY && op.categoryTitle === 'n/a') {
                        return true;
                    }
                    return op.categoryTitle === this.categoryTitle;
                })
                .filter(op => {
                    if (this.subcategoryTitle === dicts.SELECT_ALL) {
                        return true;
                    }
                    if (this.subcategoryTitle === dicts.SELECT_EMPTY && op.subcategoryTitle === 'n/a') {
                        return true;
                    }
                    return op.subcategoryTitle === this.subcategoryTitle;
                })
                .sort((op1, op2) => op2.date === op1.date ?
                    op2.id - op1.id :
                    op2.date.localeCompare(op1.date)
                );
        },
    },
}
</script>
