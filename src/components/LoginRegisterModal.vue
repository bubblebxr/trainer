<template>
  <div class="modal">
    <div class="modal-content">
      <!-- Login Form -->
      <div v-if="activeTab === 'login'">
        <h2>Login</h2>
        <form @submit.prevent="login">
          <input type="text" v-model="username" placeholder="Username">
          <input type="password" v-model="password" placeholder="Password">
          <button type="submit">Login</button>
        </form>
        <p @click="switchTab('register')">Don't have an account? Register here.</p>
      </div>

      <!-- Registration Form -->
      <div v-else>
        <h2>Register</h2>
        <form @submit.prevent="register">
          <input type="text" v-model="newUsername" placeholder="New Username">
          <input type="password" v-model="newPassword" placeholder="New Password">
          <button type="submit">Register</button>
        </form>
        <p @click="switchTab('login')">Already have an account? Login here.</p>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue';

const activeTab = ref('login');
const username = ref('');
const password = ref('');
const newUsername = ref('');
const newPassword = ref('');

const login = () => {
  // Handle login logic here
  console.log('Logging in with:', username.value, password.value);
  // Example: Use Vuex actions or emit event to parent component
};

const register = () => {
  // Handle register logic here
  console.log('Registering:', newUsername.value, newPassword.value);
  // Example: Use Vuex actions or emit event to parent component
};

const switchTab = (tab) => {
  activeTab.value = tab;
};
</script>

<style scoped>
/* Styles for your modal */
.modal {
  position: fixed;
  top: 0;
  left: 0;
  width: 100%;
  height: 100%;
  background: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
}

.modal-content {
  background: white;
  padding: 20px;
  border-radius: 5px;
  width: 300px;
}

form {
  display: flex;
  flex-direction: column;
  gap: 10px;
}

button {
  padding: 10px;
}
</style>
