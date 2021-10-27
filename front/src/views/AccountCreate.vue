<template>
    <div>
        <title-component :title="$route.meta.title" />

        <h1>Create account</h1>

        <p><button @click="onBack">Back</button></p>

        <table>
            <tr>
                <td><span>Title: </span></td>
                <td><input type="text" v-model.trim="title" placeholder="enter title"></td>
            </tr>
            <tr>
                <td><span>Currency: </span></td>
                <td><SelectList v-model="currency" :valuesList="currencies" /></td>
            </tr>
        </table>

        <ErrorTitle v-if="error" :msg="error" />

        <p><button @click="onCreate">Create</button></p>
    </div>
</template>

<script>
import SelectList from '@/components/SelectList.vue';
import ErrorTitle from '@/components/ErrorTitle.vue';
import router from '@/router';
import dicts from '@/config/dicts.js';
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'AccountCreate',

    components: {
        SelectList,
        ErrorTitle,
    },

    data() {
        return {
            currencies: dicts.currencies,
            currency: null,
            title: null,
            error: null,
        };
    },

    methods: {
        onBack() {
            router.push('/accounts');
        },

        onCreate() {
            if (!this.title) {
                this.error = 'Title must not be empty';
                return;
            }

            if (this.title.length < 3 || this.title.length > 100) {
                this.error = 'Title length must be 3..100';
                return;
            }

            const newAccount = {
                title: this.title,
                currency: this.currency,
            }
            this.error = null;

            apiRequests.createAccount(newAccount,
                this.createSuccess,
                this.createErorr,
            );
        },

        createSuccess(payload) {
            this.title = '';
            this.error = '';
        },

        createErorr(message) {
            this.error = message;
        },
    },
}
</script>
