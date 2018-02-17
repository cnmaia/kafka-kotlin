package cnmaia.test.kafka

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@SpringBootApplication
open class Application(private val producer: Producer) : CommandLineRunner {
    override fun run(vararg args: String?) {
        producer.produce()
    }
}

fun main(args: Array<String>) {
    SpringApplication.run(Application::class.java, *args)
}