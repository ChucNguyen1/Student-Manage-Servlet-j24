# 🤖 Hướng Dẫn Sử Dụng Chatbot Thông Minh

## 📋 Tổng Quan

Chatbot đã được nâng cấp từ phiên bản trả lời **generic** (chung chung) sang phiên bản **intelligent** (thông minh) với khả năng:

- ✅ Trả lời **chính xác** dựa trên dữ liệu thực từ database
- ✅ Hiển thị **lịch học cụ thể** theo ngày (Thứ 2, Thứ 3,...)
- ✅ Tra cứu **điểm số từng môn** học
- ✅ Tính toán **điểm trung bình** tự động
- ✅ Phân tích câu hỏi bằng **NLP đơn giản**

---

## 🎯 Các Tính Năng Chính

### 1️⃣ **Hỏi về Thời Khóa Biểu**

#### Câu hỏi mẫu:

```
✅ "Thứ 2 học gì?"
✅ "T3 có mấy tiết?"
✅ "Hôm nay học môn gì?"
✅ "Lịch học thứ 5"
✅ "Xem thời khóa biểu tuần này"
```

#### Kết quả trả về:

```
📅 **Lịch học Thứ 2:**

⏰ Tiết 1: **Toán** (Phòng: A101)
⏰ Tiết 2: **Toán** (Phòng: A101)
⏰ Tiết 3: **Văn** (Phòng: B205)
⏰ Tiết 4: **Anh** (Phòng: C303)
```

---

### 2️⃣ **Hỏi về Điểm Số Cụ Thể**

#### Câu hỏi mẫu:

```
✅ "Điểm toán bao nhiêu?"
✅ "Xem điểm văn"
✅ "Điểm môn anh"
✅ "Điểm lý của tôi"
```

#### Kết quả trả về:

```
📚 **Môn Toán:**

🗣️ Điểm miệng 1: 8.0
🗣️ Điểm miệng 2: 9.0
📝 Điểm 15p (1): 7.5
📝 Điểm 15p (2): 8.5
📄 Điểm 1 tiết (1): 8.0
📋 Điểm thi: 9.0

**Điểm TB:** 8.4
**Xếp loại:** Giỏi ⭐
```

---

### 3️⃣ **Hỏi về Điểm Trung Bình**

#### Câu hỏi mẫu:

```
✅ "Điểm trung bình bao nhiêu?"
✅ "Điểm TB học kỳ này?"
✅ "Xem điểm GPA"
✅ "Tổng kết điểm"
```

#### Kết quả trả về:

```
📊 **Điểm trung bình học kỳ:** 8.25
**Xếp loại:** Giỏi ⭐
```

---

### 4️⃣ **Hỏi về Thông Tin Cá Nhân**

#### Câu hỏi mẫu:

```
✅ "Tôi học lớp nào?"
✅ "Thông tin của tôi"
✅ "Tên tôi là gì?"
```

#### Kết quả trả về:

```
📋 **Thông tin của bạn:**
- Họ tên: Nguyễn Văn A
- Lớp: 10A1
- Giới tính: Nam
```

---

## 🛠️ Kiến Trúc Kỹ Thuật

### **Backend: ChatbotServlet.java**

```
/api/student/chatbot (POST)
│
├─ Nhận câu hỏi từ user
├─ Phân tích ý định (Intent Recognition)
│  ├─ Thời khóa biểu? → handleScheduleQuestion()
│  ├─ Điểm số? → handleGradeQuestion()
│  └─ Thông tin cá nhân? → Trả về HocSinh info
│
├─ Gọi DAO tương ứng
│  ├─ ThoiKhoaBieuDAO.findByLopAndHocKy()
│  ├─ DiemChiTietDAO.getBangDiemCaNhan()
│  └─ HocSinhDAO.findById()
│
└─ Trả về JSON response
   {
     "success": true,
     "answer": "📅 Lịch học Thứ 2: ..."
   }
```

### **Frontend: chatbox.js**

