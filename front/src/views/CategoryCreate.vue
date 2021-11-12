<template>
    <div>
        <title-component :title="$route.meta.title" />

        <h1>Create category</h1>

        <p><button @click="onBack">Back</button></p>

        <table>
            <tr>
                <td><span>Title: </span></td>
                <td><input type="text" v-model.trim="title" placeholder="enter title"></td>
            </tr>
        </table>

        <ErrorTitle v-if="error" :msg="error" />

        <p><button @click="onCreate">Create</button></p>
    </div>
</template>

<script>
import { mapMutations } from 'vuex';
import ErrorTitle from '@/components/ErrorTitle.vue';
import router from '@/router';
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'CategoryCreate',

    components: {
        ErrorTitle,
    },

    data() {
        return {
            title: null,
            error: null,
        };
    },

    methods: {
        ...mapMutations(['addCategory']),

        onBack() {
            router.push('/categories');
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

            const newCategory = {
                title: this.title,
                parentId: this.$route.query.parentId,
            }
            this.error = null;

            console.log(newCategory);

            apiRequests.createCategory(newCategory,
                this.createSuccess,
                this.createErorr,
            );
        },

        createSuccess(payload) {
            this.addCategory(payload);
            this.onBack();
        },

        createErorr(message) {
            this.error = message;
        },
    },
}
</script>
