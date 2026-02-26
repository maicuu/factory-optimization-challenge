<script setup>
import { useI18n } from 'vue-i18n'
const { t, locale } = useI18n()

const toggleLanguage = () => {
  locale.value = locale.value === 'en' ? 'pt' : 'en'
}
</script>

<template>
  <div class="min-h-screen bg-slate-50 font-sans text-slate-900">
    <header class="bg-white border-b border-slate-200 px-8 py-4 flex justify-between items-center sticky top-0 z-50 shadow-sm">
      <div class="flex items-center gap-8">
        <section>
          <h1 class="text-xl font-black text-slate-800 tracking-tight">{{ t('title') }}</h1>
          <p class="text-xs font-bold text-indigo-500 uppercase tracking-widest">{{ t('subtitle') }}</p>
        </section>
        
        <nav class="hidden md:flex gap-2 ml-8">
          <router-link to="/" 
            class="text-sm font-bold text-slate-500 hover:text-indigo-600 px-4 py-2 rounded-xl transition-colors"
            active-class="bg-indigo-50 text-indigo-600">
            {{ t('nav.dashboard') }}
          </router-link>
          
          <router-link to="/materials" 
            class="text-sm font-bold text-slate-500 hover:text-indigo-600 px-4 py-2 rounded-xl transition-colors"
            active-class="bg-indigo-50 text-indigo-600">
            {{ t('nav.materials') }}
          </router-link>
          
          <router-link to="/products" 
            class="text-sm font-bold text-slate-500 hover:text-indigo-600 px-4 py-2 rounded-xl transition-colors"
            active-class="bg-indigo-50 text-indigo-600">
            {{ t('nav.products') }}
          </router-link>
        </nav>
      </div>
      
      <button @click="toggleLanguage" 
              class="text-xs font-bold uppercase tracking-wider text-slate-500 hover:text-indigo-600 transition bg-slate-100 hover:bg-indigo-50 px-4 py-2 rounded-xl">
        {{ t('switchLang') }}
      </button>
    </header>

    <main class="py-8 container mx-auto">
      <router-view v-slot="{ Component }">
        <keep-alive>
          <component :is="Component" />
        </keep-alive>
      </router-view>
    </main>
  </div>
</template>

<style>
</style>