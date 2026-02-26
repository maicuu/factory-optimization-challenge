<template>
  <div class="max-w-6xl mx-auto p-4 sm:p-6 space-y-8">
    
    <header class="flex flex-col md:flex-row justify-between items-center bg-white p-6 sm:p-8 rounded-3xl shadow-sm border border-slate-100 gap-6">
      <div class="text-center md:text-left">
        <h2 class="text-3xl font-black text-slate-800 tracking-tight">{{ t('dash.title') }}</h2>
        <p class="text-slate-500 font-medium">{{ t('dash.subtitle') }}</p>
      </div>
      <button 
        @click="calculateProfit" 
        :disabled="loading"
        class="w-full md:w-auto bg-indigo-600 hover:bg-indigo-700 text-white px-8 py-4 rounded-2xl font-black text-lg transition-all shadow-xl shadow-indigo-200 flex items-center justify-center gap-3 active:scale-95 disabled:bg-slate-300 disabled:shadow-none"
      >
        <span v-if="loading" class="animate-spin text-xl">◌</span>
        {{ loading ? t('dash.analyzing') : t('dash.optimizeBtn') }}
      </button>
    </header>

    <div v-if="plan" class="space-y-8">
      <section class="grid grid-cols-1 sm:grid-cols-2 md:grid-cols-3 gap-6">
        <div class="bg-white p-6 rounded-2xl border-b-8 border-emerald-500 shadow-sm">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest">{{ t('dash.maxProfit') }}</p>
          <p class="text-3xl font-black text-emerald-600 mt-2">$ {{ plan.maxTotalValue.toFixed(2) }}</p>
        </div>
        
        <div class="bg-white p-6 rounded-2xl border-b-8 border-indigo-500 shadow-sm">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest">{{ t('dash.utilization') }}</p>
          <p class="text-3xl font-black text-indigo-600 mt-2">{{ utilizationRate }}%</p>
        </div>

        <div class="bg-white p-6 rounded-2xl border-b-8 border-amber-500 shadow-sm sm:col-span-2 md:col-span-1">
          <p class="text-[10px] font-black text-slate-400 uppercase tracking-widest">{{ t('dash.bottleneck') }}</p>
          <p class="text-xl font-black text-amber-600 mt-2 truncate">{{ bottleneckResource }}</p>
        </div>
      </section>

      <div class="grid grid-cols-1 lg:grid-cols-2 gap-8">
        <section class="bg-white rounded-3xl shadow-sm border border-slate-100 overflow-hidden">
          <div class="bg-slate-50 px-6 py-4 border-b border-slate-100">
            <h3 class="font-bold text-slate-700 uppercase text-xs tracking-widest">{{ t('dash.optimalMix') }}</h3>
          </div>
          <div class="overflow-x-auto">
            <table class="w-full min-w-[500px]">
              <tbody class="divide-y divide-slate-50">
                <tr v-for="item in plan.items" :key="item.productCode" class="hover:bg-slate-50 transition-colors">
                  <td class="p-6">
                    <p class="text-xs font-bold text-indigo-500">{{ item.productCode }}</p>
                    <p class="font-bold text-slate-800">{{ item.productName }}</p>
                  </td>
                  <td class="p-6 text-right">
                    <p class="text-sm font-black text-slate-400">{{ t('dash.qty') }}</p>
                    <p class="text-xl font-black text-slate-700">{{ item.quantity }}</p>
                  </td>
                  <td class="p-6 text-right">
                    <p class="text-sm font-black text-slate-400">{{ t('dash.subtotal') }}</p>
                    <p class="text-xl font-black text-emerald-500">$ {{ item.totalValue.toFixed(2) }}</p>
                  </td>
                </tr>
              </tbody>
            </table>
          </div>
        </section>

        <section class="bg-slate-900 rounded-3xl shadow-xl p-6 sm:p-8 text-white relative overflow-hidden">
          <h3 class="text-xl font-bold mb-6 flex items-center gap-2">
            <span class="w-2 h-2 bg-amber-400 rounded-full animate-ping"></span>
            {{ t('dash.stockImpact') }}
          </h3>
          
          <div class="space-y-6">
            <div v-for="mat in materialUsage" :key="mat.name" class="space-y-2">
              <div class="flex justify-between text-sm font-bold">
                <span class="text-slate-400">{{ mat.name }}</span>
                <span :class="mat.usage > 95 ? 'text-amber-400' : 'text-slate-300'">
                  {{ mat.usage.toFixed(1) }}% {{ t('dash.used') }}
                </span>
              </div>
              <div class="w-full bg-slate-800 h-3 rounded-full overflow-hidden">
                <div 
                  class="h-full transition-all duration-1000" 
                  :class="mat.usage > 95 ? 'bg-amber-400 shadow-[0_0_10px_rgba(251,191,36,0.5)]' : 'bg-indigo-500'"
                  :style="{ width: mat.usage + '%' }"
                ></div>
              </div>
            </div>
          </div>

          <div class="mt-8 p-4 bg-white/5 rounded-2xl border border-white/10 text-sm italic text-slate-400">
            {{ t('dash.tipPart1') }} <strong class="text-amber-400">{{ bottleneckResource }}</strong> {{ t('dash.tipPart2') }}
          </div>
        </section>
      </div>

      <section v-if="bottleneckInfo" class="bg-indigo-900 rounded-3xl shadow-2xl p-6 sm:p-8 text-white relative overflow-hidden">
        <div class="relative z-10 flex flex-col md:flex-row justify-between items-center gap-6">
          <div class="space-y-2 text-center md:text-left">
            <h3 class="text-2xl font-black flex items-center justify-center md:justify-start gap-2 text-indigo-100">
              <span class="text-3xl">🔮</span> {{ t('dash.whatIfTitle') }}
            </h3>
            <p class="text-indigo-300 font-medium">
              {{ t('dash.whatIfDesc1') }} <strong class="text-amber-400">{{ bottleneckInfo.name }}</strong> {{ t('dash.whatIfDesc2') }} 
              (+{{ bottleneckInfo.bonusAmount.toFixed(1) }} {{ t('dash.units') }})?
            </p>
          </div>

          <button 
            @click="runSimulation" 
            :disabled="simulating"
            class="w-full md:w-auto bg-amber-500 hover:bg-amber-400 text-slate-900 px-8 py-4 rounded-2xl font-black text-lg transition-all shadow-lg active:scale-95"
          >
            {{ simulating ? t('dash.calc') : t('dash.simBtn') }}
          </button>
        </div>

        <div v-if="simulatedPlan" class="relative z-10 mt-8 grid grid-cols-1 sm:grid-cols-2 gap-4 border-t border-indigo-700/50 pt-8">
          <div class="bg-indigo-950/50 p-6 rounded-2xl border border-indigo-500/30">
            <p class="text-xs font-bold text-indigo-400 uppercase tracking-widest text-center sm:text-left">{{ t('dash.originalProfit') }}</p>
            <p class="text-3xl font-black mt-1 text-center sm:text-left">$ {{ plan.maxTotalValue.toFixed(2) }}</p>
          </div>
          <div class="bg-gradient-to-br from-emerald-500/20 to-emerald-900/20 p-6 rounded-2xl border border-emerald-500/50">
            <p class="text-xs font-bold text-emerald-400 uppercase tracking-widest text-center sm:text-left">{{ t('dash.projectedProfit') }}</p>
            <div class="flex flex-col sm:flex-row items-center sm:items-end gap-3 mt-1 justify-center sm:justify-start text-emerald-400">
              <p class="text-4xl font-black">$ {{ simulatedPlan.maxTotalValue.toFixed(2) }}</p>
              <p class="text-sm font-bold text-emerald-300 mb-1 flex items-center">
                <svg class="w-4 h-4 mr-1" fill="currentColor" viewBox="0 0 20 20"><path fill-rule="evenodd" d="M12 7a1 1 0 110-2h5a1 1 0 011 1v5a1 1 0 11-2 0V8.414l-4.293 4.293a1 1 0 01-1.414 0L8 10.414l-4.293 4.293a1 1 0 01-1.414-1.414l5-5a1 1 0 011.414 0L10.586 10 14.172 6.414H12z" clip-rule="evenodd"></path></svg>
                (+ $ {{ (simulatedPlan.maxTotalValue - plan.maxTotalValue).toFixed(2) }})
              </p>
            </div>
          </div>
        </div>
      </section>
    </div>

    <div v-else class="py-20 text-center space-y-4 bg-white rounded-3xl border-4 border-dashed border-slate-100">
      <div class="text-6xl grayscale">📊</div>
      <p class="text-slate-400 font-bold text-lg">{{ t('dash.empty') }}</p>
    </div>
  </div>
