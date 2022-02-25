<template>
    <div>
        <page-title />
        <h1>Accounts</h1>

        <p>
            <redirect-button :title="'Create account'" :path="'/accounts/create'" />
        </p>

        <error-message v-if="error" :message="error" />

        <AccountsTable />
    </div>
</template>

<script>
import AccountsTable from './components/AccountsTable.vue';

export default {
    name: 'Accounts',

    components: {
        AccountsTable,
    },

    data() {
        return {
            error: null,
        };
    },

    created() {
        if (this.$route.query.reason === 'noaccount') {
            this.error = 'You can not create operations without at least one account';
        }
    },
}
</script>
