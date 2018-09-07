# live_rates
A simple api service using Scala and Akka that interacts with Redis (in memory database) to give out currency exchange rates.

Default host:port for redis is 127.0.0.1:6379 - Configurable from application.conf file

Redis Client used - https://github.com/debasishg/scala-redis
Redis Client is used in a wrapper called RedisClientWrapper (A facade). The redis client can be changed inside the wrapper without affecting the interaction with other components.






