# Spring Cloud


Spring Cloud是一个基于Spring Boot实现的微服务架构开发工具。它构建了服务治理(发现注册)、配置中心、消息总线、负载均衡、断路器、数据监控、分布式会话和集群状态管理等功能，为我们提供一整套企业级分布式云应用的完整解决方案。

#### 服务治理
Spring Cloud的核心是服务治理同时也是它最为基础的模块，它主要用来实现各个微服务实例的自动化注册与发现。

*   服务注册：在服务治理框架中，通常都会构建一个注册中心，每个服务单元向注册中心登记自己提供的服务，将主机与端口号、版本号、通信协议等一些附加信息告知注册中心，注册中心按照服务名分类组织服务清单，服务注册中心还需要以心跳的方式去监控清单中的服务是否可用，若不可用需要从服务清单中剔除，达到排除故障服务的效果。
*   服务发现：由于在服务治理框架下运行，服务间的调用不再通过指定具体的实例地址来实现，而是通过向服务名发起请求调用实现。


*   服务治理包含三个核心的角色：服务注册中心、服务提供者和服务消费者，他们相对独立，新的服务要向服务注册中心注册，新的消费者会向服务注册中心索引服务列表。
*  服务注册中心本质上是为了解耦服务提供者和服务消费者。对于任何一个微服务，原则上都应存在或者支持多个提供者，这是由微服务的分布式属性决定的。更进一步，为了支持弹性扩缩容特性，一个微服务的提供者的数量和分布往往是动态变化的，也是无法预先确定的。

##### Eureka
*  在SpringCloud中使用Eureka实现服务的注册和发现，它既包含了服务端组件也包含客户端组件，并且服务端和客户端均采用Java编写，所以Eureka主要适用与通过Java实现的分布式系统，或是与JVM兼容语言构建的系统，但是，由于Eureka服务端的服务治理机制提供了完备的RESTful API，所以他也支持将非Java语言构建的微服务纳入Eureka的服务治理体系中来。

*   Eureka服务端，一般也称作服务注册中心，支持高可用配置。它依托于强一致性提供良好的服务实例可用性，可以应对多种不同的故障场景。

*   Eureka客户端，主要用于服务的注册和发现。客户端服务通过注解和参数配置的方式嵌入在客户端应用的代码中。客户端向注册中心注册自身提供的服务并周期性地发送心跳来更新它的的服务租约。同时，它也向服务端请求当前注册的服务信息并把它们缓存到本地并周期性地刷新服务状态。

##### 1. 服务注册中心

  *   搭建一个服务注册中心就是搭建一个Eureka的服务端，主要需要在工程中添加spring-cloud-starter-eureka-server的依赖，之后在工程的启动类添加
  @EnableEurekaServer的注解，然后在配置文件添加

          1. eureka.client.serviceUrl.defaultZone=<这个服务器的地址>
          2. eureka.client.registerWithEureka=false以及eureka.client.fetchRegistry=false让服务器不注册自己。

##### 2. 服务提供者
*   一个Eureka客户端程序，通过注册到注册中心，然后达到可以被其他Eureka客户端发现以及调用。
编写一个服务提供者需要添加spring-cloud-starter-eureka的依赖，在启动类添加@EnableDiscoveryClient的注解，
然后在配置文件添加以下配置：

         1. eureka.client.serviceUrl.defaultZone，指引服务注册中心的地址
         2. spring.application.name,注册到服务中心的服务名

 这样将一个SpringBoot工程注册成一个Eureka的服务，一个服务可以有多个实例，它开放出接口给
 别的服务调用就成为了服务提供者。     


##### 3. 服务消费者
*    在服务治理中服务的提供者和服务的消费者并没有太大的界定，一般来说一个服务既可以是服务的提供者同时也可以是服务的消费者，要成为服务的消费者首先是一个Eureka的客户端程序，然后注册到注册中心，之后就能够使用服务发现的能力
去发现在服务注册中心中注册的其他服务，要调用服务需要使用Ribbon和Feign，它们使用服务发现的功能去服务注册中心获取服务列表，之后就能以服务名的方式去调用服务，
不然只能使用传统的ip+端口去访问而不能使用服务名，同时Ribbon和Feign实现了客户端的负载均衡。

