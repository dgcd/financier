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

            <th>Group</th>
            <th>Category</th>
            <th>Counterparty</th>
            <th>Comment</th>
        </tr>
        <tr v-for="o in sortedOperations" :key="o.id">
            <td>{{ o.id }}</td>
            <td>{{ o.date }}</td>
            <td>{{ o.currency }}</td>
            <td>{{ o.accountTitle }}</td>

            <td>{{ o.type | shortenExpenseType }}</td>
            <td align="right">{{ (o.amount / o.quantity) | formatMoneyToString }}</td>
            <td>{{ o.quantity }}</td>
            <td align="right">{{ o.amount | formatMoneyToString }}</td>

            <td>{{ o.groupTitle }}</td>
            <td>{{ o.categoryTitle }}</td>
            <td>{{ o.counterparty }}</td>
            <td>{{ o.comment }}</td>
        </tr>
    </table>
</template>

<script>
import { mapState } from 'vuex';
export default {
    name: 'OperationsTable',

    props: {
        operations: {
            type: Array,
            required: true,
        },
    },

    computed: {
        ...mapState(['checkedOperationTypes']),

        sortedOperations() {
            return this.operations
                .filter(op => {
                    if (!this.checkedOperationTypes.showExpense && op.type === "EXPENSE")
                        return false;
                    if (!this.checkedOperationTypes.showIncome && (op.type === "INCOME" || op.type === "BASE"))
                        return false;
                    if (!this.checkedOperationTypes.showTrans && op.type === "TRANS")
                        return false;
                    return true;
                })
                .sort((op1, op2) => op2.date === op1.date ?
                    op2.id - op1.id :
                    op2.date.localeCompare(op1.date)
                );
        },
    },
}
</script>
