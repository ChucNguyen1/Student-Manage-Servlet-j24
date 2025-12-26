# JWT Authentication System - Hướng Dẫn Sử Dụng

## 📋 Tổng Quan

Hệ thống JWT Authentication đã được triển khai hoàn chỉnh cho ứng dụng Student Management với các tính năng:

- ✅ Access Token (30 phút)
- ✅ Refresh Token (7 ngày)
- ✅ Token Revocation (Logout)
- ✅ Role-based Authorization
- ✅ CORS Support
- ✅ Database Token Storage

---

## 🚀 Cài Đặt & Khởi Chạy

### 1. Cập Nhật Database

Chạy script SQL để tạo bảng `refresh_tokens`:

```sql
-- File: database/create_refresh_tokens_table.sql
-- Mở file này trong SQL Server Management Studio và chạy
```

Hoặc sử dụng command line:

```bash
sqlcmd -S localhost -d student_management -i database/create_refresh_tokens_table.sql
```

### 2. Build Project

```bash
mvn clean install
```

### 3. Deploy Application

- Deploy WAR file vào Tomcat
- Hoặc run trực tiếp từ Eclipse/IntelliJ

### 4. Test JWT Endpoints

Mở trình duyệt và truy cập:

```
http://localhost:8080/student-management-j24/jwt-test.html
```

---

## 🔑 API Endpoints

### Base URL

```
http://localhost:8080/student-management-j24
```

### 1. Login - `POST /api/auth/login`

**Request:**

```json
{
  "username": "admin",
  "password": "123456"
}
```

**Response (Success):**

```json
{
  "success": true,
  "message": "Login successful",
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9...",
  "expiresIn": 1800,
  "user": {
    "id": 1,
    "username": "admin",
    "role": "ADMIN",
    "fullName": "Administrator"
  }
}
```

**Response (Error):**

```json
{
  "success": false,
  "error": "Invalid username or password"
}
```

---

### 2. Refresh Token - `POST /api/auth/refresh`

**Request:**

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Response:**

```json
{
  "success": true,
  "accessToken": "eyJhbGciOiJIUzUxMiJ9...",
  "expiresIn": 1800
}
```

---

### 3. Logout - `POST /api/auth/logout`

**Request:**

```json
{
  "refreshToken": "eyJhbGciOiJIUzUxMiJ9..."
}
```

**Response:**

```json
{
  "success": true,
  "message": "Logged out successfully"
}
```

---

### 4. Protected Endpoints - `GET/POST /api/{role}/*`

**Request Headers:**

```
Authorization: Bearer eyJhbGciOiJIUzUxMiJ9...
Content-Type: application/json
```

**Examples:**

- `GET /api/admin/students` - Chỉ ADMIN
- `GET /api/teacher/classes` - Chỉ GIAOVIEN
- `GET /api/student/grades` - Chỉ HOCSINH

**Response (Success):**

```json
{
  "success": true,
  "data": [...]
}
```

**Response (Unauthorized - 401):**

```json
{
  "success": false,
  "error": "Invalid or expired token"
}
```

**Response (Forbidden - 403):**

```json
{
  "success": false,
  "error": "You don't have permission to access this resource"
}
```

---

## 🧪 Test với Postman/cURL

### Login

```bash
curl -X POST http://localhost:8080/student-management-j24/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{"username":"admin","password":"123456"}'
```

### Access Protected Endpoint

```bash
curl -X GET http://localhost:8080/student-management-j24/api/admin/students \
  -H "Authorization: Bearer YOUR_ACCESS_TOKEN" \
  -H "Content-Type: application/json"
```

### Refresh Token

```bash
curl -X POST http://localhost:8080/student-management-j24/api/auth/refresh \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"YOUR_REFRESH_TOKEN"}'
```

### Logout

```bash
curl -X POST http://localhost:8080/student-management-j24/api/auth/logout \
  -H "Content-Type: application/json" \
  -d '{"refreshToken":"YOUR_REFRESH_TOKEN"}'
```

---

## 🔐 Bảo Mật

### 1. Secret Key

**Development:**

- Secret key mặc định trong code (CHỈ DÙNG CHO TEST)

**Production:**

- Đặt biến môi trường:

```bash
# Windows
set JWT_SECRET_KEY=YourVerySecureAndLongSecretKeyHere

# Linux/Mac
export JWT_SECRET_KEY=YourVerySecureAndLongSecretKeyHere
```

### 2. Token Storage

**Client-side:**

- Access Token: localStorage/sessionStorage
- Refresh Token: httpOnly cookie (khuyến nghị) hoặc localStorage

**Server-side:**

- Refresh tokens được hash (SHA-256) và lưu trong database
- Có thể revoke tokens bất kỳ lúc nào

### 3. CORS Configuration

**Development:**

```java
// Allow all origins
Access-Control-Allow-Origin: *
```

**Production:**

```java
// Chỉ cho phép domain cụ thể
Access-Control-Allow-Origin: https://yourdomain.com
```

Sửa trong file: `src/main/java/com/student/filter/CorsFilter.java`

---

## 📊 Phân Quyền (Authorization)

