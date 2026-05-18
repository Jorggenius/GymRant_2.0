const ADMIN_USER = "admin";
const ADMIN_PASS = "admin";
const STORAGE_KEY = "gymrat_admin_logged";

document.addEventListener("DOMContentLoaded", () => {
  const currentPage = window.location.pathname.split("/").pop();

  if (currentPage === "login.html" || currentPage === "") {
    setupLogin();
    redirectIfLogged();
  } else {
    protectRoute();
    setupLogoutButtons();
  }
});

function setupLogin() {
  const form = document.getElementById("loginForm");
  if (!form) return;

  form.addEventListener("submit", (e) => {
    e.preventDefault();

    const username = document.getElementById("username").value.trim();
    const password = document.getElementById("password").value.trim();

    if (username === ADMIN_USER && password === ADMIN_PASS) {
      localStorage.setItem(STORAGE_KEY, "true");
      window.location.href = "adminOneActivity.html";
    } else {
      showAuthMessage("Usuario o contraseña incorrectos.", "error");
    }
  });
}

function isAdminLogged() {
  return localStorage.getItem(STORAGE_KEY) === "true";
}

function redirectIfLogged() {
  if (isAdminLogged()) {
    window.location.href = "adminOneActivity.html";
  }
}

function protectRoute() {
  if (!isAdminLogged()) {
    window.location.href = "login.html";
  }
}

function setupLogoutButtons() {
  const buttons = document.querySelectorAll("[data-logout]");
  buttons.forEach((btn) => {
    btn.addEventListener("click", logout);
  });
}

function logout() {
  localStorage.removeItem(STORAGE_KEY);
  window.location.href = "login.html";
}

function showAuthMessage(text, type = "error") {
  const message = document.getElementById("authMessage");
  if (!message) return;

  message.textContent = text;
  message.className = `message ${type}`;
}
