<script setup>
import { ref, watch, onMounted } from "vue"
import axios from "axios"

const props = defineProps({
  material: Object
})

const emit = defineEmits(["saved"])

const id = ref(null)
const name = ref("")
const unit = ref("")
const quantity = ref(0)

watch(() => props.material, (value) => {
  if (value) {
    id.value = value.id
    name.value = value.name
    unit.value = value.unit
    quantity.value = value.stockQuantity
  }
})

async function submitForm() {

  const payload = {
    name: name.value,
    unit: unit.value,
    stockQuantity: quantity.value
  }

  if (id.value) {
    await axios.put(`http://localhost:8080/rawMaterial/${id.value}`, payload)
  } else {
    console.log("Creating new material with data:", payload)
    await axios.post("http://localhost:8080/rawMaterial", payload)
  }

  emit("saved")
  resetForm()
}

function resetForm(){
  id.value = null
  name.value = ""
  unit.value = ""
  quantity.value = 0
}

onMounted(() => {
  if (props.material) {
    id.value = props.material.id
    name.value = props.material.name
    unit.value = props.material.unit
    quantity.value = props.material.stockQuantity
  }
})
</script>

<template>
<div class="form-container">

  <h2>{{ id ? "Edit Raw Material" : "Create Raw Material" }}</h2>

  <form @submit.prevent="submitForm">

    <div class="field">
      <label>Name</label>
      <input v-model="name" required />
    </div>

    <div class="field">
      <label>Unit</label>
      <input v-model="unit" placeholder="kg, g, ml..." required />
    </div>

    <div class="field">
      <label>Quantity</label>
      <input v-model.number="quantity" type="number" min="0" required />
    </div>

    <button type="submit">
      {{ id ? "Update" : "Create" }}
    </button>

  </form>

</div>
</template>

<style scoped>

.form-container{
  width:400px;
  margin:auto;
  padding:20px;
  border:1px solid #ddd;
  border-radius:8px;
}

.field{
  margin-bottom:15px;
}

input{
  width:100%;
  padding:8px;
}

button{
  width:100%;
  padding:10px;
  background:#4CAF50;
  color:white;
  border:none;
  cursor:pointer;
}

</style>