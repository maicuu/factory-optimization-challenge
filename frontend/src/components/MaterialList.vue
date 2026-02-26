<template>
  <div class="max-w-5xl mx-auto p-4 sm:p-6 space-y-8">
    
    <header class="flex flex-col sm:flex-row justify-between items-start sm:items-end gap-4">
      <div>
        <h2 class="text-3xl font-black text-slate-800 tracking-tight">{{ t('mat.title') }}</h2>
        <p class="text-slate-500 font-medium">{{ t('mat.subtitle') }}</p>
      </div>
      <div class="text-left sm:text-right bg-indigo-50 sm:bg-transparent p-3 sm:p-0 rounded-xl w-full sm:w-auto">
        <span class="text-xs font-bold uppercase tracking-widest text-indigo-500">{{ t('mat.total') }}</span>
        <p class="text-2xl font-black text-slate-700">{{ materials.length }}</p>
      </div>
    </header>

    <section class="bg-white p-6 rounded-2xl shadow-xl shadow-slate-200/50 border border-slate-100 flex flex-col md:flex-row gap-4 items-end">
      <div class="flex-[1.5] w-full">
        <label class="block text-xs font-bold text-slate-500 ml-1 mb-1 uppercase">{{ t('mat.code') }}</label>
        <input v-model="newMaterial.code" :placeholder="t('mat.codePlaceholder')" 
               class="w-full border-2 border-slate-100 rounded-xl p-3 focus:border-indigo-500 outline-none bg-slate-50/50">
      </div>
      
      <div class="flex-[2.5] w-full">
        <label class="block text-xs font-bold text-slate-500 ml-1 mb-1 uppercase">{{ t('mat.name') }}</label>
        <input v-model="newMaterial.name" :placeholder="t('mat.namePlaceholder')" 
               class="w-full border-2 border-slate-100 rounded-xl p-3 focus:border-indigo-500 outline-none bg-slate-50/50">
      </div>

      <div class="w-full md:w-32">
        <label class="block text-xs font-bold text-slate-500 ml-1 mb-1 uppercase">Unit</label>
        <select v-model="newMaterial.unit" 
                class="w-full border-2 border-slate-100 rounded-xl p-3 focus:border-indigo-500 outline-none bg-slate-50/50 appearance-none">
          <option value="kg">kg</option>
          <option value="L">L</option>
          <option value="m">m</option>
          <option value="un">un</option>
        </select>
      </div>

      <div class="w-full md:w-32">
        <label class="block text-xs font-bold text-slate-500 ml-1 mb-1 uppercase">{{ t('mat.stock') }}</label>
        <input v-model.number="newMaterial.stockQuantity" type="number" step="0.01" min="0" 
               class="w-full border-2 border-slate-100 rounded-xl p-3 focus:border-indigo-500 outline-none bg-slate-50/50">
      </div>

      <button @click="saveMaterial" 
              class="w-full md:w-auto bg-indigo-600 hover:bg-indigo-700 text-white px-8 py-3 rounded-xl font-bold shadow-md transition-all active:scale-95 whitespace-nowrap">
        {{ t('mat.save') }}
      </button>
    </section>

    <div class="w-full">
      <div class="hidden md:block bg-white rounded-2xl shadow-sm border border-slate-100 overflow-hidden">
        <table class="w-full text-left border-collapse">
          <thead class="bg-slate-50 border-b border-slate-100">
            <tr>
              <th class="p-4 text-xs font-bold text-slate-500 uppercase tracking-widest">{{ t('mat.code') }}</th>
              <th class="p-4 text-xs font-bold text-slate-500 uppercase tracking-widest">{{ t('mat.name') }}</th>
              <th class="p-4 text-xs font-bold text-slate-500 uppercase tracking-widest">{{ t('mat.stock') }}</th>
              <th class="p-4 text-xs font-bold text-slate-500 uppercase tracking-widest">{{ t('mat.status') }}</th>
              <th class="p-4 text-center text-xs font-bold text-slate-500 uppercase tracking-widest">{{ t('mat.actions') }}</th>
            </tr>
          </thead>
          <tbody class="divide-y divide-slate-50">
            <tr v-for="m in materials" :key="m.id" class="hover:bg-slate-50 transition-colors group">
              <td class="p-4"><span class="text-xs font-mono bg-slate-100 text-slate-600 px-2 py-1 rounded-md">{{ m.code }}</span></td>
              <td class="p-4 font-bold text-slate-700">{{ m.name }}</td>
              <td class="p-4 font-black text-slate-600">
                {{ m.stockQuantity }} <span class="text-xs font-bold text-slate-400 ml-1">{{ m.unit || 'un' }}</span>
              </td>
              <td class="p-4">
                <span :class="m.stockQuantity < 15 ? 'bg-red-50 text-red-600 border-red-100' : 'bg-emerald-50 text-emerald-600 border-emerald-100'" 
                      class="inline-flex items-center gap-1.5 px-2.5 py-1 rounded-full text-xs font-bold border">
                  <span :class="m.stockQuantity < 15 ? 'bg-red-500 animate-pulse' : 'bg-emerald-500'" class="w-2 h-2 rounded-full"></span>
                  {{ m.stockQuantity < 15 ? t('mat.critical') : t('mat.good') }}
                </span>
              </td>
              <td class="p-4 text-center">
                <button @click="deleteMaterial(m.id)" 
                        class="text-slate-300 hover:text-red-500 p-2 opacity-100 md:opacity-0 md:group-hover:opacity-100 transition-all">
                  <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                    <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
                  </svg>
                </button>
              </td>
            </tr>
          </tbody>
        </table>
      </div>

      <div class="grid grid-cols-1 gap-4 md:hidden">
        <article v-for="m in materials" :key="m.id" class="bg-white p-5 rounded-2xl shadow-sm border border-slate-100 space-y-4">
          <div class="flex justify-between items-start">
            <div>
              <span class="text-[10px] font-mono bg-slate-100 text-slate-500 px-2 py-1 rounded uppercase tracking-widest">{{ m.code }}</span>
              <h3 class="text-lg font-bold text-slate-800 mt-1">{{ m.name }}</h3>
            </div>
            <button @click="deleteMaterial(m.id)" class="p-2 text-red-400 bg-red-50 rounded-xl">
              <svg xmlns="http://www.w3.org/2000/svg" class="h-5 w-5" fill="none" viewBox="0 0 24 24" stroke="currentColor">
                <path stroke-linecap="round" stroke-linejoin="round" stroke-width="2" d="M19 7l-.867 12.142A2 2 0 0116.138 21H7.862a2 2 0 01-1.995-1.858L5 7m5 4v6m4-6v6m1-10V4a1 1 0 00-1-1h-4a1 1 0 00-1 1v3M4 7h16" />
              </svg>
            </button>
          </div>
          <div class="flex justify-between items-center pt-2 border-t border-slate-50">
            <div>
              <p class="text-[10px] font-bold text-slate-400 uppercase">{{ t('mat.stock') }}</p>
              <p class="text-xl font-black text-slate-700">{{ m.stockQuantity }} <span class="text-sm text-slate-400">{{ m.unit || 'un' }}</span></p>
            </div>
            <span :class="m.stockQuantity < 15 ? 'bg-red-50 text-red-600' : 'bg-emerald-50 text-emerald-600'" class="px-3 py-1 rounded-full text-xs font-bold">
              {{ m.stockQuantity < 15 ? t('mat.critical') : t('mat.good') }}
            </span>
          </div>
        </article>
      </div>

      <div v-if="materials.length === 0" class="p-12 text-center text-slate-400 font-medium bg-white rounded-2xl border-2 border-dashed border-slate-100">
        {{ t('mat.empty') }}
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref, onMounted } from 'vue'
import api from '../services/api' 
import { useI18n } from 'vue-i18n'
import Swal from 'sweetalert2'

