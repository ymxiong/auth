# Auth中间件
## Auth 版本

master版本0.1.5

### 版本新功能

#### fix

+ Chain支持跨服务链路外部系统接入无链路参数导致Status解析NPE的问题——自动注入ChainAdvice（后续考虑根据路径解析）

#### feat

+ 支持鉴权逻辑定制化

## Auth中间件概述

Auth中间件以分布式系统统一鉴权与链路信息管理为核心，目前主要包含以下模块：

+ auth

  提供分布式统一鉴权的解决方案

+ chain

  以trace为基础模型，提供分布式链路信息管理解决方案

+ status

  提供分布式错误统一管理方案

## Auth模块

### Auth鉴权支持

[几种常见的鉴权方式](https://www.jianshu.com/p/4a00c0c3bf1d)

Auth鉴权框架旨在通过几个注解简便地实现鉴权功能，支持最常见的几种鉴权方式，也支持开发人员自定义的鉴权方式扩展，包括：

- 环境参数鉴权（表达式鉴权）
  例如：请求头鉴权、Cookie(token)鉴权等
  开发人员只须提供环境参数与请求参数的名称以及它们之间的关系，形成一个或一组简易的表达式，框架会自动提取参数内容并验证表达式的正确性，以此为鉴权依据
  表达式 例：

```plain
header.param == request.param
```

- 角色鉴权（分组鉴权）
  例如：role == "admin" 与 role == "user " 的用户持有不同组级别的权限
  开发人员只须填写角色名称，并指定角色权限即可完成鉴权

- 自定义鉴权
  例如：
  拥有数据库支持的鉴权系统，将用户之于每一个api的权限存储于数据库中，将查询结果作为鉴权依据；将不同角色的权限存储于数据库中，将查询结果作为鉴权依据。

- 鉴权责任链
  对同一个api的鉴权的方式可能不止一种，不同的鉴权方式之间存在 and 或者 or 关系（即需要同时满足或满足其一即可）
  开发人员通过多个注解的协同使用，并配置它们之间的鉴权逻辑关系构成一条完整的鉴权责任链，实现以上几种方式的结合

### Auth鉴权维度

- 不进行鉴权
  某些api接口可以对任何人开放，不需要鉴权，则不需使用

- common接口鉴权
  即登录鉴权，用户登录后该接口对其开放

- 数据接口鉴权
  例如：该用户的个人信息只能由该用户访问

- 角色鉴权
  例如：管理员、vip用户、普通用户访问权限存在差异

### Auth基本使用

鉴权通过将放行该次api调用，否则抛出401权限不足异常

需要结合`@AuthExpression @AuthGroup @Auth`三个注解，并设置鉴权逻辑配合使用

#### 注解使用

##### [@AuthExpression ]()

该注解用于支持系统参数鉴权，通过编写一个或一组表达式配置鉴权条件（可不编写鉴权逻辑，提供默认逻辑，即 `环境参数 OP 请求参数`）

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthExpression {

    /**
     * 表达式条件
     * @return
     */
    String[] value() default {};

    /**
     * 鉴权规则
     * 用于鉴别表达式间逻辑关系
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

}
```

示例：

```java
  @AuthExpression("entityKey==${strategy}userId") //${strategy}为占位符
  @ApiOperation(
      value = "获取实体",
      notes = "获取实体"
  )
  @RequestMapping(
      value = "single/simple",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getSimpleMapByPK(@RequestParam(value = "entityKey", required = true) String entityKey) {
    return Status.successBuilder()
        .addDataValue(userService.getSimpleMapByPK(entityKey))
        .map();
  }
```

该接口将会自动校验`request.param (entityKey) == context.param (userId)`条件

- 若 `context.param` 可存在于请求头、Cookie、Chain（链路信息）中，对应不同的${strategy}

  + 若 `context.param` 存在于cookie中，则**必须以** `cookie$` 开头，且后面接参数名称

    例：userId位于cookie中，则须写作： `cookie$userId` ，才能成功解析出userId

  + 若 `context.param` 存在于header中，则**必须以** `header$` 开头，且后面接参数名称

    例：userId位于header中，则须写作： `header$userId` ，才能成功解析出userId

  + 若 `context.param` 存在于chain中，则**必须以** `chain$` 开头，且后面接参数名称

    例：userId位于chain中，则须写作： `chain$userId` ，才能成功解析出userId

  

若校验条件为一组表达式，则使用如下（默认鉴权规则为`Logical.AND`）：

```java
@AuthExpression(value = {"entityKey==userId","abc"=="cde"}, logical = Logical.OR)
```

##### [@AuthGroup ]()

该注解用于支持角色鉴权，通过设置一个或一组角色名配置鉴权条件

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface AuthGroup {

    /**
     * 角色名称
     * 用于根据角色名称拦截确认权限
     * @return 角色名称
     */
    String[] value() default {};

    /**
     * 鉴权规则
     * 用于鉴别分组规则
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

}
```

示例：

```java
  @AuthGroup(value = {"super","admin"},logical = Logical.OR)
  @ApiOperation(
      value = "发布一组实体",
      notes = "发布一组实体"
  )
  @RequestMapping(
      value = "batch",
      method = RequestMethod.POST
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> postBatch(@RequestBody ArrayList<UserPostMapper> postMappers) {
    return Status.successBuilder()
        .addDataValue(userService.postList(postMappers))
        .map();
  }
```

##### [@Auth ]()

该注解用于形成鉴权责任链，即指明`@AuthGroup @AuthExpression`之间的鉴权逻辑，缺省为Logical.AND，缺省情况下注解也可缺省

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 责任链名称
     * 无实际用处
     */
    String value() default "";

    /**
     * 鉴权规则
     * 用于鉴别分组规则
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

}
```

示例：

```java
  @Auth(logical = Logical.OR)
  @AuthGroup(value = {"super","admin"},logical = Logical.AND)
  @AuthExpression(value = "entityKey==userId")
  @ApiOperation(
      value = "获取实体detail",
      notes = "获取实体detail"
  )
  @RequestMapping(
      value = "single",
      method = RequestMethod.GET
  )
  @Transactional(
      rollbackFor = Exception.class
  )
  @ResponseBody
  public Map<String, Object> getDetailMapByPK(@RequestParam(value = "entityKey", required = true) String entityKey) {
    return Status.successBuilder()
        .addDataValue(userService.getDetailMapByPK(entityKey))
        .map();
  }
```

#### 校验逻辑编写

##### 文件位置

![img](https://cdn.nlark.com/yuque/0/2020/png/324858/1603683682769-643e0c7f-281d-4d02-a684-15b0df135db7.png)

##### 编写校验逻辑

须继承`cc.eamon.open.auth.advice.AuthAdvice`

```java
@Aspect
@Component
public class AuthAdvice extends cc.eamon.open.auth.advice.AuthAdvice {

    @Resource
    private UserPermissionFacade userPermissionFacade;
    
    @Resource
    private GroupPermissionFacade groupPermissionFacade;


    /**
     * 开启整体鉴权功能
     * @return true
     */
    @Override
    public boolean open() {
        return true;
    }

    /**
     * 增强表达式鉴权逻辑，如查询数据库核验权限：使用userPermissionFacade
     * 表达式核验结果注入该方法进行进一步鉴权，默认表达式检验结果即为该方法返回值
     * 该方法返回结果最终确认是否鉴权通过
     * 可不重写该方法
     * @param request
     * @param response
     * @param uri 权限名称（api）
     * @param expression 表达式
     * @param expressionResult 表达式判断结果
     * @return
     */
    @Override
    public boolean checkExpression(HttpServletRequest request, HttpServletResponse response, String uri, String expression,boolean expressionResult) {
        return expressionResult;
    }

    /**
     * 须重写该方法
     * 如下所示：super组核验通过，其他组不通过 也可查询权限数据库：使用groupPermissionFacade
     * @param request
     * @param response
     * @param uri 权限名称（api）
     * @param group 角色
     * @return
     */
    @Override
    public boolean checkGroup(HttpServletRequest request, HttpServletResponse response, String uri, String group) {
        return "super".equals(group);
    }


    /**
     * 重写表达式环境参数获取方式
     * 可不重写，以下为默认方式
     * @param request
     * @param response
     * @param valueName 环境参数名：注意根据不同strategy写法不同
     * @return
     */
    @Override
    public Object getContextValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        String value = request.getHeader(valueName);
        if (valueName.contains("cookie")) {
            String[] values = valueName.split("\\$");
            if (values.length < 2) return null;
            Cookie[] cookies = request.getCookies();
            if (null != cookies) {
                for (Cookie cookie : cookies) {
                    if (values[1].equals(cookie.getName())) return cookie.getValue();
                }
            }
        }else if (valueName.contains("header")){
            return value;
        }
        return value;
    }

    /**
     * 重写表达式请求参数获取方式
     * 可不重写，以下为默认方式
     * @param request
     * @param response
     * @param valueName 请求参数名
     * @return
     */
    @Override
    public Object getRequestValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return request.getParameter(valueName);
    }
}

