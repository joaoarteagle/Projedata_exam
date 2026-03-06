<script setup>
import { ref, onMounted, watch } from "vue"
import axios from "axios"

const props = defineProps({
  product: Object
})

const emit = defineEmits(["saved","close"])

const name = ref("")
const price = ref("")
const rawMaterials = ref([])
const selectedRawMaterials = ref([])
const quantities = ref({})

async function loadRawMaterials(){
  const response = await axios.get("http://localhost:8080/rawMaterial")
  rawMaterials.value = response.data
}

watch(() => props.product, (product) => {

  resetForm()

  if(!product) return

  name.value = product.name
  price.value = product.price

  if(product.composition && product.composition.length){

    selectedRawMaterials.value = product.composition.map(c => c.rawMaterial.id)

    product.composition.forEach(c => {
      quantities.value[c.rawMaterial.id] = c.requiredQuantity
    })

  }

}, { immediate: true })


async function submitForm(){

  const composition = selectedRawMaterials.value.map(id => ({
    rawMaterialId: id,
    requiredQuantity: quantities.value[id]
  }))

  const productData = {
    name: name.value,
    price: price.value,
    composition: composition
  }

  if(props.product){

    await axios.put(
      `http://localhost:8080/products/${props.product.id}`,
      productData
    )

  }else{
    console.log("Creating new product with data:", productData)

    await axios.post(
      "http://localhost:8080/products",
      productData
    )


  }

  emit("saved")
  emit("close")
  resetForm()
}
function resetForm(){
  name.value = ""
  price.value = ""
  selectedRawMaterials.value = []
  quantities.value = {}
}

onMounted(loadRawMaterials)
</script>

<template>
<div class="form-container">

  <h2>{{ product ? "Edit Product" : "Create Product" }}</h2>

  <form @submit.prevent="submitForm">

    <div class="field">
      <label>Name</label>
      <input
        v-model="name"
        type="text"
        placeholder="Product name"
        required
      />
    </div>

    <div class="field">
      <label>Price</label>
      <input
        v-model="price"
        type="number"
        step="0.01"
        placeholder="0.00"
        required
      />
    </div>

    <div class="field">

      <label>Raw Materials</label>

      <div class="raw-list">

        <div
          v-for="material in rawMaterials"
          :key="material.id"
          class="raw-item"
        >

          <input
            type="checkbox"
            :value="material.id"
            v-model="selectedRawMaterials"
            class="checkbox"
          />

          <span>
            {{ material.name }} ({{ material.unit }})
          </span>

          <input
            v-if="selectedRawMaterials.includes(material.id)"
            v-model.number="quantities[material.id]"
            type="number"
            min="1"
            placeholder="Qty"
          />

        </div>

      </div>

    </div>

    <button type="submit">
      Create Product
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

.checkbox{
  width:10%;
}

.raw-list{
  max-height:150px;
  overflow:auto;
  border:1px solid #ddd;
  padding:8px;
}

.raw-item{
  display:flex;
  align-items:center;
  gap:10px;
  margin-bottom:5px;
}

.raw-item input[type="number"]{
  width:80px;
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