const { t } = useI18n()
const materials = ref([])
const newMaterial = ref({ code: '', name: '', stockQuantity: null, unit: 'un' })

const fetchMaterials = async () => {
  try {
    const { data } = await api.get('/materials')
    materials.value = data
  } catch (error) {
    console.error("Error fetching materials:", error)
  }
}

const saveMaterial = async () => {
  if (!newMaterial.value.code || !newMaterial.value.name) {
    Swal.fire({
      title: t('common.attention'),
      text: t('mat.validation_msg'),
      icon: 'warning',
      confirmButtonColor: '#4f46e5'
    })
    return
  }
  
  try {
    await api.post('/materials', newMaterial.value)
    newMaterial.value = { code: '', name: '', stockQuantity: null, unit: 'un' }
    await fetchMaterials()
    
    Swal.fire({
      title: t('common.success'),
      icon: 'success',
      timer: 1500,
      showConfirmButton: false
    })
  } catch (error) {
    Swal.fire({
      title: t('common.error'),
      text: t('mat.error_save'),
      icon: 'error',
      confirmButtonColor: '#4f46e5'
    })
  }
}

const deleteMaterial = async (id) => {
  const result = await Swal.fire({
    title: t('mat.confirmDelete'),
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
      await api.delete(`/materials/${id}`)
      await fetchMaterials()
      Swal.fire({ icon: 'success', timer: 1000, showConfirmButton: false })
    } catch (error) {
      Swal.fire({
        title: t('common.blocked'),
        text: t('mat.error_delete_in_use'),
        icon: 'error',
        confirmButtonColor: '#4f46e5'
      })
    }
  }
}

onMounted(fetchMaterials)
</script>