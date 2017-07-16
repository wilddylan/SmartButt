# SmartButt
Awesome chat bot

项目环境：JDK1.8，Maven3.39，IDEA  
项目仓库：[Github地址](https://github.com/WildDylan/SmartButt.git)
目前数据库表结构：
![表结构](http://upload-images.jianshu.io/upload_images/144590-2509f1d2ae9bcd9d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

`t_corpus_base`：基础预料表，存储所有的会话，目前为创建普通索引，完整匹配（`select * from t_corpus_base  where content = '你好' limit 1`）。在后续文章中会修改为`%`匹配，到时候再做全文检索。

|base|类型|注释|索引|
|:-------------|:-------------|:-----|:-----|
|id|int|id|PRIMIRY|
|content|varchar(300)|内容|varchar(150)|

`t_corpus_tree`：对应表，`qid`为会话开始ID，`aid`为该句话对应的回答或者下一个用户可能的问题，后续会做一些表结构的调整。暂时够基础功能使用。

| tree |类型|注释|索引|
|:-------------|:-------------|:-----|:-----|
|id|int|id|PRIMIRY|
|qid|int|会话开始ID|int|
|aid|int|会话下一个ID|int|

`t_corpus_butt`：在`tree`中没有查到的内容，暂时放到这里，用于接下来的学习。表结构后续会发生大的变化，当前仅做存储未查询到的会话。

| butt |类型|注释|索引|
|:-------------|:-------------|:-----|:-----|
|id|int|id|PRIMIRY|
|qid|int|会话开始ID|int|

> 表设计请遵循单一功能原则

maven 依赖库与模块划分（够支持当前所需，后续有需求再增加）：
```xml
    <modules>
        <module>model</module>
        <module>dao</module>
        <module>service</module>
        <module>common</module>
        <module>web</module>
    </modules>

    <properties>
        <spring.version>4.3.5.RELEASE</spring.version>
        <mybatis.version>3.4.1</mybatis.version>
        <mybatis-spring.version>1.3.1</mybatis-spring.version>
        <mysql.version>6.0.6</mysql.version>
    </properties>

    <dependencies>
        <!-- Spring包 -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-core</artifactId>
            <version>${spring.version}</version>
            <exclusions>
                <exclusion>
                    <groupId>commons-logging</groupId>
                    <artifactId>commons-logging</artifactId>
                </exclusion>
            </exclusions>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-web</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-webmvc</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-context-support</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-tx</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-aspects</artifactId>
            <version>${spring.version}</version>
        </dependency>
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <!-- redis cache related.....start -->
        <dependency>
            <groupId>org.springframework.data</groupId>
            <artifactId>spring-data-redis</artifactId>
            <version>1.6.0.RELEASE</version>
        </dependency>
        <dependency>
            <groupId>redis.clients</groupId>
            <artifactId>jedis</artifactId>
            <version>2.7.3</version>
        </dependency>
        <!-- redis cache related.....end -->

        <!-- mybatis驱动包 -->
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis</artifactId>
            <version>${mybatis.version}</version>
        </dependency>
        <dependency>
            <groupId>org.mybatis</groupId>
            <artifactId>mybatis-spring</artifactId>
            <version>${mybatis-spring.version}</version>
        </dependency>

        <!-- mysql驱动包 -->
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>

        <!-- dbcp数据源 -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>druid</artifactId>
            <version>1.0.29</version>
        </dependency>

        <!-- junit测试包 -->
        <dependency>
            <groupId>junit</groupId>
            <artifactId>junit</artifactId>
            <version>4.11</version>
            <scope>test</scope>
        </dependency>

        <!-- json数据 -->
        <dependency>
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-mapper-asl</artifactId>
            <version>1.9.13</version>
        </dependency>
        <dependency>
            <groupId>org.codehaus.jackson</groupId>
            <artifactId>jackson-core-asl</artifactId>
            <version>1.9.13</version>
        </dependency>

        <!-- https://mvnrepository.com/artifact/com.alibaba/fastjson -->
        <dependency>
            <groupId>com.alibaba</groupId>
            <artifactId>fastjson</artifactId>
            <version>1.2.34</version>
        </dependency>

        <!-- log4j日志包 -->
        <dependency>
            <groupId>org.slf4j</groupId>
            <artifactId>jcl-over-slf4j</artifactId>
            <version>1.7.20</version>
        </dependency>
        <dependency>
            <groupId>ch.qos.logback</groupId>
            <artifactId>logback-classic</artifactId>
            <version>1.1.7</version>
        </dependency>

        <!-- jstl 标签库 -->
        <dependency>
            <groupId>jstl</groupId>
            <artifactId>jstl</artifactId>
            <version>1.2</version>
        </dependency>
        <dependency>
            <groupId>taglibs</groupId>
            <artifactId>standard</artifactId>
            <version>1.1.2</version>
        </dependency>

        <!--分词-->
        <dependency>
            <groupId>edu.stanford.nlp</groupId>
            <artifactId>stanford-corenlp</artifactId>
            <version>3.8.0</version>
        </dependency>
        <dependency>
            <groupId>edu.stanford.nlp</groupId>
            <artifactId>stanford-corenlp</artifactId>
            <version>3.8.0</version>
            <classifier>models</classifier>
        </dependency>
        <dependency>
            <groupId>edu.stanford.nlp</groupId>
            <artifactId>stanford-corenlp</artifactId>
            <version>3.8.0</version>
            <classifier>models-chinese</classifier>
        </dependency>
    </dependencies>

    <build>
        <finalName>smart</finalName>
        <plugins>
            <plugin>
                <groupId>org.apache.maven.plugins</groupId>
                <artifactId>maven-compiler-plugin</artifactId>
                <version>3.3</version>
                <configuration>
                    <source>1.8</source>
                    <target>1.8</target>
                </configuration>
            </plugin>
            <plugin>
                <groupId>org.eclipse.jetty</groupId>
                <artifactId>jetty-maven-plugin</artifactId>
                <version>9.2.1.v20140609</version>
                <configuration>
                    <httpConnector>
                        <port>9441</port>
                    </httpConnector>
                    <scanIntervalSeconds>2</scanIntervalSeconds>
                </configuration>
            </plugin>
        </plugins>
    </build>
```

jdbc.properties（注意URL后边的参数）: 
```
jdbc.driver=com.mysql.jdbc.Driver
jdbc.url=jdbc:mysql://localhost:3306/smart-butt?useUnicode=true&characterEncoding=UTF-8&zeroDateTimeBehavior=convertToNull&allowMultiQueries=true&useSSL=false
jdbc.username=
jdbc.password=
```
#### Maven 基于idea的多模块项目搭建

有太多的文章都是授之鱼而非渔。准备 IDEA，MAVEN 和 JDK的环境
1. [JDK](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)下载安装与配置，[MAVEN](https://maven.apache.org/download.cgi)下载安装与配置，[IDEA](https://www.jetbrains.com/idea/download/#section=mac)下载与[破解](http://idea.qinxi1992.cn)
2. 打开IDEA，新建maven项目
![img_1.png](http://upload-images.jianshu.io/upload_images/144590-d7176ff7ee3c3113.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![img_2.png](http://upload-images.jianshu.io/upload_images/144590-f65c1d602638af8d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![img_3.png](http://upload-images.jianshu.io/upload_images/144590-b71d75bb079e55a2.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![img_4.png](http://upload-images.jianshu.io/upload_images/144590-348c8b48afb3aa7f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

下面开始新建模块：

![img_5.png](http://upload-images.jianshu.io/upload_images/144590-ba34f48a7f2283df.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![img_6.png](http://upload-images.jianshu.io/upload_images/144590-7f5b4a0473e1d58f.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![img_7.png](http://upload-images.jianshu.io/upload_images/144590-309566f76692a750.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![img_8.png](http://upload-images.jianshu.io/upload_images/144590-0e9aee4389bf62a0.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
这里做第一个示例，接下来同样的步骤创建完 service, model, dao（注意不要创建web）。此时的项目结构是：
![img_9.png](http://upload-images.jianshu.io/upload_images/144590-26572dae1f89a2cb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
web 有些特殊，是这样创建的：
![img_10.png](http://upload-images.jianshu.io/upload_images/144590-1fc5d97de3b9ffae.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
一直Next到底就好了。

---

接下来我们开始配置模块的依赖关系：
```
web：common, service, model
service: common, model, dao
dao: model
```
Mac 下使用 `command + ;` 打开项目架构先进行dao 的依赖配置

![img_11.png](http://upload-images.jianshu.io/upload_images/144590-5b66da4de3e074bb.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
![img_12.png](http://upload-images.jianshu.io/upload_images/144590-cc0a18a5bf0a169d.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

接下来自己按照上边所说的依赖关系挨个进行设置，应用，确定即可。

但是这并不是结束了，因为我们是maven的多模块项目，所以还应当按照我们配置的依赖在每个模块的pom中配置依赖的模块（我这里使用web模块为示例）：
![img_15.png](http://upload-images.jianshu.io/upload_images/144590-142109fe0c047fa1.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
其余的模块大家自己配置。

---

接下来引入我们所需要的库，为了方便，我们引入到最外层的Pom文件中：

![img_13.png](http://upload-images.jianshu.io/upload_images/144590-2508ede6c52d4fc9.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

将文章开头的需要的库包括插件粘贴进去即可，maven将会自动导入，这个地方如果之前没有下载过可能会很慢。为了方便我们管理，我们打开右侧的maven选项，如果没有，点击左下角按钮打开：

![img_14.png](http://upload-images.jianshu.io/upload_images/144590-7e79feeda1213c3c.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

这里因为我们是多模块项目，所以在修改了依赖中的内容之后，一定要在最外层进行`clean`与`install`（或者 compile），保证全部的模块都重新的编译。

![img_16.png](http://upload-images.jianshu.io/upload_images/144590-d26e68f2d8a2d1cf.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

接下来，我们去web模块中配置spring，servlet，jdbc，log等内容

![img_17.png](http://upload-images.jianshu.io/upload_images/144590-93dd410f33c8bdc7.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
我们发现没有java文件夹，所以直接右键创建一个，然后在项目结构中将其指定为源码类型的文件夹。
![img_18.png](http://upload-images.jianshu.io/upload_images/144590-61707d076cc50953.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

接下来就是里边的spring等配置了，这些大家直接去仓库看吧，基本都相同。至此，我们大致的将语料库的架子搭建了起来。

把传统的一问一答的思路转变为无问答。聊天即为会话，我们认为所有的上下文都是有可能关联的，所以用概率学来处理问题。下面来看一个简单的处理流程：
![img_1.png](http://upload-images.jianshu.io/upload_images/144590-5550daf5fa2b9b18.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

基础库会存储所有的用户发来的内容（也会包含一部分最初我们基础的对话内容）
Tree 库中的意思是 会话ID 对应 下一条可能的数据，相同的数据可是可以的。通过随机去处理其实简单的多。

当有新的用户与机器聊天的时候，首先去 基础语料库中匹配，表中的内容大致是这样的：
![img_2.png](http://upload-images.jianshu.io/upload_images/144590-186e586038113693.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

每一行都是一行内容，在Tree表中，存储的则是对应关系：

![img_3.png](http://upload-images.jianshu.io/upload_images/144590-ccdb857e528a8c77.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

qid：会话
aid：回复的内容

这些id全部是base中每一条内容的id。举例：机器人现在库中是空的，还没有学会 `你好` 该怎么回答，那么过程就是：

表全部为空，示例1

用户1：你好 
1. 数据库中没有匹配到任何内容
2. 添加到 base 会话表中
3. 添加到 butt 未知表中
4. 由于表当前全为空，所以随机回复也是刚插入的 `你好` 这条数据

---

表全部为空，示例2

用户2：你怎么这么傻
1. 数据库中没有匹配到任何内容
2. 添加到 base 会话表中
3. 添加到 butt 未知表中
4. 由于表当前全为空，所以随机回复也是刚插入的 `你怎么这么傻` 这条数据

--- 

假设，用户在 示例1 之后，问了问题，`你多大年纪了`：
（机器在不知情的状况下，认为，在 说`你好`之后会说`你多大年纪了`）
1. base 数据库中没有匹配到这个问题，插入 base 会话表中
2. 添加到 tree中，你好 对应 你多大年纪了
2. 添加到butt 未知表中
3. 由于未知表中存在了 `你好`，`你多大年纪了`，随机回复一条，但是`你多大年纪了`要除去。

---

假设，用户2 在示例2 之后，回答了问题，`我不傻，是你傻`：
（机器在不知情的状况下，认为，在 说`你怎么这么傻`之后会说`我不傻，是你傻`）

1. base 库中，没有匹配到这个问题，入库
2. 添加到tree中
3. 添加到butt未知表中
4. 由于未知表中依旧不知道2个问题，你好，你多大年纪了，所以随机回复一个除`我不傻，是你傻`话之外的问题让用户回答。

---

在n轮之后，可能机器人学会了很多中你好的回答，或者是你好之后用户问了什么问题：

![img_4.png](http://upload-images.jianshu.io/upload_images/144590-aea7c484dfbba897.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)

我们看到，用户对于你好这个问题的回答几乎是很相近的。所以下次，如果有人来问你好，机器人就会去库中查出来，然后数量内随机，那么越多被回答的内容被随机到返回的几率也就越大。当然，其他几率比较小的问题也可能被随机到，但是这都是别人跟机器人聊天问的东西，我们把这些问题抛给其他人也不是不对。

---

机器人一个简单的学习途径就搭建起来了，但这仅仅是学会了这些话，机器并不懂这些话到底是什么含义，或者有没有更好的途径，那么就要借助语义识别与分词了。

---

我们这里的分词使用的是斯坦福大学的[分词系统](https://stanfordnlp.github.io/CoreNLP/api.html)，分词之后，我们可以做很多的事情，比如说两句话的意思是否一致，能大致的判断出这个人是想要什么东西，做什么事情，而不是像最初那样笨笨的学习，如果在学习的过程中有个人捣乱，哪所有人也遭殃了。

假如说机器人不管说什么，他都回复：小明很笨，过不了多久，大部分用户跟机器人聊天，只要没有确切的答案，都会说小明很笨了.... 哈哈

所以，这里要做一些操作，我们再次把表分开。tree表中存放的都是我们预置的一些预料，base的功能还是存放所有的通过机器的会话，butt中依旧存放未知的语句。这时候多一样表：机器学习到的内容，t_corpus_hit，这张表我们单独存放学习到的数据，以免对原有固定或者基础答案的内容造成冲击。

这张表在入库的时候一定要把控的很严格，稍有不慎就会传播色情内容，所以基础的过滤与鉴定是要有的，这张表中我们作为命中表，如果对于一个问题的回答多个人命中之后，那么该回答作为可用回答，插入到tree中。命中的过程也就是2句话意义是否相近的过程。

加入用户说：我想吃黄焖鸡，另一个用户也说，黄焖鸡我很想吃，那么在同一个scope分词后，同样存在吃，黄焖鸡，大致的可以认为他们相近，当然这里就涉及到一些干扰的去除等等。这里后续我们会加入元祖的概念。

就像我们出生到现在，我们也在学习，什么话之后应该说什么话。未来我们把上下文加入之后，会得到一个更好的结果。
