<template>
    <table>
        <tr>
            <td><span>Date: </span></td>
            <td><date-picker v-model.trim="date"/></td>
        </tr>
        <tr>
            <td><span>Currency: </span></td>
            <td><currency-selector v-model="currency" :filterExistentAccounts="true"/></td>
        </tr>
        <tr>
            <td><span>Account: </span></td>
            <td><account-selector v-model="accountId" :currency="currency" /></td>
        </tr>
        <br>
        <tr>
            <td><span>Operation type: </span></td>
            <td><operation-type-selector v-model="operationType"/></td>
        </tr>
        <tr>
            <td><span>Quantity: </span></td>
            <td><input type="text" v-model.trim="quantity" placeholder="enter quantity"></td>
        </tr>
        <tr>
            <td><span>Total amount: </span></td>
            <td><input type="text" v-model.trim="amount" placeholder="enter total amount"></td>
            <span v-if="signumAlarm">{{signumAlarm}}</span>
        </tr>
        <br>
        <tr>
            <td><span>Counterparty: </span></td>
            <td><input type="text" v-model.trim="counterparty" placeholder="enter counterparty"></td>
        </tr>
        <tr>
            <td><span>Comment: </span></td>
            <td><input type="text" v-model.trim="comment" placeholder="enter comment"></td>
        </tr>
    </table>
</template>

<script>
import utils from '@/service/utils.js';

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
            date: utils.getTodayDateString(),
            currency: null,
            accountId: null,
            quantity: '1',
            amount: null,
            operationType: null,
            counterparty: null,
            comment: null,
        };
    },

    created() {
        this.emitOperation();
    },

    computed: {
        signumAlarm() {
            if (this.operationType === 'EXPENSE')
                return 'Negate!';
            return null;
        }
    },

    watch: {
        date()          { this.emitOperation(); },
        currency()      { this.emitOperation(); },
        accountId()     { this.emitOperation(); },
        quantity()      { this.emitOperation(); },
        amount()        { this.emitOperation(); },
        operationType() { this.emitOperation(); },
        counterparty()  { this.emitOperation(); },
        comment()       { this.emitOperation(); },
    },

    methods: {
        emitOperation() {
            const operation = {
                date: this.date,
                currency: this.currency,
                accountId: this.accountId,
                quantity: this.quantity,
                amount: this.amount,
                operationType: this.operationType,
                counterparty: this.counterparty && this.counterparty.trim() ? this.counterparty.trim() : null,
                comment:      this.comment      && this.comment.trim()      ? this.comment.trim()      : null,
            };
            this.$emit('input', operation);
        },
    },
}
</script>
