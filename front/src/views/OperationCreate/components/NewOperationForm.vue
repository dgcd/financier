<template>
    <table>
        <tr>
            <td><span>Comment: </span></td>
            <td><input type="text" v-model.trim="comment" placeholder="enter comment"></td>
        </tr>
    </table>
</template>

<script>
export default {
    name: 'OperationCreate-NewOperationForm',

    props: {
        value: {
            type: Object,
            required: true,
        },
    },

    data() {
        return {
            comment: null,
        };
    },

    created() {
        this.comment = this.value.comment;
    },

    watch: {
        comment() {
            this.$emit('input', this.makeOperation());
        },
    },

    methods: {
        makeOperation() {
            return {
                comment: this.comment,
            };
        }
    },
}
</script>
