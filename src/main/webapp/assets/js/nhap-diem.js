
(function () {
  "use strict";

  /**
   * Hàm tính điểm trung bình tự động
   * Công thức: ĐTB = (Miệng + 15p + 1Tiết×2 + Thi×3) ÷ 7
   *
   * @param {number} maHS - Mã học sinh
   */
  function tinhDiem(maHS) {
    const miengInput = document.getElementById("mieng_" + maHS);
    const p15Input = document.getElementById("p15_" + maHS);
    const tiet1Input = document.getElementById("tiet1_" + maHS);
    const thiInput = document.getElementById("thi_" + maHS);
    const dtbElement = document.getElementById("dtb_" + maHS);

    if (!miengInput || !p15Input || !tiet1Input || !thiInput || !dtbElement) {
      console.error("Không tìm thấy elements cho maHS:", maHS);
      return;
    }

    const mieng = parseFloat(miengInput.value) || 0;
    const p15 = parseFloat(p15Input.value) || 0;
    const tiet1 = parseFloat(tiet1Input.value) || 0;
    const thi = parseFloat(thiInput.value) || 0;

    const thiValue = thiInput.value.trim();
    if (thiValue === "") {
      dtbElement.textContent = "---";
      dtbElement.className = "dtb-display chua-co-diem";
      return;
    }

    let dtb = (mieng + p15 + tiet1 * 2 + thi * 3) / 7;


    dtb = Math.round(dtb * 100) / 100;

    dtbElement.textContent = dtb.toFixed(2);

    let hocLucClass = "";
    if (dtb >= 8.0) {
      hocLucClass = "hoc-luc-gioi"; 
    } else if (dtb >= 6.5) {
      hocLucClass = "hoc-luc-kha"; 
    } else if (dtb >= 5.0) {
      hocLucClass = "hoc-luc-tb"; 
    } else {
      hocLucClass = "hoc-luc-yeu"; 
    }

    dtbElement.className = "dtb-display " + hocLucClass;

    dtbElement.classList.add("updated-score");
    setTimeout(function () {
      dtbElement.classList.remove("updated-score");
    }, 1000);
  }

  /**
   * Khởi tạo event listeners cho tất cả các input điểm
   */
  function initDiemInputs() {
    const maHSInputs = document.querySelectorAll('input[name="maHS"]');

    maHSInputs.forEach(function (input) {
      const maHS = input.value;
      const inputIds = [
        "mieng_" + maHS,
        "p15_" + maHS,
        "tiet1_" + maHS,
        "thi_" + maHS,
      ];

      inputIds.forEach(function (inputId) {
        const inputElement = document.getElementById(inputId);
        if (inputElement) {
          inputElement.addEventListener("keyup", function () {
            tinhDiem(maHS);
          });
          inputElement.addEventListener("change", function () {
            tinhDiem(maHS);
          });
          inputElement.addEventListener("input", function () {
            tinhDiem(maHS);
          });
        }
      });

      tinhDiem(maHS);
    });
  }

  /**
   * Xác nhận trước khi submit form
   */
  function initFormSubmit() {
    const form = document.getElementById("formNhapDiem");
    if (form) {
      form.addEventListener("submit", function (e) {
        const confirmed = confirm(
          "Bạn có chắc chắn muốn lưu điểm cho tất cả học sinh?"
        );
        if (!confirmed) {
          e.preventDefault();
        }
      });
    }
  }

  /**
   * Validate điểm (0-10)
   */
  function validateDiem(input) {
    const value = parseFloat(input.value);
    if (isNaN(value)) {
      return true; 
    }
    if (value < 0) {
      input.value = 0;
      return false;
    }
    if (value > 10) {
      input.value = 10;
      return false;
    }
    return true;
  }

  /**
   * Thêm validation cho các input điểm
   */
  function initValidation() {
    const numberInputs = document.querySelectorAll('input[type="number"]');
    numberInputs.forEach(function (input) {
      input.addEventListener("blur", function () {
        validateDiem(input);
      });
    });
  }

  document.addEventListener("DOMContentLoaded", function () {
    console.log("Nhập điểm module initialized");
    initDiemInputs();
    initFormSubmit();
    initValidation();
  });

  window.NhapDiemModule = {
    tinhDiem: tinhDiem,
    initDiemInputs: initDiemInputs,
  };
})();
