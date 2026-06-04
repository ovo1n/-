# API 文档

## 基础信息

- **基础 URL**: http://localhost:8080/api
- **响应格式**: JSON
- **认证**: 暂未启用（可使用 JWT Token）

---

## 用户相关 API

### 1. 用户注册

**请求方式**: POST  
**端点**: `/users/register`

**请求体**:
```json
{
  "username": "user123",
  "phoneNumber": "13800138000",
  "password": "123456",
  "realName": "张三",
  "email": "user@example.com"
}
```

**响应示例**:
```json
{
  "code": 200,
  "message": "注册成功",
  "data": {
    "id": 1,
    "username": "user123",
    "phoneNumber": "13800138000",
    "realName": "张三"
  }
}
```

### 2. 用户登录

**请求方式**: POST  
**端点**: `/users/login?username=user123&password=123456`

### 3. 获取用户信息

**请求方式**: GET  
**端点**: `/users/{userId}`

---

## 宠物相关 API

### 1. 获取待领养宠物列表

**请求方式**: GET  
**端点**: `/pets/pending?pageNum=1&pageSize=10`

### 2. 搜索宠物

**请求方式**: GET  
**端点**: `/pets/search?keyword=白`

### 3. 创建宠物信息

**请求方式**: POST  
**端点**: `/pets`

---

## 领养申请 API

### 1. 提交领养申请

**请求方式**: POST  
**端点**: `/adoptions`

### 2. 查询用户的领养申请

**请求方式**: GET  
**端点**: `/adoptions/user/{userId}`

### 3. 审核通过申请

**请求方式**: POST  
**端点**: `/adoptions/{applicationId}/approve?reviewerId=1&comment=同意`

---

## 智能匹配 API

### 1. 获取用户推荐宠物

**请求方式**: GET  
**端点**: `/matching/recommendations/{userId}?limit=10`

### 2. 生成推荐

**请求方式**: POST  
**端点**: `/matching/generate/{userId}`

---

## 统计 API

### 1. 获取仪表板数据

**请求方式**: GET  
**端点**: `/statistics/dashboard`
