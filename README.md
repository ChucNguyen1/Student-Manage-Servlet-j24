I. làm việc với gitflow

1️⃣ Clone repository về máy  
git clone https://github.com/company/student-management.git

2️⃣ Kiểm tra các branch hiện có  
git branch  
Kết quả: main: Chứa code ổn định, bản release/production  
          develop: Nhánh phát triển chính, chứa code đã qua test

3️⃣ Chuyển sang nhánh develop  
develop là nơi chứa code cập nhật nhất mà nhóm đang phát triển.  
Bạn không nên code trực tiếp trên develop.

4️⃣ Tạo nhánh feature riêng cho công việc của mình  
VD: git checkout -b feature/add-login-page

5️⃣ Làm việc, code, commit  
Sau khi code xong:  
git add .  
git commit -m "comment"

6️⃣ Đẩy nhánh của bạn lên GitHub  
git push -u origin feature/add-login-page

7️⃣ Tạo Pull Request (PR) trên GitHub  
Trên giao diện GitHub:  
Chọn nhánh feature/add-login-page  
Nhấn "Compare & pull request" -> Chọn merge vào nhánh develop -> Viết mô tả PR (ví dụ: “Hoàn thành giao diện login”) -> Nhấn Create Pull Request

8️⃣ Sau khi PR được merge, cập nhật lại develop  
Quay về develop:  
git checkout develop  
git pull origin develop  
Rồi xóa nhánh feature cũ:  
git branch -d feature/add-login-page  
git push origin --delete feature/add-login-page

II. Java web application - Student-Management

Features:  
Database: SQL server  
Language: JAVA  
Programs: eclipse, apache tomcat 11.0

##  Setup môi trường (Quan trọng!)

### 1. Cấu hình Database

Tạo file `.env` từ file mẫu:

```bash
# Copy file mẫu
cp src/main/resources/.env.example src/main/resources/.env
```

Sau đó mở file `.env` và điền thông tin database của bạn:

```properties
DB_URL=jdbc:sqlserver://localhost:1433;databaseName=StudentManagementDB;encrypt=false
DB_USERNAME=sa
DB_PASSWORD=your_actual_password
```

### 2. Cấu hình Gemini AI (cho chatbot)

Tạo file `config.properties` từ file mẫu:

```bash
# Copy file mẫu
cp src/main/resources/config.properties.example src/main/resources/config.properties
```

Lấy API key từ [Google AI Studio](https://aistudio.google.com/apikey) và điền vào:

```properties
gemini.api.key=YOUR_ACTUAL_API_KEY
```

### 3. Import Database

- Mở SQL Server Management Studio
- Chạy các file script trong thư mục `database/`
- Tạo database tên `StudentManagementDB`

### 4. Chạy ứng dụng

- Mở project trong Eclipse
- Cấu hình Apache Tomcat 11.0
- Run project

 **Lưu ý:** Các file `.env` và `config.properties` chứa thông tin nhạy cảm, đã được ignore trong git. KHÔNG commit các file này!
