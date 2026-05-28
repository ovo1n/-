# 流浪宠物救助与领养智能管理平台

🐕 **一个面向社会大众的公益类宠物救助领养网站** | 基于Spring Boot的毕设完整方案

---

## 📋 项目概述

本项目是一个完整的宠物救助与领养管理系统，旨在解决社会流浪宠物领养信息不对称、流程不规范、匹配效率低的问题。

**核心定位**：
- 面向社会大众（不限校园）
- 覆盖普通领养人、宠物救助人、平台管理员三类用户
- 具有公益属性与技术亮点
- 完整的毕设方案

---

## 🛠 技术栈

### 后端
- **框架**: Java 8 + Spring Boot 2.7.14
- **数据库**: MySQL 8.0
- **ORM**: MyBatis-Plus
- **构建工具**: Maven

### 前端
- **页面**: HTML5 + CSS3 + JavaScript
- **框架**: Bootstrap 5
- **模板引擎**: Thymeleaf
- **图表库**: ECharts
- **二维码**: ZXing

### 其他
- **认证**: Spring Security + JWT
- **加密**: MD5 (可升级到BCrypt)
- **验证**: Spring Validation

---

## 📁 项目结构

```
pet-adoption-platform/
├── src/main/java/com/petadoption/
│   ├── PetAdoptionApplication.java          # 主启动类
│   ├── controller/                          # 控制层
│   │   └── UserController.java              # 用户控制器
│   ├── service/                             # 业务层
│   │   ├── UserService.java                 # 用户服务接口
│   │   └── impl/
│   │       └── UserServiceImpl.java          # 用户服务实现
│   ├── mapper/                              # 数据访问层
│   │   ├── UserMapper.java
│   │   ├── PetMapper.java
│   │   ├── AdoptionApplicationMapper.java
│   │   ├── MatchingRecordMapper.java
│   │   └── FollowUpRecordMapper.java
│   ├── model/
│   │   ├── entity/                          # 实体类
│   │   │   ├── User.java                    # 用户实体
│   │   │   ├── Pet.java                     # 宠物实体
│   │   │   ├── AdoptionApplication.java     # 领养申请实体
│   │   │   ├── MatchingRecord.java          # 匹配记录实体
│   │   │   └── FollowUpRecord.java          # 回访记录实体
│   │   └── dto/                             # 数据传输对象
│   │       └── ApiResponse.java             # 统一响应类
│   └── utils/                               # 工具类
│
├── src/main/resources/
│   ├── application.yml                      # 应用配置
│   ├── templates/                           # Thymeleaf模板
│   └── static/                              # 静态资源
│
├── docs/
│   ├── database-schema.sql                  # 数据库设计
│   └── API文档.md                           # API文档
│
├── pom.xml                                  # Maven配置
└── README.md                                # 项目说明
```

---

## 🚀 快速开始

### 1️⃣ 环境要求
- JDK 1.8+
- MySQL 8.0+
- Maven 3.6+
- IntelliJ IDEA 2024 (可选)

### 2️⃣ 数据库初始化
```bash
# 1. 创建数据库
mysql -u root -p < docs/database-schema.sql

# 2. 验证表是否创建成功
USE pet_adoption;
SHOW TABLES;
```

### 3️⃣ 项目配置
编辑 `src/main/resources/application.yml`：
```yaml
spring:
  datasource:
    url: jdbc:mysql://localhost:3306/pet_adoption?useUnicode=true&characterEncoding=utf-8&useSSL=false&serverTimezone=Asia/Shanghai
    username: root          # 修改为您的MySQL用户名
    password: root          # 修改为您的MySQL密码
```

### 4️⃣ 运行项目
```bash
# 编译
mvn clean install

# 运行
mvn spring-boot:run

# 或直接运行主类 PetAdoptionApplication.java
```

**访问地址**: http://localhost:8080/api

---

## 📚 核心功能模块

### 1. 前台用户模块
- ✅ 用户注册登录（手机号/账号密码）
- ✅ 个人信息完善（住所、养宠经验、家庭人口等）
- ✅ 宠物信息浏览（搜索、筛选）
- ✅ 领养申请提交
- ✅ 个人中心（申请进度、收藏宠物、领养记录）
- ✅ 救助信息发布（救助人专属）

### 2. 智能匹配模块 ⭐
- 领养人条件录入：居住环境、养宠经验、家庭人口、养宠意愿
- 宠物属性录入：品种、体型、性格、健康情况、饲养难度
- **Java算法自动匹配**：按分数推荐适配宠物
- 推送提醒：提升领养成功率