| Role     | Endpoints        | Quyền        |
| -------- | ---------------- | ------------ |
| ADMIN    | `/api/admin/*`   | Full access  |
| ADMIN    | `/api/teacher/*` | ✅ Access    |
| ADMIN    | `/api/student/*` | ✅ Access    |
| GIAOVIEN | `/api/teacher/*` | ✅ Access    |
| GIAOVIEN | `/api/admin/*`   | ❌ Forbidden |
| HOCSINH  | `/api/student/*` | ✅ Access    |
| HOCSINH  | `/api/admin/*`   | ❌ Forbidden |

---

## 🛠️ Tạo API Controllers Mới

### Example: Admin API

```java
@WebServlet("/api/admin/students")
public class AdminStudentController extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lấy thông tin user từ request attributes (đã set bởi JWTAuthenticationFilter)
        Integer userId = (Integer) request.getAttribute("userId");
        String username = (String) request.getAttribute("username");
        String role = (String) request.getAttribute("role");
        TaiKhoan account = (TaiKhoan) request.getAttribute("account");

        // Business logic
        List<Student> students = studentService.getAllStudents();

        // Return JSON response
        ResponseUtil.sendSuccess(response, students);
    }
}
```

---

## 🔄 Token Lifecycle

```
┌─────────────────────────────────────────────────────────┐
│ 1. USER LOGIN                                           │
│    → POST /api/auth/login                               │
│    → Receive: accessToken + refreshToken                │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ 2. ACCESS RESOURCES (30 minutes)                        │
│    → GET /api/admin/students                            │
│    → Header: Authorization: Bearer {accessToken}        │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ 3. ACCESS TOKEN EXPIRES                                 │
│    → 401 Unauthorized                                   │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ 4. REFRESH TOKEN (7 days valid)                         │
│    → POST /api/auth/refresh                             │
│    → Body: {refreshToken}                               │
│    → Receive: new accessToken                           │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ 5. CONTINUE USING (with new accessToken)                │
└─────────────────────────────────────────────────────────┘
                            │
                            ▼
┌─────────────────────────────────────────────────────────┐
│ 6. LOGOUT                                               │
│    → POST /api/auth/logout                              │
│    → Refresh token revoked in database                  │
└─────────────────────────────────────────────────────────┘
```

---

## 📝 Database Maintenance

### Cleanup Expired Tokens

**Manual:**

```sql
EXEC sp_CleanupExpiredTokens;
```

**Automatic (SQL Server Agent):**

- Uncomment phần job schedule trong file `create_refresh_tokens_table.sql`
- Job sẽ chạy tự động mỗi ngày lúc 2:00 AM

---

## 🐛 Troubleshooting

### 1. "Invalid or expired token"

- Token đã hết hạn (> 30 phút)
- ✅ **Solution:** Dùng refresh token để lấy access token mới

### 2. "Refresh token has been revoked"

- User đã logout
- ✅ **Solution:** Login lại

### 3. "You don't have permission"

- Role không phù hợp với endpoint
- ✅ **Solution:** Kiểm tra role của user và endpoint đang access

### 4. CORS Error

- Frontend domain chưa được allow
- ✅ **Solution:** Cấu hình CORS trong `CorsFilter.java`

### 5. Database Connection Error

- Bảng `refresh_tokens` chưa tồn tại
- ✅ **Solution:** Chạy script SQL tạo bảng

---

## 📚 Files Đã Tạo

```
src/main/java/com/student/
├── controller/api/
│   └── AuthController.java          # Login, Refresh, Logout endpoints
├── dao/
│   └── RefreshTokenDAO.java         # Interface
├── dao/impl/
│   └── RefreshTokenDAOImpl.java     # Implementation
├── dto/
│   ├── LoginRequest.java            # DTO cho login request
│   ├── LoginResponse.java           # DTO cho login response
│   ├── ErrorResponse.java           # DTO cho error response
│   └── RefreshTokenRequest.java     # DTO cho refresh request
├── filter/
│   ├── JWTAuthenticationFilter.java # JWT validation filter
│   └── CorsFilter.java              # CORS support filter
├── model/
│   └── RefreshToken.java            # RefreshToken model
└── utils/
    ├── JWTUtil.java                 # JWT generation & validation
    └── ResponseUtil.java            # JSON response utilities

database/
└── create_refresh_tokens_table.sql  # Database schema

src/main/webapp/
└── jwt-test.html                    # Test interface

pom.xml                              # Updated with JWT dependencies
```

---

## 🎯 Next Steps

1. ✅ **Tạo API Controllers** cho Admin, Teacher, Student
2. ✅ **Integrate với Frontend** (React/Vue/Angular)
3. ✅ **Implement Rate Limiting** để chống brute force
4. ✅ **Add Logging** cho security events
5. ✅ **Setup HTTPS** cho production
6. ✅ **Implement Token Blacklist** (optional)

---

## 📞 Support

Nếu có vấn đề, kiểm tra:

1. Console logs trong browser (F12)
2. Server logs trong Tomcat
3. Database connection
4. Token expiration time

---

**Created:** December 25, 2025  
**Version:** 1.0  
**Author:** Student Management System Team
