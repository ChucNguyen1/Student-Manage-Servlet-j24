# Hệ Thống Quản Lý Phân Quyền & Tài Khoản

## 🎯 Tính Năng Chính

### 1. **Sinh Tài Khoản Tự Động (Auto Provisioning)**

Tự động tạo hàng loạt tài khoản cho học sinh và giáo viên chưa có tài khoản.

#### Đặc điểm:

- ✅ **Sinh tài khoản học sinh**: Tạo tự động cho tất cả học sinh đang hoạt động
  - Username: `HS{mã học sinh}` (ví dụ: HS1, HS2, HS100)
  - Password mặc định: `123456`
  - Role: `HOCSINH`
- ✅ **Sinh tài khoản giáo viên**: Tạo tự động cho tất cả giáo viên đang hoạt động

  - Username: `GV{mã giáo viên}` (ví dụ: GV1, GV2, GV50)
  - Password mặc định: `123456`
  - Role: `GIAOVIEN`

- ✅ **Batch Insert**: Sử dụng transaction để đảm bảo tính toàn vẹn dữ liệu
- ✅ **Kiểm tra trùng lặp**: Tự động bỏ qua những người đã có tài khoản

#### Cách sử dụng:

1. Truy cập: **Admin Dashboard → Quản Lý Tài Khoản**
2. Nhấn nút **"Sinh Tự Động"** ở phần tương ứng
3. Xác nhận để bắt đầu quá trình tạo tài khoản
4. Hệ thống sẽ hiển thị số lượng tài khoản được tạo thành công

---

### 2. **Kiểm Soát Trạng Thái (Status Control)**

Quản lý trạng thái hoạt động của tài khoản.

#### Đặc điểm:

- 🔒 **Khóa tài khoản**: Vô hiệu hóa tài khoản khi:

  - Học sinh ra trường/chuyển trường
  - Giáo viên nghỉ việc/ngừng hoạt động
  - Vi phạm quy định

- 🔓 **Mở khóa tài khoản**: Kích hoạt lại tài khoản đã bị khóa
- ⚡ **Thay đổi tức thì**: Có hiệu lực ngay lập tức
- 🚫 **Ngăn đăng nhập**: Tài khoản bị khóa không thể đăng nhập

#### Cách sử dụng:

1. Tìm tài khoản cần xử lý trong danh sách
2. Nhấn nút **"Khóa"** (màu vàng) để khóa tài khoản
3. Nhấn nút **"Mở"** (màu xanh) để mở khóa
4. Xác nhận hành động

---

### 3. **Reset Mật Khẩu (Password Reset)**

Hỗ trợ người dùng khi quên mật khẩu.

#### Đặc điểm:

- 🔑 **Reset về mặc định**: Đặt lại mật khẩu thành `123456`
- 📧 **Thông báo**: Admin được thông báo kết quả reset
- 🔐 **Bảo mật**: Yêu cầu xác nhận trước khi thực hiện
- ✏️ **Khuyến nghị**: Người dùng nên đổi mật khẩu sau khi đăng nhập lần đầu

#### Cách sử dụng:

1. Tìm tài khoản cần reset mật khẩu
2. Nhấn nút **"Reset"** (màu xanh dương)
3. Xác nhận việc reset mật khẩu
4. Thông báo cho người dùng mật khẩu mới là: `123456`

---

## 📋 Giao Diện Quản Lý

### Danh Sách Tài Khoản

Hiển thị đầy đủ thông tin:

- ✓ Mã tài khoản
- ✓ Username
- ✓ Họ tên người dùng
- ✓ Vai trò (Admin/Giáo viên/Học sinh)
- ✓ Trạng thái (Hoạt động/Đã khóa)
- ✓ Ngày tạo tài khoản
- ✓ Các thao tác (Khóa/Mở/Reset)

### Tính Năng Bổ Sung

- 🔍 **Tìm kiếm**: Theo username hoặc họ tên
- 📄 **Phân trang**: Hiển thị 10 tài khoản/trang
- 🏷️ **Badge màu sắc**: Phân biệt rõ ràng vai trò và trạng thái
- 📊 **Thống kê**: Tổng số tài khoản trong hệ thống

---

## 🔐 Phân Quyền

### Quyền Hạn

Chức năng này **chỉ dành cho ADMIN**:

- ❌ Giáo viên không có quyền truy cập
- ❌ Học sinh không có quyền truy cập
- ✅ Admin có toàn quyền quản lý tài khoản

### Bảo Mật

- 🛡️ Mật khẩu được lưu trữ an toàn
- 🔒 Sử dụng PreparedStatement chống SQL Injection
- ⚠️ Xác nhận trước khi thực hiện hành động quan trọng
- 📝 Logging đầy đủ các thao tác

---

## 💾 Cấu Trúc Database

### Bảng TaiKhoan

```sql
maTK         INT PRIMARY KEY IDENTITY
username     VARCHAR(50) UNIQUE NOT NULL
password     VARCHAR(100) NOT NULL
role         VARCHAR(20) NOT NULL -- 'ADMIN', 'GIAOVIEN', 'HOCSINH'
maGV         INT NULL              -- FK to GiaoVien
maHS         INT NULL              -- FK to HocSinh
isActive     BIT DEFAULT 1         -- Trạng thái hoạt động
createdAt    DATETIME DEFAULT GETDATE()
updatedAt    DATETIME DEFAULT GETDATE()
```

---

## 🚀 API Endpoints

