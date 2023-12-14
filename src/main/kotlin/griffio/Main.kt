package griffio

import app.cash.sqldelight.db.SqlDriver
import app.cash.sqldelight.driver.jdbc.asJdbcDriver
import griffio.queries.Sample

import org.postgresql.ds.PGSimpleDataSource

private fun getSqlDriver(): SqlDriver {
    val datasource = PGSimpleDataSource()
    datasource.setURL("jdbc:postgresql://localhost:5432/postgres")
    datasource.applicationName = "App Main"
    return datasource.asJdbcDriver()
}

fun stringIdentifier(n: Int) = (1..n).map { ('A'..'Z').random() }.joinToString("")
fun longIdentifier(n: Int) = (1..n).map { (1..10).random() }.joinToString("").toLong()
fun phone() = "${stringIdentifier(3)}-${stringIdentifier(3)}-${stringIdentifier(3)}"
fun postal() = stringIdentifier(6)

fun main() {

    val driver = getSqlDriver()
    val sample = Sample(driver)

}
