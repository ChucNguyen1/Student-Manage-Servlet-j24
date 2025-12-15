
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
    var message =
      "Lớp: " +
      event.extendedProps.tenLop +
      "\n" +
      "Môn học: " +
      event.extendedProps.tenMonHoc +
      "\n" +
      "Phòng học: " +
      event.extendedProps.phongHoc;

    alert(message);

    // TODO: Có thể thay bằng Bootstrap Modal để đẹp hơn
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
