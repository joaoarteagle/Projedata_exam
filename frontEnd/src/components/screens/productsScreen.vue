<script setup>
import { ref, onMounted } from "vue";
import api from "../../services/api";
import Modal from "../forms/modal.vue";
import ProductForm from "../forms/productForm.vue";

const products = ref([]);
const expanded = ref(null);

const showModal = ref(false);
const selectedProduct = ref(null);

async function loadProducts() {
  const response = await api.get("/products");
  products.value = response.data;
}

async function deleteProduct(id) {
  await api.delete(`/products/${id}`);
  loadProducts();
}

function toggleExpand(id) {
  expanded.value = expanded.value === id ? null : id;
}

function openCreateModal() {
  selectedProduct.value = null;
  showModal.value = true;
}

function openEditModal(product) {
  selectedProduct.value = product;
  showModal.value = true;
}

function closeModal() {
  showModal.value = false;
}

function handleSaved() {
  loadProducts();
  closeModal();
}

onMounted(loadProducts);
</script>

<template>
  <div class="page">
    <header class="header">
      <h1 class="heading">Products</h1>

      <button @click="openCreateModal">+ New Product</button>
    </header>

    <Modal
      :show="showModal"
      :title="selectedProduct ? 'Edit Product' : 'Create Product'"
      @close="closeModal"
    >
      <ProductForm
        :product="selectedProduct"
        @saved="handleSaved"
        @close="closeModal"
      />
    </Modal>

    <table class="table">
      <thead>
        <tr>
          <th></th>
          <th>ID</th>
          <th>Name</th>
          <th>Price</th>
          <th>Actions</th>
        </tr>
      </thead>

      <tbody v-for="product in products" :key="product.id">
        <tr :class="{ 'row--expanded': expanded === product.id }">
          <td class="td-toggle">
            <button class="toggle-btn" @click="toggleExpand(product.id)">
              <svg
                width="14"
                height="14"
                viewBox="0 0 24 24"
                fill="none"
                stroke="currentColor"
                stroke-width="2.5"
                stroke-linecap="round"
                stroke-linejoin="round"
                :style="{
                  transform:
                    expanded === product.id ? 'rotate(90deg)' : 'rotate(0deg)',
                  transition: 'transform 0.2s',
                }"
              >
                <polyline points="9 18 15 12 9 6" />
              </svg>
            </button>
          </td>

          <td>
            <span class="code">
              {{ product.id }}
            </span>
          </td>

          <td class="name-cell">
            {{ product.name }}
          </td>

          <td>
            <span class="tag tag--amber"> R$ {{ product.price }} </span>
          </td>

          <td class="actions">

            <button class="btn-danger" @click="deleteProduct(product.id)">
              Delete
            </button>
          </td>
        </tr>

        <tr v-if="expanded === product.id" class="composition-row">
          <td colspan="5">
            <div class="composition-panel">
              <p class="composition-title">Composition</p>

              <div
                v-if="product.composition && product.composition.length"
                class="composition-list"
              >
                <div
                  v-for="item in product.composition"
                  :key="item.id"
                  class="composition-item"
                >
                  <span class="mat-name">
                    
                    {{ item.rawMaterial.name }}
                  </span>

                  <span class="mat-qty">
                    {{ item.requiredQuantity }}

                    <em>
                      {{ item.rawMaterial.unit }}
                    </em>
                  </span>
                </div>
              </div>

              <p v-else class="composition-empty">
                No raw materials registered.
              </p>
            </div>
          </td>
        </tr>
      </tbody>

      <tbody v-if="products.length === 0">
        <tr>
          <td colspan="5" class="empty">No products registered.</td>
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
.heading {
  font-size: 28px;
  font-weight: 700;
  color: #111;
}
.btn-primary:hover {
  background: #2563eb;
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
.table td {
  padding: 13px 14px;
  color: #374151;
}
.td-toggle {
  width: 36px;
  padding: 0 8px;
}
.toggle-btn {
  width: 26px;
  height: 26px;
  display: grid;
  place-items: center;
  border-radius: 6px;
  border: 1px solid #e5e7eb;
  background: transparent;
  color: #6b7280;
  cursor: pointer;
  transition:
    background 0.15s,
    border-color 0.15s,
    color 0.15s;
}
.code {
  font-family: "IBM Plex Mono", monospace;
  font-size: 12px;
  color: #ffffff;
}
.name-cell {
  font-weight: 600;
}
.tag {
  font-family: "IBM Plex Mono", monospace;
  font-size: 11px;
  font-weight: 500;
  padding: 3px 8px;
  border-radius: 4px;
}
.tag--amber {
  background: #fef3c7;
  color: #92400e;
}
.actions {
  display: flex;
  gap: 8px;
}
.btn-danger {
  font-family: "Sora", sans-serif;
  font-size: 12px;
  font-weight: 600;
  background: transparent;
  border: 1px solid #fecaca;
  border-radius: 6px;
  padding: 5px 12px;
  color: #dc2626;
  cursor: pointer;
  transition:
    border-color 0.15s,
    background 0.15s;
}
.composition-row td {
  padding: 0;
  border-bottom: 1px solid #dbeafe;
}
.composition-panel {
  background: #f0f7ff;
  border-left: 3px solid #3b82f6;
  padding: 16px 20px;
}
.composition-title {
  font-size: 11px;
  font-weight: 700;
  letter-spacing: 0.1em;
  text-transform: uppercase;
  color: #3b82f6;
  margin-bottom: 12px;
}
.composition-list {
  display: flex;
  flex-direction: column;
  gap: 6px;
}
.composition-item {
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 8px 12px;
  background: #fff;
  border: 1px solid #dbeafe;
  border-radius: 7px;
  font-size: 13px;
}
.mat-name {
  color: #374151;
  font-weight: 500;
}
.mat-qty {
  font-family: "IBM Plex Mono", monospace;
  font-size: 12px;
  color: #166534;
  background: #dcfce7;
  padding: 2px 8px;
  border-radius: 4px;
}
.mat-qty em {
  font-style: normal;
  opacity: 0.7;
  margin-left: 3px;
}
.composition-empty {
  font-size: 13px;
  color: #9ca3af;
}
.empty {
  text-align: center;
  color: #9ca3af;
  padding: 40px;
}
</style>