```

##### 检验逻辑分接口定制化——Since 0.1.5

+ Auth注解新增回调Authenticator Class

```java
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface Auth {

    /**
     * 权限名称
     * 用于根据权限名称拦截确认权限
     * @return 权限名称
     */
    String value() default "";

    /**
     * 鉴权规则
     * 用于鉴别分组规则
     * @return 鉴权规则and/or
     */
    Logical[] logical() default Logical.AND;

    /**
     * @since 0.1.5
     * 自定义鉴权器Class
     * 用于注入自定义鉴权，供回调使用
     * @return
     */
    Class<? extends Authenticator> authenticatorClass() default AuthAdvice.class;

}

```

+ 自定义Authenticator示例——须实现Authenticator接口

  支持多个接口绑定同一个自定义鉴权类

```java
import cc.eamon.open.auth.authenticator.Authenticator;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


public class AuthFileDownloadAuthenticator implements Authenticator {
    @Override
    public boolean open(HttpServletRequest request, HttpServletResponse response) {
        return true;
    }

    @Override
    public boolean checkGroup(HttpServletRequest request, HttpServletResponse response, String uri, String group) {
        return false;
    }

    @Override
    public boolean checkExpression(HttpServletRequest request, HttpServletResponse response, String uri, String expression, boolean expressionResult) {
        return false;
    }