```javascript
// Kiểm tra câu hỏi có cần dữ liệu thực không?
needsRealData(message) {
  // Regex matching:
  // - /thứ\s*[2-7]|t[2-7]/ → Lịch học
  // - /điểm.*(toán|văn|anh)/ → Điểm cụ thể
  // - /điểm.*(trung bình|tb)/ → ĐTB
  return true/false;
}

// Nếu cần dữ liệu thực → Gọi API
fetchBotResponse(userMessage) {
  fetch("/api/student/chatbot", {
    method: "POST",
    body: JSON.stringify({ message: userMessage })
  })
  .then(response => response.json())
  .then(data => this.addMessage(data.answer, false));
}
```

---

## 🧠 Thuật Toán Phân Tích Câu Hỏi

### **1. Trích xuất ngày trong tuần**

```java
private Integer extractDayOfWeek(String msg) {
  if (msg.contains("thứ 2") || msg.contains("t2")) return 2;
  if (msg.contains("thứ 3") || msg.contains("t3")) return 3;
  // ...
  if (msg.contains("hôm nay")) {
    Calendar cal = Calendar.getInstance();
    return cal.get(Calendar.DAY_OF_WEEK); // 2=T2, 3=T3,...
  }
  return null; // Không tìm thấy → Hiển thị cả tuần
}
```

### **2. Trích xuất tên môn học**

```java
private String extractSubjectName(String msg) {
  String[] subjects = {
    "toán", "văn", "anh", "lý", "hóa",
    "sinh", "sử", "địa", "gdcd", "tin", "thể dục"
  };

  for (String subject : subjects) {
    if (msg.contains(subject)) return subject;
  }
  return null; // Không tìm thấy → Hiển thị tất cả môn
}
```

### **3. Tính điểm trung bình**

Công thức: `(ĐM + Đ15p + Đ1T×2 + ĐThi×3) / 7`

```java
private double calculateDiemTB(DiemChiTiet d) {
  // Nếu có sẵn diemTBM → dùng luôn
  if (d.getDiemTBM() != null) return d.getDiemTBM();

  // Tính TB điểm miệng (trung bình 3 lần)
  double diemMieng = avg(dm1, dm2, dm3);

  // Tính TB điểm 15p (trung bình 3 lần)
  double diem15p = avg(d15p1, d15p2, d15p3);

  // Tính TB điểm 1 tiết (trung bình 2 lần)
  double diem1Tiet = avg(d1t1, d1t2);

  // Điểm thi
  double diemThi = d.getDiemThi();

  // Tổng hợp
  return (diemMieng + diem15p + diem1Tiet*2 + diemThi*3) / 7.0;
}
```

---

## 📊 Bảng Xếp Loại

| Điểm TB | Xếp Loại   | Emoji |
| ------- | ---------- | ----- |
| ≥ 9.0   | Xuất sắc   | 🏆    |
| ≥ 8.0   | Giỏi       | ⭐    |
| ≥ 6.5   | Khá        | 👍    |
| ≥ 5.0   | Trung bình | 📚    |
| < 5.0   | Yếu        | 💪    |

---

## 🔐 Bảo Mật

### **Session Validation**

```java
// Kiểm tra session tồn tại
HttpSession session = request.getSession(false);
if (session == null) {
  sendError(response, "Chưa đăng nhập");
  return;
}

// Kiểm tra role HOCSINH
TaiKhoan account = (TaiKhoan) session.getAttribute("account");
if (!"HOCSINH".equals(account.getRole())) {
  sendError(response, "Không có quyền truy cập");
  return;
}
```

### **SQL Injection Prevention**

- Sử dụng **PreparedStatement** trong DAO
- Không nối chuỗi trực tiếp với input user
- Validate input trước khi query

---

## 🧪 Test Cases

### **Test 1: Lịch học**

```
Input: "Thứ 2 học gì?"
Expected: Danh sách tiết học Thứ 2 với tên môn + phòng học
```

### **Test 2: Điểm cụ thể**

