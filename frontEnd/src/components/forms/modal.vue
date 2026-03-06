<template>
  <div v-if="show" class="modal-overlay" @click.self="close">
    <div class="modal">
      <header class="modal-header">
        <h3>{{ title }}</h3>
        <button class="close-btn" @click="close">×</button>
      </header>

      <section class="modal-body">
        <slot></slot>
      </section>

      <footer class="modal-footer">
        <slot name="footer"></slot>
      </footer>
    </div>
  </div>
</template>

<script setup>
defineProps({
  show: Boolean,
  title: String
})

const emit = defineEmits(["close"])

function close() {
  emit("close")
}
</script>

<style scoped>

.modal-overlay{
  position: fixed;
  top:0;
  left:0;
  width:100%;
  height:100%;
  background: rgba(0,0,0,0.5);
  display:flex;
  align-items:center;
  justify-content:center;
}

.modal{
  background:white;
  width:auto;
  border-radius:8px;
  box-shadow:0 10px 30px rgba(0,0,0,0.2);
  animation: fadeIn 0.2s;
}

.modal-header{
  display:flex;
  justify-content:space-between;
  align-items:center;
  padding:12px 16px;
  border-bottom:1px solid #eee;
}

.modal-body{
  padding:16px;
}

.modal-footer{
  padding:12px 16px;
  border-top:1px solid #eee;
  text-align:right;
}

.close-btn{
    color: black;
  border:none;
  background:none;
  font-size:20px;
  cursor:pointer;
}


</style>