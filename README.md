I. làm việc với gitflow
1️⃣ Clone repository về máy
  git clone https://github.com/company/student-management.git
2️⃣ Kiểm tra các branch hiện có
  git branch
  Kết quả: main:	Chứa code ổn định, bản release/production
          develop:	Nhánh phát triển chính, chứa code đã qua test
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
  Chọn nhánh feature/add-login-page Nhấn "Compare & pull request" -> Chọn merge vào nhánh develop -> Viết mô tả PR (ví dụ: “Hoàn thành giao diện login”) -> Nhấn Create Pull Request
8️⃣ Sau khi PR được merge, cập nhật lại develop
  Quay về develop:
    git checkout develop
    git pull origin develop
  Rồi xóa nhánh feature cũ: git branch -d feature/add-login-page
                            git push origin --delete feature/add-login-page
II. Java web application - Student-Management
Features:
Database: SQL server
Language: JAVA 
Programs: eclipse, apache tomcat 11.0
Steps to reproduce:
Mở Eclipse -> Vào database lấy data -> chạy trên SQL Server(se) -> vào src/main/java đến com.student.utils đến DBConnection sửa đường dẫn và password Sql vào và test.



