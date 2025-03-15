import { createRouter, createWebHistory } from "vue-router";
import Flights from "../views/FlightsView.vue";

const router = createRouter({
  history: createWebHistory(import.meta.env.BASE_URL),
  routes: [
    {
      path: "/",
      name: "home",
      component: Flights,
    },
    {
      path: "/booking/:flightId/:planeId/:tickets",
      name: "booking",
      component: () => import("../views/BookingView.vue"),
    },
    {
      path: "/flight-details/:id",
      name: "flight-details",
      component: () => import("../views/FlightDetailsView.vue"),
    },
    {
      path: "/purchased",
      name: "purchased",
      component: () => import("../views/PurchasedView.vue"),
      props: true,
    },
  ],
});

export default router;
