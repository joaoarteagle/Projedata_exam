<script setup>
import { ref, onMounted } from "vue"
import api from "../../services/api"

const suggestions = ref([])

async function loadSuggestions(){
  const response = await api.get("/production/suggestions")
  suggestions.value = response.data
  console.log("Loaded production suggestions:", suggestions.value)
}

onMounted(loadSuggestions)
</script>

<template>

<div class="page">

<header class="header">
  <h1 class="heading">Production Suggestions</h1>
</header>

<table class="table">

<thead>
<tr>
<th>Product</th>
<th>Quantity</th>
<th>Unit Price</th>
<th>Total Value</th>
</tr>
</thead>

<tbody>

<tr v-for="item in suggestions" :key="item.productId">

<td class="name-cell">
  {{ item.productName }}
</td>

<td>
  <span class="code">
    {{ item.quantity }}
  </span>
</td>

<td>
  <span class="tag tag--amber">
    R$ {{ item.unitPrice }}
  </span>
</td>

<td>
  <span class="tag tag--green">
    R$ {{ item.totalValue }}
  </span>
</td>

</tr>

</tbody>

<tbody v-if="suggestions.length === 0">
<tr>
<td colspan="4" class="empty">
No production suggestions available.
</td>
</tr>
</tbody>

</table>

</div>

</template>

<style scoped>

.page{
  padding:48px 40px;
  max-width:900px;
  margin:0 auto;
  color:#1a1a1a;
}

.header{
  display:flex;
  align-items:flex-end;
  justify-content:space-between;
  margin-bottom:32px;
}

.heading{
  font-size:28px;
  font-weight:700;
  color:#111;
}

.table{
  width:100%;
  border-collapse:collapse;
  font-size:14px;
}

.table thead tr{
  border-bottom:2px solid #e5e7eb;
}

.table th{
  text-align:left;
  padding:10px 14px;
  font-size:11px;
  font-weight:700;
  letter-spacing:.1em;
  text-transform:uppercase;
  color:#9ca3af;
}

.table td{
  padding:13px 14px;
  color:#374151;
}

.name-cell{
  font-weight:600;
}

.code{
  font-family:"IBM Plex Mono", monospace;
  font-size:12px;
}

.tag{
  font-family:"IBM Plex Mono", monospace;
  font-size:11px;
  font-weight:500;
  padding:3px 8px;
  border-radius:4px;
}

.tag--amber{
  background:#fef3c7;
  color:#92400e;
}

.tag--green{
  background:#dcfce7;
  color:#166534;
}

.empty{
  text-align:center;
  color:#9ca3af;
  padding:40px;
}

</style>