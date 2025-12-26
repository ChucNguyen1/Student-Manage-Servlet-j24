# HƯỚNG DẪN CÀI ĐẶT & SỬ DỤNG TÍNH NĂNG GIA SƯ AI

## 📋 TỔNG QUAN

Tính năng **Gia sư AI** sử dụng Google Gemini AI để phân tích kết quả học tập của học sinh và đưa ra lời khuyên cá nhân hóa.

---

## 🔧 CÀI ĐẶT CHI TIẾT

### Bước 1: Lấy Google Gemini API Key

#### 1.1. Truy cập Google AI Studio

1. Mở trình duyệt và truy cập: **https://aistudio.google.com/**
2. Đăng nhập bằng tài khoản Google của bạn (Gmail)
   - Nếu chưa có tài khoản Google, tạo mới tại https://accounts.google.com/

#### 1.2. Tạo API Key

1. Sau khi đăng nhập, click vào **"Get API key"** ở menu bên trái
2. Hoặc truy cập trực tiếp: **https://aistudio.google.com/apikey**

3. Trên trang API Keys:
   - Click nút **"Create API Key"** màu xanh
4. Chọn Google Cloud Project:

   - **Option 1**: Chọn **"Create API key in new project"** (Khuyến nghị cho người mới)

     - Hệ thống sẽ tự tạo project mới
     - API key được tạo ngay lập tức

   - **Option 2**: Chọn project có sẵn (nếu bạn đã có Google Cloud project)
     - Dropdown list → Chọn project
     - Click "Create API key"

5. **Copy API Key**:
   ```
   Ví dụ API key: AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   ```
   - Click icon 📋 (Copy) bên cạnh API key
   - **LƯU Ý**: API key chỉ hiển thị 1 lần, lưu lại an toàn!

#### 1.3. Bật Gemini API (Nếu chưa kích hoạt)

1. Truy cập: https://console.cloud.google.com/
2. Chọn project vừa tạo
3. Tìm kiếm "Generative Language API" hoặc "Gemini API"
4. Click **"Enable"** để kích hoạt API

---

### Bước 2: Cấu hình API Key vào Hệ Thống

Có **3 cách** để cấu hình API key, chọn 1 trong 3:

#### 🔹 CÁCH 1: Config File (Khuyến nghị ⭐)

**Ưu điểm**: An toàn, dễ quản lý, không cần restart OS

**Các bước**:

1. Tạo file `config.properties` trong thư mục:

   ```
   src/main/resources/config.properties
   ```

2. Thêm nội dung:

   ```properties
   # Google Gemini AI Configuration
   gemini.api.key=AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   gemini.model=gemini-1.5-flash
   gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/

   # Optional Settings
   gemini.temperature=0.7
   gemini.max.tokens=2048
   ```

3. **Tạo file `.gitignore`** (nếu chưa có) và thêm:

   ```gitignore
   # Ignore config files containing secrets
   **/config.properties
   ```

4. **Tạo file template** `config.properties.example`:

   ```properties
   # Copy file này thành config.properties và điền API key
   gemini.api.key=YOUR_API_KEY_HERE
   gemini.model=gemini-1.5-flash
   gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/
   ```

5. Code đã hỗ trợ đọc từ `config.properties` tự động qua class `ConfigLoader`

---

#### 🔹 CÁCH 2: Environment Variable (Khuyến nghị cho Production)

**Ưu điểm**: An toàn nhất, không lưu trong code

**Windows**:

1. **Temporary (chỉ trong session hiện tại)**:

   ```cmd
   set GEMINI_API_KEY=AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   ```

2. **Permanent (lưu vĩnh viễn)**:

   ```cmd
   setx GEMINI_API_KEY "AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
   ```

   - **Lưu ý**: Phải **restart Terminal/Eclipse** sau khi chạy lệnh này

3. **Kiểm tra đã set thành công**:
   ```cmd
   echo %GEMINI_API_KEY%
   ```

**Linux/Mac**:

1. **Temporary**:

   ```bash
   export GEMINI_API_KEY="AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
   ```

2. **Permanent** (thêm vào file profile):

   ```bash
   # Mở file .bashrc hoặc .zshrc
   nano ~/.bashrc

   # Thêm dòng này vào cuối file:
   export GEMINI_API_KEY="AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"

   # Lưu và reload
   source ~/.bashrc
   ```

3. **Kiểm tra**:
   ```bash
   echo $GEMINI_API_KEY
   ```

**Tomcat/Server (Production)**:

