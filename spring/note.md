### Spring 基础

#### 自动装配

1. 模式注解

```
@Repository

@Component

@Service

@Controller

@Configuration

@ComponentScan

```
2. @Enable 模块注解

```

@EnableMvc

@EnableAsync

....

```
实现方式

（1）注解驱动方式
```

@import(class =xxx)
public @interface EnableXXX{

}


@Configuration
public class xxx {

}

```
（2） 接口驱动方式

```

@import(class =xxx)
public @interface EnableXXX{

}


public class xxx extend AdviceModeImportSelector{

}

```
    
3.条件装配


（1）@Profile 配置化


（2）@Conditional 编程方式

```

@Conditional(xxxConditional.class)
public @interface xxx{

}

```

4. 自动装配


#### SpringAppliction

1.SpringApplication 准备阶段
2.SpringApplication 运行阶段

#### MVC

#### WebFlux

#### 外部化配置