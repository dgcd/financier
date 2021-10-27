<template>
    <div id="app">
        <div v-if="isAppReady">
            <AppNavbar />

            <router-view />
        </div>
        <p v-else>{{ loadingInfo }}</p>
    </div>
</template>

<script>
import AppNavbar from '@/components/AppNavbar.vue';
import { mapState, mapMutations } from 'vuex';
import apiRequests from '@/service/apiRequests.js';

export default {
    name: 'App',
    data() {
        return {
            loadingInfo: 'Application is loading...',
        };
    },
    components: {
        AppNavbar,
    },
    computed: {
        ...mapState(['isAppReady']),
    },
    created() {
        apiRequests.getInitData(
            this.setInitData,
            errMsg => this.loadingInfo = errMsg,
        );
    },
    methods: {
        ...mapMutations(['setInitData'])
    },
}
</script>
