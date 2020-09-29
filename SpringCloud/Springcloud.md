## Eureka

Eureka Client周期性（默认30s）发送一次心跳续约

Eureka Server在一定时间（90s）内没有接受到某个服务实例的心跳时，将会注销该实例。

Eureka Client会缓存服务注册表中的信息，减轻Server压力的同时，在所有server都宕机的情况也能在缓存中找到服务并调用。

Eureka通过心跳检查、客户端缓存等机制，提高了系统的灵活性、可伸缩性和可用性。

Eureka Server可以通过运行多个实例并相互注册来实现高可用部署，Eureka Server实例会彼此增量地同步信息，从而保证所有节点的一致性。（节点直接相互注册时eureka默认的）

Eureka的元数据有两种：标准元数据、自定义元数据

自我保护模式：

当Eureka Server节点在短时间内丢失过多的客户端的时候（可能发生了网络分区的故障），那么这个节点会进入自我保护模式，进而保护服务注册表里的信息，不再删除表里的数据。但网络服务恢复后，该节点会自动退出自我保护模式。

eureka.server.enable-self-preservation = false 禁用自我保护模式