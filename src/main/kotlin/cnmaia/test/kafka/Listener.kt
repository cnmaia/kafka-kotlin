package cnmaia.test.kafka

import org.springframework.kafka.annotation.KafkaListener
import java.util.concurrent.CountDownLatch

class Listener {
    private val latch : CountDownLatch = CountDownLatch(1)

    @KafkaListener(id = "first-listener", topics = ["fist"], groupId = "first-group")
    fun list(foo: String) {
        println(foo)
        this.latch.countDown()
    }
}