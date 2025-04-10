package com.mufti.test.storeapps.utils.filter

import androidx.sqlite.db.SimpleSQLiteQuery
import com.mufti.test.storeapps.utils.constant.CategoryProductType
import com.mufti.test.storeapps.utils.constant.CategoryProductType.*

object FilterUtils {
    fun getCategoryQuery(categoryType: CategoryProductType): SimpleSQLiteQuery {
        val simpleQuery = StringBuilder().append("SELECT * FROM products ")
        when (categoryType) {
            MEN_CLOTHING -> simpleQuery.append("WHERE category = \"men's clothing\"")
            JEWELERY -> simpleQuery.append("WHERE category = \"jewelery\"")
            ELECTRONICS -> simpleQuery.append("WHERE category = \"electronics\"")
            WOMEN_CLOTHING -> simpleQuery.append("WHERE category = \"women's clothing\"")
            else -> {}
        }
        return SimpleSQLiteQuery(simpleQuery.toString())
    }
}
