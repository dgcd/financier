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
            <show-closed-accounts-checkbox v-if="selections.showEmptyAccounts"/>
        </p>

        <error-message v-if="error" :message="error" />

        <AccountsTable :closeAccoutHadler="closeAccount" />
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
import AccountsTable from './AccountsTable.vue';
import apiRequests from '@/service/apiRequests.js';

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
        if (this.$route.query.reason === 'noaccounts') {
            this.error = 'You can not create operations without at least one account';
        }
    },

    computed: {
        ...mapState(['selections']),
    },

    methods: {
        ...mapMutations(['updateAccounts']),

        closeAccount(id) {
            if (!window.confirm(`Close account '${id}'?`)) {
                return;
            }
            apiRequests.closeAccount(
                { id },
                this.requestSuccess,
                this.requestError,
            );
        },

        requestSuccess(payload) {
            this.updateAccounts([payload]);
        },

        requestError(message) {
            this.error = message;
        },
    },
}
</script>
