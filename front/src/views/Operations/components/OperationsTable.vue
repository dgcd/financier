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

            <td>{{ o.type }}</td>
            <td>{{ o.amount / o.quantity }}</td>
            <td>{{ o.quantity }}</td>
            <td>{{ o.amount }}</td>

            <td>{{ o.group }}</td>
            <td>{{ o.category }}</td>
            <td>{{ o.counterparty }}</td>
            <td>{{ o.comment }}</td>
        </tr>
    </table>
</template>

<script>
export default {
    name: 'OperationsTable',

    props: {
        operations: {
            type: Array,
            required: true,
        },
    },

    data() {
        return {
            accountsMap: this.makeAccountsMap(),
        };
    },

    computed: {
        sortedOperations() {
            return this.operations
                .map(op => {
                    return {
                        ...op,
                        accountTitle: this.accountsMap[op.accountId].title,
                    };
                })
                .sort((op1,op2) => op2.id - op1.id);
        },
    },

    methods: {
        makeAccountsMap() {
            const accMap = {};
            for (var acc of this.$store.state.accounts) {
                accMap[acc.id] = acc;
            }
            return accMap;
        },
    },
}
</script>
