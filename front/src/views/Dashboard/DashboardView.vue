<template>
    <div>
        <page-title />
        <h1>Dashboard</h1>

        <span v-if="!operations.length">No operations. Nothing to show.</span>

        <div v-else>
            <button class="btn btn-link" @click="isOverallSubview = false">By category</button>
            <button class="btn btn-link" @click="isOverallSubview = true">Overall</button>
    
            <table>
                <tr><td>Currency:</td><td><currency-selector v-model="currency" /></td></tr>
                <tr><td>Show only years:&nbsp;</td><td><input type="checkbox" v-model="showOnlyYears" /></td></tr>
                <tr v-if="!isOverallSubview"><td>Show subcategories:&nbsp;</td><td><input type="checkbox" v-model="showSubcategories" /></td></tr>
            </table>

            <div v-if="!isOverallSubview">
                <ByCategorySubview
                    :currency="currency"
                    :showOnlyYears="showOnlyYears"
                    :showSubcategories="showSubcategories"
                />
            </div>

            <div v-if="isOverallSubview">
                <OverallSubview
                    :currency="currency"
                    :showOnlyYears="showOnlyYears"
                />
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
            showOnlyYears: false,
            showSubcategories: false,
            isOverallSubview: false,
        };
    },

    components: {
        OverallSubview,
        ByCategorySubview,
    },

    computed: {
        ...mapState(['operations']),
    },
}
</script>