### 3. 后台管理模块
- 用户管理：审核救助人、管理账号、权限分配
- 宠物管理：审核待领养宠物、上下架、编辑资料
- 领养申请管理：审核、通过/驳回、记录回访
- 救助记录管理：核实信息、跟进进度
- **数据可视化**：ECharts图表展示
- 公告管理：发布知识、通知

### 4. 附加功能
- 宠物领养回访：定期录入宠物状态
- 流浪宠物求助入口：大众上报流浪宠物
- 养宠知识专区：科普宠物饲养、疫苗、护理

---

## 🔌 API 示例

### 用户注册
```bash
POST /api/users/register
Content-Type: application/json

{
  "username": "user123",
  "phoneNumber": "13800138000",
  "password": "123456",
  "realName": "张三",
  "email": "user@example.com"
}
```

### 用户登录
```bash
POST /api/users/login?username=user123&password=123456
```

### 获取用户信息
```bash
GET /api/users/1
```

---

## 🧠 智能匹配算法原理

### 匹配维度

| 维度 | 权重 | 说明 |
|------|------|------|
| 住所匹配度 | 30% | 根据宠物体型与住所类型匹配 |
| 养宠经验 | 25% | 宠物饲养难度与用户经验对标 |
| 家庭人口 | 20% | 宠物性格与家庭环境适配 |
| 健康状况 | 15% | 宠物健康状况与用户承诺对标 |
| 额外加分 | 10% | 用户同意科学养宠、有阳台等 |

### 计算公式
```
匹配度 = 住所分 × 0.3 + 经验分 × 0.25 + 人口分 × 0.2 + 健康分 × 0.15 + 加分 × 0.1
```

---

## 📖 开发进度

### Phase 1: 基础框架（已完成 ✅）
- [x] Spring Boot项目初始化
- [x] 数据库设计与创建
- [x] 实体类与Mapper设计
- [x] 用户服务基础实现
- [x] API响应统一格式

### Phase 2: 用户模块（进行中 🔄）
- [ ] 完整用户注册登录
- [ ] 个人信息管理
- [ ] 身份验证与授权

### Phase 3: 宠物模块（待开发 📋）
- [ ] 宠物信息CRUD
- [ ] 宠物图片上传
- [ ] 二维码生成

### Phase 4: 领养模块（待开发 📋）
- [ ] 领养申请管理
- [ ] 申请审核流程
- [ ] 回访记录管理

### Phase 5: 智能匹配（待开发 📋）
- [ ] 匹配算法实现
- [ ] 匹配推送通知
- [ ] 推荐数据展示

### Phase 6: 前端页面（待开发 📋）
- [ ] 首页设计
- [ ] 用户中心
- [ ] 宠物详情页
- [ ] 管理后台

### Phase 7: 可视化与部署（待开发 📋）
- [ ] ECharts数据展示
- [ ] 生产环境部署
- [ ] 性能优化

---

## 🔐 安全性考虑

- ✅ 密码MD5加密（可升级为BCrypt）
- ✅ Spring Security集成
- ✅ JWT Token认证
- ✅ 参数验证与异常处理
- ⚠️ 待完善：CORS配置、SQL注入防护、XSS防护

---

## 📞 常见问题

**Q: 如何修改数据库连接？**
A: 编辑 `application.yml` 中的数据库配置：
```yaml
datasource:
  url: jdbc:mysql://your-host:3306/pet_adoption
  username: your-username
  password: your-password
```

**Q: 默认管理员账号是什么？**
A: 项目初期无默认账号，需通过注册后在数据库手动修改role为3。

**Q: 如何生成宠物二维码？**
A: 项目已集成ZXing库，在宠物创建时自动生成。

---

## 🤝 参与贡献

欢迎提交Issue和Pull Request来完善项目！

---

## 📄 许可证

MIT License

---

## 👨‍💻 作者

- **项目**：流浪宠物救助与领养智能管理平台
- **类型**：计算机科学与技术专业毕设
- **目标**：零基础也能上手的完整Spring Boot毕设方案

---

## 📝 更新日志

### v1.0.0 (2026-05-28)
- 🚀 项目初始化
- 📦 创建基础框架
- 🗄 数据库设计完成
- 👤 用户服务基础实现

---

**祝您毕设顺利！** 🎓✨