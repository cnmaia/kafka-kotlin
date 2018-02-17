package cnmaia.test.kafka

import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback

@SpringBootApplication
open class Producer(private val kafkaTemplate: KafkaTemplate<Long, String>) : CommandLineRunner {

    fun main(args: Array<String>) {
        SpringApplication.run(Producer::class.java, *args)
    }

    override fun run(vararg args: String?) {

        val future: ListenableFuture<SendResult<Long, String>> = kafkaTemplate.send("first", "test")

        future.addCallback(object : ListenableFutureCallback<SendResult<Long, String>> {
            override fun onSuccess(result: SendResult<Long, String>?) {
                println("Funfou")
            }

            override fun onFailure(ex: Throwable?) {
                println("Não funfou")
                ex?.printStackTrace()
            }

        })


    }
}