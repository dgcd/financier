<template>
    <div>
        <page-title />
        <h1>Dashboard</h1>

        <span v-if="!operations.length">No operations. Nothing to show.</span>

        <div v-else>
            <button class="btn btn-link" @click="isOverallSubview = true">Overall</button>
            <button class="btn btn-link" @click="isOverallSubview = false">By category</button>
    
            <table>
                <tr><td>Currency:</td><td><currency-selector v-model="currency" /></td></tr>
                <!-- <tr><td>Include all currencies:&nbsp;</td><td><input type="checkbox" v-model="includeAllCurrencies" /></td></tr> -->
                <tr><td>Show only years:&nbsp;</td><td><input type="checkbox" v-model="showOnlyYears" /></td></tr>
            </table>

            <div v-if="isOverallSubview">
                <OverallSubview
                    :currency="currency"
                    :showOnlyYears="showOnlyYears"
                />
            </div>

            <div v-if="!isOverallSubview">
                <ByCategorySubview />
            </div>
        </div>
    </div>
</template>

<script>
import OverallSubview from './OverallSubview.vue';
import ByCategorySubview from './ByCategorySubview.vue';
import { mapState } from 'vuex';

export default {
    name: 'DashboardView',
    data() {
        return {
            currency: 'RUB',
            // includeAllCurrencies: false,
            showOnlyYears: false,

            isOverallSubview: true,
        };
    },

    components: {
        OverallSubview,
        ByCategorySubview,
    },

    computed: {
        ...mapState(['operations']),
    }
}
</script>
