import { createI18n } from 'vue-i18n'

const messages = {
  en: {
    title: 'Factory Optimization',
    subtitle: 'Industrial Intelligence',
    switchLang: '🇧🇷 Português',
    nav: { dashboard: 'Dashboard', materials: 'Materials', products: 'Products' },
    
    // --- COMMON / UI ---
    common: {
      success: 'Success!',
      error: 'Error',
      attention: 'Attention',
      cancel: 'Cancel',
      yes_delete: 'Yes, delete it!',
      irreversible: 'This action cannot be undone.',
      blocked: 'Operation Blocked'
    },

    // --- MATERIALS ---
    mat: {
      title: 'Inventory Management',
      subtitle: 'Track and manage raw materials',
      total: 'Total Materials',
      add: 'Add New Material',
      code: 'Code',
      codePlaceholder: 'Ex: RM-001',
      name: 'Material Name',
      namePlaceholder: 'Ex: Steel, Wood...',
      stock: 'Stock Qty',
      save: 'Save Material',
      empty: 'No materials found. Add your first raw material!',
      status: 'Status',
      actions: 'Actions',
      critical: 'Critical',
      good: 'Healthy',
      confirmDelete: 'Delete this material?',
      validation_msg: 'Please provide both code and name.',
      error_save: 'Could not save. Ensure the code is unique.',
      error_delete_in_use: 'This material is linked to a product recipe.'
    },

    // --- PRODUCTS ---
    prod: {
      title: 'Products Management',
      subtitle: 'Define products and production recipes (BOM)',
      total: 'Total Products',
      createTitle: 'Create New Product',
      code: 'Product Code',
      name: 'Product Name',
      namePlaceholder: 'Ex: Dining Table',
      price: 'Unit Price ($)',
      recipe: 'Product Composition (Recipe)',
      selectMaterial: 'Select a material',
      addMaterial: 'Add to Recipe',
      noIngredients: 'No ingredients added yet...',
      saving: 'Saving...',
      saveFull: 'Save Complete Product',
      bomTitle: 'Recipe / Bill of Materials',
      empty: 'No products found. Start by creating one!',
      confirmDelete: 'Delete this product?',
      alertExists: 'Material already in recipe.',
      alertIncomplete: 'Missing data or empty recipe.',
      alertError: 'Error saving product. Check if code is unique.'
    },

    // --- DASHBOARD ---
    dash: {
      title: 'Production Simulator',
      subtitle: 'Optimization for maximum profitability',
      optimizeBtn: 'Optimize Production 🚀',
      analyzing: 'Analyzing...',
      success: 'Analysis Complete!',
      error_msg: 'Failed to run optimization.',
      maxProfit: 'Max Possible Profit',
      utilization: 'Resource Utilization',
      bottleneck: 'Primary Bottleneck',
      optimalMix: 'Optimal Production Mix',
      qty: 'QTY',
      subtotal: 'SUBTOTAL',
      stockImpact: 'Stock Impact Analysis',
      used: 'used',
      tipPart1: 'Tip: Increasing stock of',
      tipPart2: 'could significantly improve profit.',
      whatIfTitle: 'What-If Analysis',
      whatIfDesc1: 'System identified',
      whatIfDesc2: 'as a bottleneck. What if we add',
      units: 'units',
      simBtn: 'Simulate +20% Scenario',
      sim_success: 'Simulation successful!',
      calc: 'Calculating...',
      originalProfit: 'Original Profit',
      projectedProfit: 'Projected Profit',
      empty: 'Run optimization to see current inventory insights.'
    }
  },

  pt: {
    title: 'Otimização de Fábrica',
    subtitle: 'Inteligência Industrial',
    switchLang: '🇺🇸 English',
    nav: { dashboard: 'Painel', materials: 'Insumos', products: 'Produtos' },

    common: {
      success: 'Sucesso!',
      error: 'Erro',
      attention: 'Atenção',
      cancel: 'Cancelar',
      yes_delete: 'Sim, excluir!',
      irreversible: 'Esta ação não pode ser desfeita.',
      blocked: 'Operação Bloqueada'
    },
    
    mat: {
      title: 'Gestão de Estoque',
      subtitle: 'Rastreie e gerencie matérias-primas',
      total: 'Total de Insumos',
      add: 'Novo Insumo',
      code: 'Código',
      codePlaceholder: 'Ex: RM-001',
      name: 'Nome do Insumo',
      namePlaceholder: 'Ex: Aço, Madeira...',
      stock: 'Qtd. Estoque',
      save: 'Salvar Insumo',
      empty: 'Nenhum insumo encontrado.',
      status: 'Status',
      actions: 'Ações',
      critical: 'Crítico',
      good: 'Saudável',
      confirmDelete: 'Excluir este insumo?',
      validation_msg: 'Preencha o código e o nome.',
      error_save: 'Erro ao salvar. Verifique se o código é único.',
      error_delete_in_use: 'Este insumo está sendo usado em uma receita.'
    },

    prod: {
      title: 'Gestão de Produtos',
      subtitle: 'Defina produtos e receitas (BOM)',
      total: 'Total de Produtos',
      createTitle: 'Criar Novo Produto',
      code: 'Código do Produto',
      name: 'Nome do Produto',
      namePlaceholder: 'Ex: Mesa de Jantar',
      price: 'Preço Unitário (R$)',
      recipe: 'Composição (Receita)',
      selectMaterial: 'Selecione um insumo',
      addMaterial: 'Adicionar à Receita',
      noIngredients: 'Nenhum insumo adicionado...',
      saving: 'Salvando...',
      saveFull: 'Salvar Produto Completo',
      bomTitle: 'Receita / Bill of Materials',
      empty: 'Nenhum produto encontrado.',
      confirmDelete: 'Excluir este produto?',
      alertExists: 'Insumo já está na receita.',
      alertIncomplete: 'Dados incompletos ou receita vazia.',
      alertError: 'Erro ao salvar produto.'
    },

    dash: {
      title: 'Simulador de Produção',
      subtitle: 'Maximice seu lucro com o estoque atual',
      optimizeBtn: 'Otimizar Produção 🚀',
      analyzing: 'Analisando...',
      success: 'Análise Concluída!',
      error_msg: 'Falha ao processar otimização.',
      maxProfit: 'Lucro Máximo Possível',
      utilization: 'Utilização de Recursos',
      bottleneck: 'Gargalo Principal',
      optimalMix: 'Mix de Produção Ideal',
      qty: 'QTD',
      subtotal: 'SUBTOTAL',
      stockImpact: 'Análise de Impacto',
      used: 'usado',
      tipPart1: 'Dica: Aumentar o estoque de',
      tipPart2: 'pode melhorar o lucro significativamente.',
      whatIfTitle: 'Análise Executiva "What-If"',
      whatIfDesc1: 'Identificamos',
      whatIfDesc2: 'como gargalo. E se comprarmos +20% de',
      units: 'unidades',
      simBtn: 'Simular Cenário +20%',
      sim_success: 'Simulação realizada!',
      calc: 'Calculando...',
      originalProfit: 'Lucro Original',
      projectedProfit: 'Lucro Projetado',
      empty: 'Clique em otimizar para ver os insights do inventário.'
    }
  }
}

export const i18n = createI18n({
  legacy: false, 
  locale: 'en', 
  fallbackLocale: 'en',
  messages,
})