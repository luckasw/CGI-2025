<script setup lang="ts">
import { ref } from "vue";
import { useRoute } from "vue-router";

interface SeatMinimal {
    row: number;
    column: number;
}

const route = useRoute();
const seats = ref<SeatMinimal[]>([]);

if (route.query.seats) {
    try {
        seats.value = JSON.parse(
            decodeURIComponent(route.query.seats as string),
        );
    } catch (error) {
        console.error("Error parsing seat data:", error);
    }
}

const getColumnLetter = (index: number): string =>
    String.fromCharCode(65 + index);
</script>

<template>
    <div class="purchased-view">
        <h1>Purchase completed</h1>
        <div v-if="seats.length">
            <ul>
                <li v-for="(seat, index) in seats" :key="index">
                    Row {{ seat.row + 1 }}, Column
                    {{ getColumnLetter(seat.column) }}
                </li>
            </ul>
        </div>
        <div v-else>No seat information available.</div>
        <router-link to="/">Back to Home</router-link>
    </div>
</template>

<style scoped>
.purchased-view {
    max-width: 800px;
    margin: 0 auto;
    padding: 1rem;
}
</style>
