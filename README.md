# MQ-best-practice

主要总结下两种常用消息队列（RabbitMQ、kafka）的最佳实践

## RabbitMQ

RabbitMQ 需要考虑到以下几个方面

- 消息不能丢失（消费者确认、定时任务、Alternate Exchange）
- 消息至少一个消费（）
