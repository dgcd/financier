<template>
    <div>
        <page-title />
        <h1>Create category</h1>

        <p>
            <redirect-button :title="'Back'" :path="'/categories'" />
        </p>

        <p>
            <NewCategoryForm v-model="category" />
        </p>

        <error-message v-if="error" :message="error" />

        <p>
            <button class="btn btn-link" @click="onCreate">Create</button>
        </p>
    </div>
</template>

<script>
import { mapMutations } from 'vuex';
import apiRequests from '@/service/apiRequests.js';
import NewCategoryForm from './NewCategoryForm.vue';

export default {
    name: 'CategoryCreateView',

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
            if (this.category.title.length < 1 || this.category.title.length > 30) {
                this.error = 'Title length must be 1..30';
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
