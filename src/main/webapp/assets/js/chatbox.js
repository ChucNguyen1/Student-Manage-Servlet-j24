/**
 * CHATBOX WIDGET
 * Hỗ trợ trò chuyện tự động cho học sinh
 */

(function () {
  "use strict";

  var chatbox = {
    messages: [],
    isTyping: false,
    lastContext: null, // Lưu context câu hỏi trước

    // Khởi tạo chatbox
    init: function () {
      this.createChatButton();
      this.createChatWindow();
      this.bindEvents();
      this.loadWelcomeMessage();
    },

    // Tạo nút chat floating
    createChatButton: function () {
      var btn = document.createElement("button");
      btn.className = "chat-button";
      btn.id = "chatButton";
      btn.innerHTML = '<i class="bi bi-chat-dots-fill"></i>';
      document.body.appendChild(btn);
    },

    // Tạo cửa sổ chat
    createChatWindow: function () {
      var chatHTML = `
        <div class="chat-window" id="chatWindow">
          <div class="chat-header">
            <div class="chat-header-info">
              <div class="chat-header-avatar">
                <i class="bi bi-robot"></i>
              </div>
              <div class="chat-header-text">
                <h6>Trợ lý ảo</h6>
                <p>Hỗ trợ học sinh 24/7</p>
              </div>
            </div>
            <button class="chat-close" id="chatClose">
              <i class="bi bi-x"></i>
            </button>
          </div>
          
          <div class="chat-messages" id="chatMessages">
            <!-- Messages will be added here -->
          </div>
          
          <div class="chat-input">
            <form class="chat-input-form" id="chatForm">
              <input 
                type="text" 
                class="chat-input-field" 
                id="chatInput"
                placeholder="Nhập tin nhắn..."
                autocomplete="off"
              >
              <button type="submit" class="chat-send-btn" id="chatSend">
                <i class="bi bi-send-fill"></i>
              </button>
            </form>
          </div>
        </div>
      `;

      document.body.insertAdjacentHTML("beforeend", chatHTML);
    },

    // Gán sự kiện
    bindEvents: function () {
      var self = this;
      var chatButton = document.getElementById("chatButton");
      var chatClose = document.getElementById("chatClose");
      var chatWindow = document.getElementById("chatWindow");
      var chatForm = document.getElementById("chatForm");

      // Toggle chat window
      chatButton.addEventListener("click", function () {
        chatWindow.classList.toggle("active");
        if (chatWindow.classList.contains("active")) {
          document.getElementById("chatInput").focus();
        }
      });

      // Close chat
      chatClose.addEventListener("click", function () {
        chatWindow.classList.remove("active");
      });

      // Submit message
      chatForm.addEventListener("submit", function (e) {
        e.preventDefault();
        self.sendMessage();
      });
    },

    // Thêm tin nhắn vào giao diện
    addMessage: function (content, isUser) {
      var messagesDiv = document.getElementById("chatMessages");
      var time = new Date().toLocaleTimeString("vi-VN", {
        hour: "2-digit",
        minute: "2-digit",
      });

      var messageHTML = `
        <div class="chat-message ${isUser ? "user" : "bot"}">
          <div>
            <div class="message-content">${this.escapeHtml(content)}</div>
            <div class="message-time">${time}</div>
          </div>
        </div>
      `;

      messagesDiv.insertAdjacentHTML("beforeend", messageHTML);
      messagesDiv.scrollTop = messagesDiv.scrollHeight;

      this.messages.push({
        content: content,
        isUser: isUser,
        time: time,
      });
    },

    // Hiển thị typing indicator
    showTyping: function () {
      var messagesDiv = document.getElementById("chatMessages");
      var typingHTML = `
        <div class="chat-message bot" id="typingIndicator">
          <div class="typing-indicator">
            <div class="typing-dot"></div>
            <div class="typing-dot"></div>
            <div class="typing-dot"></div>
          </div>
        </div>
      `;

      messagesDiv.insertAdjacentHTML("beforeend", typingHTML);
      messagesDiv.scrollTop = messagesDiv.scrollHeight;
      this.isTyping = true;
    },

    // Ẩn typing indicator
    hideTyping: function () {
      var indicator = document.getElementById("typingIndicator");
      if (indicator) {
        indicator.remove();
      }
      this.isTyping = false;
    },

    // Gửi tin nhắn
    sendMessage: function () {
      var input = document.getElementById("chatInput");
      var message = input.value.trim();

      if (!message) return;

      // Add user message
      this.addMessage(message, true);
      input.value = "";

      // Simulate bot response
      this.showTyping();

      var self = this;
      setTimeout(function () {
        self.hideTyping();
        self.getBotResponse(message);
      }, 1000 + Math.random() * 1000);
    },

    // Lấy phản hồi từ bot
    getBotResponse: function (userMessage) {
      var self = this;

      // Kiểm tra xem có phải câu hỏi cần dữ liệu thực không
      if (this.needsRealData(userMessage)) {
        // Gọi API backend
        this.fetchBotResponse(userMessage);
      } else {
        // Trả lời generic
        var response = this.getAutoResponse(userMessage);
        this.addMessage(response, false);
      }
    },

    // Kiểm tra câu hỏi có cần dữ liệu thực không
    needsRealData: function (message) {
      var msg = message.toLowerCase();

      // Câu hỏi về lịch học cụ thể
      if (msg.match(/thứ\s*[2-7]|t[2-7]|hôm nay.*học|học.*gì/)) {
        return true;
      }

      // Câu hỏi về điểm cụ thể
      if (
        msg.match(/điểm.*(toán|văn|anh|lý|hóa|sinh|sử|địa|tin|thể dục|gdcd)/)
      ) {
        return true;
      }

      // Câu hỏi về điểm trung bình
      if (msg.match(/điểm.*(trung bình|tb|gpa|tổng)/)) {
        return true;
      }

      // Câu hỏi về thông tin cá nhân
      if (msg.match(/tôi.*lớp|lớp.*nào|tên.*tôi|thông tin/)) {
        return true;
      }

      // Câu hỏi về thông báo
      if (msg.match(/thông báo|tb mới|announcement/)) {
        return true;
      }

      // Câu hỏi về cải thiện (cần context)
      if (msg.match(/cải thiện|làm.*nào|học.*tốt|nâng.*cao|tăng.*điểm/)) {
        return true;
      }

      // Câu hỏi về chi tiết điểm
      if (msg.match(/chi tiết|các cột|điểm thành phần|xem.*điểm/)) {
        return true;
      }

      return false;
    },

    // Gọi API để lấy dữ liệu thực
    fetchBotResponse: function (userMessage) {
      var self = this;

      // Lấy base URL
      var metaTag = document.querySelector('meta[name="base-url"]');
      var baseURL = metaTag ? metaTag.content : "";

      fetch(baseURL + "/api/student/chatbot", {
        method: "POST",
        headers: {
          "Content-Type": "application/json; charset=UTF-8",
        },
        body: JSON.stringify({
          message: userMessage,
          context: self.lastContext, // Gửi context câu trước
        }),
      })
        .then(function (response) {
          return response.json();
        })
        .then(function (data) {
          if (data.success) {
            self.addMessage(data.answer, false);
            // Lưu context cho câu hỏi tiếp theo
            if (data.context) {
              self.lastContext = data.context;
            }
          } else {
            self.addMessage(
              data.error || "Đã có lỗi xảy ra. Vui lòng thử lại! 😢",
              false
            );
          }
        })
        .catch(function (error) {
          console.error("Chatbot API error:", error);
          self.addMessage(
            "Không thể kết nối đến server. Vui lòng kiểm tra kết nối mạng! 🔌",
            false
          );
        });
    },

    // Tự động trả lời dựa trên từ khóa (cho câu hỏi generic)
    getAutoResponse: function (message) {
      var msg = message.toLowerCase();

      // Thông báo
      if (msg.includes("thông báo")) {
        return 'Các thông báo mới nhất từ nhà trường có thể xem tại mục "Thông báo". Bạn sẽ nhận được thông báo về các sự kiện, lịch thi, và hoạt động của trường. 📢';
      }

      // Đổi mật khẩu
      if (msg.includes("mật khẩu") || msg.includes("đổi mật khẩu")) {
        return 'Để đổi mật khẩu, vào mục "Đổi mật khẩu" trên menu. Nhớ chọn mật khẩu mạnh và không chia sẻ với người khác nhé! 🔒';
      }

      // AI phân tích
      if (msg.includes("ai") && msg.includes("phân tích")) {
        return 'Tính năng "Phân tích AI" giúp bạn có cái nhìn tổng quan về kết quả học tập và nhận lời khuyên cải thiện. Bạn tìm thấy nút này ở trang Xem điểm. 🤖';
      }

      // Xin chào
      if (msg.includes("chào") || msg.includes("hello") || msg.includes("hi")) {
        return "Xin chào! Tôi là trợ lý ảo, sẵn sàng giúp bạn sử dụng hệ thống. Bạn cần hỗ trợ gì? 😊";
      }

      // Cảm ơn
      if (msg.includes("cảm ơn") || msg.includes("thanks")) {
        return "Không có gì! Nếu cần hỗ trợ thêm, hãy nhắn tin cho tôi nhé! 😊";
      }

      // Mặc định
      return (
        'Tôi hiểu bạn đang hỏi về "' +
        message +
        '". Bạn có thể hỏi tôi về:\n- Xem điểm: "Điểm toán bao nhiêu?", "Điểm trung bình?"\n- Thời khóa biểu: "Thứ 2 học gì?", "Hôm nay học mấy tiết?"\n- Thông tin: "Tôi học lớp nào?"\n\nHoặc liên hệ phòng đào tạo để được hỗ trợ trực tiếp! 📞'
      );
    },

    // Tin nhắn chào mừng
    loadWelcomeMessage: function () {
      var self = this;
      setTimeout(function () {
        self.addMessage(
          "Xin chào! Tôi là trợ lý ảo thông minh. Tôi có thể giúp bạn:\n\n" +
            "📊 Xem điểm từng môn hoặc điểm TB\n" +
            "📅 Kiểm tra lịch học theo ngày\n" +
            "📢 Thông báo và hoạt động trường\n\n" +
            "Hãy hỏi tôi bất cứ điều gì! 😊",
          false
        );

        // Add quick replies
        var messagesDiv = document.getElementById("chatMessages");
        var quickRepliesHTML = `
          <div class="quick-replies">
            <button class="quick-reply-btn" onclick="chatbox.handleQuickReply('Điểm trung bình?')">Điểm TB</button>
            <button class="quick-reply-btn" onclick="chatbox.handleQuickReply('Thứ 2 học gì?')">Lịch T2</button>
            <button class="quick-reply-btn" onclick="chatbox.handleQuickReply('Tôi học lớp nào?')">Thông tin</button>
          </div>
        `;
        messagesDiv.insertAdjacentHTML("beforeend", quickRepliesHTML);
      }, 500);
    },

    // Xử lý quick reply
    handleQuickReply: function (text) {
      document.getElementById("chatInput").value = text;
      this.sendMessage();
    },

    // Escape HTML để tránh XSS
    escapeHtml: function (text) {
      var div = document.createElement("div");
      div.textContent = text;
      return div.innerHTML.replace(/\n/g, "<br>");
    },
  };

  // Khởi tạo khi DOM ready
  if (document.readyState === "loading") {
    document.addEventListener("DOMContentLoaded", function () {
      chatbox.init();
    });
  } else {
    chatbox.init();
  }

  // Expose chatbox globally
  window.chatbox = chatbox;
})();
