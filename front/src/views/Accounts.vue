<template>
    <div>
        <title-component :title="$route.meta.title" />

        <h1>Accounts</h1>

        <p>
            <button @click="onClickCreate">Create account</button>
        </p>

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
import { mapState } from 'vuex';
import router from '@/router';

export default {
    name: 'Accounts',
    computed: {
        ...mapState(['accounts']),
    },
    methods: {
        onClickCreate() {
            router.push('/accounts/create');
        },
    },
}
</script>
