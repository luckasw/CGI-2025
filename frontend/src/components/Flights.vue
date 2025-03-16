<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { apiService } from "../services/apiService";

interface Flight {
    id: number;
    departureTime: string;
    arrivalTime: string;
    origin: string;
    destination: string;
    price: number;
    planeId: string;
}

const flights = ref<Flight[]>([]);
const loading = ref(true);
const filters = ref({
    destination: "",
    date: "",
    minFlightTime: "",
    maxFlightTime: "",
    minPrice: "",
    maxPrice: "",
});

const fetchFlights = async () => {
    try {
        const data: Flight[] = await apiService.getFlights();
        flights.value = data;
    } catch (error) {
        console.error("Error fetching flights:", error);
    } finally {
        loading.value = false;
    }
};

const filteredFlights = computed(() => {
    const {
        destination,
        date,
        minFlightTime,
        maxFlightTime,
        minPrice,
        maxPrice,
    } = filters.value;
    return flights.value.filter((flight) => {
        const matchesDestination =
            !destination ||
            flight.destination
                .toLowerCase()
                .includes(destination.toLowerCase());

        const matchesDate = !date || flight.departureTime.startsWith(date);

        const flightTime =
            (new Date(flight.arrivalTime).getTime() -
                new Date(flight.departureTime).getTime()) /
            (1000 * 60);
        const matchesFlightTime =
            (!minFlightTime || flightTime >= Number(minFlightTime)) &&
            (!maxFlightTime || flightTime <= Number(maxFlightTime));

        const matchesPrice =
            (!minPrice || flight.price >= Number(minPrice)) &&
            (!maxPrice || flight.price <= Number(maxPrice));

        return (
            matchesDestination &&
            matchesDate &&
            matchesFlightTime &&
            matchesPrice
        );
    });
});

const parseDate = (timestamp: string): string =>
    new Date(timestamp).toLocaleDateString();

const parseTime = (timestamp: string): string =>
    new Date(timestamp).toLocaleTimeString();

onMounted(fetchFlights);
</script>

<template>
    <div class="flights">
        <div class="flights-container">
            <div class="filters">
                <input
                    type="text"
                    v-model="filters.destination"
                    placeholder="Destination"
                />
                <label for="date-input">
                    Departure Date
                    <input
                        type="date"
                        v-model="filters.date"
                        placeholder="Date"
                        id="date-input"
                    />
                </label>
                <input
                    type="number"
                    v-model="filters.minFlightTime"
                    placeholder="Min flight time (minutes)"
                />
                <input
                    type="number"
                    v-model="filters.maxFlightTime"
                    placeholder="Max flight time (minutes)"
                />
                <input
                    type="number"
                    v-model="filters.minPrice"
                    placeholder="Min Price"
                />
                <input
                    type="number"
                    v-model="filters.maxPrice"
                    placeholder="Max Price"
                />
            </div>

            <div class="flights-list">
                <div v-if="loading">
                    <p>Loading flights...</p>
                </div>
                <div v-else>
                    <div v-if="filteredFlights.length">
                        <div
                            v-for="flight in filteredFlights"
                            :key="flight.id"
                            class="flight-card"
                        >
                            <p>
                                <strong>Date:</strong>
                                {{ parseDate(flight.departureTime) }} -
                                {{ parseDate(flight.arrivalTime) }}
                            </p>
                            <p>
                                <strong>Time:</strong>
                                {{ parseTime(flight.departureTime) }} -
                                {{ parseTime(flight.arrivalTime) }}
                            </p>
                            <p>
                                <strong>Origin:</strong> {{ flight.origin }} |
                                <strong>Destination:</strong>
                                {{ flight.destination }}
                            </p>
                            <p><strong>Price:</strong> ${{ flight.price }}</p>
                            <router-link
                                :to="{
                                    name: 'flight-details',
                                    params: { id: flight.id },
                                }"
                            >
                                <button>Book Flight</button>
                            </router-link>
                        </div>
                    </div>
                    <div v-else>
                        <p>No flights match your filters.</p>
                    </div>
                </div>
            </div>
        </div>
    </div>
</template>

<style scoped>
.flights {
    max-width: 900px;
    margin: 20px auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 6px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.flights-container {
    display: flex;
    gap: 20px;
}

.filters {
    flex: 0 0 200px;
    display: flex;
    flex-direction: column;
    gap: 10px;
}

.filters input {
    padding: 6px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.flights-list {
    flex: 1;
}

.flight-card {
    border: 1px solid #eee;
    border-radius: 4px;
    padding: 12px;
    margin-bottom: 15px;
    background-color: #f9f9f9;
}

.flight-card p {
    margin: 5px 0;
    font-size: 14px;
}

button {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 8px 12px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

button:hover {
    background-color: #0056b3;
}
</style>
