<script setup lang="ts">
import { ref, onMounted } from "vue";
import { useRoute } from "vue-router";
import { apiService } from "@/services/apiService";

export interface Seat {
    row: number;
    column: number;
    occupied: boolean;
    suggested: boolean;
    selected: boolean;
}

const route = useRoute();

const flightId = ref(route.params.flightId);
const tickets = ref(Number(route.params.tickets));
const planeId = ref(Number(route.params.planeId));

const suggestionFilters = ref({
    nearWindow: route.query.nearWindow === "true",
    moreLegSpace: route.query.moreLegSpace === "true",
    nearExit: route.query.nearExit === "true",
    together: route.query.together === "true",
    tickets: tickets.value,
});

const seating = ref<Seat[][]>([]);
const success = ref(true);
const selectedSeats = ref<Seat[]>([]);

const fetchSeating = async () => {
    try {
        const response = await apiService.getSeatingSuggestion(
            planeId.value,
            suggestionFilters.value,
        );
        seating.value = response.seating;
        success.value = response.success;
        seating.value.forEach((row) =>
            row.forEach((seat) => {
                if (seat.selected) selectedSeats.value.push(seat);
            }),
        );
    } catch (error) {
        console.error("Error fetching seating suggestions", error);
    }
};

onMounted(fetchSeating);

const getColumnLetter = (index: number) => String.fromCharCode(65 + index);

const toggleSeatSelection = (seat: Seat) => {
    if (seat.occupied) return;
    if (seat.selected) {
        seat.selected = false;
        selectedSeats.value = selectedSeats.value.filter(
            (s) => !(s.row === seat.row && s.column === seat.column),
        );
    } else {
        if (selectedSeats.value.length < tickets.value) {
            seat.selected = true;
            selectedSeats.value.push(seat);
        }
    }
};
</script>

<template>
    <div class="seating-container">
        <div v-if="!success" class="error-message">
            Couldn’t find any suggestions.
        </div>

        <div v-if="seating.length > 0">
            <div class="column-letters">
                <span class="row-number-placeholder"></span>
                <template
                    v-for="(seat, colIndex) in seating[0]"
                    :key="'col-' + colIndex"
                >
                    <span
                        v-if="colIndex === Math.floor(seating[0].length / 2)"
                        class="aisle"
                    ></span>
                    <span class="column-letter">{{
                        getColumnLetter(colIndex)
                    }}</span>
                </template>
            </div>

            <div
                v-for="(row, rowIndex) in seating"
                :key="rowIndex"
                class="seat-row"
            >
                <span class="row-number">{{ rowIndex + 1 }}</span>
                <template v-for="(seat, colIndex) in row" :key="colIndex">
                    <div
                        v-if="colIndex === Math.floor(row.length / 2)"
                        class="aisle"
                    ></div>
                    <div
                        class="seat"
                        :class="{
                            occupied: seat.occupied,
                            suggested: seat.suggested,
                            free: !seat.occupied && !seat.suggested,
                            selected: seat.selected,
                        }"
                        @click="toggleSeatSelection(seat)"
                    ></div>
                </template>
            </div>
        </div>

        <div class="selected-seats">
            <h3>Selected Seats:</h3>
            <span v-if="selectedSeats.length === 0">None</span>
            <ul>
                <li
                    v-for="seat in selectedSeats"
                    :key="`${seat.row}-${seat.column}`"
                >
                    Row {{ seat.row + 1 }}, {{ getColumnLetter(seat.column) }}
                </li>
            </ul>
        </div>

        <div
            class="purchase-button-container"
            v-if="selectedSeats.length === tickets"
        >
            <router-link
                :to="{
                    name: 'purchased',
                    query: {
                        seats: encodeURIComponent(
                            JSON.stringify(selectedSeats),
                        ),
                    },
                }"
            >
                <button>Complete Purchase</button>
            </router-link>
        </div>
    </div>
</template>

<style scoped>
.seating-container {
    max-width: 900px;
    margin: 20px auto;
    padding: 20px;
    background-color: #ffffff;
    border-radius: 6px;
    box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
    text-align: center;
}

.column-letters {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 10px;
}

.row-number-placeholder,
.row-number {
    width: 30px;
    text-align: center;
    font-weight: bold;
}

.column-letter {
    width: 30px;
    text-align: center;
    font-weight: bold;
}

.aisle {
    width: 20px;
}

.seat-row {
    display: flex;
    align-items: center;
    justify-content: center;
    margin-bottom: 5px;
}

.seat {
    width: 30px;
    height: 30px;
    margin: 2px;
    border: 1px solid #ccc;
    border-radius: 4px;
    cursor: pointer;
    transition: background-color 0.3s ease;
}

.seat.free {
    background-color: #f9f9f9;
}

.seat.occupied {
    background-color: #d9534f;
    cursor: not-allowed;
}

.seat.suggested {
    background-color: #5bc0de;
}

.seat.selected {
    background-color: #007bff;
}

.error-message {
    color: #a94442;
    background-color: #f2dede;
    border: 1px solid #ebccd1;
    border-radius: 4px;
    padding: 10px;
    margin-bottom: 20px;
}

.selected-seats {
    margin-top: 20px;
    padding: 10px;
    background-color: #f9f9f9;
    border: 1px solid #eee;
    border-radius: 4px;
}

.selected-seats h3 {
    margin-top: 0;
    font-size: 16px;
}

.selected-seats ul {
    list-style: none;
    padding: 0;
}

.selected-seats li {
    font-size: 14px;
}

.purchase-button-container {
    margin-top: 20px;
    text-align: right;
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
