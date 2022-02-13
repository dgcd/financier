<template>
    <table class="tbl">
        <tr>
            <th>Id</th>
            <th>Date</th>
            <th>Currency</th>
            <th>Amount</th>
            <th>Quantity</th>
            <th>Summary</th>
            <th>Type</th>
            <th>Group</th>
            <th>Category</th>
            <th>Counterparty</th>
            <th>Comment</th>
        </tr>
        <tr v-for="o in sortedOperations" :key="o.id">
            <td>{{ o.id }}</td>
            <td>{{ o.date }}</td>
            <td>{{ o.currency }}</td>
            <td>{{ o.amount }}</td>
            <td>{{ o.quantity }}</td>
            <td>{{ o.amount * o.quantity }}</td>
            <td>{{ o.type }}</td>
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

    computed: {
        sortedOperations() {
            return this.operations;
        },
    },
}
</script>
