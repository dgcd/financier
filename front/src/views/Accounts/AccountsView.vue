<template>
    <div>
        <page-title />
        <h1>Accounts</h1>

        <p>
            <redirect-button :title="'Create account'" :path="'/accounts/create'" />
        </p>

        <p>
            <exchange-rates />
        </p>

        <p>
            <show-empty-accounts-checkbox />
        </p>

        <error-message v-if="error" :message="error" />

        <AccountsTable />
    </div>
</template>

<script>
import AccountsTable from './AccountsTable.vue';

export default {
    name: 'AccountsView',

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
