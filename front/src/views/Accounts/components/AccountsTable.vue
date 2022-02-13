<template>
    <div>
        <table class="tbl">
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Currency</th>
                <th>Balance</th>
            </tr>
            <tr v-for="a in sortedAccounts" :key="a.id">
                <td>{{ a.id }}</td>
                <td>{{ a.title }}</td>
                <td>{{ a.currency }}</td>
                <td>{{ a.balance }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
export default {
    name: 'Accounts-AccountsTable',

    props: {
        accounts: {
            type: Array,
            reqired: true,
        },
    },

    computed: {
        sortedAccounts() {
            return this.accounts.sort((a,b) => b.id - a.id);
        },
    },
}
</script>
