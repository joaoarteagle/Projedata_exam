<script setup lang="ts">
import ProductionSuggestion from './components/screens/ProductionSuggestion.vue';
import ProductsScreen from './components/screens/productsScreen.vue';
import RawMaterialScreen from './components/screens/rawMaterialScreen.vue';

</script>

<template>
  <ProductsScreen/>
  <RawMaterialScreen/>
  <ProductionSuggestion/>
</template>

<style scoped>
.logo {
  height: 6em;
  padding: 1.5em;
  will-change: filter;
  transition: filter 300ms;
}
.logo:hover {
  filter: drop-shadow(0 0 2em #646cffaa);
}
.logo.vue:hover {
  filter: drop-shadow(0 0 2em #42b883aa);
}
</style>
