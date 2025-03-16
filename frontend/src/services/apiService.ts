import axios from "axios";

const API_BASE_URL = import.meta.env.VITE_API_BASE_URL;

const apiClient = axios.create({
  baseURL: API_BASE_URL,
  headers: {
    "Content-Type": "application/json",
  },
});

export const apiService = {
  getFlights: async () => {
    const response = await apiClient.get("/flights");
    return response.data;
  },
  getFlightById: async (id: string) => {
    const response = await apiClient.get(`/flights/${id}`);
    return response.data;
  },
  getSeatingSuggestion: async (planeId: number, suggestionFilters: any) => {
    const response = await apiClient.post(
      `/seats/${planeId}`,
      suggestionFilters,
    );
    return response.data;
  },
};
