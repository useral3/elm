# 🍔 elm 仿饿了么外卖平台

一个基于 Spring Boot + Vue 3 的全栈外卖点餐系统，模拟饿了么核心业务功能，重点展示 Redis 在高并发场景下的应用。

## 📁 项目结构

```
elm/
├── backend/                  # 后端 Spring Boot 项目
│   ├── src/main/java/org/example/elm/
│   │   ├── config/           # 配置（JWT拦截器、Redis、跨域、定时任务、缓存预热）
│   │   ├── controller/       # 控制器层（含秒杀、排行榜）
│   │   ├── entity/           # 实体类
│   │   ├── mapper/           # MyBatis Mapper
│   │   ├── service/          # 业务服务层
│   │   │   └── impl/         # 服务实现
│   │   └── util/             # 工具类（JWT）
│   └── src/main/resources/
│       ├── mapper/           # MyBatis XML
│       └── application.properties
├── frontend/                 # 前端 Vue 3 项目
│   └── src/
│       ├── components/       # 公共组件
│       ├── router/           # 路由配置
│       ├── utils/            # 工具函数
│       └── views/            # 页面视图（20+ 页面）
├── docs/                     # 文档与截图
├── uploads/                  # 文件上传目录
├── elm.sql                   # 数据库初始化
└── seed.sql                  # 示例数据（100家商家 + 206个菜品）
```

## 🛠 技术栈

### 后端
- **Spring Boot** 4.0.1 + **@Scheduled** 定时任务
- **MyBatis** — ORM 框架
- **MySQL** 8.0 — 关系型数据库
- **Redis** — 缓存 / 购物车 / 排行榜 / 秒杀库存 / 分布式锁 / Lua 脚本
- **JWT** (jjwt 0.12.5) — 用户认证 + 黑名单
- **OkHttp** 4.12 — 通义千问 AI API
- **Fastjson** 2.0.43 — JSON 处理
- **Lombok** — 简化代码

### 前端
- **Vue** 3 + **Vue Router** 4 + **Axios**
- **Element Plus** 2.13 — UI 组件库
- **Font Awesome** 4.7 — 图标库

## ✨ 功能特性

| 模块 | 功能 | 亮点 |
|------|------|------|
| 🔐 用户认证 | 注册/登录/登出、JWT 令牌、登录拦截器、黑名单 | 短信验证码、登录限流 |
| 🏪 商家浏览 | 分类筛选、商家详情、100 家示例数据 | Cache-Aside 缓存 + 缓存预热 |
| 🍜 菜品管理 | 按商家查询、206 个示例菜品 | 同上 |
| 🛒 购物车 | 添加/删除/修改数量 | Redis Hash + Set，7 天 TTL |
| 📦 订单系统 | 创建/支付/列表/详情 | 订单缓存、超时自动取消 |
| ⚡ 限时秒杀 | 秒杀商品列表、一键抢购 | Lua 原子扣库存 + SETNX 分布式锁 |
| 🏆 热销排行 | Redis Sorted Set 销量排名 | 支付自动累加、启动从 DB 恢复 |
| 📍 收货地址 | 增删改查 | — |
| 💬 AI 客服 | 通义千问智能对话 | API 故障自动降级到本地 mock |
| ⭐ 收藏功能 | 收藏/取消/列表 | 含商家详情关联 |
| 🕐 浏览记录 | 自动记录、管理（单删/全清） | Redis List，去重排序 |
| 🔄 订单超时 | 30 分钟未支付自动取消 | `@Scheduled` + 缓存清除 |
| 📱 响应式设计 | 移动端适配 | — |

## 🚀 快速开始

### 环境要求

- JDK 17+ / Maven 3.6+ / MySQL 8.0+ / Redis 6.0+ / Node.js 16+ / Yarn

### 1. 数据库初始化

```bash
mysql -u root -p < elm.sql
mysql -u root -p --default-character-set=utf8mb4 elm < seed.sql
```

### 2. 配置修改

编辑 `backend/src/main/resources/application.properties`，修改数据库、Redis、通义千问 API Key。

### 3. 启动

```bash
# 后端
cd backend && ./mvnw spring-boot:run

# 前端
cd frontend && yarn install && yarn dev
```

后端 http://localhost:8080 | 前端 http://localhost:8081

## 📡 核心 API

| 方法 | 路径 | 说明 |
|------|------|------|
| POST | `/user/login` | 登录（含限流） |
| POST | `/user/sendCode` | 发送验证码 |
| POST | `/user/saveUser` | 注册（含验证码） |
| POST | `/business/listBusinessByOrderTypeId` | 商家列表 |
| POST | `/business/browseHistory` | 浏览记录 |
| POST | `/cart/listCart` | 购物车（Redis Hash） |
| POST | `/orders/createOrders` | 创建订单 |
| POST | `/orders/listOrdersByUserId` | 订单列表（Cache-Aside） |
| POST | `/flashSale/list` | 秒杀商品 |
| POST | `/flashSale/buy` | 秒杀下单（Lua 原子扣库存） |
| POST | `/leaderboard/topSales` | 销量排行榜（ZSet） |
| POST | `/favorite/toggleBusiness` | 收藏/取消 |
| POST | `/chat` | AI 智能客服 |

## 📝 数据库表

`business` `food` `user` `cart` `orders` `orderdetailet` `deliveryaddress` `favorite`

## 🧠 Redis 深度应用

| 场景 | 数据结构 | 技术 |
|------|----------|------|
| 商家/菜品缓存 | String (JSON) | Cache-Aside + 预热 + 主动失效 |
| 购物车 | Hash + Set | 纯 Redis 存储，7 天 TTL |
| 秒杀库存 | String | **Lua 脚本原子扣减** |
| 防重复下单 | String (SETNX) | **分布式锁** |
| 销量排行榜 | Sorted Set | ZINCRBY + reverseRange |
| 浏览记录 | List | LPUSH + LREM + LTRIM |
| JWT 会话 | String | 2 小时 TTL + 黑名单 |
| 登录限流 | String | INCR + TTL 窗口计数 |
| 验证码 | String | 5 分钟 TTL + 60 秒冷却 |
| 订单缓存 | String (JSON) | Cache-Aside，支付/取消后失效 |

---

*项目为学习实训用途，仅供参考。*