```
Input: "Điểm toán bao nhiêu?"
Expected: Chi tiết điểm miệng, 15p, 1 tiết, thi + Điểm TB
```

### **Test 3: Điểm TB**

```
Input: "Điểm trung bình?"
Expected: Điểm TB học kỳ + Xếp loại + Emoji
```

### **Test 4: Hôm nay**

```
Input: "Hôm nay học mấy tiết?"
Expected: Lịch học của ngày hiện tại (dựa vào Calendar)
```

### **Test 5: Không có dữ liệu**

```
Input: "Điểm văn?"
Scenario: User chưa có điểm văn
Expected: "Không tìm thấy môn văn trong danh sách..."
```

---

## 🚀 Deployment

### **1. Build Project**

```bash
mvn clean package
```

### **2. Deploy to Tomcat**

```bash
cp target/student-management-j24.war $TOMCAT_HOME/webapps/
```

### **3. Restart Server**

```bash
# Windows
cd %TOMCAT_HOME%\bin
shutdown.bat
startup.bat

# Linux/Mac
cd $TOMCAT_HOME/bin
./shutdown.sh
./startup.sh
```

### **4. Test API**

```bash
curl -X POST http://localhost:8080/student-management-j24/api/student/chatbot \
  -H "Content-Type: application/json" \
  -d '{"message":"Thứ 2 học gì?"}' \
  -b "JSESSIONID=<your-session>"
```

---

## 📈 Future Enhancements

### **Phase 2: Natural Language Processing**

- [ ] Sử dụng **Apache OpenNLP** cho phân tích câu hỏi
- [ ] Hỗ trợ **typo correction** (e.g., "diem toan" → "điểm toán")
- [ ] Phát hiện **ngữ cảnh** từ lịch sử chat

### **Phase 3: AI Integration**

- [ ] Tích hợp **Google Gemini AI** để phân tích intent
- [ ] Chatbot gợi ý **cách cải thiện điểm**
- [ ] Dự đoán **xu hướng điểm số** dựa trên lịch sử

### **Phase 4: Multi-platform**

- [ ] **Mobile App** (React Native)
- [ ] **Telegram Bot** integration
- [ ] **Voice input** (Speech-to-Text)

---

## 🐛 Troubleshooting

### **Lỗi 1: "Chưa đăng nhập"**

**Nguyên nhân:** Session đã hết hạn hoặc chưa login

**Giải pháp:**

1. Đăng nhập lại vào hệ thống
2. Kiểm tra cookie `JSESSIONID` trong DevTools

---

### **Lỗi 2: "Không kết nối được đến server"**

**Nguyên nhân:** Backend server chưa chạy hoặc URL sai

**Giải pháp:**

1. Kiểm tra Tomcat đã start: `http://localhost:8080`
2. Kiểm tra `<meta name="base-url">` trong JSP
3. Xem console log: `F12` → Console

---

### **Lỗi 3: Trả về "Bạn chưa có điểm nào"**

**Nguyên nhân:** Database không có dữ liệu điểm

**Giải pháp:**

1. Kiểm tra bảng `diem_chi_tiet`:
   ```sql
   SELECT * FROM diem_chi_tiet WHERE maHS = 1;
   ```
2. Thêm dữ liệu test nếu cần

---

## 📚 References

- **Jakarta Servlet API**: https://jakarta.ee/specifications/servlet/
- **Gson Documentation**: https://github.com/google/gson
- **Regex Tutorial**: https://regexr.com/
- **Java Stream API**: https://docs.oracle.com/javase/8/docs/api/java/util/stream/Stream.html

---

## 👨‍💻 Contributors

- **Backend Developer**: ChatbotServlet.java, DAO integration
- **Frontend Developer**: chatbox.js, UI/UX improvements
- **Database Designer**: Schema optimization for performance

---

## 📝 License

MIT License - Dự án quản lý học sinh

---

**🎉 Chúc bạn sử dụng chatbot hiệu quả!**
