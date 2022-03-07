<template>
    <table>
        <tr>
            <td><span>Title: </span></td>
            <td><input type="text" v-model.trim="title" placeholder="enter title"></td>
        </tr>
    </table>
</template>

<script>
export default {
    name: 'CategoryCreate-NewCategoryForm',

    props: {
        value: {
            type: Object,
            required: true,
        },
    },

    data() {
        return {
            title: null,
        };
    },

    created() {
        this.title = this.value.title;
    },

    watch: {
        title() {
            this.$emit('input', { title: this.title });
        },
    },
}
</script>