    @Override
    public Object getContextValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return null;
    }

    @Override
    public Object getRequestValue(HttpServletRequest request, HttpServletResponse response, String valueName) {
        return null;
    }
}

```

#### 注意点

鉴权条件是缺省判断的，即 and 条件出错即止，or 条件正确即止

当同时有@AuthGroup 与 @AuthExpression条件时，角色权限优先判断

#### 全局错误通知

##### 文件位置

![img](https://cdn.nlark.com/yuque/0/2020/png/324858/1603683672065-bdc6e03e-04dc-4291-81a0-68bac14e7ebe.png)

##### 编写内容

须继承`cc.eamon.open.status.StatusAdvice`

```java
@ControllerAdvice
public class StatusAdvice extends cc.eamon.open.status.StatusAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 全局异常处理
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    @Override
    public Map<String, Object> statusExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return super.statusExceptionHandler(request, response, e);
    }

    @Override
    public boolean setResponseStatus() {
        return false;
    }

}

```

用于全局异常处理，跨服务异常传递

## Chain模块

### 核心模型

ChainContext

该模型是一个线程私有的ConcurrentHashMap数据结构，当前支持以下类型的链路自动序列化与反序列化：

- String
- Date

- Interger
- Long

- Map<String, String>（注意泛型必须均为String）
- 其他类型默认遵循fastjson序列化规范

### 使用方式

使用ChainContextHolder线程私有holder进行set/get操作，Chain将自动解析并传递至链路下游服务

### 功能展示

#### 链路默认信息

包含TraceId SpanId ParentId InvokeId OpenTime ThreadTime等

+ TraceId：服务调用链模型id，为树状结构
+ SpanId：当前服务内部模型id，若为入口服务则traceId==spanId
+ ParentId：父服务spanId，若为入口服务则traceId==spanId==parentId
+ InvokeId：服务层次模型，根据树状结构的层数及调用顺序，进行初始化
+ OpenTime：Trace开始时间
+ ThreadTime：Span开始时间

![img](https://cdn.nlark.com/yuque/0/2021/png/324858/1609583772422-89a005bf-4df4-4578-a176-510a7a44563a.png)![img](https://cdn.nlark.com/yuque/0/2021/png/324858/1609583773711-0cbbac67-8adb-48af-a2e2-6f4f5096682d.png)![img](https://cdn.nlark.com/yuque/0/2021/png/324858/1609583775357-0fdc2478-bde5-47b3-9eeb-04c0e5e93ee3.png)

#### 链路错误管理

以下展示为跨服务异常传递从异常发生服务逆向传递最终解析至前端的整条错误链路：

![img](https://cdn.nlark.com/yuque/0/2021/png/324858/1609583864344-475cafda-b22b-4501-81df-f2544a42b40b.png)![img](https://cdn.nlark.com/yuque/0/2021/png/324858/1609583865287-36ceba94-62ad-43dd-92a4-2fe555e63b35.png)![img](https://cdn.nlark.com/yuque/0/2021/png/324858/1609583866416-c8736ffe-58c2-4fa6-a3fa-83421f342e5a.png)

## Status模块

### 错误统一管理

在每个服务内部common模块下status.json中进行错误定制和统一管理，包含以下默认错误，定制错误对数组进行扩展即可：

```json
{
  "type": "status",
  "errors": [
    {
      "id": "SUCCESS",
      "code": 200,
      "message": "请求成功"
    },
    {
      "id": "FAILED",
      "code": 700,
      "message": "请求失败"
    },
    {
      "id": "NO_AUTH",
      "code": 401,
      "message": "权限不足"
    },
    {
      "id": "NO_RECOGNIZE",
      "code": 1000,
      "message": "错误未识别"
    },
    {
      "id": "EXP_ERROR",
      "code": 800,
      "message": "表达式错误"
    },
    {
      "id": "SERVICE_ERROR",
      "code": 701,
      "message": "服务调用错误"
    },
    {
      "id": "CHAIN_ERROR",
      "code": 725,
      "message": "链路错误"
    },
    {
      "id": "CHAIN_PARSE_ERROR",
      "code": 726,
      "message": "链路转化错误"
    }
  ]
}

