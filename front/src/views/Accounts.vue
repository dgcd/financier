<template>
    <div>
        <title-component :title="$route.meta.title" />

        <h1>Accounts</h1>

        <table class="tbl">
            <tr>
                <th>Id</th>
                <th>Title</th>
                <th>Currency</th>
                <th>Balance</th>
            </tr>
            <tr v-for="a in accounts" :key="a.id">
                <td>{{ a.id }}</td>
                <td>{{ a.title }}</td>
                <td>{{ a.currency }}</td>
                <td>{{ a.balance }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'Accounts',
    data() {
        return {
            accounts: [],
            error: null,
        };
    },
    created() {
        apiRequests.getAccounts(this.success, this.fail);
    },
    methods: {
        success(payload) {
            this.accounts = payload;
        },
        fail(message) {
            this.error = message;
        },
    },
}
</script>
