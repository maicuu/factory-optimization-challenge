<template>
  <div class="max-w-5xl mx-auto p-4 sm:p-6 space-y-8">
    
    <header class="flex flex-col sm:flex-row justify-between items-start sm:items-end gap-4">
      <div>
        <h2 class="text-3xl font-black text-slate-800 tracking-tight">{{ t('prod.title') }}</h2>
        <p class="text-slate-500 font-medium">{{ t('prod.subtitle') }}</p>
      </div>
      <div class="text-left sm:text-right bg-indigo-50 sm:bg-transparent p-3 sm:p-0 rounded-xl w-full sm:w-auto">
        <span class="text-xs font-bold uppercase tracking-widest text-indigo-500">{{ t('prod.total') }}</span>
        <p class="text-2xl font-black text-slate-700">{{ products.length }}</p>
      </div>
    </header>

    <section class="bg-white rounded-3xl shadow-xl shadow-slate-200/60 border border-slate-100 overflow-hidden">
      <div class="bg-slate-50 px-6 py-4 border-b border-slate-100 flex justify-between items-center">
        <h3 class="font-bold text-slate-700 flex items-center gap-2 text-sm uppercase tracking-wider">
          <span class="w-2 h-6 bg-indigo-500 rounded-full"></span>
          {{ t('prod.createTitle') }}
        </h3>
      </div>
      
      <div class="p-6 space-y-6">
        <div class="grid grid-cols-1 md:grid-cols-3 gap-6">
          <div class="space-y-1">
            <label class="text-xs font-bold text-slate-500 ml-1 uppercase">{{ t('prod.code') }}</label>
            <input v-model="newProduct.code" placeholder="PRD-XXXX" 
                   class="w-full border-2 border-slate-100 rounded-xl p-3 focus:border-indigo-500 outline-none bg-slate-50/50">
          </div>
          <div class="space-y-1">
            <label class="text-xs font-bold text-slate-500 ml-1 uppercase">{{ t('prod.name') }}</label>
            <input v-model="newProduct.name" :placeholder="t('prod.namePlaceholder')" 
                   class="w-full border-2 border-slate-100 rounded-xl p-3 focus:border-indigo-500 outline-none bg-slate-50/50">
          </div>
          <div class="space-y-1">
            <label class="text-xs font-bold text-slate-500 ml-1 uppercase">{{ t('prod.price') }}</label>
            <input v-model.number="newProduct.price" type="number" step="0.01" min="0"
                   class="w-full border-2 border-slate-100 rounded-xl p-3 focus:border-indigo-500 outline-none bg-slate-50/50">
          </div>
        </div>

        <div class="bg-indigo-50/50 rounded-2xl p-4 sm:p-6 border-2 border-indigo-100/50">
          <label class="text-[10px] font-black text-indigo-400 uppercase tracking-widest mb-4 block">{{ t('prod.recipe') }}</label>
          
          <div class="flex flex-col md:flex-row gap-4">
            <div class="flex-1">
              <select v-model="selectedMaterialId" class="w-full border-2 border-white rounded-xl p-3 outline-none focus:border-indigo-300 shadow-sm bg-white appearance-none">
                <option disabled value="">{{ t('prod.selectMaterial') }}</option>
                <option v-for="m in materials" :key="m.id" :value="m.id">
                  {{ m.name }} ({{ m.stockQuantity }} {{ m.unit || 'un' }})
                </option>
              </select>
            </div>
            <div class="w-full md:w-32">
              <input v-model.number="selectedQty" type="number" step="0.01" min="0" placeholder="Qty" 
                     class="w-full border-2 border-white rounded-xl p-3 outline-none focus:border-indigo-300 shadow-sm bg-white">
            </div>
            <button @click="addIngredient" 
                    class="bg-indigo-600 hover:bg-indigo-700 text-white px-8 py-3 rounded-xl font-bold shadow-md active:scale-95 transition-all">
              {{ t('prod.addMaterial') }}
            </button>
          </div>

          <div class="mt-6 flex flex-wrap gap-2 sm:gap-3">
            <TransitionGroup name="list">
              <div v-for="(comp, index) in newProduct.compositions" :key="comp.rawMaterial.id" 
                   class="bg-white border-2 border-indigo-100 px-3 py-2 rounded-xl text-xs sm:text-sm font-bold text-indigo-700 flex items-center gap-2 shadow-sm">
                <span class="bg-indigo-100 px-2 py-0.5 rounded-lg text-[10px]">{{ comp.quantityNeeded }} {{ getMaterialUnit(comp.rawMaterial.id) }}</span>
                <span class="truncate max-w-[100px] sm:max-w-none">{{ getMaterialName(comp.rawMaterial.id) }}</span>
                <button @click="removeIngredient(index)" class="text-indigo-300 hover:text-red-500 transition-colors">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-4 w-4" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="3" d="M6 18L18 6M6 6l12 12" />
                  </svg>
                </button>
              </div>
            </TransitionGroup>
            <p v-if="newProduct.compositions.length === 0" class="text-indigo-300 text-xs italic py-2">{{ t('prod.noIngredients') }}</p>
          </div>
        </div>

        <button @click="saveProduct" :disabled="isSaving"
                class="w-full bg-slate-800 hover:bg-black disabled:bg-slate-400 text-white py-4 rounded-2xl font-black text-lg transition-all shadow-xl flex justify-center items-center gap-3">
          <span v-if="isSaving" class="animate-spin text-xl">◌</span>
          {{ isSaving ? t('prod.saving') : t('prod.saveFull') }}
        </button>
      </div>
    </section>

    <div class="grid grid-cols-1 md:grid-cols-2 gap-6">
      <TransitionGroup name="list">
        <article v-for="p in products" :key="p.id" 
             class="bg-white p-5 sm:p-6 rounded-3xl border-2 border-slate-50 hover:border-indigo-100 shadow-sm hover:shadow-xl transition-all group relative overflow-hidden">
          
          <div class="flex justify-between items-start relative z-10">
            <div class="flex-1">
              <span class="text-[10px] font-black bg-slate-100 text-slate-500 px-2 py-1 rounded-md uppercase tracking-widest">{{ p.code }}</span>
              <h3 class="font-black text-xl text-slate-800 mt-2 line-clamp-1">{{ p.name }}</h3>
              <p class="text-2xl font-black text-emerald-500 mt-1">$ {{ p.price.toFixed(2) }}</p>
            </div>
            <button @click="deleteProduct(p.id)" 
                    class="p-3 text-slate-300 hover:text-red-500 hover:bg-red-50 rounded-2xl transition-all">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-6 w-6" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
              </svg>
            </button>
          </div>

          <div class="mt-6 space-y-3 relative z-10">
            <h4 class="text-[10px] font-black text-slate-400 uppercase tracking-widest">{{ t('prod.bomTitle') }}</h4>
            <div class="flex flex-wrap gap-2">
              <span v-for="c in p.compositions" :key="c.id" 
                    class="text-[11px] font-bold bg-slate-50 text-slate-600 border border-slate-100 px-3 py-1.5 rounded-xl flex items-center gap-2">
                <span class="text-indigo-500">{{ c.quantityNeeded }} {{ getMaterialUnit(c.rawMaterial.id) }}</span> 
                <span class="truncate max-w-[80px] sm:max-w-none">{{ c.rawMaterial.name }}</span>
              </span>
            </div>
          </div>
          
          <div class="absolute -right-4 -bottom-4 text-slate-50 group-hover:text-indigo-50 transition-colors pointer-events-none opacity-50">
            <svg xmlns="http://www.w3.org/2000/svg" class="h-24 w-24" fill="currentColor" viewBox="0 0 24 24">
              <path d="M11 2v20c-5.07 0-9.05-3.9-9.18-8.47l-.02-.53h2c.13 3.37 3.14 6 6.7 6.5l.5.03V4.47l-.5.03c-3.56.5-6.57 3.13-6.7 6.5h-2c.13-4.57 4.11-8.47 9.18-8.47zM13 2c5.07 0 9.05 3.9 9.18 8.47l.02.53h-2c-.13-3.37-3.14-6-6.7-6.5l-.5-.03v15.06l.5-.03c3.56-.5 6.57-3.13 6.7-6.5h2c-.13 4.57-4.11 8.47-9.18 8.47V2z"/>
            </svg>
          </div>
        </article>
      </TransitionGroup>

      <div v-if="products.length === 0" class="col-span-full py-20 text-center space-y-4 bg-white rounded-3xl border-4 border-dashed border-slate-100">
        <div class="text-5xl">📦</div>
        <p class="text-slate-400 font-bold">{{ t('prod.empty') }}</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted, onActivated } from 'vue'
