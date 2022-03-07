<template>
    <div>
        <page-title />
        <h1>Create account</h1>

        <p>
            <redirect-button :title="'Back'" :path="'/accounts'" />
        </p>

        <NewAccountForm v-model="account" />

        <error-message v-if="error" :message="error" />

        <p>
            <button class="btn btn-link" @click="onCreate">Create</button>
        </p>
    </div>
</template>

<script>
import { mapMutations } from 'vuex';
import dicts from '@/config/dicts.js';
import apiRequests from '@/service/apiRequests.js';
import NewAccountForm from './NewAccountForm.vue';

export default {
    name: 'AccountCreateView',

    components: {
        NewAccountForm,
    },

    data() {
        return {
            currencies: dicts.currencies,
            error: null,
            account: {
                currency: null,
                title: null,
            },
        };
    },

    methods: {
        ...mapMutations(['addAccount']),

        onCreate() {
            if (!this.account.title) {
                this.error = 'Title must not be empty';
                return;
            }

            if (this.account.title.length < 3 || this.account.title.length > 100) {
                this.error = 'Title length must be 3..100';
                return;
            }

            this.error = null;

            apiRequests.createAccount(
                this.account,
                this.requestSuccess,
                this.requestError,
            );
        },

        requestSuccess(payload) {
            this.addAccount(payload);
            this.$router.push('/accounts');
        },

        requestError(message) {
            this.error = message;
        },
    },
}
</script>