##### Eureka的高可用配置
编写两个或多个服务注册中心，在配置文件中去掉registerWithEureka和fetchRegistry的配置，然后在eureka.client.serviceUrl.defaultZone
配置其他服务注册中心的地址多个以逗号隔开，这样就完成高可用配置，让多个服务注册中心同步服务清单。

## 一些组件

##### 1. Spring-boot-actuator组件

*   spring-boot-actuator模块是SpringBoot提供的一个监控和管理生产环境的模块，可以使用http、jmx、ssh、telnet等管理和监控应用。审计（Auditing）、 健康（health）、数据采集（metrics gathering）会自动加入到应用里面。Actuator可以有效地省去或大大减少监控系统在采集应用指标时的开发量。
加入actuator模块后会自己加入许多的端点，如果根据端点的作用来说，我们可以原生端点分为三大类：

- 应用配置类：获取应用程序中加载的应用配置、环境变量、自动化配置报告等与Spring Boot应用密切相关的配置类信息。

- 度量指标类：获取应用程序运行过程中用于监控的度量指标，比如：内存信息、线程池信息、HTTP请求统计等。

- 操作控制类：提供了对应用的关闭等操作类功能。

端点如下表所示：

ID | 描述 | 敏感（Sensitive）
-----|-----|-----
autoconfig  | 显示一个auto-configuration的报告，该报告展示所有auto-configuration候选者及它们被应用或未被应用的原因           |true
beans       | 显示一个应用中所有Spring Beans的完整列表          |true
configprops	| 显示一个所有@ConfigurationProperties的整理列表    |true
dump        | 执行一个线程转储|true
env         | 暴露来自Spring ConfigurableEnvironment的属性     |true
health      | 展示应用的健康信息（当使用一个未认证连接访问时显示一个简单的’status’，使用认证连接访问则显示全部信息详情）                 |false
info        | 显示任意的应用信息                               |false
metrics     | 展示当前应用的’指标’信息                         |true
mappings    | 显示一个所有@RequestMapping路径的整理列表        |true
shutdown    | 允许应用以优雅的方式关闭（默认情况下不启用）       |true
trace       | 显示trace信息（默认为最新的一些HTTP请求）         |true

默认情况下actuator模块会对敏感的端点访问进行权限验证，除了/health和/info端点能访问，其他的大多数会出现401，可以通过
以下几个方式解决：

        1. 将management.security.enabled设置为false来关闭验证
        2. 开启Http验证来获取权限
        3. 设置ContextPath

如果对一些SpringBoot的Starters POMs中还没有封装的产品进行开发，没有对应的健康检测器的时候可以自己实现HealthIndicator接口，实现其中的health()方法来对相应的产品进行检测。



##### 2. Ribbon组件

*   Spring Cloud Ribbon是一个基于HTTP和TCP的客户端负载均衡工具，它基于Netflix Ribbon实现。通过Spring Cloud的封装，可以让我们轻松地将面向服务的REST模板请求自动转换成客户端负载均衡的服务调用。它不像服务注册中心、配置中心、API网关那样独立部署，但是它几乎存在于每个微服务的基础设施中。

 使用@LoadBalanced修饰过的RestTemplate去开启客户端的负载均衡，使用restTemplate对服务的访问，使用的ribbon的步骤如下：

        1. 添加spring-cloud-starter-ribbon的依赖
        2. 在启动类添加使用@LoadBalanced代码如下:
          @Bean
        	@LoadBalanced
        	public RestTemplate restTemplate(){
        		return new RestTemplate();
        	}
        3. 在需要使用服务调用的地方使用restTemplate去请求服务


  Ribbon使用的是客户端负载均衡，而负载均衡一般来说可以分为硬件负载均衡和软件负载均衡

*  硬件负载均衡

 通过服务器和外部网络间安装负载均衡的设备，称为"负载均衡器"，硬件的负载均衡在功能想、性能上往往高于软负载均衡，不过价格昂贵，例：F5负载均衡器。能够通过智能交换机来实现负载，负载的能力与系统、应用无关，主要是通过网络层来判断，比如某时候系统处理能力已经不行了，但是可以通过网络来进行分配，成本高，除设备价格高昂，而且配置冗余．很难想象后面服务器做一个集群，但最关键的负载均衡设备却是单点配置；无法有效掌握服务器及应用状态。

