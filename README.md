# 🍔 elm 仿饿了么外卖平台

一个基于 Spring Boot + Vue 3 的全栈外卖点餐系统，模拟饿了么核心业务功能。

## 📁 项目结构

```
elm/
├── backend/                  # 后端 Spring Boot 项目
│   ├── src/main/java/org/example/elm/
│   │   ├── config/           # 配置类（JWT拦截器、Redis、跨域、文件上传）
│   │   ├── controller/       # 控制器层
│   │   ├── entity/           # 实体类
│   │   ├── mapper/           # MyBatis Mapper 接口
│   │   ├── service/          # 业务服务层
│   │   │   └── impl/         # 服务实现类
│   │   └── util/             # 工具类（JWT）
│   └── src/main/resources/
│       ├── mapper/           # MyBatis XML 映射文件
│       ├── static/           # 静态资源
│       └── application.properties
├── frontend/                 # 前端 Vue 3 项目
│   └── src/
│       ├── components/       # 公共组件（导航栏、页脚）
│       ├── router/           # 路由配置
│       ├── utils/            # 工具函数
│       └── views/            # 页面视图
├── docs/                     # 文档与截图
├── uploads/                  # 文件上传目录
└── elm.sql                   # 数据库初始化脚本
```

## 🛠 技术栈

### 后端
- **Spring Boot** 4.0.1
- **MyBatis** 4.0.1 — ORM 框架
- **MySQL** 8.0 — 关系型数据库
- **Redis** — 缓存（验证码、商家列表等）
- **JWT** (jjwt 0.12.5) — 用户认证
- **OkHttp** 4.12 — HTTP 客户端（通义千问 API）
- **Fastjson** 2.0.43 — JSON 处理
- **Lombok** — 简化代码

### 前端
- **Vue** 3.2 — 前端框架
- **Vue Router** 4.0 — 路由管理
- **Element Plus** 2.13 — UI 组件库
- **Axios** 1.13 — HTTP 请求
- **Font Awesome** 4.7 — 图标库

## ✨ 功能特性

| 模块 | 功能 |
|------|------|
| 🔐 用户认证 | 注册/登录、JWT 令牌认证、登录拦截器 |
| 🏪 商家浏览 | 商家列表、按分类筛选、商家详情 |
| 🍜 菜品管理 | 菜品列表、按商家查询 |
| 🛒 购物车 | 添加/删除商品、修改数量 |
| 📦 订单系统 | 创建订单、订单列表、订单详情 |
| 📍 收货地址 | 地址增删改查 |
| 💬 AI 聊天 | 集成通义千问 API，智能客服助手 |
| ⭐ 收藏功能 | 收藏喜欢的商家 |
| 📱 响应式设计 | 移动端适配 |

## 🚀 快速开始

### 环境要求

- JDK 17+
- Maven 3.6+
- MySQL 8.0+
- Redis 6.0+
- Node.js 16+
- Yarn / npm

### 1. 数据库初始化

```bash
# 创建数据库并导入数据
mysql -u root -p < elm.sql
```

### 2. 配置修改

编辑 `backend/src/main/resources/application.properties`，修改为你的环境配置：

```properties
# 数据库连接
spring.datasource.url=jdbc:mysql://localhost:3306/elm?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf8
spring.datasource.username=你的数据库用户名
spring.datasource.password=你的数据库密码

# Redis 连接
spring.data.redis.host=你的Redis地址
spring.data.redis.port=6379
spring.data.redis.password=你的Redis密码

# 通义千问 API Key（可选，用于 AI 聊天功能）
dashscope.api.key=你的API密钥
```

### 3. 启动后端

```bash
cd backend
./mvnw spring-boot:run
# Windows: mvnw.cmd spring-boot:run
```

后端启动后访问：http://localhost:8080

### 4. 启动前端

```bash
cd frontend
yarn install   # 或 npm install
yarn serve     # 或 npm run serve
```

前端启动后访问：http://localhost:8081

## 📡 API 接口概览

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/api/auth/login` | 用户登录 |
| POST | `/api/auth/register` | 用户注册 |
| GET | `/api/business/list` | 商家列表 |
| GET | `/api/business/{id}` | 商家详情 |
| GET | `/api/food/list/{businessId}` | 菜品列表 |
| POST | `/api/cart/add` | 添加购物车 |
| GET | `/api/cart/list` | 购物车列表 |
| POST | `/api/orders/create` | 创建订单 |
| GET | `/api/orders/list/{userId}` | 订单列表 |
| POST | `/api/chat/send` | AI 聊天 |
| POST | `/api/deliveryAddress/add` | 添加地址 |
| GET | `/api/deliveryAddress/list/{userId}` | 地址列表 |

## 📝 数据库表结构

- `business` — 商家信息（10 种分类：美食、早餐、跑腿代购等）
- `food` — 菜品信息
- `user` — 用户信息
- `cart` — 购物车
- `orders` — 订单
- `orderdetailet` — 订单明细
- `deliveryaddress` — 收货地址
- `favorite` — 收藏

---

*项目为学习实训用途，仅供参考。*