1. **Tomcat setenv.sh (Linux/Mac)**:

   ```bash
   # Tạo/edit file: TOMCAT_HOME/bin/setenv.sh
   export GEMINI_API_KEY="AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx"
   ```

2. **Tomcat setenv.bat (Windows)**:

   ```cmd
   REM Tạo/edit file: TOMCAT_HOME\bin\setenv.bat
   set GEMINI_API_KEY=AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx
   ```

3. Restart Tomcat

---

#### 🔹 CÁCH 3: Hardcode (CHỈ DÙNG CHO TESTING)

**⚠️ CẢNH BÁO**:

- Không an toàn
- Không commit API key lên Git
- Chỉ dùng để test nhanh trên máy local

**Các bước**:

1. Mở file:

   ```
   src/main/java/com/student/service/AIAdvisorService.java
   ```

2. Tìm method `getGeminiApiKey()`:

   ```java
   private String getGeminiApiKey() {
       // 1. Ưu tiên: Environment variable
       String apiKey = System.getenv("GEMINI_API_KEY");
       if (apiKey != null && !apiKey.isEmpty()) {
           return apiKey;
       }

       // 2. Config file
       apiKey = ConfigLoader.get("gemini.api.key");
       if (apiKey != null && !apiKey.isEmpty()) {
           return apiKey;
       }

       // 3. Fallback: Hardcode (CHỈ DÙNG CHO DEV)
       return "YOUR_GEMINI_API_KEY_HERE"; // ← THAY ĐỔI TẠI ĐÂY
   }
   ```

3. Thay `YOUR_GEMINI_API_KEY_HERE` bằng API key thật:

   ```java
   return "AIzaSyBxxxxxxxxxxxxxxxxxxxxxxxxxxxxx";
   ```

4. **LƯU Ý**: Nhớ xóa hoặc dùng env variable trước khi commit code!

---

### Bước 3: Cài đặt Dependencies (Maven)

#### 3.1. Thêm Google Gson vào pom.xml

1. Mở file `pom.xml` trong project root

2. Tìm section `<dependencies>` và thêm:

```xml
<!-- Google Gson for JSON parsing -->
<dependency>
    <groupId>com.google.code.gson</groupId>
    <artifactId>gson</artifactId>
    <version>2.10.1</version>
</dependency>
```

3. **Vị trí đặt**: Bên trong tag `<dependencies>`, có thể đặt sau các dependency khác

#### 3.2. Update Maven Project

**Eclipse**:

1. Right-click vào project → Maven → Update Project
2. Hoặc: Alt + F5 → Check "Force Update" → OK

**IntelliJ IDEA**:

1. Right-click vào `pom.xml` → Maven → Reload Project
2. Hoặc: Click icon 🔄 ở Maven panel

**Command Line**:

```bash
mvn clean install
```

#### 3.3. Kiểm tra đã cài đặt thành công

1. Mở file `AIAdvisorService.java`
2. Kiểm tra import không bị lỗi:
   ```java
   import com.google.gson.Gson;
   import com.google.gson.JsonObject;
   ```
3. Nếu không có gạch đỏ → Thành công ✅

---

### Bước 4: Cấu hình Database (Nếu chưa có)

#### 4.1. Kiểm tra bảng cần thiết

Đảm bảo database có các bảng:

- `hoc_sinh` (Học sinh)
- `diem_chi_tiet` (Điểm chi tiết)
- `hoc_ky` (Học kỳ)
- `nam_hoc` (Năm học)
- `mon_hoc` (Môn học)
- `lop_hoc` (Lớp học)

#### 4.2. Kiểm tra DAO hoạt động

Test các DAO methods:

```java
DiemChiTietDAO diemDAO = new DiemChiTietDAOImpl();
List<DiemChiTiet> diem = diemDAO.getBangDiemCaNhan(1, 1); // maHS=1, maHK=1
System.out.println("Số môn: " + diem.size()); // Phải > 0
```

---

### Bước 5: Deploy và Test

#### 5.1. Build Project

**Maven**:

```bash
mvn clean package
```

**Eclipse**:

- Right-click project → Run As → Maven build
- Goals: `clean package`
- Run

#### 5.2. Deploy lên Tomcat

**Eclipse (với Tomcat Server)**:

1. Right-click project → Run As → Run on Server
2. Chọn Tomcat server → Finish
3. Đợi server start

**Manual Deploy**:

1. Copy file `.war` từ `target/` folder
2. Paste vào `TOMCAT_HOME/webapps/`
3. Restart Tomcat

#### 5.3. Test API Endpoint

