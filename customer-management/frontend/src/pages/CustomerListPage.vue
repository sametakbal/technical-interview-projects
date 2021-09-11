<template>
  <div class="mt-4">
    <b-row>
      <b-col cols="3">
        <b-form-select v-model="selectedCity" :options="cities" class="form-select"></b-form-select>
        <b-form-select v-model="selectedDistrict" :options="districts" class="form-select mt-3"></b-form-select>
        <br>
        <div class="d-grid gap-2">
          <b-button variant="primary" @click.stop="applyFilter" :disabled="filterButtonIsDisable()">Apply Filter</b-button>
        </div>
      </b-col>
      <b-col cols="9">
          <div class="d-flex justify-content-center">
              <div style="width: 100%">
                <b-form-input v-model="searchTerm"
                              placeholder="First Name, Last Name, ID Number, Email or Phone Search"></b-form-input>
              </div>
              <div class="d-flex flex-row">
                <b-button variant="outline-primary" class="my-2 my-sm-0" @click.stop="search" type="submit">Search
                </b-button>
                <b-button v-if="selectedCity!== null || searchTerm.length >0" variant="outline-primary" class="my-2 my-sm-0" @click.stop="loadCustomers()" type="submit">Clear
                </b-button>
              </div>
          </div>
        <div v-if="loading" class="d-flex justify-content-center" style="margin-top: 25%">
          <b-spinner variant="primary" style="width: 10rem; height: 10rem;"></b-spinner>
        </div>
        <div v-else-if="customers.length === 0" class="d-flex justify-content-center mt-5">
          <h5>Don't have an any customer. Can create
            <b-link to="/customer">here</b-link>
          </h5>
        </div>
        <b-table v-else hover :items="customers" :fields="fields">
          <template v-slot:cell(firstName)="row">
            <b-link :to="'/customer/'+row.item.idNumber">
              {{ row.item.firstName }}
            </b-link>
          </template>
          <template v-slot:cell(city)="row">
            {{ row.item.city.city }}
          </template>
          <template v-slot:cell(district)="row">
            {{ row.item.district.district }}
          </template>
          <template v-slot:cell(delete)="row">
            <b-button variant="danger" @click.stop="setTempUser(row.item)" v-b-modal.modal-1>
              <b-icon icon="trash"></b-icon>
            </b-button>
          </template>
        </b-table>
      </b-col>
    </b-row>
    <b-modal id="modal-1" title="Are you sure?">
      <p v-if="tempCustomer !== null" class="my-4">Are you sure you want to delete the user named {{tempCustomer.firstName}} {{tempCustomer.lastName}} ?</p>
      <template #modal-footer>
        <b-button variant="danger" @click="deleteCustomer()">Yes</b-button>
      </template>
    </b-modal>
  </div>
</template>
<script>
import axios from "axios";

export default {
  watch:{
    selectedCity: function () {
      this.selectedDistrict = null;
      this.getDistricts();
    },
  },
  methods: {
    filterButtonIsDisable(){
      return this.selectedCity === null && this.selectedDistrict === null;
    },
    loadCustomers() {
      this.searchTerm = '';
      this.selectedDistrict = null;
      this.selectedCity = null;
      axios.get('/customers')
          .then((res) => {
            this.customers = res.data;
            this.loading = false;
          })
          .catch((error) => {
            console.log(error)
          });
    },
    search() {
      this.loading = true;
      axios.get(`/customers/search?searchTerm=${this.searchTerm}`)
          .then((res) => {
            this.customers = res.data;
            this.loading = false;
          })
          .catch((error) => {
            console.log(error)
          });
    },
    setTempUser(customer){
      this.tempCustomer = customer;
    },
    deleteCustomer(){
      axios.delete('/customers/'+this.tempCustomer.customerId)
          .then((res)=>{
              console.log(res);
              this.loadCustomers();
          })
          .catch((err)=>{console.log(err)});
      this.$bvModal.hide('modal-1');
    },
    applyFilter() {
      let params = '?';
      if (this.searchTerm.length > 0) {
        params += 'searchTerm=' + this.searchTerm + '&';
      }
      if (this.selectedCity !== null){
        params += `cityId=${this.selectedCity}&`;
      }
      if (this.selectedDistrict !== null){
        params += `districtId=${this.selectedDistrict}`;
      }
      this.loading = true;
      axios.get(`/customers/search${params}`)
          .then((res) => {
            this.customers = res.data;
            this.loading = false;
          })
          .catch((error) => {
            console.log(error)
          });
    },
    getCities(){
      this.selectedDistrict = null;
      axios.get('/cities')
          .then((res)=>{
            this.cities.push({ value: null, text: 'Select City' });
            for (let i = 0; i < res.data.length; i++) {
              this.cities.push({value:res.data[i].cityId,text: res.data[i].city});
            }
          })
          .catch((err)=>{console.log(err)});
    },
    getDistricts(){
      axios.get('/districts?cityId='+this.selectedCity)
          .then((res)=>{
            this.districts = [];
            this.districts.push({ value: null, text: 'Select District' });
            for (let i = 0; i < res.data.length; i++) {
              this.districts.push({value:res.data[i].districtId,text: res.data[i].district});
            }
          })
          .catch((err)=>{console.log(err)});
    },
  },
  data() {
    return {
      tempCustomer: null,
      selectedCity: null,
      selectedDistrict: null,
      searchTerm: '',
      loading: true,
      customers: [],
      fields: [
        {
          key: 'idNumber',
          sortable: true,
          label: 'ID Number'
        },
        {
          key: 'firstName',
          sortable: true,
          label: 'First Name',
        },
        {
          key: 'lastName',
          label: 'Last Name',
          sortable: true
        },
        {
          key: 'email',
          label: 'Email',
          sortable: true
        },
        {
          key: 'phone',
          label: 'Phone',
          sortable: true
        },
        {
          key: 'city',
          label: 'City',
          sortable: true
        },
        {
          key: 'district',
          label: 'District',
          sortable: true
        },
        {
          key: 'delete',
          label: ''
        },
      ],
      cities: [],
      districts: [{ value: null, text: 'Before Select City' },]
    }
  },
  created() {
    this.loadCustomers();
    this.getCities();
  }
}
</script>