<script setup>
import { ref, onMounted } from "vue";
import api from "../../services/api";
import Modal from "../forms/modal.vue";
import RawMaterialForm from "../forms/rawMaterialForm.vue";

const materials = ref([]);

const showModal = ref(false);
const selectedMaterial = ref(null);

async function loadMaterials() {
  const response = await api.get("/rawMaterial");
  materials.value = response.data;
}

async function deleteMaterial(id) {
  await api.delete(`/rawMaterial/${id}`);
  loadMaterials();
}

function openCreateModal() {
  selectedMaterial.value = null;
  showModal.value = true;
}

function openEditModal(material) {
  selectedMaterial.value = material;
  showModal.value = true;
}

function closeModal() {
  showModal.value = false;
}

function handleSaved() {
  loadMaterials();
  closeModal();
}

onMounted(loadMaterials);
</script>

<template>
  <div class="page">
    <header class="header">
      <h1 class="heading">Raw Materials</h1>

      <button @click="openCreateModal">
        + New Raw Material
      </button>
    </header>

    <Modal
      :show="showModal"
      :title="selectedMaterial ? 'Edit Raw Material' : 'Create Raw Material'"
      @close="closeModal"
    >
      <RawMaterialForm
        :material="selectedMaterial"
        @saved="handleSaved"
        @close="closeModal"
      />
    </Modal>

    <table class="table">
      <thead>
        <tr>
          <th>ID</th>
          <th>Name</th>
          <th>Stock</th>
          <th>Unit</th>
          <th>Actions</th>
        </tr>
      </thead>

      <tbody v-for="material in materials" :key="material.id">
        <tr>

          <td>
            <span class="code">
              {{ material.id }}
            </span>
          </td>

          <td class="name-cell">
            {{ material.name }}
          </td>

          
          <td>
            <span class="tag tag--green">
              {{ material.stockQuantity }}
            </span>
          </td>
          
          <td>
            {{ material.unit }}
          </td>
          <td class="actions">

            <button
              class="btn-edit"
              @click="openEditModal(material)"
            >
              Edit
            </button>

            <button
              class="btn-danger"
              @click="deleteMaterial(material.id)"
            >
              Delete
            </button>

          </td>

        </tr>
      </tbody>

      <tbody v-if="materials.length === 0">
        <tr>
          <td colspan="5" class="empty">
            No raw materials registered.
          </td>
        </tr>
      </tbody>
    </table>
  </div>
</template>

<style scoped>

.page {
  padding: 48px 40px;
  max-width: 900px;
  margin: 0 auto;
  color: #1a1a1a;
}

.header {
  display: flex;
  align-items: flex-end;
  justify-content: space-between;
  margin-bottom: 32px;
}

.eyebrow {
  font-size: 11px;
  letter-spacing: 0.14em;
  text-transform: uppercase;
  color: #22c55e;
  font-weight: 600;
  margin-bottom: 4px;
}

.heading {
  font-size: 28px;
  font-weight: 700;
  color: #111;
}


.table {
  width: 100%;
  border-collapse: collapse;
  font-size: 14px;
}

.table thead tr {
  border-bottom: 2px solid #e5e7eb;
}

.table th {
  text-align: left;
  padding: 10px 14px;
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  color: #9ca3af;
}

.table tbody tr {
  border-bottom: 1px solid #f3f4f6;
  transition: background 0.1s;
}



.table td {
  padding: 13px 14px;
  color: #374151;
}

.code {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 12px;
  color: #6b7280;
}

.tag {
  font-family: 'IBM Plex Mono', monospace;
  font-size: 11px;
  font-weight: 500;
  padding: 3px 8px;
  border-radius: 4px;
}
.tag--green { background: #dcfce7; color: #166534; }

.actions { display: flex; gap: 8px; }



.btn-danger {
  font-family: 'Sora', sans-serif;
  font-size: 12px;
  font-weight: 600;
  background: transparent;
  border: 1px solid #fecaca;
  border-radius: 6px;
  padding: 5px 12px;
  color: #dc2626;
  cursor: pointer;
  transition: border-color 0.15s, background 0.15s;
}


.empty {
  text-align: center;
  color: #9ca3af;
  padding: 40px;
}
</style>