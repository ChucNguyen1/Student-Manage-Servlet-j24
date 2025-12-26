# 🔐 HƯỚNG DẪN BẢO MẬT - SECURITY BEST PRACTICES

## ⚠️ VẤN ĐỀ: Tại sao không hardcode API key trong code?

### 1. **Nguy cơ bị lộ:**

```java
// ❌ NGUY HIỂM - KHÔNG BAO GIỜ LÀM NHƯ NÀY!
private static final String API_KEY = "AIzaSyXXXXXXXXXXXXXXXXX";
```

**Hậu quả:**

- ✗ Code push lên GitHub → API key public
- ✗ Hacker dùng key của bạn → Hết quota/charge tiền
- ✗ Google phát hiện → Ban account
- ✗ Không thể thu hồi nếu bị leak

### 2. **Vi phạm best practices:**

- ✗ Không tách biệt được môi trường (dev/staging/prod)
- ✗ Mỗi lần đổi key phải sửa code và deploy lại
- ✗ Team member khác nhìn thấy được key
- ✗ Version control lưu lại history API key

---

## ✅ GIẢI PHÁP CHUYÊN NGHIỆP

### **Level 1: File config.properties (Development)**

```
student-management/
├── src/main/resources/
│   ├── config.properties          ← GIT IGNORE (chứa key thật)
│   └── config.properties.example  ← Commit (template)
└── .gitignore                     ← Thêm config.properties
```

**Cách dùng:**

1. Copy `config.properties.example` → `config.properties`
2. Điền API key thật vào
3. Git tự động ignore file này

**Ưu điểm:**

- ✓ Không commit sensitive data
- ✓ Dễ quản lý local
- ✓ Mỗi dev có key riêng

**Nhược điểm:**

- ⚠️ Phải copy file thủ công
- ⚠️ Cần cẩn thận khi deploy

---

### **Level 2: Environment Variables (Production)**

**Linux/Mac:**

```bash
export GEMINI_API_KEY="AIzaSyXXXXXXXXXXXXXXXXX"
```

**Windows:**

```cmd
setx GEMINI_API_KEY "AIzaSyXXXXXXXXXXXXXXXXX"
```

**Docker:**

```yaml
environment:
  - GEMINI_API_KEY=${GEMINI_API_KEY}
```

**Ưu điểm:**

- ✓ Tách biệt hoàn toàn code và config
- ✓ Dễ quản lý trên server
- ✓ Chuẩn 12-Factor App

---

### **Level 3: Secret Management (Enterprise)**

**AWS Secrets Manager:**

```java
AWSSecretsManager client = AWSSecretsManagerClientBuilder.standard()
    .withRegion("us-east-1")
    .build();

GetSecretValueRequest request = new GetSecretValueRequest()
    .withSecretId("prod/gemini/api-key");

GetSecretValueResult result = client.getSecretValue(request);
String apiKey = result.getSecretString();
```

**Azure Key Vault:**

```java
SecretClient secretClient = new SecretClientBuilder()
    .vaultUrl("https://my-vault.vault.azure.net")
    .credential(new DefaultAzureCredentialBuilder().build())
    .buildClient();

KeyVaultSecret secret = secretClient.getSecret("gemini-api-key");
String apiKey = secret.getValue();
```

**HashiCorp Vault:**

```java
VaultTemplate vaultTemplate = new VaultTemplate(endpoint, sessionManager);
String apiKey = vaultTemplate.read("secret/gemini-api-key").getData().get("value");
```

---

## 🛡️ CODE ĐÃ IMPLEMENT TRONG PROJECT

### **ConfigLoader.java** - Quản lý config an toàn

```java
// Thứ tự ưu tiên:
// 1. Environment variable (Production)
String key = System.getenv("GEMINI_API_KEY");

// 2. Config file (Development)
if (key == null) {
    key = ConfigLoader.get("gemini.api.key");
}

// 3. Fallback mode (Không có key)
if (key == null) {
    System.err.println("⚠️ Không có API key, dùng chế độ cơ bản");
}
```

---

## 📋 CHECKLIST BẢO MẬT

### **Trước khi commit:**

- [ ] Kiểm tra không có API key trong code
- [ ] File `config.properties` đã được git ignore
- [ ] Chỉ commit file `.example`
- [ ] Review changes với `git diff`

### **Trước khi deploy:**

- [ ] Set environment variables trên server
- [ ] Không upload file config.properties
- [ ] Test connection với API
- [ ] Enable logging để monitor usage

### **Khi API key bị leak:**

1. **NGAY LẬP TỨC** revoke key cũ tại Google Cloud Console
2. Generate key mới
3. Update key mới trên tất cả môi trường
4. Review git history, xóa key nếu đã commit
5. Report incident (nếu là production)

---

## 🎓 BEST PRACTICES CHO DEV

### **DO (Nên làm):**

- ✓ Dùng environment variables cho production
- ✓ Dùng file config (git ignored) cho development
- ✓ Rotate API keys định kỳ (3-6 tháng)
- ✓ Implement rate limiting
- ✓ Monitor API usage
- ✓ Log khi API key không tồn tại
- ✓ Có fallback mode khi API fail

### **DON'T (Không nên làm):**

- ✗ Hardcode API key trong code
- ✗ Commit file config có key thật
- ✗ Share key qua email/chat
- ✗ Dùng chung key cho nhiều môi trường
- ✗ Để key trong comments
- ✗ Screenshot code có chứa key
- ✗ Lưu key trong database không mã hóa

---

## 🚀 SETUP HƯỚNG DẪN

### **Bước 1: Tạo file config**

```bash
cd src/main/resources/
cp config.properties.example config.properties
```

### **Bước 2: Lấy API key**

1. Truy cập: https://aistudio.google.com/apikey
2. Đăng nhập Google Account
3. Click "Create API Key"
4. Copy key

### **Bước 3: Cấu hình**

Mở `config.properties`, thay đổi:

```properties
gemini.api.key=YOUR_ACTUAL_API_KEY_HERE
```

### **Bước 4: Verify**

```bash
# Check file đã được ignore
git status

# Không thấy config.properties trong danh sách = ✓ OK
```

---

## 📚 TÀI LIỆU THAM KHẢO

- [OWASP API Security Top 10](https://owasp.org/www-project-api-security/)
- [12-Factor App Config](https://12factor.net/config)
- [Google Cloud Secret Management](https://cloud.google.com/secret-manager)
- [GitHub Secret Scanning](https://docs.github.com/en/code-security/secret-scanning)

---

## 💡 LỜI KHUYÊN

> "Treat API keys like passwords - NEVER commit them to version control!"

> "If you accidentally commit a secret, consider it compromised immediately and rotate it."

> "Security is not a feature, it's a mindset."

---

**Nhớ rằng:** Một lần sơ suất có thể dẫn đến:

- 💸 Chi phí không mong muốn
- 🔓 Bị hack hệ thống
- 😰 Mất uy tín
- ⚖️ Vi phạm pháp luật (GDPR, etc.)

**Hãy luôn cẩn thận với sensitive data!** 🔐