**Test 1: Kiểm tra endpoint tồn tại**

Mở browser, truy cập (thay `8080` bằng port của bạn):

```
http://localhost:8080/student-management-j24/api/student/ai-advisor
```

Nếu thấy "Method Not Allowed" hoặc login redirect → **Thành công** (endpoint đã hoạt động)

**Test 2: Test với Postman/curl**

```bash
curl -X POST http://localhost:8080/student-management-j24/api/student/ai-advisor \
  -H "Content-Type: application/json" \
  -d '{"hocKyId":1,"namHocId":1}' \
  -b "JSESSIONID=<your-session-id>"
```

**Test 3: Test qua UI**

1. Đăng nhập với tài khoản học sinh
2. Vào menu **"Xem điểm"**
3. Click nút **"🤖 Phân tích AI"**
4. Mở **Console** (F12) để xem log
5. Kiểm tra response:
   - Thành công: Hiển thị kết quả phân tích
   - Lỗi: Kiểm tra console log và server log

---

### Bước 6: Kiểm tra API Key hoạt động

#### 6.1. Test API Key bằng curl

```bash
curl "https://generativelanguage.googleapis.com/v1beta/models/gemini-1.5-flash:generateContent?key=YOUR_API_KEY" \
  -H 'Content-Type: application/json' \
  -d '{
    "contents": [{
      "parts":[{"text": "Hello"}]
    }]
  }'
```

**Response mong đợi**:

```json
{
  "candidates": [
    {
      "content": {
        "parts": [{ "text": "Hello! How can I help you today?" }]
      }
    }
  ]
}
```

**Nếu lỗi 403**: API key không hợp lệ hoặc chưa enable Gemini API

#### 6.2. Check Server Log

Mở server log (Tomcat console) và tìm:

```
✅ Thành công:
Response from Gemini API: {"candidates":[...]}

❌ Lỗi:
API call failed with response code: 403
java.io.IOException: Server returned HTTP response code: 403
```

---

### Bước 7: Cấu hình Bảo mật (Production)

#### 7.1. Tạo .gitignore

Tạo/cập nhật file `.gitignore` trong project root:

```gitignore
# Compiled class files
*.class
target/

# Log files
*.log

# IDE files
.idea/
.settings/
.classpath
.project
*.iml

# Config files with secrets
**/config.properties
!**/config.properties.example

# OS files
.DS_Store
Thumbs.db
```

#### 7.2. Tạo config.properties.example

Tạo file template để đồng đội biết cần config gì:

```properties
# =============================================
# CONFIGURATION TEMPLATE
# Copy file này thành config.properties
# =============================================

# Google Gemini AI API Key
# Lấy từ: https://aistudio.google.com/apikey
gemini.api.key=YOUR_API_KEY_HERE

# Model name (không nên thay đổi)
gemini.model=gemini-1.5-flash

# API URL (không nên thay đổi)
gemini.api.url=https://generativelanguage.googleapis.com/v1beta/models/

# Optional: Temperature (0.0 - 1.0)
gemini.temperature=0.7

# Optional: Max tokens
gemini.max.tokens=2048
```

#### 7.3. Commit code an toàn

```bash
# Đảm bảo config.properties không bị track
git rm --cached src/main/resources/config.properties
git add .gitignore
git add src/main/resources/config.properties.example

# Commit
git commit -m "Add AI Advisor feature with secure config"
git push
```

---

## ✅ CHECKLIST CÀI ĐẶT

Đánh dấu ✓ khi hoàn thành:

- [ ] Đã tạo Google account và lấy Gemini API key
- [ ] Đã enable Gemini API trong Google Cloud Console
- [ ] Đã cấu hình API key (chọn 1 trong 3 cách)
- [ ] Đã test API key bằng curl (response code 200)
- [ ] Đã thêm Gson dependency vào pom.xml
- [ ] Đã Maven update project thành công
- [ ] Database có đầy đủ bảng và dữ liệu test
- [ ] Đã build project không lỗi (mvn clean package)
- [ ] Đã deploy lên Tomcat thành công
- [ ] API endpoint /api/student/ai-advisor hoạt động
- [ ] UI button "Phân tích AI" hiển thị đúng
- [ ] Click button → Modal hiển thị
- [ ] AI trả về kết quả (hoặc fallback response)
- [ ] Đã tạo .gitignore và config.properties.example
- [ ] Không commit API key lên Git

---

## 🚀 SỬ DỤNG

### Đối với Học sinh:

