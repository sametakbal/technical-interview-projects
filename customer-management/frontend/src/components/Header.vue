<template>
  <div>
    <b-navbar type="light" variant="light">
      <b-navbar-nav>
        <b-nav-item :active="$route.path === 'customers'" to="/customers">Customers</b-nav-item>
        <b-nav-item :active="$route.path === '/customer'" to="/customer">Create Customer</b-nav-item>
      </b-navbar-nav>
    </b-navbar>
    <router-view></router-view>
  </div>
</template>
<script>
export default {};
</script>