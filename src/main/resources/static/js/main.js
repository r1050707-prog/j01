const toggle = document.querySelector(".nav-toggle");
const nav = document.querySelector("#site-nav");

if (toggle && nav) {
  toggle.addEventListener("click", () => {
    const open = nav.classList.toggle("is-open");
    toggle.setAttribute("aria-expanded", String(open));
  });
}

const pill = document.querySelector("#server-pill");

async function checkServer() {
  if (!pill) return;
  try {
    const response = await fetch("/api/health");
    if (!response.ok) throw new Error("down");
    const health = await response.json();
    pill.textContent = health.port + " 포트 연결됨";
    pill.classList.add("is-up");
  } catch (error) {
    pill.textContent = "서버 응답 없음";
    pill.classList.add("is-down");
  }
}

function applyCompany(company) {
  document.querySelectorAll("[data-field]").forEach((node) => {
    const key = node.dataset.field;
    if (company[key]) node.textContent = company[key];
  });
}

async function loadCompany() {
  try {
    const response = await fetch("/api/company");
    if (!response.ok) return;
    applyCompany(await response.json());
  } catch (error) {
    /* 정적 문구를 그대로 보여 줍니다. */
  }
}

function renderInquiries(items, box) {
  if (!items.length) {
    box.innerHTML = "<p>아직 접수된 문의가 없습니다. 왼쪽 양식을 보내 보세요.</p>";
    return;
  }
  box.innerHTML = items.map((item) => `
    <article class="inquiry">
      <header>
        <strong>${escapeHtml(item.organization)} · ${escapeHtml(item.name)}</strong>
        <time>${escapeHtml(item.createdAt)}</time>
      </header>
      <p>${escapeHtml(item.message)}</p>
      <p>${escapeHtml(item.phone)}${item.email ? " · " + escapeHtml(item.email) : ""}</p>
    </article>
  `).join("");
}

function escapeHtml(value) {
  return String(value)
    .replaceAll("&", "&amp;")
    .replaceAll("<", "&lt;")
    .replaceAll(">", "&gt;")
    .replaceAll('"', "&quot;");
}

async function loadInquiries() {
  const box = document.querySelector("#inquiry-list");
  if (!box) return;
  const response = await fetch("/api/inquiries");
  if (!response.ok) {
    box.textContent = "문의 목록을 불러오지 못했습니다.";
    return;
  }
  renderInquiries(await response.json(), box);
}

function showFieldErrors(errors) {
  document.querySelectorAll(".field-error").forEach((node) => {
    node.textContent = "";
  });
  if (!errors) return;
  Object.entries(errors).forEach(([field, message]) => {
    const node = document.querySelector(`[data-error="${field}"]`);
    if (node) node.textContent = message;
  });
}

const form = document.querySelector("#inquiry-form");
if (form) {
  form.addEventListener("submit", async (event) => {
    event.preventDefault();
    const status = document.querySelector("#form-status");
    const submit = form.querySelector("button[type='submit']");
    showFieldErrors({});
    status.textContent = "";
    status.className = "form-status";
    submit.disabled = true;

    const payload = {
      name: form.name.value.trim(),
      organization: form.organization.value.trim(),
      phone: form.phone.value.trim(),
      email: form.email.value.trim(),
      message: form.message.value.trim()
    };

    try {
      const response = await fetch("/api/inquiries", {
        method: "POST",
        headers: { "Content-Type": "application/json" },
        body: JSON.stringify(payload)
      });
      const body = await response.json();
      if (!response.ok) {
        showFieldErrors(body.errors);
        status.textContent = body.message || "접수를 처리하지 못했습니다.";
        status.classList.add("bad");
        return;
      }
      form.reset();
      status.textContent = "문의가 접수되었습니다. 오른쪽 목록에서 확인할 수 있습니다.";
      status.classList.add("ok");
      await loadInquiries();
    } catch (error) {
      status.textContent = "서버에 연결하지 못했습니다. 페이지를 새로고침한 뒤 다시 시도해 주세요.";
      status.classList.add("bad");
    } finally {
      submit.disabled = false;
    }
  });
  loadInquiries();
}

checkServer();
loadCompany();
