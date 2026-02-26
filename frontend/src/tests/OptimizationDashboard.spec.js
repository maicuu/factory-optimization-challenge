import { mount, flushPromises } from '@vue/test-utils'
import { describe, it, expect, vi, beforeEach } from 'vitest'
import OptimizationDashboard from '../components/OptimizationDashboard.vue'
import api from '../services/api'

vi.mock('../services/api')

describe('OptimizationDashboard.vue', () => {
  const globalMocks = {
    global: {
      mocks: {
        t: (msg) => msg
      }
    }
  }

  beforeEach(() => {
    vi.clearAllMocks()
  })

  it('should render empty state initially', () => {
    const wrapper = mount(OptimizationDashboard, globalMocks)
    
    expect(wrapper.find('button').text()).toContain('dash.optimizeBtn')
    expect(wrapper.text()).toContain('dash.empty')
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

    api.get.mockImplementation((url) => {
      if (url.includes('/optimization')) return Promise.resolve({ data: mockPlan })
      if (url.includes('/materials')) return Promise.resolve({ data: [] })
      if (url.includes('/products')) return Promise.resolve({ data: [] })
      return Promise.reject(new Error('Not found'))
    })

    const wrapper = mount(OptimizationDashboard, globalMocks)

    await wrapper.find('button').trigger('click')
    await flushPromises()

    expect(api.get).toHaveBeenCalledWith('/optimization')
    expect(wrapper.text()).not.toContain('dash.empty')
    expect(wrapper.text()).toContain('940.00')
    expect(wrapper.text()).toContain('Premium Cabinet')
    expect(wrapper.text()).toContain('Office Chair')
  })
})