<script setup lang="ts">
import { ref, computed, onMounted } from "vue";
import { useRoute } from "vue-router";
import { apiService } from "@/services/apiService";

interface Flight {
    id: number;
    departureTime: string;
    arrivalTime: string;
    origin: string;
    destination: string;
    price: number;
    planeId: string;
}

const route = useRoute();
const flightId = route.params.id as string;

const flight = ref<Flight | null>(null);
const numTickets = ref(1);

const suggestionFilters = ref({
    nearWindow: false,
    moreLegSpace: false,
    nearExit: false,
    together: false,
});

const fetchFlight = async () => {
    try {
        const response = await apiService.getFlightById(flightId);
        flight.value = response;
    } catch (error) {
        console.error("Error fetching flight:", error);
    }
};

const totalPrice = computed(() =>
    flight.value ? flight.value.price * numTickets.value : 0,
);

const bookingUrl = computed(() =>
    flight.value
        ? {
              path: `/booking/${flight.value.id}/${flight.value.planeId}/${numTickets.value}`,
              query: {
                  nearWindow: suggestionFilters.value.nearWindow,
                  moreLegSpace: suggestionFilters.value.moreLegSpace,
                  nearExit: suggestionFilters.value.nearExit,
                  together: suggestionFilters.value.together,
              },
          }
        : {},
);

const parseDate = (timestamp: string) =>
    new Date(timestamp).toLocaleDateString();

onMounted(fetchFlight);
</script>

<template>
    <div class="flight-details">
        <div v-if="flight" class="flight-card">
            <h2>{{ flight.origin }} - {{ flight.destination }}</h2>
            <p>
                <strong>Departure:</strong>
                {{ parseDate(flight.departureTime) }}
            </p>
            <p>
                <strong>Arrival:</strong>
                {{ parseDate(flight.arrivalTime) }}
            </p>
            <p><strong>Price per Ticket:</strong> ${{ flight.price }}</p>

            <div class="ticket-section">
                <label for="numTickets">Number of Tickets:</label>
                <input
                    id="numTickets"
                    type="number"
                    v-model.number="numTickets"
                    min="1"
                    required
                />
            </div>

            <p><strong>Total Price:</strong> ${{ totalPrice }}</p>

            <div class="seat-filters">
                <h3>Seat Preferences</h3>
                <label>
                    <input
                        type="checkbox"
                        v-model="suggestionFilters.nearWindow"
                    />
                    Near Window
                </label>
                <label>
                    <input
                        type="checkbox"
                        v-model="suggestionFilters.moreLegSpace"
                    />
                    More Leg Space
                </label>
                <label>
                    <input
                        type="checkbox"
                        v-model="suggestionFilters.nearExit"
                    />
                    Near Exit
                </label>
                <label>
                    <input
                        type="checkbox"
                        v-model="suggestionFilters.together"
                    />
                    Seats Together
                </label>
            </div>

            <router-link :to="bookingUrl">
                <button>Book Now</button>
            </router-link>
        </div>

        <div v-else>
            <p>Loading flight details...</p>
        </div>
    </div>
</template>

<style scoped>
.flight-details {
    max-width: 900px;
    margin: 20px auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 6px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
}

.flight-card {
    border: 1px solid #eee;
    border-radius: 4px;
    padding: 12px;
    background-color: #f9f9f9;
}

.flight-card h2 {
    margin-top: 0;
    font-size: 20px;
}

.flight-card p {
    margin: 5px 0;
    font-size: 14px;
}

.ticket-section {
    margin: 10px 0;
}

.ticket-section input {
    padding: 6px;
    font-size: 14px;
    border: 1px solid #ccc;
    border-radius: 4px;
}

.seat-filters {
    margin-top: 15px;
}

.seat-filters h3 {
    margin-bottom: 10px;
}

.seat-filters label {
    display: block;
    font-size: 14px;
    margin-bottom: 5px;
}

button {
    background-color: #007bff;
    color: #fff;
    border: none;
    padding: 8px 12px;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
    margin-top: 15px;
}

button:hover {
    background-color: #0056b3;
}
</style>
