package cnmaia.test.kafka

import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.support.SendResult
import org.springframework.stereotype.Component
import org.springframework.util.concurrent.ListenableFuture
import org.springframework.util.concurrent.ListenableFutureCallback
import java.util.*

@Component
class Producer(private val kafkaTemplate: KafkaTemplate<Long, String>) {

    fun produce() {

        while(true) {
            val message = UUID.randomUUID().toString()
            val future: ListenableFuture<SendResult<Long, String>> = kafkaTemplate.send("first", message)

            future.addCallback(object : ListenableFutureCallback<SendResult<Long, String>> {
                override fun onSuccess(result: SendResult<Long, String>?) {
                    println("Sent Message: " + message)
                }

                override fun onFailure(ex: Throwable) {
                    ex.printStackTrace()
                }
            })

            Thread.sleep(1000)
        }
    }
}
