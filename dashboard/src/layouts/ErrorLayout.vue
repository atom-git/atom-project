<template>
  <a-layout :class="['atom-error-layout']">
    <RouterView></RouterView>
  </a-layout>
</template>

<script>
export default {
  name: 'ErrorLayout'
}
</script>
