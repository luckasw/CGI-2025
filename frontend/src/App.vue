<script setup lang="ts">
import { RouterLink, RouterView } from "vue-router";
import router from "./router";
</script>

<template>
    <header>
        <h2>
            <router-link to="/">FLIGHTS</router-link>
        </h2>
    </header>
    <RouterView />
</template>

<style scoped>
header {
    max-width: 900px;
    margin: 20px auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 6px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

h2 {
    margin: 0;
    font-size: 24px;
    letter-spacing: 1px;
}

a {
    text-decoration: none;
    color: inherit;
    cursor: pointer;
}
</style>
