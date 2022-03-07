<template>
    <div id="app" class="container-fluid m-1">
        <div v-if="isAppReady">
            <Navbar />

            <router-view />
        </div>

        <p v-else>{{ loadingInfo }}</p>
    </div>
</template>

<script>
import { mapState, mapMutations } from 'vuex';
import apiRequests from '@/service/apiRequests.js';
import Navbar from './Navbar.vue';

export default {
    name: 'App',

    components: {
        Navbar,
    },

    data() {
        return {
            loadingInfo: 'Application is loading...',
        };
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
