<template>
    <p class="errorMessage">{{ message }}</p>
</template>

<script>
export default {
    name: 'ErrorMessage',

    props: {
        message: {
            type: String,
            required: true,
        },
    },
}
</script>

<style scoped>
p.errorMessage {
    color: red;
}
</style>
