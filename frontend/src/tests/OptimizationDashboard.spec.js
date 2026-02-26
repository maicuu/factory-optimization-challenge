import { mount, flushPromises } from '@vue/test-utils'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import OptimizationDashboard from '../components/OptimizationDashboard.vue'
import api from '../services/api'
import { i18n } from '../i18n' 

vi.mock('../services/api')

describe('OptimizationDashboard.vue', () => {
  
  const mountOptions = {
    global: {
      plugins: [i18n]
    }
  }

  beforeEach(() => {
    vi.clearAllMocks()
   
    i18n.global.locale.value = 'en'
  })

  it('should render empty state initially', () => {
    const wrapper = mount(OptimizationDashboard, mountOptions)
    
   
    expect(wrapper.find('button').text()).toContain(i18n.global.t('dash.optimizeBtn'))
    expect(wrapper.text()).toContain(i18n.global.t('dash.empty'))
    expect(wrapper.find('.bg-emerald-500').exists()).toBe(false)
  })

  it('should fetch and render optimization results', async () => {
    const mockPlan = {
      maxTotalValue: 940.00,
      items: [
        { productCode: 'PRD-C', productName: 'Premium Cabinet', quantity: 2, totalValue: 700.00 },
        { productCode: 'PRD-B', productName: 'Office Chair', quantity: 3, totalValue: 240.00 }
      ]
    }

    // Mock das chamadas de API
    api.get.mockImplementation((url) => {
      if (url.includes('/optimization')) return Promise.resolve({ data: mockPlan })
      if (url.includes('/materials')) return Promise.resolve({ data: [] })
      if (url.includes('/products')) return Promise.resolve({ data: [] })
      return Promise.reject(new Error('Not found'))
    })

    const wrapper = mount(OptimizationDashboard, mountOptions)

    // Clica no botão de otimizar
    await wrapper.find('button').trigger('click')
    await flushPromises()

    // Validações
    expect(api.get).toHaveBeenCalledWith('/optimization')
    expect(wrapper.text()).not.toContain(i18n.global.t('dash.empty'))
    
    
    expect(wrapper.text()).toContain('940')
    expect(wrapper.text()).toContain('Premium Cabinet')
    expect(wrapper.text()).toContain('Office Chair')
  })
})