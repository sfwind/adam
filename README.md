## qyweixin-adapter

## 1. 点评微信企业号是一个集成点评内部微信应用的平台，dper可以通过点评微信企业号使用接入的应用。点评微信企业号提供统一便捷的接口，同时提供用户信息管理，部门信息管理，应用访问权限管理，应用菜单管理等功能。

## 2 接入须知

### 2.1 如需接入点评微信企业号，请先与HR应用技术(es.ba@dianping.com)沟通，确定应用名，应用功能描述，应用的url，应用图标，应用菜单。应用创建成功后，可以获得一个agentid，既是微信企业号中应用的id号。

### 2.2 限于与微信通信必须开通公网访问，测试环境统一使用beta环境。

## 3 接入指南
### 3.1 导入依赖
```

    <dependency>
        <groupId>com.dianping</groupId>
        <artifactId>qyweixin-adapter-api</artifactId>
        <version>0.0.10</version>
    </dependency>


    <bean id="messageService" class="com.dianping.dpsf.spring.ProxyBeanFactory"
          init-method="init">
        <property name="serviceName"
                  value="http://service.dianping.com/ba/es/qyweixin/adapter/MessageService_1.0.0" />
        <property name="iface"
                  value="com.dianping.ba.es.qyweixin.adapter.api.service.MessageService" />
        <property name="serialize" value="hessian" />
        <property name="callMethod" value="sync" />
        <property name="timeout" value="5000" />
    </bean>
```

### 3.2 微信消息类型
文字类消息
事件类消息
图片类消息
声音类消息
新闻类消息
视频类消息
文件类消息

点评微信企业号目前只支持转发文字类信息,新闻类消息和事件类消息，其他消息类型会在后续版本中陆续添加支持

### 3.3 接收文字类信息
当应用方需要接收文字类消息，可以与点评微信企业号技术人员沟通，申请一个新的swallow topic。应用方只需要监听topic的广播，就可以收到用户发送的实时消息。

代码示例：

```

public class QyWeiXinMessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {
        TextMessage textMessage = message.transferContentToBean(TextMessage.class);
        String textMessageContent = textMessage.getContent();
        // do something
    }
}
```

### 3.4 接收事件类信息
事件类消息，发生在点击应用菜单时，一共有两种事件，第一种事件是view事件，此类事件绑定另一个外部url，微信会跳转到一个页面，第二种事件是click事件，此类事件会有一个eventKey的关键值，应用方在消费时可以通过eventKey来判断用户点击的是哪个菜单。同样应用方先要与点评微信企业号技术人员沟通，申请一个新的事件消息swallow topic，可以效仿文字类消息的监听方式。

代码示例：

```
public class QyWeiXinEventMessageConsumer implements MessageListener {

    @Override
    public void onMessage(Message message) {

        EventMessage eventMessage = message.transferContentToBean(EventMessage.class);
        String eventKey = eventMessage.getEventKey();

        if (eventKey.equals(ConfigUtils.getMenuL())) {
            // do something
        }
    }
}
```

### 3.5 发送文字类消息
应用方可以通过调用点评微信企业号提供的pigeon服务，通过应用给用户发送文字类消息。

#### 3.5.1 接口说明
pigeon服务：http://service.dianping.com/ba/es/qyweixin/adapter/MessageService_1.0.0

接口：sendMessage(MessageDto messageDto)

MessageDto属性说明
Touser    非空  发送的目标用户，多人时，用‘|’分隔
Msgtype  非空  固定为text
AgentId   非空  应用id
Safe        可为空  0为非安全消息，1为安全消息。
mediaDto 非空 文字类时，设为子类TextDto
priority 可以为空，默认优先级普通（BATCH_MSG）。建议调用方对消息实时性要求不高时（非互动类消息）可以使用默认的优先级（BATCH_MSG）。

#### 3.5.2 代码示例
```

    private void sendReplyMessage(TextMessage textMessage, String text) {
        MessageDto messageDto = new MessageDto();
        TextDto textDto = new TextDto();
        textDto.setContent(text);
        List<String> users = new ArrayList<String>();
        users.add(textMessage.getFromUserName());
        messageDto.setTouser(users);
        messageDto.setAgentid(textMessage.getAgentID());
        messageDto.setSafe(0);
        messageDto.setMediaDto(textDto);
        messageDto.setPriority(MessageDto.BATCH_MSG);
        try {
            weixinMessagePigeon.sendMessage(messageDto);
        } catch (QyWeixinAdaperException e) {
            LOGGER.error("an exception has occur, error message: " + e.getMessage());
        }
    }
```

### 3.6 通过swallow发送消息

#### 3.6.1 代码示例
```

    public class TextMessageProducer {
        @Autowired
        private Producer producerClient;
        private static final Logger logger = LoggerFactory.getLogger(TextMessageProducer.class);

        public void sendMsg(MessageDto messageDto){
            logger.info("send message");
            if(messageDto !=null){
                try {
                    producerClient.sendMessage(messageDto);
                } catch (SendFailedException e) {
                    logger.error("fail to send message:"+ messageDto, e);
                }
            }
        }
    }
```

#### 3.6.2 配置示例
```

    <!--生产者的工厂类 ProducerFactory -->
    <bean id="producerFactory" class="com.dianping.swallow.producer.impl.ProducerFactoryImpl"
          factory-method="getInstance" />
    <!--生产者 ProducerClient -->
    <bean id="producerClient" factory-bean="producerFactory"
          factory-method="createProducer">
        <constructor-arg>
            <ref bean="destination" />
        </constructor-arg>
        <constructor-arg>
            <ref bean="producerConfig" />
        </constructor-arg>
    </bean>
    <!--消息的目的地 Destination -->
    <bean id="destination" class="com.dianping.swallow.common.message.Destination"
          factory-method="topic">
        <constructor-arg value="qyweixin_message" /> <!-- ${topicName}为消息的Topic，需自定义-->
    </bean>
    <!--生产者的配置类 ProducerConfig -->
    <bean id="producerConfig" class="com.dianping.swallow.producer.ProducerConfig">
        <property name="mode" value="SYNC_MODE" /><!--同步模式(推荐)-->
    </bean>
    <bean id="textMessageProducer" class="com.dianping.ba.es.qyweixin.adapter.mq.TextMessageProducer"/>
```

### 3.7 微信H5应用获取身份信息
访问微信H5应用站点时需要经过我们的adapter，然后adapter会从微信端获取登陆者的身份信息，生成对应身份信息的token，在从adapter跳转至应用时携带在url中。
具体操作如下：
1.在菜单或是需要访问页面获取访问者信息时，将url作如下拼接:
假设应用站点url为www.app.com,则需要将访问的url设置为qywx.dper.com/oauth/oauthCode?url=http%3A%2F%2Fwww.app.com&agentId=1 注意此处的url地址需要进行urlencode,
2.点击链接后，链接会跳转到www.app.com?uuid_token=samplecode&code=donotcare,其中的uuid_token就是获取用户信息的凭证
3.使用uuid_token访问qywx.dper.com/oauth/userId?uuid_token=samplecode即可获得用户的工号，如若没有该用户则会返回notfound