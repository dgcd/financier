<template>
    <div>
        <page-title />
        <h1>Create {{titleSpec}} operation</h1>

        <p>
            <redirect-button :title="'Back'" :path="'/operations'" />
        </p>

        <NewOperationForm
            v-if="this.accounts.length"
            v-model="operation"
            :isTrans="isTrans"
            :isIncome="isIncome"
        />

        <error-message v-if="error" :message="error" />

        <p>
            <button class="btn btn-link" @click="onCreate">Create</button>
        </p>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
import apiRequests from '@/service/apiRequests.js';
import NewOperationForm from './components/NewOperationForm.vue';
import dicts from '@/config/dicts.js';

export default {
    name: 'OperationCreate',

    components: {
        NewOperationForm,
    },

    data() {
        return {
            operation: {},
            error: null,
            isTrans: true,
            isIncome: true,
            titleSpec: null,
        };
    },

    created() {
        if (!this.accounts.length) {
            this.$router.push('/accounts?reason=noaccount');
            return;
        }

        const type = this.$route.query.type;
        if (type === dicts.OPERATION_TYPE_TRANS) {
            this.isTrans = true;
            this.isIncome = false;
            this.titleSpec = 'Transfert';
        } else if (type === dicts.OPERATION_TYPE_EXPENSE) {
            this.isTrans = false;
            this.isIncome = false;
            this.titleSpec = 'Expense';
        } else {
            this.isTrans = false;
            this.isIncome = true;
            this.titleSpec = 'Income or Base';
        }
    },

    computed: {
        ...mapState(['accounts']),
    },

    methods: {
        ...mapMutations(['addOperations', 'updateAccounts']),

        onCreate() {
            if (!this.operationValid()) {
                return;
            }
            this.error = null;
            apiRequests.createOperation(
                this.operation,
                this.requestSuccess,
                this.requestError,
            );
        },

        operationValid() {
            return true;
        },

        requestSuccess(payload) {
            this.addOperations(payload.newOperations);
            this.updateAccounts(payload.updatedAccounts);
            this.$router.push('/operations');
        },

        requestError(message) {
            this.error = message;
        },
    },
}
</script>