| Endpoint                                  | Method | Chức năng                        |
| ----------------------------------------- | ------ | -------------------------------- |
| `/admin/taikhoan-list`                    | GET    | Hiển thị danh sách tài khoản     |
| `/admin/taikhoan-toggle-status`           | GET    | Khóa/Mở khóa tài khoản           |
| `/admin/taikhoan-reset-password`          | GET    | Reset mật khẩu về mặc định       |
| `/admin/taikhoan-auto-provision-students` | GET    | Sinh tài khoản học sinh tự động  |
| `/admin/taikhoan-auto-provision-teachers` | GET    | Sinh tài khoản giáo viên tự động |

---

## 📝 Hướng Dẫn Sử Dụng Chi Tiết

### Kịch bản 1: Đầu năm học mới - 100 học sinh nhập học

1. Admin đăng nhập vào hệ thống
2. Thêm 100 học sinh mới vào hệ thống (qua chức năng Quản lý học sinh)
3. Truy cập **Quản Lý Tài Khoản**
4. Nhấn **"Sinh Tài Khoản Học Sinh"** → **"Sinh Tự Động"**
5. Hệ thống tự động tạo 100 tài khoản với:
   - Username: HS1 đến HS100
   - Password: 123456
6. Phát username/password cho học sinh

### Kịch bản 2: Học sinh ra trường

1. Tìm tài khoản của học sinh trong danh sách
2. Nhấn nút **"Khóa"**
3. Xác nhận → Tài khoản bị vô hiệu hóa
4. Học sinh không thể đăng nhập vào hệ thống

### Kịch bản 3: Học sinh quên mật khẩu

1. Admin tìm tài khoản của học sinh
2. Nhấn nút **"Reset"**
3. Xác nhận reset về mật khẩu mặc định `123456`
4. Thông báo cho học sinh mật khẩu mới
5. Khuyến nghị học sinh đổi mật khẩu sau khi đăng nhập

### Kịch bản 4: Học sinh chuyển đến hoặc thêm học sinh mới giữa năm học

1. Admin thêm học sinh mới vào hệ thống (qua chức năng Quản lý học sinh)
   - Ví dụ: Thêm học sinh có mã HS101
2. Truy cập **Quản Lý Tài Khoản**
3. Nhấn **"Sinh Tài Khoản Học Sinh"** → **"Sinh Tự Động"**
4. Hệ thống sẽ:
   - Tự động phát hiện học sinh mã HS101 chưa có tài khoản
   - Tạo tài khoản mới với Username: `HS101`, Password: `123456`
   - Bỏ qua các học sinh đã có tài khoản (HS1-HS100)
   - Thông báo: "Đã tạo 1/1 tài khoản học sinh"
5. Phát username/password cho học sinh mới

**Lưu ý**: Chức năng "Sinh Tự Động" thông minh - chỉ tạo tài khoản cho những người chưa có, không ảnh hưởng đến tài khoản hiện có.

---

## 🎨 Screenshots & Features

### Card Sinh Tài Khoản Tự Động

- 🟦 Card màu xanh cho Học sinh
- 🟩 Card màu xanh lá cho Giáo viên
- Hiển thị rõ format username và password
- Nút action với icon lightning bolt

### Bảng Danh Sách

- Badge màu đỏ: Admin
- Badge màu xanh lá: Giáo viên
- Badge màu xanh dương: Học sinh
- Badge xanh: Tài khoản đang hoạt động
- Badge đỏ: Tài khoản bị khóa

### Toast Notifications

- ✅ Thành công: Màu xanh lá
- ❌ Lỗi: Màu đỏ
- ℹ️ Thông tin: Màu xanh dương
- Tự động ẩn sau 5 giây

---

## ⚙️ Cấu Hình

### Mật Khẩu Mặc Định

Có thể thay đổi trong `TaiKhoanController.java`:

```java
private static final String DEFAULT_PASSWORD = "123456";
```

### Số Lượng Hiển Thị/Trang

Có thể điều chỉnh trong `TaiKhoanController.java`:

```java
int pageSize = 10; // Thay đổi số này
```

---

## 🔧 Công Nghệ Sử Dụng

- **Backend**: Java Servlet, JSP, JSTL
- **Frontend**: Bootstrap 5, Bootstrap Icons
- **Database**: SQL Server
- **Pattern**: MVC, DAO, Service Layer
- **Security**: PreparedStatement, Input Validation

---

## 📌 Lưu Ý Quan Trọng

1. ⚠️ **Backup database** trước khi sinh tài khoản hàng loạt
2. 🔐 **Khuyến nghị**: Yêu cầu người dùng đổi mật khẩu lần đầu đăng nhập
3. 📝 **Logging**: Tất cả thao tác quan trọng được log trong console
4. 🚫 **Không thể xóa**: Chức năng chỉ hỗ trợ khóa, không xóa tài khoản
5. ✅ **Transaction**: Sử dụng batch insert với transaction để đảm bảo an toàn

---

## 🐛 Troubleshooting

### Lỗi: Không tạo được tài khoản

- Kiểm tra database connection
- Xem log trong console
- Đảm bảo học sinh/giáo viên có trạng thái = 1 (đang hoạt động)

### Lỗi: Username đã tồn tại

- Hệ thống tự động bỏ qua
- Kiểm tra lại danh sách tài khoản đã có

### Lỗi: Không thể khóa tài khoản

- Kiểm tra quyền admin
- Xem log lỗi trong console

---

## 📞 Hỗ Trợ

Nếu gặp vấn đề, vui lòng:

1. Kiểm tra console log
2. Kiểm tra database constraints
3. Xem lại hướng dẫn sử dụng

---

**Phát triển bởi**: Student Management System Team
**Phiên bản**: 1.0
**Ngày cập nhật**: 28/12/2025
