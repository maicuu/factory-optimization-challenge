<script setup>
import { useI18n } from 'vue-i18n'


const { t, locale } = useI18n()


const toggleLanguage = () => {
  locale.value = locale.value === 'en' ? 'pt' : 'en'
}
</script>

<template>
  <div class="min-h-screen bg-slate-50 font-sans text-slate-900 selection:bg-indigo-100 selection:text-indigo-700">
    
    <header class="bg-white/80 backdrop-blur-md border-b border-slate-200 px-8 py-4 flex justify-between items-center sticky top-0 z-50 shadow-sm">
      <div class="flex items-center gap-8">
        <section>
          <h1 class="text-xl font-black text-slate-800 tracking-tight">
            {{ t('title') }}
          </h1>
          <p class="text-xs font-bold text-indigo-500 uppercase tracking-widest leading-none">
            {{ t('subtitle') }}
          </p>
        </section>
        
        <nav class="hidden md:flex gap-1 ml-8 bg-slate-100/50 p-1 rounded-2xl">
          <router-link to="/" 
            class="text-sm font-bold text-slate-500 hover:text-indigo-600 px-4 py-2 rounded-xl transition-all duration-200"
            active-class="bg-white text-indigo-600 shadow-sm">
            {{ t('nav.dashboard') }}
          </router-link>
          
          <router-link to="/materials" 
            class="text-sm font-bold text-slate-500 hover:text-indigo-600 px-4 py-2 rounded-xl transition-all duration-200"
            active-class="bg-white text-indigo-600 shadow-sm">
            {{ t('nav.materials') }}
          </router-link>
          
          <router-link to="/products" 
            class="text-sm font-bold text-slate-500 hover:text-indigo-600 px-4 py-2 rounded-xl transition-all duration-200"
            active-class="bg-white text-indigo-600 shadow-sm">
            {{ t('nav.products') }}
          </router-link>
        </nav>
      </div>
      
      <div class="flex items-center gap-4">
        <button @click="toggleLanguage" 
                class="text-[10px] font-black uppercase tracking-tighter text-slate-500 hover:text-indigo-600 transition bg-slate-100 hover:bg-white border border-transparent hover:border-slate-200 px-3 py-1.5 rounded-lg shadow-sm">
          {{ t('switchLang') }}
        </button>
      </div>
    </header>

    <main class="py-8 px-6 container mx-auto">
      <router-view v-slot="{ Component }">
        <transition name="fade" mode="out-in">
          <keep-alive>
            <component :is="Component" />
          </keep-alive>
        </transition>
      </router-view>
    </main>
  </div>
</template>

<style>

.fade-enter-active,
.fade-leave-active {
  transition: opacity 0.2s ease;
}

.fade-enter-from,
.fade-leave-to {
  opacity: 0;
}


html {
  scroll-behavior: smooth;
}
</style>