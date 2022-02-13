<template>
    <div>
        <page-title />
        <h1>Create category</h1>

        <p>
            <redirect-button :title="'Back'" :path="'/categories'" />
        </p>

        <NewCategoryForm v-model="category" />

        <error-message v-if="error" :message="error" />

        <p>
            <button @click="onCreate">Create</button>
        </p>
    </div>
</template>

<script>
import { mapMutations } from 'vuex';
import apiRequests from '@/service/apiRequests.js';
import NewCategoryForm from './components/NewCategoryForm.vue';

export default {
    name: 'CategoryCreate',

    components: {
        NewCategoryForm,
    },

    data() {
        return {
            category: {
                title: null,
            },
            error: null,
        };
    },

    methods: {
        ...mapMutations(['addCategory']),

        onCreate() {
            if (!this.category.title) {
                this.error = 'Title must not be empty';
                return;
            }
            if (this.category.title.length < 3 || this.category.title.length > 100) {
                this.error = 'Title length must be 3..100';
                return;
            }

            this.error = null;
            this.category.parentId = this.$route.query.parentId;

            apiRequests.createCategory(
                this.category,
                this.requestSuccess,
                this.requestError,
            );
        },

        requestSuccess(payload) {
            this.addCategory(payload);
            this.$router.push('/categories');
        },

        requestError(message) {
            this.error = message;
        },
    },
}
</script>
