<template>
    <button class="btn btn-link" @click="onClick">{{ title }}</button>
</template>

<script>
export default {
    name: 'RedirectButton',

    props: {
        title: {
            type: String,
            required: true,
        },
        path: {
            type: String,
            required: true,
        },
    },

    methods: {
        onClick() {
            this.$router.push(this.path);
        },
    },
}
</script>