</template>

<script setup>
import { ref, computed } from 'vue'
import api from '../services/api'
import { useI18n } from 'vue-i18n'
import Swal from 'sweetalert2'

const { t } = useI18n()
const plan = ref(null)
const simulatedPlan = ref(null) 
const loading = ref(false)
const simulating = ref(false)
const materials = ref([])
const products = ref([])

const calculateProfit = async () => {
  loading.value = true
  simulatedPlan.value = null 
  try {
    const [resPlan, resMat, resProd] = await Promise.all([
      api.get('/optimization'),
      api.get('/materials'),
      api.get('/products') 
    ])
    
    plan.value = resPlan.data
    materials.value = resMat.data
    products.value = resProd.data

    Swal.fire({
      toast: true,
      position: 'top-end',
      icon: 'success',
      title: t('common.success'),
      showConfirmButton: false,
      timer: 2000
    })
  } catch (error) {
    Swal.fire({
      title: t('common.error'),
      text: t('dash.error_msg'),
      icon: 'error',
      confirmButtonColor: '#4f46e5'
    })
  } finally {
    loading.value = false
  }
}

const materialUsage = computed(() => {
  if (!plan.value || !materials.value.length || !products.value.length) return []
  
  const usageMap = {}
  materials.value.forEach(m => usageMap[m.id] = 0)
  
  plan.value.items.forEach(planItem => {
    const product = products.value.find(p => p.code === planItem.productCode)
    if (product?.compositions) {
      product.compositions.forEach(comp => {
        const matId = comp.rawMaterial.id
        if (usageMap[matId] !== undefined) {
          usageMap[matId] += comp.quantityNeeded * planItem.quantity
        }
      })
    }
  })

  return materials.value.map(m => {
    const consumed = usageMap[m.id] || 0
    let usage = m.stockQuantity > 0 ? (consumed / m.stockQuantity) * 100 : 0
    return { 
      id: m.id, 
      name: m.name, 
      usage: usage > 100 ? 100 : usage, 
      consumed, 
      unit: m.unit 
    }
  })
})