```

### 跨服务异常信息定制

*version 0.1.4 新增*

#### maven依赖

须在domain module新增如下maven依赖：

```xml
<dependency>
		<groupId>cc.eamon.open</groupId>
    <artifactId>auth</artifactId>
    <version>0.1.4</version>
  	<scope>provided</scope>
</dependency>
```

#### 注解

##### @ErrorHandler——异常定制接口方法

用于注解需要定制异常信息的feign申明接口，即被@FeignClient注解的接口中的具体方法

```java
package cc.eamon.open.status.codec;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorHandler {

    ErrorInstance[] instances() default {};

}

```

##### @ErrorInstance——异常定制实例

用于注解申明具体接口对应错误码的定制异常信息，包含：

+ id：对齐status.json中的错误id
+ statusCode：对应当前定制接口方法的具体错误码
+ message：指明当前接口在该错误码下的定制错误信息
+ decodeException：指明当前接口在该错误码下的定制错误类型

```java
package cc.eamon.open.status.codec;

import cc.eamon.open.status.StatusException;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;


@Retention(RetentionPolicy.RUNTIME)
public @interface ErrorInstance {

    String id() default "NO_RECOGNIZE_MSG";

    int statusCode() default 701;

    String message() default "rpc error";

    Class<? extends Exception> decodeException() default StatusException.class;
}

```

#### 使用示例

```java
package com.horsecoder.storage.domain;

