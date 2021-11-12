<template>
    <p class="errorTitle">{{ msg }}</p>
</template>

<script>
export default {
    name: 'ErrorTitle',
    props: {
        msg: {
            type: String,
            required: true,
        },
    },
}
</script>

<style scoped>
p.errorTitle {
    color: red;
}
</style>
