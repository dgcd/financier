<template>
    <div>
        <h2>By category</h2>

        <table class="tbl">
            <tr>
                <th>Category</th>
                <th v-if="showSubcategories">Subcategory</th>
                <th v-for="col in monthsAndYearsColumns" :key="col">{{ col }}</th>
            </tr>
            <tr v-for="row in preparedRows" :key="row.id" :class="row.isSubcat ? '' : 'boldRow'">
                <td>{{ row.isSubcat ? '' : row.categoryTitle }}</td>
                <td v-if="showSubcategories">{{ row.subcategoryTitle }}</td>
                <td v-for="col in row.columns" :key="col.id">{{ col.amount | formatMoneyToString }}</td>
            </tr>
        </table>
    </div>
</template>

<script>
import { mapGetters } from 'vuex';

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
        showSubcategories: {
            type: Boolean,
            required: true,
        },
    },

    computed: {
        monthsAndYearsColumns() {
            console.log("monthsAndYearsColumns");
            const monthsSpace = this.makeMonthsSpace();
            let columns = [];
            for (let year of monthsSpace) {
                if (!this.showOnlyYears) {
                    columns = columns.concat(year.monthsTokens);
                }
                columns.push(year.yearToken);
            }
            return columns.reverse();
        },

        monthsSpaceLength() {
            let length = this.makeMonthsSpace().length;
            for (let year of this.makeMonthsSpace()) {
                length += year.monthsTokens.length;
            }
            return length;
        },

        preparedData() {
            console.log("preparedData (by cat)");
            const preparedOps = this.groupOpsByMonthByCurrencyByCategories();
            // console.log("preparedOps: ", preparedOps)
            const monthsSpace = this.makeMonthsSpace();
            // console.log("monthsSpace: ", monthsSpace)
            const categoriesTree = this.getCategoriesTree();
            // console.log("categoriesTree: ", categoriesTree)
            const tableData = this.makeTableData(monthsSpace, preparedOps, categoriesTree, this.currency);
            // console.log("tableData: ", tableData)
            return tableData;
        },

        preparedRows() {
            console.log("preparedRows (by cat)");
            let tableData = this.preparedData;
            if (!this.showSubcategories) {
                tableData = tableData.filter(row => !row.isSubcat);
            }
            if (this.showOnlyYears) {
                return tableData.map(row => {
                    const newRow = { ...row };
                    newRow.columns = row.columns.filter(c => !c.isMonth);
                    return newRow;
                });
            }
            return tableData;
        },
    },

    methods: {
        ...mapGetters(['groupOpsByMonthByCurrencyByCategories', 'makeMonthsSpace', 'getCategoriesTree']),

        makeTableData(monthsSpace, preparedOps, categoriesTree, currency) {
            const resultRows = [];
            let rowId = 1;
            for (let cat of categoriesTree) {
                const catRow = {
                    categoryTitle: cat.category,
                    id: rowId++,
                    columns: [],
                };
                const catRows = [];
                resultRows.push(catRow);
                for (let subCat of cat.subcategories) {
                    const subCatRow = {
                        categoryTitle: cat.category,
                        subcategoryTitle: subCat,
                        isSubcat: true,
                        id: rowId++,
                        columns: [],
                    }
                    let columnsId = 1;
                    resultRows.push(subCatRow);
                    catRows.push(subCatRow);
                    for (let year of monthsSpace) {
                        let yearAmount = 0;
                        for (let month of year.monthsTokens) {
                            let monthAmount = 0;
                            if (preparedOps[month] &&
                                preparedOps[month][currency] &&
                                preparedOps[month][currency][cat.category] &&
                                preparedOps[month][currency][cat.category][subCat]
                            ) {
                                for (let op of preparedOps[month][currency][cat.category][subCat]) {
                                    monthAmount += op.amount;
                                }
                            }
                            subCatRow.columns.push({
                                amount: monthAmount,
                                isMonth: true,
                                id: columnsId++,
                            });
                            yearAmount += monthAmount;
                        }
                        subCatRow.columns.push({
                            amount: yearAmount,
                            id: columnsId++,
                        });
                    }
                    subCatRow.columns = subCatRow.columns.reverse();
                }

                for (let i = 0; i < this.monthsSpaceLength; i++) {
                    let amountSumm = 0;
                    let isMonth;
                    for (let row of catRows) {
                        amountSumm += row.columns[i].amount;
                        isMonth = row.columns[i].isMonth;
                    }
                    catRow.columns.push({
                        amount: amountSumm,
                        isMonth,
                        id: i,
                    });
                }
            }
            return resultRows;
        },
    },
}
</script>
