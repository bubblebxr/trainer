import { createRouter, createWebHashHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import ticketComponent from '../components/bookTicket.vue'
import hotelComponent from '../components/bookHotel.vue'
import foodComponent from '../components/bookFood.vue'
import ordersComponent from '../components/myOrders.vue'
import ticketDetail from '../components/ticketDetail.vue'
const routes = [
  {
    path: '/', 
    redirect: '/home'
  },
  {
    path: '/home',
    name: 'home',
    component: HomeView,
    children: [ 
      {
        path: 'ticket',
        component: ticketComponent
      },
      {
        path: 'hotel',
        component: hotelComponent
      },
      {
        path: 'dining',
        component: foodComponent
      },
      {
        path: 'orders',
        component: ordersComponent
      },
      {
        path: 'ticketDetail',
        name: 'detail',
        component: ticketDetail,
      },
    ]
  },
]

const router = createRouter({
  history: createWebHashHistory(),
  routes
})

export default router
