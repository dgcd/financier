<template>
    <div>
        <page-title />
        <h1>Create operation</h1>

        <p>
            <redirect-button :title="'Back'" :path="'/operations'" />
        </p>

        <NewOperationForm v-if="this.accounts.length" v-model="operation" />

        <error-message v-if="error" :message="error" />

        <p>
            <button @click="onCreate">Create</button>
        </p>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
import apiRequests from '@/service/apiRequests.js';
import NewOperationForm from './components/NewOperationForm.vue';

export default {
    name: 'OperationCreate',

    components: {
        NewOperationForm,
    },

    data() {
        return {
            operation: {},
            error: null,
        };
    },

    created() {
        if (!this.accounts.length) {
            this.$router.push('/accounts?reason=noaccount');
            return;
        }
    },

    computed: {
        ...mapState(['accounts']),
    },

    methods: {
        ...mapMutations(['addOperation', 'updateAccount']),

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
            this.addOperation(payload.operation);
            this.updateAccount(payload.account);
            this.$router.push('/operations');
        },

        requestError(message) {
            this.error = message;
        },
    },
}
</script>
