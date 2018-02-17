package cnmaia.test.kafka

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Component
import java.util.concurrent.CountDownLatch

@Component
class Consumer {
    private val latch : CountDownLatch = CountDownLatch(1)

    @KafkaListener(id = "first-listener", topics = ["first"], groupId = "first-group")
    fun list(message: String) {
        println("Received Message: "+ message)
        this.latch.countDown()
    }
}