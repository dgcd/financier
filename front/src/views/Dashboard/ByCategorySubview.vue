<template>
    <div>
        <h2>By category</h2>

        <table class="tbl">
            <tr>
                <th>Category</th>
                <th v-if="showSubcategories">Subcategory</th>
                <th v-for="col in monthsAndYearsColumns" :key="col">{{ col }}</th>
            </tr>
            <tr
                v-for="row in preparedRows"
                :key="row.id"
                :class="!row.isSubcat && showSubcategories ? 'boldRow' : ''"
            >
                <td>{{ row.isSubcat ? '' : row.categoryTitle }}</td>
                <td v-if="showSubcategories">{{ row.subcategoryTitle }}</td>
                <td
                    v-for="col in row.columns"
                    :key="col.id"
                    :class="!col.isMonth && !showOnlyYears ? 'boldRow' : ''"
                >{{ col.amount | formatMoneyToString }}</td>
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
            const preparedOps = this.groupOpsByMonthByCurrencyByCategories();
            const monthsSpace = this.makeMonthsSpace();
            const categoriesTree = this.getCategoriesTree();
            const tableData = this.makeTableData(monthsSpace, preparedOps, categoriesTree, this.currency);
            return tableData;
        },

        preparedRows() {
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
                const categoryTitle = cat.category;
                const catRow = {
                    categoryTitle,
                    id: rowId++,
                    columns: [],
                };
                const catRows = [];
                resultRows.push(catRow);
                for (let subcategoryTitle of cat.subcategories) {
                    const subCatRow = {
                        categoryTitle,
                        subcategoryTitle,
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
                                preparedOps[month][currency][categoryTitle] &&
                                preparedOps[month][currency][categoryTitle][subcategoryTitle]
                            ) {
                                for (let op of preparedOps[month][currency][categoryTitle][subcategoryTitle]) {
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
