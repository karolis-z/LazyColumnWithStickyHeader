package com.myapplications.testconcatadapter

import java.time.LocalDate

object DataSource {

    val listRecentCurrencies = listOf(
        Currency("Lihuanian Litas", LocalDate.now()),
        Currency("Latvian Latas", LocalDate.now().minusDays(1)),
        Currency("US Dollar", LocalDate.now().minusDays(3)),
        Currency("Guernsey pound", LocalDate.now().minusDays(5)),
        Currency("Botswana pula", LocalDate.now().minusDays(2)),

    )

    val listRestCurrencies = listOf(
        Currency("Indian rupee", null),
        Currency("Belarusian ruble", null),
        Currency("Bolivian boliviano", null),
        Currency("Surinamese dollar", null),
        Currency("Georgian lari", null),
        Currency("Zimbabwean dollar", null),
        Currency("Euro", null),
        Currency("Barbadian dollar", null),
        Currency("Serbian dinar", null),
        Currency("Sudanese pound", null),
        Currency("Vietnamese đồng", null),
        Currency("Indian rupee", null),
        Currency("Belarusian ruble", null),
        Currency("Bolivian boliviano", null),
        Currency("Surinamese dollar", null),
        Currency("Georgian lari", null),
        Currency("Zimbabwean dollar", null),
        Currency("Euro", null),
        Currency("Barbadian dollar", null),
        Currency("Serbian dinar", null),
        Currency("Sudanese pound", null),
        Currency("Vietnamese đồng", null),
        Currency("Indian rupee", null),
        Currency("Belarusian ruble", null),
        Currency("Bolivian boliviano", null),
        Currency("Surinamese dollar", null),
        Currency("Georgian lari", null),
        Currency("Zimbabwean dollar", null),
        Currency("Euro", null),
        Currency("Barbadian dollar", null),
        Currency("Serbian dinar", null),
        Currency("Sudanese pound", null),
        Currency("Vietnamese đồng", null),
        Currency("Indian rupee", null),
        Currency("Belarusian ruble", null),
        Currency("Bolivian boliviano", null),
        Currency("Surinamese dollar", null),
        Currency("Georgian lari", null),
        Currency("Zimbabwean dollar", null),
        Currency("Euro", null),
        Currency("Barbadian dollar", null),
        Currency("Serbian dinar", null),
        Currency("Sudanese pound", null),
        Currency("Vietnamese đồng", null),
    )

}