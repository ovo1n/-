# 项目开发指南

## 📚 项目文件结构

```
pet-adoption-platform/
├── src/main/java/com/petadoption/
│   ├── controller/              # API控制层
│   ├── service/                 # 业务逻辑层
│   ├── mapper/                  # 数据访问层
│   ├── model/
│   │   ├── entity/              # 数据库实体
│   │   └── dto/                 # 数据传输对象
│   ├── utils/                   # 工具类
│   ├── config/                  # 配置类
│   └── exception/               # 异常处理
├── src/main/resources/
│   ├── application.yml          # 配置文件
│   ├── templates/               # HTML模板
│   └── static/                  # 静态资源
├── docs/
│   ├── database-schema.sql      # 数据库设计
│   ├── API-DOCUMENTATION.md     # API文档
│   └── DEVELOPMENT_GUIDE.md     # 开发指南
├── pom.xml                      # Maven配置
└── README.md                    # 项目说明
```

## 🚀 快速开始

### 1. 数据库初始化

```bash
mysql -u root -p < docs/database-schema.sql
```

### 2. 修改配置

编辑 `application.yml`，修改数据库连接信息

### 3. 运行项目

```bash
mvn spring-boot:run
```

访问 http://localhost:8080

## 🏗 核心模块

### 用户模块
- UserService: 用户注册、登录、信息管理
- UserController: 用户 API 接口

### 宠物模块
- PetService: 宠物信息 CRUD、发布、下架
- PetController: 宠物 API 接口
- QRCodeGenerator: 二维码生成

### 领养模块
- AdoptionApplicationService: 领养申请管理
- AdoptionApplicationController: 领养 API

### 智能匹配模块 ⭐
- MatchingService: 匹配度计算、推荐
- MatchingController: 匹配 API

### 回访模块
- FollowUpRecordService: 回访记录管理
- FollowUpRecordController: 回访 API

## 🛠 添加新功能

### 步骤

1. 创建 Entity 类（在 model/entity）
2. 创建 Mapper 接口（在 mapper）
3. 创建 Service 接口（在 service）
4. 创建 ServiceImpl 实现类（在 service/impl）
5. 创建 Controller（在 controller）
6. 测试 API

## 🧪 测试

### 使用 cURL 测试

```bash
# 获取待领养宠物
curl http://localhost:8080/api/pets/pending

# 查询统计数据
curl http://localhost:8080/api/statistics/dashboard
```

## 🚢 部署

### 打包

```bash
mvn clean package -DskipTests
```

### 运行

```bash
java -jar target/pet-adoption-platform-1.0.0.jar
```

## 📝 常见问题

**Q: 如何修改端口?**  
A: 在 application.yml 中修改 server.port

**Q: 如何启用调试模式?**  
A: 设置日志级别为 DEBUG

**Q: 生产环境如何配置?**  
A: 创建 application-prod.yml，使用 --spring.profiles.active=prod