import api from '../services/api' 
import { useI18n } from 'vue-i18n'
import Swal from 'sweetalert2'

const { t } = useI18n()
const products = ref([])
const materials = ref([])
const isSaving = ref(false)

const newProduct = ref({
  code: '',
  name: '',
  price: 0,
  compositions: []
})

const selectedMaterialId = ref('')
const selectedQty = ref(null)

const fetchData = async () => {
  try {
    const [resProd, resMat] = await Promise.all([
      api.get('/products'),
      api.get('/materials')
    ])
    products.value = resProd.data
    materials.value = resMat.data
  } catch (error) {
    console.error("Fetch error:", error)
  }
}

const addIngredient = () => {
  if (!selectedMaterialId.value || !selectedQty.value || selectedQty.value <= 0) return
  
  const exists = newProduct.value.compositions.some(c => c.rawMaterial.id === selectedMaterialId.value)
  if (exists) {
    Swal.fire({
      title: t('common.attention'),
      text: t('prod.alertExists'),
      icon: 'info',
      confirmButtonColor: '#4f46e5'
    })
    return
  }
  
  newProduct.value.compositions.push({
    quantityNeeded: selectedQty.value,
    rawMaterial: { id: selectedMaterialId.value }
  })
  
  selectedMaterialId.value = ''
  selectedQty.value = null
}

