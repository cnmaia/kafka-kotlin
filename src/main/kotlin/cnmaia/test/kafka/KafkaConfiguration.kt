package cnmaia.test.kafka

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.common.serialization.LongDeserializer
import org.apache.kafka.common.serialization.LongSerializer
import org.apache.kafka.common.serialization.StringDeserializer
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.core.*

@Configuration
@EnableKafka
open class KafkaConfiguration {

    @Bean
    open fun kafkaListener(): ConcurrentKafkaListenerContainerFactory<Long, String> {
        val factory = ConcurrentKafkaListenerContainerFactory<Long, String>()
        factory.consumerFactory = consumerFactory()
        factory.setConcurrency(3)
        factory.containerProperties.pollTimeout = 3000

        return factory
    }

    @Bean
    open fun consumerFactory(): ConsumerFactory<Long, String> {
        return DefaultKafkaConsumerFactory(consumerConfigs())
    }

    @Bean
    open fun consumerConfigs(): Map<String, Any> {
        val props = HashMap<String, Any>()

        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        props.put(ConsumerConfig.GROUP_ID_CONFIG, "first-group")
        // This is for when I want an offset that doesnt exist (happen when starts)
        // largest = latest available = latest committed message
        // smallest = earliest available = first committed message alive (ttl)
        props.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")
        // If we dont set this we have to commit the offset ourselves
        props.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "1000")
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, LongDeserializer::class.java)
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer::class.java)
        props.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, true)
        props.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "20000")

        return props
    }

    @Bean
    open fun producerFactory(): ProducerFactory<Long, String> {
        return DefaultKafkaProducerFactory(producerConfigs())
    }

    @Bean
    open fun producerConfigs(): Map<String, Any> {
        val props = HashMap<String, Any>()

        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
        props.put(ProducerConfig.RETRIES_CONFIG, "0")
        props.put(ProducerConfig.ACKS_CONFIG, "1")
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, LongSerializer::class.java)
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer::class.java)

        return props
    }

    @Bean
    open fun template(): KafkaTemplate<Long, String> {
        return KafkaTemplate(producerFactory())
    }

}