<template>
    <div>
        <page-title />
        <h1>Edit operation</h1>

        <p>
            <redirect-button :title="'Back'" :path="'/operations'" />
        </p>

        <table>
            <tr>
                <td><span>Operation id: </span></td>
                <td><span>{{id}}</span></td>
            </tr>

            <tr>
                <td><span>Operation type: </span></td>
                <td><span>{{operationType}}</span></td>
            </tr>

            <tr>
                <td><span>Date: </span></td>
                <td><date-picker v-model.trim="date"/></td>
            </tr>

            <tr v-if="operationType !== 'BASE'">
                <td><span>Comment: </span></td>
                <td><input type="text" v-model.trim="comment" placeholder="enter comment"></td>
            </tr>

            <tr v-if="operationType !== 'BASE'">
                <td><span>Counterparty: </span></td>
                <td><input type="text" v-model.trim="counterparty" placeholder="enter counterparty"></td>
            </tr>
        </table>

        <error-message v-if="error" :message="error" />

        <p>
            <button class="btn btn-link" @click="onSave">Save</button>
        </p>
    </div>
</template>

<script>
import { mapMutations, mapState } from 'vuex';
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'OperationCreateView',

    data() {
        return {
            id: '',
            operationType: '',

            date: '',
            comment: '',
            counterparty: '',

            error: null,
        }
    },

    created() {
        let srcOperation = this.operations.find(o => o.id == this.$route.query.id)
        this.id = srcOperation.id
        this.operationType = srcOperation.type
        this.date = srcOperation.date
        this.comment = srcOperation.comment || ''
        this.counterparty = srcOperation.counterparty || ''
    },

    computed: {
        ...mapState(['operations']),
    },

    methods: {
        ...mapMutations([
            'removeOperationsByIds',
            'addOperations',
        ]),

        onSave() {
            let operation = {
                id: this.id,
                date: this.date,
                comment: this.comment.trim() ? this.comment.trim() : null,
                counterparty: this.counterparty.trim() ? this.counterparty.trim() : null,
            }
            this.error = null;
            apiRequests.editOperation(
                operation,
                this.requestSuccess,
                this.requestError,
            );
        },

        requestSuccess(payload) {
            console.log('success payload:', payload)
            this.removeOperationsByIds([payload.id]);
            this.addOperations([payload]);
            this.$router.push('/operations');
        },

        requestError(message) {
            console.log('error message:', message)
            this.error = message;
        },
    },
}
</script>
