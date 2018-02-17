package cnmaia.test.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback

@Component
class Producer(private val kafkaTemplate: KafkaTemplate<Long, String>) {

    fun produce() {
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