import { defineConfig } from 'vite'
import vue from '@vitejs/plugin-vue'

export default defineConfig({
  plugins: [vue()],
  test: {
    environment: 'jsdom',
    globals: true,
    server: {
      deps: {
        inline: ['@exodus/bytes', 'html-encoding-sniffer']
      }
    },
    css: true
  }
})