const removeIngredient = (index) => {
  newProduct.value.compositions.splice(index, 1)
}

const getMaterialName = (id) => {
  const mat = materials.value.find(m => m.id === id)
  return mat ? mat.name : 'Unknown'
}

const getMaterialUnit = (id) => {
  const mat = materials.value.find(m => m.id === id)
  return mat && mat.unit ? mat.unit : 'un'
}

const saveProduct = async () => {
  if (!newProduct.value.code || !newProduct.value.name || newProduct.value.compositions.length === 0) {
    Swal.fire({
      title: t('common.attention'),
      text: t('prod.alertIncomplete'),
      icon: 'warning',
      confirmButtonColor: '#4f46e5'
    })
    return
  }
  
  isSaving.value = true
  try {
    await api.post('/products', newProduct.value)
    newProduct.value = { code: '', name: '', price: 0, compositions: [] }
    await fetchData()
    
    Swal.fire({
      title: t('common.success'),
      icon: 'success',
      timer: 1500,
      showConfirmButton: false
    })
  } catch (error) {
    Swal.fire({
      title: t('common.error'),
      text: t('prod.alertError'),
      icon: 'error',
      confirmButtonColor: '#4f46e5'
    })
  } finally {
    isSaving.value = false
  }
}

const deleteProduct = async (id) => {
  const result = await Swal.fire({
    title: t('prod.confirmDelete'),
    text: t('common.irreversible'),
    icon: 'warning',
    showCancelButton: true,
    confirmButtonColor: '#4f46e5',
    cancelButtonColor: '#ef4444',
    confirmButtonText: t('common.yes_delete'),
    cancelButtonText: t('common.cancel')
  })

  if (result.isConfirmed) {
    try {
      await api.delete(`/products/${id}`)
      await fetchData()
      Swal.fire({ icon: 'success', timer: 1000, showConfirmButton: false })
    } catch (error) {
      Swal.fire({ title: t('common.error'), icon: 'error' })
    }
  }
}

onMounted(fetchData)
onActivated(fetchData)
</script>

<style scoped>
.list-enter-active, .list-leave-active {
  transition: all 0.4s ease;
}
.list-enter-from, .list-leave-to {
  opacity: 0;
  transform: translateX(30px);
}
</style>