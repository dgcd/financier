<template>
    <div>
        <page-title />
        <h1>Create operation</h1>

        <p>
            <redirect-button :title="'Back'" :path="'/operations'" />
        </p>

        <NewOperationForm v-model="operation" />

        <error-message v-if="error" :message="error" />

        <p><button @click="onCreate">Create</button></p>
    </div>
</template>

<script>
import { mapMutations } from 'vuex';
import apiRequests from '@/service/apiRequests.js';
import NewOperationForm from './components/NewOperationForm.vue';

export default {
    name: 'OperationCreate',

    components: {
        NewOperationForm,
    },

    data() {
        return {
            operation: {
                comment: null,
            },
            error: null,
        };
    },

    methods: {
        ...mapMutations(['addOperation']),

        onCreate() {
            if (!this.operationValid()) {
                return;
            }

            this.error = null;
            console.log(this.operation);

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
            this.addOperation(payload);
            this.$router.push('/operations');
        },

        requestError(message) {
            this.error = message;
        },
    },
}
</script>
