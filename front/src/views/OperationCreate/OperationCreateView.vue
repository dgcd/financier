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
import NewOperationForm from './NewOperationForm.vue';
import dicts from '@/config/dicts.js';

export default {
    name: 'OperationCreateView',

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
            this.$router.push('/accounts?reason=noaccounts');
            return;
        }

        if (!this.categories.filter(c => c.parentId).length) {
            this.$router.push('/categories?reason=nocategories');
            return;
        }

        const type = this.$route.query.type;
        if (type === dicts.OPERATION_TYPE_TRANS) {
            this.isTrans = true;
            this.isIncome = false;
            this.titleSpec = 'Trans or Exchange';
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
        ...mapState(['accounts', 'categories']),
    },

    methods: {
        ...mapMutations(['addOperations', 'updateAccounts']),

        onCreate() {
            if (!this.operationValid(this.operation)) {
                return;
            }
            this.error = null;
            apiRequests.createOperation(
                this.operation,
                this.requestSuccess,
                this.requestError,
            );
        },

        operationValid(operation) {
            if (operation.amount === 0) {
                this.error = 'Amount can not be 0';
                return false;
            }
            if (!operation.amount) {
                this.error = 'Amount is not correct';
                return false;
            }
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
