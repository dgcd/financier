<template>
    <table>
        <tr>
            <td><span>Title: </span></td>
            <td><input type="text" v-model.trim="title" placeholder="enter title"></td>
        </tr>
        <tr>
            <td><span>Currency: </span></td>
            <td><currency-selector v-model="currency"/></td>
        </tr>
    </table>
</template>

<script>

export default {
    name: 'AccountCreate-NewAccountForm',

    props: {
        value: {
            type: Object,
            required: true,
        },
    },

    data() {
        return {
            title: null,
            currency: null,
        };
    },

    created() {
        this.title = this.value.title;
        this.currency = this.value.currency;
    },

    watch: {
        title() {
            this.$emit('input', this.makeAccount());
        },
        currency() {
            this.$emit('input', this.makeAccount());
        },
    },

    methods: {
        makeAccount() {
            return {
                title: this.title,
                currency: this.currency,
            };
        },
    },
}
</script>
