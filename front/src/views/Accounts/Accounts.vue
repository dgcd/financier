<template>
    <div>
        <page-title />
        <h1>Accounts</h1>

        <p>
            <redirect-button :title="'Create account'" :path="'/accounts/create'" />
        </p>

        <AccountsTable :accounts="accounts" />
    </div>
</template>

<script>
import { mapState } from 'vuex';
import AccountsTable from './components/AccountsTable.vue';

export default {
    name: 'Accounts',

    components: {
        AccountsTable,
    },

    computed: {
        ...mapState(['accounts']),
    },
}
</script>