import cc.eamon.open.status.StatusException;
import cc.eamon.open.status.codec.ErrorHandler;
import cc.eamon.open.status.codec.ErrorInstance;
import com.horsecoder.storage.config.FeignMultipartConfig;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;


@FeignClient(
        value = "horsecoder-storage",
        path = "/facade/storage/file",
        configuration = FeignMultipartConfig.class
)
public interface FileUpdateCoreFacade {


    @ErrorHandler(
            instances = {
                    @ErrorInstance(
                            id = "DIY1",
                            statusCode = 1001,
                            message = "diy1",
                            decodeException = StatusException.class),
                    @ErrorInstance(
                            id = "DIY2",
                            statusCode = 1002,
                            message = "diy2",
                            decodeException = RuntimeException.class
                    )
            }
    )
    @RequestMapping(
            value = "upload/json",
            method = RequestMethod.POST,
            consumes = MediaType.MULTIPART_FORM_DATA_VALUE
    )
    String uploadJsonFile(
            @RequestPart MultipartFile file,
            @RequestParam(value = "placeholder", required = false) String placeholder,
            @RequestParam(value = "user", required = true) String user,
            @RequestParam(value = "fileName", required = false) String fileName
    );

}
```

如上所示进行接口异常信息定制，当该接口返回异常code为1001时，将会在调用方解析为：

```java
new StatusException(1001, "diy2");
```

#### 其他变更

异常对象默认为支持跨服务传递，并且在0.1.4版本与状态码解耦，为支持状态码为200但实际内含异常的情况，并且由StatusException对象setResponseStatus属性决定是否进行跨服务状态变更（默认总是进行跨服务状态覆盖）

若须使用特殊情况，即不对跨服务状态进行覆盖，则以当前异常对象为粒度将setResponseStatus属性置为false

##### StatusException

```java
package cc.eamon.open.status;


public class StatusException extends RuntimeException {

    private int code;

    private String message;

    private String detail;

    private boolean setResponseStatus = true; // 默认进行跨服务状态覆盖

    public StatusException(String errorName) {
        super(StatusCode.getMessage(errorName));
        this.code = StatusCode.getCode(errorName);
        this.message = super.getMessage();
        this.detail = "";
    }

    public StatusException(String errorName, String detail) {
        super(StatusCode.getMessage(errorName));
        this.code = StatusCode.getCode(errorName);
        this.message = super.getMessage();
        this.detail = detail;
    }

    public StatusException(int code, String message) {
        super(message);
        this.code = code;
        this.message = message;
        this.detail = "";
    }

    public StatusException(int code, String message, String detail) {
        super(message);
        this.code = code;
        this.message = message;
        this.detail = detail;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getDetail() {
        return detail;
    }

    public void setDetail(String detail) {
        this.detail = detail;
    }

    public boolean isSetResponseStatus() {
        return setResponseStatus;
    }

    public void setSetResponseStatus(boolean setResponseStatus) {
        this.setResponseStatus = setResponseStatus;
    }

    /**
     * 用于不修改状态码(保持为200)，将错误包在message中透传到前端
     */
    public void setStatusInner() {
        this.setResponseStatus = false;
    }

    @Override
    public String toString() {
        return "StatusException{" +
                "code=" + code +
                ", message='" + message + '\'' +
                ", detail='" + detail + '\'' +
                '}';
    }
}

```

同时该值与全局跨服务异常状态覆盖开关（StatusAdvice）取或关系：

setResponseStatus() || StatusException.setResponseStatus为最终结果

```java
@ControllerAdvice
public class StatusAdvice extends cc.eamon.open.status.StatusAdvice {

    @InitBinder
    public void initBinder(WebDataBinder binder) {
    }

    /**
     * 全局异常处理
     */
    @ExceptionHandler(value = {Exception.class})
    @ResponseBody
    @Override
    public Map<String, Object> statusExceptionHandler(HttpServletRequest request, HttpServletResponse response, Exception e) {
        return super.statusExceptionHandler(request, response, e);
    }

    // 全局跨服务异常状态覆盖开关
    @Override
    public boolean setResponseStatus() {
        return false;
    }

}
```

###

