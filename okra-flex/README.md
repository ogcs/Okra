# Okra - Flex

模块用于解决Flex客户端和Java服务器通信的编解码.

依赖于BlazeDs类库. Maven如下:

```xml
<dependency>
    <groupId>org.apache.flex.blazeds</groupId>
    <artifactId>flex-messaging-core</artifactId>
    <version>4.7.2</version>
</dependency>
<dependency>
    <groupId>org.apache.flex.blazeds</groupId>
    <artifactId>flex-messaging-common</artifactId>
    <version>4.7.2</version>
</dependency>
```

Okra提供简单的AMF3协议编解码为Java Object的编解码器工具.
