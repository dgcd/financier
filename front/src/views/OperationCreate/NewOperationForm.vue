<template>
    <table>
        <tr>
            <td><span>Date: </span></td>
            <td><date-picker v-model.trim="date"/></td>
        </tr>
        <tr v-if="operationTypes">
            <td><span>Operation type: </span></td>
            <td><operation-type-selector
                v-model="operationType"
                :operationTypes="operationTypes"
            /></td>
        </tr>
        <br>

        <tr>
            <td><span>{{isExchange ? 'Currency from: ' : 'Currency: '}}</span></td>
            <td><currency-selector v-model="currency" :filterExistentAccounts="true"/></td>
        </tr>
        <tr>
            <td><span>{{isTrans ? 'Account from: ' : 'Account: '}}</span></td>
            <td><account-selector v-model="accountId" :currency="currency" /></td>
        </tr>
        <br v-if="isExchange">

        <tr v-if="isExchange">
            <td><span>Currency to: </span></td>
            <td>
                <currency-selector v-model="currencyTo" :filterExistentAccounts="true"/>
                <span v-if="isExchange && currency === currencyTo">&nbsp;&nbsp;Currencies must differ</span>
            </td>
        </tr>
        <tr v-if="isTrans">
            <td><span>Account to: </span></td>
            <td>
                <account-selector v-model="accountToId" :currency="isExchange ? currencyTo : currency" />
                <span v-if="accountId === accountToId">&nbsp;&nbsp;Accounts must differ</span>
            </td>
        </tr>
        <br>

        <tr>
            <td><span>{{isExchange ? 'Amount from: ' : 'Total amount: '}}</span></td>
            <td><input type="text" v-model.trim="amount"></td>
        </tr>
        <tr v-if="isExchange">
            <td><span>Amount to: </span></td>
            <td><input type="text" v-model.trim="amountTo"></td>
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

        <tr v-if="showCategories">
            <td><span>Category: </span></td>
            <td><category-selector v-model="categoryId"/></td>
        </tr>
        <tr v-if="showCategories && categoryId">
            <td><span>Subcategory: </span></td>
            <td><category-selector v-model="subcategoryId" :parentId="categoryId"/></td>
        </tr>
        <br v-if="showCategories">

        <tr v-if="operationType !== 'BASE'">
            <td><span>Comment: </span></td>
            <td><input type="text" v-model.trim="comment" placeholder="enter comment"></td>
        </tr>
        <tr v-if="!isTrans && operationType !== 'BASE'">
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
        isTrans: {          // if TRANS or EXCHANGE
            type: Boolean,
            required: true,
        },
        isIncome: {         // if isTrans is 'true', this field is ignored
            type: Boolean,
            required: true,
        },
    },

    data() {
        return {
            date: utils.getTodayDateString(),
            operationType: null,

            currency: null,
            currencyTo: null,

            accountId: null,
            accountToId: null,

            amount: '0',
            amountTo: '0',

            quantity: '1',

            categoryId: null,
            subcategoryId: null,

            comment: '',
            counterparty: '',

            operationTypes: this.isTrans ?
                [dicts.OPERATION_TYPE_TRANS, dicts.OPERATION_TYPE_EXCHANGE] :
                this.isIncome ?
                    [dicts.OPERATION_TYPE_INCOME, dicts.OPERATION_TYPE_BASE] :
                    [dicts.OPERATION_TYPE_EXPENSE],

            emitEnabled: false,
        };
    },

    computed: {
        price() {
            return Number(this.parseAmount(this.amount)) / Number(this.quantity);
        },

        showCategories() {
            return this.operationType !== dicts.OPERATION_TYPE_BASE;
        },

        isExchange() {
            return this.isTrans && this.operationType === dicts.OPERATION_TYPE_EXCHANGE;
        },
    },

    watch: {
        date()          { this.emitOperation(); },
        accountId()     { this.emitOperation(); },
        accountToId()   { this.emitOperation(); },
        operationType() { this.emitEnabled = true; 
                          this.emitOperation(); },
        amount()        { this.emitOperation(); },
        amountTo()      { this.emitOperation(); },
        quantity()      { this.emitOperation(); },
        subcategoryId() { this.emitOperation(); },
        comment()       { this.emitOperation(); },
        counterparty()  { this.emitOperation(); },
    },

    methods: {
        emitOperation() {
            if (!this.emitEnabled) return;
            const qtty = Number(this.quantity);
            const amnt = this.parseAmount(this.amount);
            console.log(`amnt: ${amnt}`);
            const amntTo = Number(this.amountTo);
            const operation = {
                date: this.date,
                operationType: this.operationType,

                accountId: this.accountId,
                accountToId: this.isTrans ? this.accountToId : null,

                amount: Number.isNaN(amnt) ?
                    null :
                    this.isTrans || this.isIncome ?
                        amnt :
                        -amnt,

                amountTo: Number.isNaN(amntTo) ?
                    null :
                    this.isExchange ?
                        amntTo :
                        null,

                quantity: this.isTrans ?
                    null :
                    Number.isNaN(qtty) ?
                        null :
                        qtty,

                subcategoryId: this.operationType === dicts.OPERATION_TYPE_BASE ? null : this.subcategoryId,

                comment:      this.comment.trim()      ? this.comment.trim()      : null,
                counterparty: this.counterparty.trim() ? this.counterparty.trim() : null,
            };
            const cleanedOp = utils.removeEmptyFieldsFromObject(operation);
            this.$emit('input', cleanedOp);
        },

        parseAmount(amount) {
            if (!amount) {
                console.log(`undef: ${amount}`);
                return;
            }
            let noSpaces = amount.replace(/\s+/g, '');
            console.log(`noSpaces: '${noSpaces}'`);

            if (noSpaces.length > 0 && noSpaces.charAt(0) === '=') {
                return this.parseAmountExpression(noSpaces.substring(1));
            }

            let result = Number(noSpaces);
            console.log(`result: ${result}`);

            if (Number.isNaN(result)) {
                console.log(`isNaN: ${result}`);
            }
            return result;
        },
 
//     =12.5 +20.25* 10-4 0/4   +   12 +20* 10-4 0/4      
        parseAmountExpression(s) {
            let nums = [];
            let ops = [];
            for (let i = 0; i < s.length;) {
                console.log(`for i: ${i}`);

                if (this.isNum(s.charAt(i))) {
                    let i2 = i + 1;
                    while (i2 < s.length && this.isNum(s.charAt(i2))) {
                        console.log(`i2: ${i2}, char: ${s.charAt(i2)}`);
                        i2 ++;
                    }
                    let number = Number(s.slice(i, i2));
                    i = i2;
                    console.log(`number: ${number}`);
                    nums.push(number);
                } else if (this.isOp(s.charAt(i))) {
                    let curOp = s.charAt(i);
                    while (ops.length !== 0 && this.priority(ops[ops.length -1]) >= this.priority(curOp)) {
                        let prevOp = ops.pop();
                        this.processOp(nums, prevOp);
                    }
                    i++;
                    ops.push(curOp);
                } else {
                    // wrong character
                    console.log("wrong char: " + s.charAt(i));
                    console.log(nums);
                    console.log(ops);
                    return null;
                }
            }
            console.log("end");
            console.log(nums);
            console.log(ops);
            while (ops.length !== 0) {
                this.processOp(nums, ops[ops.length -1]);
                ops.pop();
            }
            console.log("end 2");
            console.log(nums);
            console.log(ops);
            return nums[nums.length -1];
        },

        priority (op) {
            if (op === '+' || op === '-')
                return 1;
            if (op === '*' || op === '/')
                return 2;
            return -1;
        },

        isOp(c) {
            if (c === '+' || c === '-' || c === '*' || c === '/') {
                console.log(`is op: ${c}`);
                return true;
            }
            console.log(`not op: ${c}`);
            return false;
        },

        isNum(c) {
            if ((c >= '0' && c <= '9') || c === '.') { // numeric (0-9)
                console.log(`is num: ${c}`);
                return true;
            }
            console.log(`not num: ${c}`);
            return false;
        },

        processOp(nums, op) {
            let r = nums.pop();
            let l = nums.length ? nums.pop() : 0;
            switch (op) {
                case '+': nums.push(l + r); break;
                case '-': nums.push(l - r); break;
                case '*': nums.push(l * r); break;
                case '/': nums.push(l / r); break;
            }
        },
    },
}
</script>
