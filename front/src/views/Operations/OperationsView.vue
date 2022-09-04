<template>
    <div>
        <page-title />
        <h1>Operations</h1>

        <p>
            <redirect-button :title="'Create Expense'" :path="'/operations/create?type=EXPENSE'" />
            <redirect-button :title="'Create Income/Base'" :path="'/operations/create?type=INCOME'" />
            <redirect-button :title="'Create Trans/Exchange'" :path="'/operations/create?type=TRANS'" />
        </p>

        <p>
            <show-expense-checkbox />
            <show-income-checkbox />
            <show-trans-checkbox />
        </p>

        <error-message v-if="error" :message="error" />

        <OperationsTable
            :operations="operations"
            :cancelOperationHandler="cancelOperation"
        />
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
import OperationsTable from './OperationsTable.vue';
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'OperationsView',

    components: {
        OperationsTable,
    },

    data() {
        return {
            error: null,
        };
    },

    computed: {
        ...mapState(['operations']),
    },

    methods: {
        ...mapMutations(['updateAccounts', 'removeOperationsByIds']),

        cancelOperation(operation) {
            if (!window.confirm(`Cancel operation '${operation.amount} (${operation.accountTitle})'?`)) {
                return;
            }
            apiRequests.cancelOperation(
                { id: operation.id },
                this.requestSuccess,
                this.requestError,
            );
        },

        requestSuccess(payload) {
            this.removeOperationsByIds(payload.canceledOperationsIds);
            this.updateAccounts(payload.updatedAccounts);
        },

        requestError(message) {
            this.error = message;
        },
    },
}
</script>