*  软件负载均衡

  软件负载均衡的实现方式有两种，分别是服务端的负载均衡和客户端的负载均衡

  *   服务端负载均衡：当浏览器向后台发出请求的时候，会首先向反向代理服务器发送请求，反向代理服务器会根据客户端部署的ip：port映射表以及负载均衡策略，来决定向哪台服务器发送请求，一般会使用到nginx反向代理技术。

  *   客户端负载均衡：当浏览器向后台发出请求的时候，客户端会向服务注册器(例如：Eureka Server)，拉取注册到服务器的可用服务信息，然后根据负载均衡策略，直接命中哪台服务器发送请求。这整个过程都是在客户端完成的，并不需要反向代理服务器的参与。

  Ribbon主要功能是提供客户端的软件负载均衡算法，将Netflix的中间层服务连接在一起。Ribbon客户端组件提供一系列完善的配置项如连接超时，重试等。简单的说，就是在配置文件中列出Load Balancer后面所有的机器，Ribbon会自动的帮助你基于某种规则（如简单轮询，随即连接等）去连接这些机器。Ribbon提供的主要负载均衡策略有：

        1. RandomRule随机策略
        2. RoundRobinRule线性轮询策略
        3. RetryRule重试策略
        4. WeightedResponseTime加权响应时间负载均衡策略
        5. ZoneAware区域感知轮询负载均衡策略

##### 3. Hystrix组件

*  雪崩效应

  在微服务架构中通常会有多个服务层调用，基础服务的故障可能会导致级联故障，进而造成整个系统不可用的情况，这种现象被称为服务雪崩效应。服务雪崩效应是一种因“服务提供者”的不可用导致“服务消费者”的不可用,并将不可用逐渐放大的过程。

*    为了应对这些问题，Spring Cloud Hystrix 实现了断路器、线程隔离等一系列服务保护功能，它是基于Netflix的开源框架Hystrix实现的，该框架的目标在于通过控制那些访问远程系统、服务和第三方库的节点，从而对延迟和故障提供更强大的容错能力。Hystrix具备了服务降级、服务熔断、线程和信号隔离、请求缓存、请求合并以及服务监控等功能。
添加Hystrix的支持需要以下步骤：

         1. 在pom.xml处加上spring-cloud-starter-hystrix的依赖，在启动类处加上@EnableCircuitBreaker开启断路器。
         2. 在需要使用断路器的服务接口上添加@HystrixCommand注解，填写注解fallbackMethod的值，这个值是一个方法的名字，
          这样实现了一个服务降级的方法，在发生断路的时候，断路器就会将服务降级去调用这个方法。

 断路器主要使用了命令模式去实现。

 ###### 异常获取

  使用Hystrix组件时发生异常会被作为触发服务降级的条件去执行服务降级而不会抛出异常，可以@HystrixCommand注解上添加ignoreException去让指定异常不触发服务降级而抛出异常。也可以在服务降级方法上添加Trowable类型的参数，这样就能在服务降级方法中处理异常。
   
 ###### 度量监控
  HystrixCommand和HystrixObservableCommand实例在执行过程中会记录请求命令的许多度量指标，这些指标信息会以“滚动事件窗”与“桶”的方式进行汇总，并在内存中驻留一段时间，以供内部或外部进行查询使用。Spring Cloud提供了Hystrix Dashboard组件对这些指标信息进行实时的监控。通过Hystrix Dashboard反馈的实时信息，可以帮助我们发现系统中存在的问题，从而及时地采取应对措施。Hystrix Dashboard只能实时监控一个服务的指标信息所以Spring Cloud提供了Turbine组件进行集群监控，通过Turbine来汇集监控信息，并将聚合后的信息提供给Hystrix Dashboard来集中展示和监控。



##### 4. Feign组件