const bottleneckInfo = computed(() => {
  if (!materialUsage.value.length) return null
  const critical = [...materialUsage.value].sort((a, b) => b.usage - a.usage)[0]
  const original = materials.value.find(m => m.id === critical.id)
  
  return original ? {
    id: original.id,
    name: original.name,
    bonusAmount: original.stockQuantity * 0.20
  } : null
})

const bottleneckResource = computed(() => bottleneckInfo.value?.name || 'N/A')

const utilizationRate = computed(() => {
  if (!materialUsage.value.length) return 0
  const avg = materialUsage.value.reduce((acc, curr) => acc + curr.usage, 0) / materialUsage.value.length
  return avg.toFixed(1)
})

const runSimulation = async () => {
  if (!bottleneckInfo.value) return
  simulating.value = true
  try {
    const payload = { [bottleneckInfo.value.id]: bottleneckInfo.value.bonusAmount }
    const { data } = await api.post('/optimization/simulate', payload)
    simulatedPlan.value = data
    
    Swal.fire({
      icon: 'success',
      title: t('dash.sim_success'),
      timer: 2000,
      showConfirmButton: false
    })
  } catch (error) {
    Swal.fire({
      title: t('common.error'),
      icon: 'error',
      confirmButtonColor: '#4f46e5'
    })
  } finally {
    simulating.value = false
  }
}
</script>