1. Đăng nhập vào hệ thống với tài khoản học sinh
2. Vào menu **"Xem điểm"**
3. Chọn học kỳ cần phân tích
4. Click nút **"🤖 Phân tích & Lời khuyên AI"**
5. Đợi vài giây để AI phân tích
6. Xem kết quả và áp dụng lời khuyên

### Kết quả AI cung cấp:

✅ **Tổng quan:** Đánh giá chung về kết quả học tập  
✅ **Điểm mạnh:** Những môn đang học tốt  
✅ **Điểm yếu:** Những môn cần cải thiện  
✅ **Xu hướng:** Đang tiến bộ hay tụt lại  
✅ **Lời khuyên:** Phương pháp học cụ thể, phân bổ thời gian  
✅ **Động viên:** Lời khích lệ tinh thần

---

## 📁 CẤU TRÚC FILE ĐÃ TẠO

```
src/main/java/com/student/
├── dto/
│   ├── StudentScoreData.java        # Model dữ liệu điểm số
│   ├── AIAdvisorRequest.java        # Request DTO
│   └── AIAdvisorResponse.java       # Response DTO
├── service/
│   └── AIAdvisorService.java        # Service xử lý AI logic
└── controller/api/
    └── StudentAIAdvisorServlet.java # API endpoint

src/main/webapp/WEB-INF/views/student/
└── xem-diem.jsp                      # Updated với UI & JavaScript
```

---

## 🔍 TEST THỬ

### Test với Mock Data (không cần API key):

Service có sẵn fallback response nếu AI không hoạt động. Để test:

1. Không cần set API key
2. Click nút "Phân tích AI"
3. Hệ thống sẽ trả về phân tích cơ bản dựa trên logic đơn giản

### Test với Gemini API:

1. Set API key theo hướng dẫn trên
2. Restart server
3. Click "Phân tích AI" → Nhận phân tích chi tiết từ AI

---

## ⚡ TỐI ƯU HÓA

### 1. Cache kết quả (Optional - Phase 2):

Tạo bảng `ai_analysis_cache`:

```sql
CREATE TABLE ai_analysis_cache (
    id INT PRIMARY KEY AUTO_INCREMENT,
    hoc_sinh_id INT,
    hoc_ky_id INT,
    nam_hoc_id INT,
    analysis_result TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (hoc_sinh_id) REFERENCES hoc_sinh(ma_hs)
);
```

### 2. Rate Limiting (Optional - Phase 2):

Giới hạn số lần gọi AI (5 lần/ngày/học sinh) để tiết kiệm chi phí.

---

## 💰 CHI PHÍ

### Google Gemini API Pricing:

- **Free tier:** 15 requests/phút, 1,500 requests/ngày
- **Đủ cho:** ~50-100 học sinh sử dụng/ngày
- **Chi phí:** MIỄN PHÍ cho development và trường học nhỏ

### Lưu ý:

- Mỗi lần phân tích tiêu tốn ~1,000 tokens
- Free tier đủ dùng cho testing và môi trường production nhỏ
- Nếu cần scale lớn, có thể nâng cấp lên paid plan

---

## 🐛 TROUBLESHOOTING

### Lỗi: "API call failed with response code: 403"

**Nguyên nhân:** API key không hợp lệ  
**Giải pháp:** Kiểm tra lại API key và enable Gemini API

### Lỗi: "Cannot find symbol: Gson"

**Nguyên nhân:** Thiếu dependency  
**Giải pháp:** Thêm Gson vào pom.xml và rebuild

### Lỗi: "Không thể kết nối đến server"

**Nguyên nhân:** CORS hoặc network issue  
**Giải pháp:** Kiểm tra CorsFilter đã được config đúng

### AI trả về kết quả không đúng format

**Nguyên nhân:** AI đôi khi không follow JSON format chính xác  
**Giải pháp:** Đã có fallback response, hệ thống vẫn hoạt động bình thường

---

## 📞 HỖ TRỢ

Nếu gặp vấn đề, kiểm tra:

1. Console log trong browser (F12)
2. Server log trong Eclipse/IntelliJ
3. File: `AIAdvisorService.java` dòng có `e.printStackTrace()`

---

## 🎯 NÂNG CAP TƯƠNG LAI

- [ ] Lưu lịch sử phân tích
- [ ] So sánh tiến bộ qua các kỳ
- [ ] Gợi ý tài liệu học tập cụ thể
- [ ] Chatbot tương tác 2 chiều
- [ ] Gửi email lời khuyên cho phụ huynh

---

**Chúc bạn triển khai thành công! 🚀**
