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
            <td><span>{{isTrans ? 'Account from: ' : 'Account:' }}</span></td>
            <td><account-selector v-model="accountId" :currency="currency" /></td>
        </tr>
        <tr v-if="isTrans">
            <td><span>Account to: </span></td>
            <td><account-selector v-model="accountToId" :currency="currency" /></td>
        </tr>
        <br>
        <tr v-if="operationTypes">
            <td><span>Operation type: </span></td>
            <td><operation-type-selector
                v-model="operationType"
                :operationTypes="operationTypes"
            /></td>
        </tr>
        <tr>
            <td><span>Total amount: </span></td>
            <td><input type="text" v-model.trim="amount" placeholder="enter total amount"></td>
        </tr>
        <tr v-if="!isTrans">
            <td><span>Quantity: </span></td>
            <td><input type="text" v-model.trim="quantity" placeholder="enter quantity"></td>
        </tr>
        <tr v-if="!isTrans">
            <td><span>Price: </span></td>
            <td><input type="text" :value="price | formatMoneyToString" disabled></td>
        </tr>
        <br>
        <tr>
            <td><span>Category: </span></td>
            <td><category-selector v-model="categoryId"/></td>
        </tr>
        <tr>
            <td><span>Subcategory: </span></td>
            <td><category-selector v-model="subcategoryId" :parentId="categoryId"/></td>
        </tr>
        <br>
        <tr>
            <td><span>Comment: </span></td>
            <td><input type="text" v-model.trim="comment" placeholder="enter comment"></td>
        </tr>
        <tr v-if="!isTrans">
            <td><span>Counterparty: </span></td>
            <td><input type="text" v-model.trim="counterparty" placeholder="enter counterparty"></td>
        </tr>
    </table>
</template>

<script>
import utils from '@/service/utils.js';
import dicts from '@/config/dicts.js';

export default {
    name: 'OperationCreate-NewOperationForm',

    props: {
        value: {
            type: Object,
            required: true,
        },
        isTrans: {
            type: Boolean,
            required: true,
        },
        isIncome: {         // if isTrans is 'true', isIncome is ignored
            type: Boolean,
            required: true,
        },
    },

    data() {
        return {
            date: utils.getTodayDateString(),
            currency: null,
            accountId: null,
            accountToId: null,

            operationType: null,
            amount: '0',
            quantity: '1',

            categoryId: null,
            subcategoryId: null,

            comment: '',
            counterparty: '',

            operationTypes: this.isTrans ?
                [dicts.OPERATION_TYPE_TRANS] :
                this.isIncome ?
                    [dicts.OPERATION_TYPE_INCOME, dicts.OPERATION_TYPE_BASE] :
                    [dicts.OPERATION_TYPE_EXPENSE],

            emitEnabled: false,
        };
    },

    computed: {
        price() {
            return Number(this.amount) / Number(this.quantity);
        },
    },

    watch: {
        date()          { this.emitOperation(); },
        accountId()     { this.emitOperation(); },
        accountToId()   { this.emitOperation(); },
        operationType() { this.emitEnabled = true; 
                          this.emitOperation(); },
        amount()        { this.emitOperation(); },
        quantity()      { this.emitOperation(); },
        subcategoryId() { this.emitOperation(); },
        comment()       { this.emitOperation(); },
        counterparty()  { this.emitOperation(); },
    },

    methods: {
        emitOperation() {
            if (!this.emitEnabled) return;
            const qtty = Number(this.quantity);
            const amnt = Number(this.amount);
            const operation = {
                date: this.date,
                accountId: this.accountId,
                accountToId: this.isTrans ? this.accountToId : null,

                operationType: this.operationType,
                amount: Number.isNaN(amnt) ?
                    null :
                    this.isTrans || this.isIncome ?
                        amnt :
                        -amnt,
                quantity: this.isTrans ?
                    null :
                    Number.isNaN(qtty) ?
                        null :
                        qtty,

                subcategoryId: this.subcategoryId,

                comment:      this.comment.trim()      ? this.comment.trim()      : null,
                counterparty: this.counterparty.trim() ? this.counterparty.trim() : null,
            };
            const cleanedOp = utils.removeEmptyFieldsFromObject(operation);
            console.log('cleanedOp: ', cleanedOp);
            this.$emit('input', cleanedOp);
        },
    },
}
</script>
