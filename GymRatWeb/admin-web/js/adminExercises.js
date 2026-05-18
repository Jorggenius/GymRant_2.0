const API_URL = "http://localhost:8080/api/admin/ejercicios";

let ejercicios = [];

document.addEventListener("DOMContentLoaded", () => {
  initExerciseModule();
});

function initExerciseModule() {
  const form = document.getElementById("exerciseForm");
  const cancelBtn = document.getElementById("cancelEditBtn");
  const searchInput = document.getElementById("searchInput");

  if (!form) return;

  form.addEventListener("submit", handleFormSubmit);
  cancelBtn.addEventListener("click", resetForm);
  searchInput.addEventListener("input", filterExercises);

  cargarEjercicios();
}

async function cargarEjercicios() {
  try {
    showMessage("Cargando ejercicios...", "success");

    const response = await fetch(API_URL);
    if (!response.ok) {
      throw new Error("No se pudieron cargar los ejercicios.");
    }

    ejercicios = await response.json();
    renderExercises(ejercicios);

    if (ejercicios.length > 0) {
      showMessage("Ejercicios cargados correctamente.", "success");
    } else {
      showMessage("No hay ejercicios registrados todavía.", "success");
    }
  } catch (error) {
    showMessage(error.message, "error");
    renderExercises([]);
  }
}

function renderExercises(data) {
  const tbody = document.getElementById("exerciseTableBody");
  tbody.innerHTML = "";

  if (!data.length) {
    tbody.innerHTML = `
      <tr>
        <td colspan="8" class="empty-state">No se encontraron ejercicios.</td>
      </tr>
    `;
    return;
  }

  data.forEach((ejercicio) => {
    const tr = document.createElement("tr");

    tr.innerHTML = `
      <td>${ejercicio.id ?? ""}</td>
      <td>${ejercicio.nombre ?? ""}</td>
      <td>${ejercicio.categoria ?? ""}</td>
      <td>${ejercicio.musculoPrincipal ?? ""}</td>
      <td>${ejercicio.seriesRecomendadas ?? ""}</td>
      <td>${ejercicio.repeticionesRecomendadas ?? ""}</td>
      <td>${ejercicio.nivel ?? ""}</td>
      <td>
        <div class="actions">
          <button class="btn btn-warning btn-small" data-action="edit" data-id="${ejercicio.id}">Editar</button>
          <button class="btn btn-danger btn-small" data-action="delete" data-id="${ejercicio.id}">Eliminar</button>
        </div>
      </td>
    `;

    tbody.appendChild(tr);
  });

  tbody.querySelectorAll("[data-action='edit']").forEach((button) => {
    button.addEventListener("click", () => editExercise(button.dataset.id));
  });

  tbody.querySelectorAll("[data-action='delete']").forEach((button) => {
    button.addEventListener("click", () => deleteExercise(button.dataset.id));
  });
}

function getFormData() {
  return {
    nombre: document.getElementById("nombre").value.trim(),
    categoria: document.getElementById("categoria").value,
    descripcion: document.getElementById("descripcion").value.trim(),
    musculoPrincipal: document.getElementById("musculoPrincipal").value.trim(),
    seriesRecomendadas: Number(document.getElementById("seriesRecomendadas").value),
    repeticionesRecomendadas: Number(document.getElementById("repeticionesRecomendadas").value),
    nivel: document.getElementById("nivel").value
  };
}

function validateForm(data) {
  if (
    !data.nombre ||
    !data.categoria ||
    !data.descripcion ||
    !data.musculoPrincipal ||
    !data.nivel ||
    !data.seriesRecomendadas ||
    !data.repeticionesRecomendadas
  ) {
    return "Todos los campos son obligatorios.";
  }

  if (data.seriesRecomendadas < 1 || data.repeticionesRecomendadas < 1) {
    return "Series y repeticiones deben ser mayores a 0.";
  }

  return null;
}

async function handleFormSubmit(e) {
  e.preventDefault();

  const exerciseId = document.getElementById("exerciseId").value;
  const data = getFormData();
  const validationError = validateForm(data);

  if (validationError) {
    showMessage(validationError, "error");
    return;
  }

  const isEditing = Boolean(exerciseId);
  const url = isEditing ? `${API_URL}/${exerciseId}` : API_URL;
  const method = isEditing ? "PUT" : "POST";

  try {
    const response = await fetch(url, {
      method,
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(data)
    });

    if (!response.ok) {
      throw new Error(
        isEditing
          ? "No se pudo actualizar el ejercicio."
          : "No se pudo crear el ejercicio."
      );
    }

    showMessage(
      isEditing
        ? "Ejercicio actualizado correctamente."
        : "Ejercicio creado correctamente.",
      "success"
    );

    resetForm();
    cargarEjercicios();
  } catch (error) {
    showMessage(error.message, "error");
  }
}

function editExercise(id) {
  const ejercicio = ejercicios.find((item) => String(item.id) === String(id));
  if (!ejercicio) return;

  document.getElementById("exerciseId").value = ejercicio.id ?? "";
  document.getElementById("nombre").value = ejercicio.nombre ?? "";
  document.getElementById("categoria").value = ejercicio.categoria ?? "";
  document.getElementById("descripcion").value = ejercicio.descripcion ?? "";
  document.getElementById("musculoPrincipal").value = ejercicio.musculoPrincipal ?? "";
  document.getElementById("seriesRecomendadas").value = ejercicio.seriesRecomendadas ?? "";
  document.getElementById("repeticionesRecomendadas").value = ejercicio.repeticionesRecomendadas ?? "";
  document.getElementById("nivel").value = ejercicio.nivel ?? "";

  document.getElementById("formTitle").textContent = "Editar ejercicio";
  document.getElementById("saveBtn").textContent = "Actualizar ejercicio";
  document.getElementById("cancelEditBtn").classList.remove("hidden");

  window.scrollTo({ top: 0, behavior: "smooth" });
}

async function deleteExercise(id) {
  const confirmado = confirm("¿Seguro que deseas eliminar este ejercicio?");
  if (!confirmado) return;

  try {
    const response = await fetch(`${API_URL}/${id}`, {
      method: "DELETE"
    });

    if (!response.ok) {
      throw new Error("No se pudo eliminar el ejercicio.");
    }

    showMessage("Ejercicio eliminado correctamente.", "success");
    cargarEjercicios();
  } catch (error) {
    showMessage(error.message, "error");
  }
}

function resetForm() {
  document.getElementById("exerciseForm").reset();
  document.getElementById("exerciseId").value = "";
  document.getElementById("formTitle").textContent = "Crear ejercicio";
  document.getElementById("saveBtn").textContent = "Guardar ejercicio";
  document.getElementById("cancelEditBtn").classList.add("hidden");
}

function filterExercises() {
  const text = document.getElementById("searchInput").value.trim().toLowerCase();

  const filtered = ejercicios.filter((ejercicio) => {
    return (
      (ejercicio.nombre ?? "").toLowerCase().includes(text) ||
      (ejercicio.categoria ?? "").toLowerCase().includes(text) ||
      (ejercicio.nivel ?? "").toLowerCase().includes(text) ||
      (ejercicio.musculoPrincipal ?? "").toLowerCase().includes(text)
    );
  });

  renderExercises(filtered);
}

function showMessage(text, type = "success") {
  const message = document.getElementById("exerciseMessage");
  if (!message) return;

  message.textContent = text;
  message.className = `message ${type}`;
}
