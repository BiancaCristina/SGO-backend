package com.github.biancacristina.sgobackend.config

import java.text.ParseException
import com.github.biancacristina.sgobackend.services.DBService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("dev")
class DevConfig {

    @Autowired
    private val dbService: DBService? = null

    @Value("\${spring.jpa.hibernate.ddl-auto}")
    private val strategy: String? = null

    @Bean
    @Throws(ParseException::class)
    fun instantiateDatabase(): Boolean {

        if ("create" != strategy) {
            return false
        }

        dbService!!.instantiateTestDataBase()
        return true
    }
}