*  Feign是一个声明式的伪Http客户端，它使得写Http客户端变得更简单。使用Feign，只需要创建一个接口并注解。它具有可插拔的注解特性，可使用Feign 注解和JAX-RS注解。Feign支持可插拔的编码器和解码器。Feign默认集成了Ribbon，并和Eureka结合，默认实现了负载均衡的效果。
*  要使用Feign组件需要添加spring-cloud-starter-feign的依赖，在启动类处添加@EnableFeignClients的注解来启动Feign，使用方法是编写一个接口然后为接口添加@FeignClient注解，填写注解的value属性，
这个属性指代要调用服务的服务名，为接口添加对应要调用服务的接口的方法，这样就可以调用目标服务，在Feign上要使用hystrix需要：

          1. 配置feign.hystrix.enabled=true  这个配置来开启hystrix
          2. 编写一个类实现要使用hystrix的接口，这个接口已经用@FeignClient注解，实现接口的方法来
          作为发现服务降级的调用方法。
          3. 在接口的@FeignClient上填写fallback属性的值，这个是实现这个接口的类
这样就启动了hystrix.

##### 5. zuul组件
*    Zuul 是Netflix 提供的一个开源组件,致力于在云平台上提供动态路由，监控，弹性，安全等边缘服务的框架，Zuul是Netflix出品的一个基于JVM路由和服务端的负载均衡器。
在pom.xml处添加依赖，在启动类添加@EnableZuulProxy注解，以及将这个服务注册到服务注册中心，
zuul组件会默认查找服务列表然后将服务名映射成路由地址，之后在相应的路由地址下访问服务就行，
想自定义路由的名字也可以在配置文件里配置格式如下：
  ```
        zuul.routes.<任意名字>.path=/<routeName>/**
        zuul.routes.<任意名字>.service-id=<service-id>
  ```
这样就可以将服务配置到自定义的路由地址上

*    过滤器，zuul主要功能是路由转发和过滤器，所以过滤器是Zuul的核心组件，在zuul中过滤器有四种类型：

1. PRE：这种过滤器在请求被路由之前调用。我们可利用这种过滤器实现身份验证、在集群中选择请求的微服务、记录调试信息等。
2. ROUTING：这种过滤器将请求路由到微服务。这种过滤器用于构建发送给微服务的请求，并使用Apache HttpClient或Netfilx Ribbon请求微服务。
3. POST：这种过滤器在路由到微服务以后执行。这种过滤器可用来为响应添加标准的HTTP Header、收集统计信息和指标、将响应从微服务发送给客户端等。
4. ERROR：在其他阶段发生错误时执行该过滤器。

这四种过滤器类型也对应着请求的生命周期，Zuul本身实现了许多默认的过滤器不过也可以实现自己的过滤器，要实现自己的过滤器就要实现ZuulFilter这个接口，然后实现

         filterType()   返回过滤器的类型
         filterOrder()  返回过滤器的执行顺序，越小优先级越高
         shouldFilter() 是否需要执行过滤器
         run()          过滤器的具体逻辑

  四个方法。

   ###### 过滤器中处理异常

  在过滤器中象平常java代码中里抛出异常是无法被接收的，想要在过滤器中处理异常有异常有以下两种方法:

         1. 获取RequestContext，然后在try-catch代码中处理异常，在发生异常后在catch块中，调用RequestContext的
         set方法，设置error.status_code和error.exception的值，之后异常就可以被zuul内置的SendErrorFilter过滤器处理
         2. 自定义error类型的过滤器就可以处理异常。


 ##### 6.Config组件

 *    在分布式系统中，由于服务数量巨多，为了方便服务配置文件统一管理，实时更新，所以需要分布式配置中心组件。在Spring Cloud中，有分布式配置中心组件spring cloud config ，它支持配置服务放在配置服务的内存中（即本地），也支持放在远程Git仓库中。在spring cloud config 组件中，分两个角色，一是config server，二是config client。
        *    Config服务端

        编写一个Config服务端首先要在pom.xml中添加spring-cloud-config-server的依赖，然后在启动类添加@EnableConfigServer的注解，再之后就需要在
        application.yml或者application.properties中添加外部配置的地址，外部配置可以是以下几个：

                 1. GIT仓库
                 2. SVN仓库
                 3. 本地仓库

        就这样可以启动一个Config服务端。
        *    Config客户端

         编写一个Config客户端一般只需要在pom.xml里面添加spring-cloud-starter-config的依赖，然后添加一个
         bootstrap.yml或者bootstrap.properties的配置文件，然后在配置文件里添加指向Config-Server的配置就行了

              ###### 配置动态刷新
               客户端并不能主动感知到配置的变化，从而主动去获取新的配置，所以需要客户端配置动态刷新，首先为客户端项目添加actuator组件的依赖，然后为需要动态刷新配置的类添加@RefreshScope注解就可以完成
               客户端配置的动态刷新，当配置需要动态刷新时用POST的方式访问项目的/refresh端点就可以刷新配置。

