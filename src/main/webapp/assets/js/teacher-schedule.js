(function () {
  "use strict";

  /**
   * Khởi tạo FullCalendar
   */
  function initializeCalendar() {
    var calendarEl = document.getElementById("calendar");

    if (!calendarEl) {
      console.error("Không tìm thấy element #calendar");
      return;
    }

    if (typeof window.teacherScheduleData === "undefined") {
      console.error("Dữ liệu lịch dạy chưa được load");
      return;
    }

    var events = window.teacherScheduleData;

    var calendar = new FullCalendar.Calendar(calendarEl, {
      initialView: "timeGridWeek",
      locale: "vi",
      headerToolbar: {
        left: "prev,next today",
        center: "title",
        right: "timeGridWeek,timeGridDay",
      },
      buttonText: {
        today: "Hôm nay",
        week: "Tuần",
        day: "Ngày",
      },
      slotMinTime: "07:00:00",
      slotMaxTime: "18:00:00",
      allDaySlot: false,
      weekends: true,
      hiddenDays: [0],
      events: events,
      eventClick: function (info) {
        showEventDetail(info.event);
      },
      eventDidMount: function (info) {
        info.el.title =
          info.event.extendedProps.tenLop +
          " - " +
          info.event.extendedProps.tenMonHoc;
      },
      editable: false,
      droppable: false,
      height: "auto",
    });

    calendar.render();
  }

  /**
   * Hiển thị chi tiết sự kiện
   */
  function showEventDetail(event) {
    // Lấy thông tin từ event
    var tenLop = event.extendedProps.tenLop || "Chưa xác định";
    var tenMonHoc = event.extendedProps.tenMonHoc || "Chưa xác định";
    var phongHoc = event.extendedProps.phongHoc || "Chưa xác định";

    // Lấy thời gian từ event
    var startTime = event.start
      ? event.start.toLocaleTimeString("vi-VN", {
          hour: "2-digit",
          minute: "2-digit",
        })
      : "";
    var endTime = event.end
      ? event.end.toLocaleTimeString("vi-VN", {
          hour: "2-digit",
          minute: "2-digit",
        })
      : "";
    var thoiGian =
      startTime && endTime ? startTime + " - " + endTime : "Chưa xác định";

    // Cập nhật nội dung modal
    document.getElementById("detailLop").textContent = tenLop;
    document.getElementById("detailMonHoc").textContent = tenMonHoc;
    document.getElementById("detailPhongHoc").textContent = phongHoc;
    document.getElementById("detailThoiGian").textContent = thoiGian;

    // Hiển thị modal
    var modal = new bootstrap.Modal(
      document.getElementById("scheduleDetailModal")
    );
    modal.show();
  }

  /**
   * Chuyển đổi dữ liệu từ server sang format FullCalendar
   */
  function prepareEventData(rawData) {
    return rawData.map(function (item) {
      var tiet = parseInt(item.tiet);
      var startHour = 7 + (tiet - 1);
      var endHour = startHour + 1;

      return {
        title: item.tenLop + " - " + item.tenMonHoc,
        daysOfWeek: [parseInt(item.thu) - 1],
        startTime: padZero(startHour) + ":00:00",
        endTime: padZero(endHour) + ":00:00",
        extendedProps: {
          phongHoc: item.phongHoc || "Chưa xác định",
          tenLop: item.tenLop,
          tenMonHoc: item.tenMonHoc,
        },
        color: "#0d6efd",
      };
    });
  }

  /**
   * Thêm số 0 phía trước nếu số < 10
   */
  function padZero(num) {
    return num < 10 ? "0" + num : "" + num;
  }

  document.addEventListener("DOMContentLoaded", function () {
    initializeCalendar();
  });
})();
