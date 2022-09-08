<template>
    <div>
        <h2>By category</h2>
    </div>
</template>

<script>
import { mapState, mapGetters } from 'vuex';
import utils from '@/service/utils.js';

export default {
    name: 'ByCategorySubview',

    props: {
        currency: {
            type: String,
            required: true,
        },
        showOnlyYears: {
            type: Boolean,
            required: true,
        },
    },

}
</script>