##### 7. Bus组件
*    在微服务架构的系统中，我们通常会使用轻量级的消息代理来构建一个公用的消息主题让系统中所有的微服务实例连接起来，由于该主题中产生的消息会被所有实例监听和消费，所以我们称它为消息总线。在SpringCloud中用来搭建消息总线的组件为Spring Cloud Bus。Bus组件将分布式的节点用轻量的消息代理连接起来。它可以用于广播配置文件的更改或者服务之间的通讯，也可以用于监控。Bus组件目前自带支持的消息中间件是RabbitMQ和Kafka.
      ###### RabbitMQ
      *    在RabbitMQ上新建一个<自定义名称>的用户，设置permission权限，为项目添加spring-cloud-starter-bus-amqp的依赖，
      在配置文件中添加RabbitMQ的相应配置：地址，端口，用户，密码就可以实现使用RabbitMQ作为消息总线。
      ###### Kafka
      *    为项目添加spring-cloud-starter-bus-kafka的依赖就可以使用Kafka作为消息总线，使用一个名为springcloudBus的Topic

      Bus组件是基于spring cloud Stream开发，同时使用了spring的事件驱动模型，要想实现对其他消息中间件的支持，需要使用Stream组件
      实现一份binder.

##### 8. Stream组件    
*    Spring Cloud Stream是一个用来构建消息驱动的微服务应用程序的框架。Spring Cloud Stream基于Spring Boot建立独立的生产级Spring应用程序，并使用Spring Integration提供与消息代理的连接以实现消息事件驱动。Spring Cloud Stream为一些消息中间件产品提供了个性化的自动化配置实现，并且引入了发布-订阅、消费组以及分区等核心概念。

  ##### 发布-订阅
   Spring Cloud Stream 中的消息通信方式遵循了发布-订阅模式，当一条消息被发布到消息中间件之后，它会通过共享的Topic主题就行广播，消息消费者在订阅的主题中收到它并触发自身的业务逻辑处理。
  ##### 消费组

   发布-订阅模型可以很容易地通过共享topics连接应用程序，但创建一个应用多实例时，一个消息会被消费多次。对于这个问题SpringCloudStream引入的消费组的概念，消费组将应用程序的不同实例被放置在一个竞争的消费者关系中，其中只有一个实例将处理一个给定的消息。

   SpringCloudStream利用消费者组定义这种行为，每个消费者通过spring.cloud.stream.bindings.input.group指定一个组名称。所有订阅指定topics的组都会收到发布数据的一份副本，但是每一个组内只有一个成员会收到该消息。默认情况下，当一个组没有指定时，SpringCloudStream将分配给一个匿名的、独立的只有一个成员的消费组，该组与所有其他组都处于一个发布－订阅关系中。

  ##### 分区
   引入消费组概念后虽然能确保每个消息都只被消费一次，但所消费的实例每次并不是相同，在某些需要将具有相同特征的消息设置每次都给同一个消息实例处理的情况下就会有问题，所以Spring Cloud Stream引入了分区的概念。
   Spring Cloud Stream对给定应用的多个实例之间分隔数据予以支持。在分隔方案中，物理交流媒介被视为分隔成了多个片。一个或者多个生产者应用实例给多个消费者应用实例发送消息并确保相同特征的数据被同一消费者实例处理。



*  Stream组件默认提供了RabbitMQ和Kafka这两个消息中间件的支持。一个Stream的项目一般有两个部分：消息接收者和消息产生者
    ###### 消息接收者
      编写一个消息接收者首先需要添加依赖spring-cloud-starter-stream-rabbit或者spring-cloud-starter-stream-kafka,去支持相应的消息中间件
      如果是RabbitMQ需要在配置文件中添加RabbitMQ的相应配置：地址，端口，用户，密码，而Kafka则不用.之后就行以下步骤：

            1. 编写一个消息接收类，为类添加@EnableBinding注解，填写注解的值，值为要绑定消息通道的类
            2. 编写一个方法，为方法添加@StreamListener注解，填写注解的值，值为要监控的的消息通道。

      这样就实现了一个消息接收者，当绑定的消息通道上有消息来的时候就使用编写的处理方法去处理消息
      当需要为接收的消息产生一个回复的时候，就可以为方法添加@SendTo这个注解，注解的值为要返回消息的消息通道返回的消息为方法的返回值。

    ###### 消息产生者

      编写一个消息产生者同意需要添加依赖spring-cloud-starter-stream-rabbit或者spring-cloud-starter-stream-kafka,去支持相应的消息中间件
      如果是RabbitMQ需要在配置文件中添加RabbitMQ的相应配置：地址，端口，用户，密码，而Kafka则不用。
      编写一个消息产生类的方法如下：

          1. 为类添加@EnableBinding注解，填写要绑定消息通道的类
          2. 编写一个访问回值为MessageSource<T> 的消息产生方法
          3. 为方法添加@Bean和@InboundChannelAdapter和注解，填写@InboundChannelAdapter的值，值的内容为消息的传输通道
      这样就编写了一个基本的消息产生者。

##### 9. Sleuth组件    
*    Sleuth是SpringCloud的一个服务跟踪组件，能够跟踪全链路的服务调用，监控服务的响应。
为服务添加Sleuth的支持需要在这个服务以及它调用的服务上添加spring-cloud-starter-sleuth的依赖，之后就可以在输出的Log上看到
TraceID和SpanID，以及标识是否给收集的值。
添加了Sleuth组件的服务跟踪信息都是分散在各个服务的，所以需要收集。可以与Logstash和Zipkin整合就行收集
      ###### 与Zipkin整合
      要与Zipkin整合需要编写一个Zipkin服务器作为收集服务器。
      1. 编写一个Zipkin服务器的步骤如下：
          建立一个SpringBoot工程，为其添加：
          ```   
               <dependency>
                    <groupId>io.zipkin.java</groupId>
                    <artifactId>zipkin-server</artifactId>
               </dependency>
               <dependency>
                    <groupId>io.zipkin.java</groupId>
                    <artifactId>zipkin-autoconfigure-ui</artifactId>
               </dependency>
          ```
          这两个注解，其中zipkin-server是zipkin服务器，zipkin-autoconfigure-ui是可视化web管理，
          在启动类添加@EnableZipkinServer，这样就完成了Zipkin服务器。
      2. 为需要收集的服务添加spring-cloud-sleuth-zipkin依赖，同时在配置文件添加spring.zikpin.base-url的配置去指向ZipKin服务器的地址，如果是在本地而且端口号是9411的话就可以不用配置，因为这是他的默认值。

     这样就完成了与Zipkin的整合，服务的跟踪数据就会自动上传的到ZipKin服务器。

Sleuth的收集是抽样收集，默认收集比例是0.1，可通过spring.sleuth.sampler.percentage这个配置去改变比例也可以通过默认的AlwaysSampler去设置总是收集或者实现Sampler去自定义自己的收集规则。Zikpin的收集默认是使用Http的方式就行，可以通过以下修改成通过休息中间件收集：

          1. 在客户端修改依赖spring-cloud-starter-sleuth-stream和添加spring-cloud-starter-stream-rabbit
          或spring-cloud-starter-stream-kafka依赖
          2. 在服务端修改依赖为spring-cloud-starter-sleuth-zipkin-strem和添加spring-cloud-starter-stream-rabbit
          或spring-cloud-starter-stream-kafka依赖
          3. 进行相关消息中间件的配置

这样就可以将收集方式从Http收集修改成消息中间件收集  
